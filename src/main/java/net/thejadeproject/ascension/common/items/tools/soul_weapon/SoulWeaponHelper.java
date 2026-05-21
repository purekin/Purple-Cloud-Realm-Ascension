package net.thejadeproject.ascension.common.items.tools.soul_weapon;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.thejadeproject.ascension.common.items.ModItems;
import net.thejadeproject.ascension.common.items.data_components.ModDataComponents;
import net.thejadeproject.ascension.common.items.tools.data.soul_weapon.SoulWeaponComponent;
import net.thejadeproject.ascension.data_attachments.attachments.SoulWeaponData;
import net.thejadeproject.ascension.util.ModTags;

public final class SoulWeaponHelper {
    private SoulWeaponHelper() {}

    public static final float BASE_DAMAGE = 6.0F;

    public static boolean isSoulWeapon(ItemStack stack) {
        return !stack.isEmpty()
                && stack.is(ModItems.SOULBOUND_WEAPON.get())
                && stack.has(ModDataComponents.SOUL_WEAPON.get());
    }

    public static boolean isOwner(ItemStack stack, Player player) {
        SoulWeaponComponent component = stack.get(ModDataComponents.SOUL_WEAPON.get());
        return component != null && component.owner().equals(player.getUUID());
    }

    public static float calculateSoulWeaponDamage(int soulMajor, int soulMinor, int grade, int forgedMarks, String type) {
        float soulBonus = soulMajor * 2.5F
                + soulMinor * 0.25F
                + grade * 1.25F
                + forgedMarks * 0.75F;

        return BASE_DAMAGE + soulBonus * getTypeMultiplier(type);
    }

    public static float calculateSoulWeaponBonus(int soulMajor, int soulMinor, int grade, int forgedMarks, String type) {
        return calculateSoulWeaponDamage(soulMajor, soulMinor, grade, forgedMarks, type) - BASE_DAMAGE;
    }

    public static int getTemperingGain(LivingEntity killed) {
        if (killed.getType().is(ModTags.EntityTypes.BOSS)) return 25;
        if (killed.getMaxHealth() >= 100.0F) return 10;
        if (killed.getMaxHealth() >= 40.0F) return 4;
        return 1;
    }

    private static float getTypeMultiplier(String type) {
        return switch (type == null ? "" : type) {
            case "axe", "mace" -> 1.25F;
            case "blade" -> 1.15F;
            case "spear" -> 1.05F;
            default -> 1.0F;
        };
    }

    public static void writeSoulWeaponComponent(ItemStack stack, Player player, SoulWeaponData data) {
        stack.set(ModDataComponents.SOUL_WEAPON.get(), new SoulWeaponComponent(
                player.getUUID(),
                data.weaponType,
                data.currentGrade,
                data.currentTempering,
                data.lifetimeMarks
        ));
    }

    public static void syncSoulRealmProgress(SoulWeaponData data, int soulMajor, int soulMinor) {
        if (!data.bound) return;

        boolean advanced = soulMajor > data.lastSoulMajor
                || soulMajor == data.lastSoulMajor && soulMinor > data.lastSoulMinor;

        if (advanced) {
            data.currentGrade = 0;
            data.currentTempering = 0;
        }

        data.lastSoulMajor = soulMajor;
        data.lastSoulMinor = soulMinor;
    }


    public static int getRequiredTempering(int currentGrade, int lifetimeMarks) {
        return 25 + currentGrade * 15 + lifetimeMarks * 8;
    }

    public static int removeOwnedSoulWeapons(Player player) {
        int removed = 0;

        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);

            if (!isSoulWeapon(stack)) continue;
            if (!isOwner(stack, player)) continue;

            player.getInventory().setItem(i, ItemStack.EMPTY);
            removed++;
        }

        return removed;
    }

}