package sh.ftp.schipao.schipaoLB

import com.destroystokyo.paper.event.player.PlayerPostRespawnEvent
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent


class LobbyListener : Listener {

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        event.player.teleport(Configuration.curr.spawnPos.toLocation(event.player.location.world))
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onPlayerQuit(event: PlayerQuitEvent) {
        Bukkit.dispatchCommand(
            Bukkit.getConsoleSender(),
            "scoreboard players reset ${event.player.name} Deaths"
        )
        GameManager.curr.playerLeave(event.player)
    }

    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {
        when (event.action) {
            Action.RIGHT_CLICK_AIR, Action.RIGHT_CLICK_BLOCK -> {}
            else -> return
        }

        if (event.clickedBlock != null
            && Configuration.curr.joinSign == event.clickedBlock!!.position
        ) {
            GameManager.curr.playerJoin(event.player)
            event.isCancelled = true
            return
        } else if (event.item == null) return
        if (GameManager.curr.state != GameManager.GameState.LOBBY) return


        when (event.item!!.type) {
            Material.RED_BED -> GameManager.curr.playerLeave(event.player)
            Material.DIAMOND -> GameManager.curr.startGame()
            else -> {
                val teamFromWoolBlock = event.item!!.type
                    .woolDyeColor()
                    ?.let { col ->
                        GameManager.curr.teams.find { it.dyeColor == col }
                    }
                if (teamFromWoolBlock == null) return
                GameManager.curr.playerJoin(
                    event.player,
                    teamFromWoolBlock
                )
            }
        }
        event.isCancelled = true
    }

    @EventHandler
    fun onPlayerRespawn(event: PlayerPostRespawnEvent) {
        val teamSpawn = GameManager.curr
            .takeIf { it.state == GameManager.GameState.PLAYING }
            ?.teams
            ?.find { it.players.contains(event.player) }
            ?.spawnPoint
        event.player.teleport(
            teamSpawn ?: Configuration.curr.spawnPos.toLocation(event.player.location.world)
        )
    }

    @EventHandler
    fun onPlayerDeath(event: PlayerDeathEvent) {
        GameManager.curr.playerDeath(event.player)
    }
}