package ltd.jconet.lunchbox.ui

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration

object ChatPanel {

    private const val CHAT_CENTER_PX = 154
    private const val SPACE_WIDTH_PX = 4

    private val border = Component.text(
        "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━",
        NamedTextColor.GOLD
    )

    fun panel(vararg lines: Component): Component {
        val builder = Component.text()
            .append(border)
            .append(Component.newline())

        lines.forEach { line ->
            builder
                .append(line)
                .append(Component.newline())
        }

        return builder
            .append(border)
            .build()
    }

    fun title(text: String): Component {
        return centered(
            text,
            Component.text(
                text,
                NamedTextColor.GOLD
            ).decorate(TextDecoration.BOLD)
        )
    }

    fun centered(
        visibleText: String,
        component: Component
    ): Component {
        val textWidth = pixelWidth(visibleText)
        val paddingPixels =
            (CHAT_CENTER_PX - (textWidth / 2)).coerceAtLeast(0)

        val spaces = paddingPixels / SPACE_WIDTH_PX

        return Component.text(" ".repeat(spaces))
            .append(component)
    }

    private fun pixelWidth(text: String): Int {
        return text.sumOf { characterWidth(it) + 1 }
    }

    private fun characterWidth(character: Char): Int {
        return when (character) {
            ' ', 'I', 'i', '!', '.', ',', ':', ';', '\'', '|' -> 2

            'l', 't', '[', ']', '(', ')' -> 3

            'f', 'k', '<', '>' -> 4

            '@', '~' -> 6

            else -> 5
        }
    }
}