package sh.ftp.schipao.schipaoLB

import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import org.bukkit.Material
import org.bukkit.World
import org.bukkit.plugin.java.JavaPlugin
import sh.ftp.schipao.schipaoLB.outcomes.LBOutcome
import sh.ftp.schipao.schipaoLB.outcomes.json
import java.io.File


class SchipaoLB : JavaPlugin() {

    companion object {
        lateinit var dataFolder: File
        lateinit var world: World
        lateinit var worldProtector: WorldProtector
    }


    override fun onEnable() {
        world = server.respawnWorld
        Companion.dataFolder = this@SchipaoLB.dataFolder
        worldProtector = WorldProtector()
        println("Initialized worldProtector")
        loadConfig()
        println("Loaded config")
        GameManager.curr = GameManager(server.respawnWorld)
        println("Initialized GameManager")
        println(Configuration.curr)
        server.pluginManager.registerEvents(LBEventListener(Material.DRIED_KELP_BLOCK, loadOutcomes()), this)
        server.pluginManager.registerEvents(worldProtector, this)
        server.pluginManager.registerEvents(LobbyListener(), this)
        println("Registered events")
        lifecycleManager.registerEventHandler(LifecycleEvents.COMMANDS) {
            it.registrar().let { r ->
                r.register(worldProtectorCmd(worldProtector))
                r.register(GameManager.cmd())
            }
        }
        println("Registered commands")
    }

    override fun onDisable() {
        worldProtector.restore()
    }

    @OptIn(ExperimentalSerializationApi::class)
    fun loadOutcomes(): List<LBOutcome> {
        val file = File(this@SchipaoLB.dataFolder, "outcomes.json")

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
        val file = File(this@SchipaoLB.dataFolder, "config.json")

        if (!file.exists()) {
            file.parentFile.mkdirs()
            file.writeText(Json.encodeToString(Configuration.DEFAULT))
        }

        Configuration.curr = Json.decodeFromStream(file.inputStream())
    }

}
