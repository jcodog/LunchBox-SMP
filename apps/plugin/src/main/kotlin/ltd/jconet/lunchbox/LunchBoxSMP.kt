package ltd.jconet.lunchbox

import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents
import ltd.jconet.lunchbox.command.LunchBoxCommand
import org.bukkit.plugin.java.JavaPlugin

class LunchBoxSMP : JavaPlugin() {
    override fun onEnable() {
        lifecycleManager.registerEventHandler(LifecycleEvents.COMMANDS) { event ->
            event.registrar().register(
                LunchBoxCommand.create(this),
                "LunchBox SMP"
            )
        }

        logger.info("LunchBox SMP plugin enabled.")
    }

    override fun onDisable() {
        logger.info("LunchBox SMP plugin disabled.")
    }
}