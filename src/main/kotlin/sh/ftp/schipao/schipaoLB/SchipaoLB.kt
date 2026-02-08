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
import java.util.logging.Level
import java.util.logging.Logger


class SchipaoLB : JavaPlugin() {

    companion object {
        lateinit var dataFolder: File
        lateinit var world: World
        lateinit var worldProtector: WorldProtector

        private lateinit var logger: Logger
        fun log(msg: String) = logger.log(Level.INFO, msg)
    }


    override fun onEnable() {
        Companion.logger = logger
        world = server.respawnWorld
        Companion.dataFolder = this@SchipaoLB.dataFolder
        worldProtector = WorldProtector()
        log("WorldProtector initialized")
        loadConfig()
        log("Loaded config ${Configuration.curr}")
        GameManager.curr = GameManager(server.respawnWorld)
        log("Initialized GameManager")
        server.pluginManager.registerEvents(LBEventListener(Material.DRIED_KELP_BLOCK, loadOutcomes()), this)
        server.pluginManager.registerEvents(worldProtector, this)
        server.pluginManager.registerEvents(LobbyListener(), this)
        log("Registered events")
        lifecycleManager.registerEventHandler(LifecycleEvents.COMMANDS) {
            it.registrar().let { r ->
                r.register(worldProtectorCmd(worldProtector))
                r.register(GameManager.cmd())
            }
        }
        log("Registered commands")
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
