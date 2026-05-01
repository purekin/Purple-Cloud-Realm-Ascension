package net.thejadeproject.ascension.common.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.thejadeproject.ascension.common.command.commands.GiveSkillCommand;
import net.thejadeproject.ascension.common.command.commands.ResetAscensionCommand;
import net.thejadeproject.ascension.common.command.commands.SetCultivationCommand;

public class AscensionCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("ascension")
                .requires(source -> source.hasPermission(2))

                /*

                All currently implemented commands

                    /ascension reset all <targets>
                    /ascension reset attributes <targets>                                               -> works
                    /ascension reset paths <targets>
                    /ascension reset path <targets> <path>
                    /ascension reset skills <targets>                                                   -> works
                    /ascension reset techniques <targets>
                    /ascension reset technique <targets> <path>
                    /ascension reset physique <targets>

                    /ascension cultivation set <targets> <path> <majorRealm> <minorRealm> [progress]    -> works
                    /ascension cultivation get <target>                                                 -> works
                    /ascension cultivation get <target> physique                                        -> works

                    /ascension skill give <targets> <skill> [form]                                      -> works
                    /ascension skill remove <targets> <skill> [form]                                    -> works

                 */

                .then(ResetAscensionCommand.build())
                .then(SetCultivationCommand.build())
                .then(GiveSkillCommand.build())
        );
    }
}