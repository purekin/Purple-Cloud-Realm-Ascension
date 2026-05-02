package net.thejadeproject.ascension.clients.renderer.skills;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.thejadeproject.ascension.data_attachments.ModAttachments;
import net.thejadeproject.ascension.refactor_packages.paths.ModPaths;
import net.thejadeproject.ascension.refactor_packages.paths.PathData;
import net.thejadeproject.ascension.refactor_packages.registries.AscensionRegistries;
import net.thejadeproject.ascension.refactor_packages.skills.custom.ModSkills;
import net.thejadeproject.ascension.refactor_packages.techniques.custom.essence.BloodfeastSoulRefiningTechnique;
import org.joml.Matrix4f;

@OnlyIn(Dist.CLIENT)
public class BloodfeastAoeRenderer {

    // ── Geometry ──────────────────────────────────────────────────────────────
    private static final int   SEGMENTS    = 64;
    private static final float RING_WIDTH  = 0.20f;
    private static final float MIST_HEIGHT = 0.5f;

    // ── Colour ────────────────────────────────────────────────────────────────
    private static final float R = 0.75f;
    private static final float G = 0.02f;
    private static final float B = 0.04f;

    // ── Alpha ─────────────────────────────────────────────────────────────────
    private static final float RING_OUTER_ALPHA = 0.88f;
    private static final float MIST_BASE_ALPHA  = 0.40f;

    // ── Pulse ─────────────────────────────────────────────────────────────────
    private static final float PULSE_SPEED     = 1.2f;
    private static final float PULSE_AMPLITUDE = 0.12f;

    private long  lastNanos  = System.nanoTime();
    private float pulsePhase = 0.0f;

    @SubscribeEvent
    public void onRenderLevel(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_TRANSLUCENT_BLOCKS) return;

        Minecraft mc     = Minecraft.getInstance();
        Player    player = mc.player;
        if (player == null) return;

        // ── Gate 1: holding skill_cast ────────────────────────────────────────
        if (!player.hasData(ModAttachments.INPUT_STATES)) return;
        if (!player.getData(ModAttachments.INPUT_STATES).isHeld("skill_cast")) return;

        // ── Gate 2: active technique is Bloodfeast ────────────────────────────
        if (!player.hasData(ModAttachments.ENTITY_DATA)) return;
        var entityData = player.getData(ModAttachments.ENTITY_DATA);
        PathData pathData = entityData.getPathData(ModPaths.ESSENCE.getId());
        if (pathData == null || pathData.getLastUsedTechnique() == null) return;

        Object technique = AscensionRegistries.Techniques.TECHNIQUES_REGISTRY.get(
                pathData.getLastUsedTechnique()
        );
        if (!(technique instanceof BloodfeastSoulRefiningTechnique)) return;

        // ── Gate 3: skill is in loadout ───────────────────────────────────────
        if (!entityData.hasSkill(ModSkills.BLOODFEAST_BANQUET_SKILL.getId())) return;

        // ── Radius ────────────────────────────────────────────────────────────
        float radius = BloodfeastSoulRefiningTechnique.getRangeForRealm(pathData.getMajorRealm());

        // ── Pulse ─────────────────────────────────────────────────────────────
        long  now  = System.nanoTime();
        float dt   = (now - lastNanos) / 1_000_000_000.0f;
        lastNanos  = now;
        pulsePhase = (pulsePhase + PULSE_SPEED * dt) % ((float) (Math.PI * 2.0));
        float pulse = (float) Math.sin(pulsePhase) * PULSE_AMPLITUDE;

        // ── Camera-relative player feet position ──────────────────────────────
        Camera camera = mc.gameRenderer.getMainCamera();
        Vec3   cam    = camera.getPosition();

        float ox = (float) (player.getX() - cam.x);
        float oy = (float) (player.getY() - cam.y);
        float oz = (float) (player.getZ() - cam.z);

