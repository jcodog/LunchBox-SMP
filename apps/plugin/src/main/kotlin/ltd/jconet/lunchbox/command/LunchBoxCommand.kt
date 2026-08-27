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

    fun create(
        plugin: LunchBoxSMP
    ): LiteralCommandNode<CommandSourceStack> {
        return Commands.literal("lunchbox")
            .executes { context ->
                context.source.sender.sendMessage(
                    mainPanel()
                )

                Command.SINGLE_SUCCESS
            }
            .then(
                Commands.literal("version")
                    .executes { context ->
                        context.source.sender.sendMessage(
                            versionPanel(plugin)
                        )

                        Command.SINGLE_SUCCESS
                    }
            )
            .then(
                Commands.literal("status")
                    .executes { context ->
                        context.source.sender.sendMessage(
                            statusPanel(plugin)
                        )

                        Command.SINGLE_SUCCESS
                    }
            )
            .build()
    }

    private fun mainPanel(): Component {
        return ChatPanel.panel(
            ChatPanel.title("LunchBox SMP"),
            Component.empty(),

            ChatPanel.centered(
                Component.text(
                    "Official server plugin by JCoNet LTD.",
                    NamedTextColor.GRAY
                )
            )
        )
    }

    private fun versionPanel(
        plugin: LunchBoxSMP
    ): Component {
        val pluginVersion = plugin.pluginMeta.version

        val paperVersion =
            plugin.server.version.substringBefore(" (")

        val minecraftVersion =
            plugin.server.minecraftVersion

        return ChatPanel.panel(
            ChatPanel.title("LunchBox SMP"),
            Component.empty(),

            ChatPanel.centered(
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
    }

    private fun statusPanel(
        plugin: LunchBoxSMP
    ): Component {
        val moduleManager = plugin.moduleManager

        val enabledModules =
            moduleManager.enabledCount()

        val registeredModules =
            moduleManager.registeredCount()

        val failedModules =
            moduleManager.failedCount()

        val degraded = failedModules > 0

        val statusText =
            if (degraded) {
                "Degraded"
            } else {
                "Running"
            }

        val statusColor =
            if (degraded) {
                NamedTextColor.YELLOW
            } else {
                NamedTextColor.GREEN
            }

        val lines = mutableListOf<Component>(
            ChatPanel.title("LunchBox SMP"),
            Component.empty(),

            ChatPanel.centered(
                Component.text(
                    "Status: ",
                    NamedTextColor.GRAY
                ).append(
                    Component.text(
                        statusText,
                        statusColor
                    )
                )
            ),

            ChatPanel.centered(
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

        if (failedModules > 0) {
            lines.add(
                ChatPanel.centered(
                    Component.text(
                        "Failures: ",
                        NamedTextColor.GRAY
                    ).append(
                        Component.text(
                            "$failedModules failed",
                            NamedTextColor.RED
                        )
                    )
                )
            )
        }

        return ChatPanel.panel(
            *lines.toTypedArray()
        )
    }
}