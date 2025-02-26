package kenjoohono.litedungeonPlus.setup

import java.io.File
import org.bukkit.plugin.java.JavaPlugin
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser

class PluginCheck(private val plugin: JavaPlugin) {

    init {
        checkConfig()
    }

    private fun checkConfig() {
        val configFile = File("plugins/LiteDungeon/config.json")
        if (!configFile.exists()) {
            plugin.logger.severe("LiteDungeon not found! Disabling plugin")
            plugin.server.pluginManager.disablePlugin(plugin)
            return
        }

        try {
            val jsonText = configFile.readText(Charsets.UTF_8)
            val parser = JSONParser()
            val jsonObject = parser.parse(jsonText) as JSONObject
            val version = jsonObject["version"]?.toString() ?: "unknown"
            plugin.logger.info("LiteDungeon Version $version Patch")
        } catch (e: Exception) {
            plugin.logger.severe("${e.message}")
            plugin.server.pluginManager.disablePlugin(plugin)
        }
    }
}