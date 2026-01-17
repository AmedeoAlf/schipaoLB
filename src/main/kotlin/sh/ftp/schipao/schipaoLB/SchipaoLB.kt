package sh.ftp.schipao.schipaoLB

import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.decodeFromStream
import org.bukkit.Material
import org.bukkit.plugin.java.JavaPlugin
import java.io.File


class SchipaoLB : JavaPlugin() {
    lateinit var protector: WorldProtector

    override fun onEnable() {
        protector = WorldProtector(server.respawnWorld)
        server.pluginManager.registerEvents(LBEventListener(Material.DRIED_KELP_BLOCK, loadOutcomes()), this)
        server.pluginManager.registerEvents(protector, this)
        lifecycleManager.registerEventHandler(LifecycleEvents.COMMANDS) {
            it.registrar().register(
                worldProtectorCmd(protector)
            )
        }
    }

    override fun onDisable() {
        protector.restore()
    }

    @OptIn(ExperimentalSerializationApi::class)
    fun loadOutcomes(): List<LBOutcome> {
        val file = File(dataFolder, "outcomes.json")

        if (!file.exists()) {
            file.parentFile.mkdirs()
            saveResource("outcomes.json", true)
        }

        return json.decodeFromStream(file.inputStream())
    }

}
