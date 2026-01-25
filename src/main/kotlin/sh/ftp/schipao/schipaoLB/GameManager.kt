package sh.ftp.schipao.schipaoLB

import com.mojang.brigadier.Command
import com.mojang.brigadier.arguments.StringArgumentType
import io.papermc.paper.command.brigadier.Commands
import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.format.TextColor.color
import org.bukkit.DyeColor
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.entity.Player

class GameManager(world: World) {

    companion object {
        lateinit var curr: GameManager

        /*.executes {
                val player = it.source.executor as? Player ?: return@executes Command.SINGLE_SUCCESS
                GameManager.curr.playerJoin(player)
                Command.SINGLE_SUCCESS
            }
         */
        fun cmd(name: String = "lb") = Commands.literal(name)
            .then(
                Commands.literal("team")
                    .then(
                        Commands.argument("color", StringArgumentType.word())
                            .suggests { _, builder ->
                                Configuration.curr.colors.forEach { builder.suggest(it.name) }
                                builder.buildFuture()
                            }.executes { ctx ->
                                ctx.source.sender.sendMessage(run {
                                    if (ctx.source.executor !is Player) return@run "Executor must be a player"
                                    val player = ctx.source.executor as Player
                                    val colorStr = StringArgumentType.getString(ctx, "color")
                                    val dyeColor =
                                        runCatching { DyeColor.valueOf(colorStr) }.getOrElse { return@run "\"$colorStr\" is not a valid team" }
                                    val team = curr.teams.find { it.dyeColor.ordinal == dyeColor.ordinal }
                                        ?: return@run "${dyeColor.name} is not configured"
                                    GameManager.curr.playerJoin(player, team.idx)
                                    "Player ${player.name} moved to team ${team.dyeColor.name}"
                                })
                                Command.SINGLE_SUCCESS
                            }
                    )
            ).then(
                Commands.literal("list").executes { ctx ->
                    curr.teams.map { t ->
                        text(t.dyeColor.name, color(t.dyeColor.color.asRGB()))
                            .append(text(": " + t.players.joinToString { it.name }))
                    }.forEach {
                        ctx.source.sender.sendMessage(it)
                    }
                    Command.SINGLE_SUCCESS
                }
            )
            .build()
    }

    enum class GameState {
        PLAYING, LOBBY
    }

    data class Team(var players: MutableSet<Player>, val idx: Int, val spawnPoint: Location, val dyeColor: DyeColor) {}

    val teams = Configuration.curr
        .luckyBlockSpawns
        //.take(Configuration.curr.colors.size)
        .mapIndexed { idx, it ->
            Team(mutableSetOf(), idx, it.toLocation(world), Configuration.curr.colors[idx])
        }

    var state = GameState.LOBBY

    fun playerJoin(player: Player, teamIdx: Int = -1) {
        teams.forEach { it.players.remove(player) }
        val toTeam = if (teamIdx != -1) teams[teamIdx]
        else teams.reduce { acc, team -> if (acc.players.size <= team.players.size) acc else team }
        toTeam.players.add(player)
        player.world.sendMessage { text("${player.name} joined the game in team ${toTeam.dyeColor.name}") }
        if (teamIdx == -1) player.sendMessage {
            text("Use ")
                .append(text("/lb team <color>", color(0xffee10)))
                .append(text(" to change team"))
        }
    }
}