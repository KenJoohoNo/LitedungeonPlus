package kenjoohono.litedungeonPlus

import org.bukkit.plugin.java.JavaPlugin
import kenjoohono.litedungeonPlus.setup.PluginCheck
import kenjoohono.litedungeonPlus.events.IsPrivatePlus

class LitedungeonPlus : JavaPlugin() {

    override fun onEnable() {
        // SETUP
        PluginCheck(this)

        // EVENTS
        IsPrivatePlus(this)
    }
}
