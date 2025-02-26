package kenjoohono.litedungeonPlus

import org.bukkit.plugin.java.JavaPlugin
import kenjoohono.litedungeonPlus.setup.PluginCheck

class LitedungeonPlus : JavaPlugin() {

    override fun onEnable() {
        PluginCheck(this)
    }
}
