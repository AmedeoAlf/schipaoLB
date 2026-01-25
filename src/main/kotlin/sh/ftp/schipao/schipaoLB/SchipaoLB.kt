package sh.ftp.schipao.schipaoLB

import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import org.bukkit.Material
import org.bukkit.plugin.java.JavaPlugin
import java.io.File


class SchipaoLB : JavaPlugin() {
    lateinit var protector: WorldProtector

    override fun onEnable() {
        protector = WorldProtector(server.respawnWorld)
        println("Initialized worldProtector")
        GameManager.curr = GameManager(server.respawnWorld)
        println("Initialized GameManager")
        loadConfig()
        println("Loaded config")
        server.pluginManager.registerEvents(LBEventListener(Material.DRIED_KELP_BLOCK, loadOutcomes()), this)
        server.pluginManager.registerEvents(protector, this)
        server.pluginManager.registerEvents(SpawnListener(), this)
        println("Registered events")
        lifecycleManager.registerEventHandler(LifecycleEvents.COMMANDS) {
            it.registrar().register(
                worldProtectorCmd(protector)
            )
        }
        println("Registered commands")
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
        // FIXME: just for development
        file.delete()
        saveResource("outcomes.json", true)

        return json.decodeFromStream(file.inputStream())
    }

    @OptIn(ExperimentalSerializationApi::class)
    fun loadConfig() {
        val file = File(dataFolder, "config.json")

        if (!file.exists()) {
            file.parentFile.mkdirs()
            file.writeText(Json.encodeToString(Configuration.DEFAULT))
        }

        Configuration.curr = Json.decodeFromStream(file.inputStream())
    }

}
