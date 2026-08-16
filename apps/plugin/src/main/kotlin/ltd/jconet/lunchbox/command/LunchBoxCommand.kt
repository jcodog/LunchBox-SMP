package ltd.jconet.lunchbox.command

import com.mojang.brigadier.Command
import com.mojang.brigadier.tree.LiteralCommandNode
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands
import ltd.jconet.lunchbox.LunchBoxSMP

object LunchBoxCommand {
    fun create(plugin: LunchBoxSMP): LiteralCommandNode<CommandSourceStack> {
        return Commands.literal("lunchbox")
            .executes { context ->
                context.source.sender.sendRichMessage(
                    "<gold><bold>LunchBox SMP</bold></gold>\n" +
                            "<gray>Official LunchBox SMP server plugin by JCoNet LTD.</gray>"
                )

                Command.SINGLE_SUCCESS
            }
            .then(
                Commands.literal("version")
                    .executes { context ->
                        val pluginVersion = plugin.pluginMeta.version
                        val paperVersion = plugin.server.version
                        val minecraftVersion = plugin.server.minecraftVersion

                        context.source.sender.sendRichMessage(
                            "<gold><bold>LunchBox SMP</bold></gold> <gray>v$pluginVersion</gray>\n" +
                                    "<gray>Paper:</gray> <white>$paperVersion</white>\n" +
                                    "<gray>Minecraft:</gray> <white>$minecraftVersion</white>"
                        )

                        Command.SINGLE_SUCCESS
                    }
            )
            .build()
    }
}