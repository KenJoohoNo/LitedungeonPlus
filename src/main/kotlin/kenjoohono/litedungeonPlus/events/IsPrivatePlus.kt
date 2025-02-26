package kenjoohono.litedungeonPlus.events

import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerChangedWorldEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.plugin.java.JavaPlugin
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import java.io.File
import java.util.UUID

class IsPrivatePlus(private val plugin: JavaPlugin) : Listener {

    private val privatePlayers = mutableSetOf<UUID>()
    init { plugin.server.pluginManager.registerEvents(this, plugin) }

    @EventHandler
    fun onWorldChange(event: PlayerChangedWorldEvent) {
        val player = event.player
        val worldName = player.world.name
        val file = File("plugins/LiteDungeon/data/baseData.json")
        if (!file.exists()) {
            privatePlayers.remove(player.uniqueId)
            return
        }

        try {
            val jsonText = file.readText(Charsets.UTF_8)
            val parser = JSONParser()
            val json = parser.parse(jsonText) as JSONObject
            val bases = json["bases"] as JSONArray

            var isPrivateWorld = false
            for (base in bases) {
                val baseObj = base as JSONObject
                val folderName = baseObj["folderName"]?.toString() ?: continue
                val isPrivate = baseObj["isPrivate"] as? Boolean ?: false

                if (worldName.contains(folderName) && isPrivate) {
                    isPrivateWorld = true
                    break
                }
            }

            if (isPrivateWorld) {
                privatePlayers.add(player.uniqueId)
            } else {
                privatePlayers.remove(player.uniqueId)
            }
        } catch (ex: Exception) {
            privatePlayers.remove(player.uniqueId)
        }
    }

    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {
        if (event.action.name != "PHYSICAL") return
        val block = event.clickedBlock ?: return
        if (block.type != Material.FARMLAND) return

        if (privatePlayers.contains(event.player.uniqueId)) {
            event.isCancelled = true
        }
    }
}