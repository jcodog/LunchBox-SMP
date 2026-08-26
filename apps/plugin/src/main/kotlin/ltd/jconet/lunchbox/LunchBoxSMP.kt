package ltd.jconet.lunchbox

import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents
import ltd.jconet.lunchbox.command.LunchBoxCommand
import ltd.jconet.lunchbox.module.ModuleManager
import ltd.jconet.lunchbox.module.test.TestModule
import org.bukkit.plugin.java.JavaPlugin

class LunchBoxSMP : JavaPlugin() {
    lateinit var moduleManager: ModuleManager
        private set

    override fun onEnable() {
        saveDefaultConfig()

        moduleManager = ModuleManager(this)

        moduleManager.register(TestModule())
        moduleManager.enableModules()

        lifecycleManager.registerEventHandler(LifecycleEvents.COMMANDS) { event ->
            event.registrar().register(
                LunchBoxCommand.create(this),
                "LunchBox SMP"
            )
        }

        logger.info("LunchBox SMP plugin enabled.")
    }

    override fun onDisable() {
        if (::moduleManager.isInitialized) {
            moduleManager.disableModules()
        }

        logger.info("LunchBox SMP plugin disabled.")
    }
}