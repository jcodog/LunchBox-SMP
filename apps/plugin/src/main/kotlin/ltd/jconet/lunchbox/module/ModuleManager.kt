package ltd.jconet.lunchbox.module

import ltd.jconet.lunchbox.LunchBoxSMP
import java.util.logging.Level

class ModuleManager(
    private val plugin: LunchBoxSMP
) {
    private val modules = linkedMapOf<String, Module>()
    private val states = mutableMapOf<String, ModuleState>()

    fun register(module: Module) {
        require(module.id !in modules) {
            "Module '${module.id}' is already registered"
        }

        modules[module.id] = module
        states[module.id] = ModuleState.DISABLED
    }

    fun enableModules() {
        for (module in modules.values) {
            val enabled = plugin.config.getBoolean(
                "modules.${module.id}.enabled",
                false
            )

            if (!enabled) {
                states[module.id] = ModuleState.DISABLED
                continue
            }

            try {
                module.enable()

                states[module.id] = ModuleState.ENABLED

                plugin.logger.info(
                    "${module.name} module enabled"
                )
            } catch (exception: Exception) {
                states[module.id] = ModuleState.FAILED

                plugin.logger.log(
                    Level.SEVERE,
                    "Failed to enable ${module.name} module",
                    exception
                )
            }
        }
    }

    fun disableModules() {
        for (module in modules.values.reversed()) {
            if (states[module.id] != ModuleState.ENABLED) {
                continue
            }

            try {
                module.disable()

                plugin.logger.info(
                    "${module.name} module disabled"
                )
            } catch (exception: Exception) {
                plugin.logger.log(
                    Level.SEVERE,
                    "Failed to disable ${module.name} module",
                    exception
                )
            } finally {
                states[module.id] = ModuleState.DISABLED
            }
        }
    }

    fun registeredCount(): Int = modules.size

    fun enabledCount(): Int =
        states.values.count { it == ModuleState.ENABLED }

    fun failedCount(): Int =
        states.values.count { it == ModuleState.FAILED }

    fun stateOf(id: String): ModuleState? = states[id]

    fun isEnabled(id: String): Boolean =
        states[id] == ModuleState.ENABLED
}