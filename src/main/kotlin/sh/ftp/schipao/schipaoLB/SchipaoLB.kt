package sh.ftp.schipao.schipaoLB

import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.decodeFromStream
import org.bukkit.Material
import org.bukkit.plugin.java.JavaPlugin
import java.io.File


class SchipaoLB() : JavaPlugin() {

    override fun onEnable() {
        server.pluginManager.registerEvents(BlockDestroyListener(Material.DRIED_KELP_BLOCK, loadOutcomes()), this)
        lifecycleManager.registerEventHandler(LifecycleEvents.COMMANDS) {
            it.registrar().register(
                worldProtectorCmd(WorldProtector(server.respawnWorld))
            )
        }
    }

    override fun onDisable() {
    }

    @OptIn(ExperimentalSerializationApi::class)
    fun loadOutcomes(): List<LBOutcome> {
        val file = File(dataFolder, "outcomes.json")

        if (!file.exists()) {
            file.parentFile.mkdirs()
            saveResource("outcomes.json", false)
        }

        return json.decodeFromStream(file.inputStream())
    }

}
