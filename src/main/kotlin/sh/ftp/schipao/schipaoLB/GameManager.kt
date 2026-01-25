package sh.ftp.schipao.schipaoLB

import net.kyori.adventure.text.Component
import org.bukkit.DyeColor
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.entity.Player

class GameManager(world: World) {

    companion object {
        lateinit var curr: GameManager
    }

    enum class GameState {
        PLAYING, LOBBY
    }

    data class Team(var players: MutableSet<Player>, val idx: Int, val spawnPoint: Location, val color: DyeColor) {}

    val teams = Configuration.curr
        .luckyBlockSpawns
        .take(Configuration.curr.colors.size)
        .mapIndexed { idx, it ->
            Team(mutableSetOf(), idx, it.toLocation(world), Configuration.curr.colors[idx])
        }

    var state = GameState.LOBBY

    fun playerJoin(player: Player) {
        teams.forEach { it.players.remove(player) }
        val toTeam = teams.reduce { acc, team -> if (acc.players.size <= team.players.size) acc else team }
        toTeam.players.add(player)
        player.world.sendMessage { Component.text("${player.name} joined the game in team ${toTeam.color.name}") }
        player.world.sendMessage { Component.text("$teams") }
    }
}