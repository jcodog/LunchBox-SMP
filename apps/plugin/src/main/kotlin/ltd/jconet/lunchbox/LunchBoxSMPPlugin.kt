package ltd.jconet.lunchbox

import org.bukkit.plugin.java.JavaPlugin

class LunchBoxPlugin : JavaPlugin() {
    override fun onEnable() {
        logger.info("LunchBox SMP plugin enabled.")
    }

    override fun onDisable() {
        logger.info("LunchBox SMP plugin disabled.")
    }
}