        // ── Build the combined model-view matrix ──────────────────────────────
        // event.getPoseStack() already holds the camera rotation transform.
        // We push, translate to the player's world position relative to camera,
        // then upload that as the model-view matrix RenderSystem will use.
        PoseStack ps = event.getPoseStack();
        ps.pushPose();
        ps.translate(ox, oy, oz);

        // Upload matrices — this is what was missing before.
        // RenderSystem.applyModelViewMatrix() tells the shader about our pose.
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        RenderSystem.applyModelViewMatrix();

        // Also set the projection matrix from the event so it matches the scene.
        RenderSystem.setProjectionMatrix(
                event.getProjectionMatrix(),
                VertexSorting.ORTHOGRAPHIC_Z
        );

        // ── GL state ──────────────────────────────────────────────────────────
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(
                org.lwjgl.opengl.GL11.GL_SRC_ALPHA,
                org.lwjgl.opengl.GL11.GL_ONE        // additive — makes it glow
        );
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.disableCull();

        // ── Get the matrix after our translate ────────────────────────────────
        Matrix4f mat = ps.last().pose();

        Tesselator tes = Tesselator.getInstance();

        drawFloorRing(tes, mat, radius, pulse);
        drawMistColumn(tes, mat, radius, pulse);

        // ── Restore ───────────────────────────────────────────────────────────
        RenderSystem.enableCull();
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableBlend();

        ps.popPose();

        // Re-upload the restored matrix so subsequent renderers see the right state
        RenderSystem.applyModelViewMatrix();
    }

    // ── Floor ring ────────────────────────────────────────────────────────────

    private void drawFloorRing(Tesselator tes, Matrix4f mat, float radius, float pulse) {
        float outerAlpha  = clamp(RING_OUTER_ALPHA + pulse);
        float innerRadius = Math.max(0f, radius - RING_WIDTH);

        BufferBuilder buf = tes.begin(
                VertexFormat.Mode.TRIANGLE_STRIP,
                DefaultVertexFormat.POSITION_COLOR
        );

        for (int i = 0; i <= SEGMENTS; i++) {
            double angle = (2.0 * Math.PI * i) / SEGMENTS;
            float  c     = (float) Math.cos(angle);
            float  s     = (float) Math.sin(angle);

            // inner vertex — transparent
            buf.addVertex(mat, c * innerRadius, 0f, s * innerRadius)
                    .setColor(R, G, B, 0f);

            // outer vertex — glowing
            buf.addVertex(mat, c * radius, 0f, s * radius)
                    .setColor(R, G, B, outerAlpha);
        }

        BufferUploader.drawWithShader(buf.build());
    }

    // ── Mist column ───────────────────────────────────────────────────────────

    private void drawMistColumn(Tesselator tes, Matrix4f mat, float radius, float pulse) {
        float bottomAlpha = clamp(MIST_BASE_ALPHA + pulse * 0.6f);

        BufferBuilder buf = tes.begin(
                VertexFormat.Mode.TRIANGLE_STRIP,
                DefaultVertexFormat.POSITION_COLOR
        );

        for (int i = 0; i <= SEGMENTS; i++) {
            double angle = (2.0 * Math.PI * i) / SEGMENTS;
            float  c     = (float) Math.cos(angle);
            float  s     = (float) Math.sin(angle);

            // bottom — coloured
            buf.addVertex(mat, c * radius, 0f, s * radius)
                    .setColor(R, G, B, bottomAlpha);

            // top — fully transparent, GPU lerps the gradient
            buf.addVertex(mat, c * radius, MIST_HEIGHT, s * radius)
                    .setColor(R, G, B, 0f);
        }

        BufferUploader.drawWithShader(buf.build());
    }

    // ── Util ──────────────────────────────────────────────────────────────────

    private static float clamp(float v) {
        return Math.max(0f, Math.min(1f, v));
    }
}