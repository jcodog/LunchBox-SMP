package ltd.jconet.lunchbox.command

import com.mojang.brigadier.Command
import com.mojang.brigadier.tree.LiteralCommandNode
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands
import ltd.jconet.lunchbox.LunchBoxSMP
import ltd.jconet.lunchbox.ui.ChatPanel
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor

object LunchBoxCommand {

    fun create(plugin: LunchBoxSMP): LiteralCommandNode<CommandSourceStack> {
        return Commands.literal("lunchbox")
            .executes { context ->
                context.source.sender.sendMessage(
                    ChatPanel.panel(
                        ChatPanel.title("LunchBox SMP"),
                        Component.empty(),
                        ChatPanel.centered(
                            "Official server plugin by JCoNet LTD.",
                            Component.text(
                                "Official server plugin by JCoNet LTD.",
                                NamedTextColor.GRAY
                            )
                        )
                    )
                )

                Command.SINGLE_SUCCESS
            }
            .then(
                Commands.literal("version")
                    .executes { context ->
                        val pluginVersion = plugin.pluginMeta.version
                        val paperVersion = plugin.server.version
                        val minecraftVersion = plugin.server.minecraftVersion

                        context.source.sender.sendMessage(
                            ChatPanel.panel(
                                ChatPanel.title("LunchBox SMP"),
                                Component.empty(),

                                ChatPanel.centered(
                                    "Plugin v$pluginVersion",
                                    Component.text(
                                        "Plugin ",
                                        NamedTextColor.GRAY
                                    ).append(
                                        Component.text(
                                            "v$pluginVersion",
                                            NamedTextColor.WHITE
                                        )
                                    )
                                ),

                                ChatPanel.centered(
                                    "Paper $paperVersion",
                                    Component.text(
                                        "Paper ",
                                        NamedTextColor.GRAY
                                    ).append(
                                        Component.text(
                                            paperVersion,
                                            NamedTextColor.WHITE
                                        )
                                    )
                                ),

                                ChatPanel.centered(
                                    "Minecraft $minecraftVersion",
                                    Component.text(
                                        "Minecraft ",
                                        NamedTextColor.GRAY
                                    ).append(
                                        Component.text(
                                            minecraftVersion,
                                            NamedTextColor.WHITE
                                        )
                                    )
                                )
                            )
                        )

                        Command.SINGLE_SUCCESS
                    }
            )
            .then(
                Commands.literal("status")
                    .executes { context ->
                        val moduleManager = plugin.moduleManager
                        val enabledModules = moduleManager.enabledCount()
                        val registeredModules = moduleManager.registeredCount()

                        context.source.sender.sendMessage(
                            ChatPanel.panel(
                                ChatPanel.title("LunchBox SMP"),
                                Component.empty(),

                                ChatPanel.centered(
                                    "Status: Running",
                                    Component.text(
                                        "Status: ",
                                        NamedTextColor.GRAY
                                    ).append(
                                        Component.text(
                                            "Running",
                                            NamedTextColor.GREEN
                                        )
                                    )
                                ),

                                ChatPanel.centered(
                                    "Modules: $enabledModules enabled / $registeredModules registered",
                                    Component.text(
                                        "Modules: ",
                                        NamedTextColor.GRAY
                                    ).append(
                                        Component.text(
                                            "$enabledModules enabled",
                                            NamedTextColor.GREEN
                                        )
                                    ).append(
                                        Component.text(
                                            " / ",
                                            NamedTextColor.DARK_GRAY
                                        )
                                    ).append(
                                        Component.text(
                                            "$registeredModules registered",
                                            NamedTextColor.WHITE
                                        )
                                    )
                                )
                            )
                        )

                        Command.SINGLE_SUCCESS
                    }
            )
            .build()
    }
}