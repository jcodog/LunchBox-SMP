package ltd.jconet.lunchbox.module

import ltd.jconet.lunchbox.LunchBoxSMP

class ModuleManager(
    private val plugin: LunchBoxSMP
) {
    private val modules = linkedMapOf<String, Module>()
    private val enabledModules = mutableSetOf<String>()

    fun register(module: Module) {
        modules[module.id] = module
    }

    fun enableModules() {
        for (module in modules.values) {
            val enabled = plugin.config.getBoolean(
                "modules.${module.id}.enabled",
                false
            )

            if (!enabled) {
                continue
            }

            module.enable()
            enabledModules.add(module.id)

            plugin.logger.info("${module.name} module enabled")
        }
    }

    fun disableModules() {
        for (module in modules.values.reversed()) {
            if (module.id !in enabledModules) {
                continue
            }

            module.disable()
            plugin.logger.info("${module.name} module disabled")
        }

        enabledModules.clear()
    }
}