package sh.ftp.schipao.schipaoLB

import com.mojang.brigadier.Command
import com.mojang.brigadier.arguments.StringArgumentType
import io.papermc.paper.command.brigadier.Commands
import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.format.TextColor.color
import net.kyori.adventure.title.Title
import org.bukkit.Color
import org.bukkit.DyeColor
import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.World
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class GameManager(val world: World) {

    companion object {
        lateinit var curr: GameManager

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
                                    GameManager.curr.playerJoin(player, team)
                                    "Player ${player.name} moved to team ${team.dyeColor.name}"
                                })
                                Command.SINGLE_SUCCESS
                            }
                    )
            ).then(
                Commands.literal("list").executes { ctx ->
                    curr.teams.map { t ->
                        t.component().append(text(": " + t.players.joinToString { it.name }))
                    }.forEach {
                        ctx.source.sender.sendMessage(it)
                    }
                    Command.SINGLE_SUCCESS
                }
            ).then(
                Commands.literal("start").executes { ctx ->
                    if (!ctx.source.sender.isOp) {
                        curr.startGame()
                    } else {
                        curr.startGameNoChecks()
                    }
                    Command.SINGLE_SUCCESS
                }
            )
            .build()
    }

    enum class GameState {
        PLAYING, LOBBY
    }

    data class Team(
        var players: MutableSet<Player>,
        val idx: Int,
        val spawnPoint: Location,
        val dyeColor: DyeColor,
    ) {
        fun component() = text(dyeColor.name)
            .color(color(dyeColor.color.asRGB()))
    }

    val teams = Configuration.curr
        .luckyBlockSpawns
        //.take(Configuration.curr.colors.size)
        .mapIndexed { idx, it ->
            Team(mutableSetOf(), idx, it.toLocation(world), Configuration.curr.colors[idx])
        }

    var state = GameState.LOBBY

    fun playerJoin(player: Player, team: Team? = null) {
        if (state != GameState.LOBBY) {
            player.sendMessage { text("Wait for the current game to stop") }
            return
        }
        teams.forEach { it.players.remove(player) }
        val smallestTeam =
            team ?: teams.reduce { acc, team -> if (acc.players.size <= team.players.size) acc else team }
        smallestTeam.players.add(player)
        player.world.sendMessage {
            text("${player.name} joined the game in team ")
                .append { smallestTeam.component() }
        }
        if (team == null) player.sendMessage {
            text("Use ")
                .append(text("/lb team <color>", color(0xffee10)))
                .append(text(" to change team"))
        }
        player.inventory.clear()
        getPlayerDeaths(player).score = 0

        curr.teams.forEach { team ->
            player.inventory.setItem(team.idx, ItemStack.of(team.dyeColor.toWool()).apply {
                itemMeta = itemMeta.apply {
                    displayName(text("Join team ").append { team.component() })
                }
            })
        }
        player.inventory.setItem(7, ItemStack.of(Material.DIAMOND).apply {
            itemMeta = itemMeta.apply {
                displayName(text("START GAME").color(color(Color.AQUA.asRGB())))
            }
        })
        player.inventory.setItem(8, ItemStack.of(Material.RED_BED).apply {
            itemMeta = itemMeta.apply {
                displayName(text("LEAVE GAME"))
            }
        })
    }

    fun leaveTeam(player: Player): Team? {
        val removedFrom = teams.find { it.players.remove(player) }
        if (removedFrom == null) return null
        player.inventory.clear()
        player.gameMode = GameMode.ADVENTURE
        if (state == GameState.PLAYING && removedFrom.players.isEmpty()) {
            world.sendMessage {
                text("Team ")
                    .append(removedFrom.component())
                    .append(text(" has been destroyed"))
            }
            if (curr.teams.filter { it.players.isNotEmpty() }.size < 2) {
                val winners = curr.teams.find { it.players.isNotEmpty() } ?: teams[0] // Fallback for "cheated" games with 1 team
                world.players.forEach { player ->
                    player.showTitle(
                        Title.title(
                            winners.component().append { text(" has won!").color(color(Color.YELLOW.asRGB())) },
                            text("Congratulations to " + winners.players.joinToString { it.name })
                        )
                    )
                }
            }
        }
        return removedFrom
    }

    fun playerLeave(player: Player) {
        val found = leaveTeam(player)
        if (found != null && state == GameState.PLAYING)
            player.world.sendMessage { text("${player.name} left the match") }
    }

    fun playerDeath(player: Player) {
        if (state != GameState.PLAYING) return
        val deaths = getPlayerDeaths(player).score
        if (deaths >= Configuration.curr.maxDeaths) {
            player.showTitle(
                Title.title(
                    text("GAME OVER"),
                    text("You have died $deaths times"),
                )
            )
            leaveTeam(player)
        }
    }

    fun startGameNoChecks() {
        state = GameState.PLAYING
        teams.forEach { team ->
            team.players.forEach { player ->
                player.gameMode = GameMode.SURVIVAL
                player.teleport(team.spawnPoint)
                player.inventory.clear()
                player.showTitle(
                    Title.title(
                        text("Game started"),
                        text("Be the last to reach ${Configuration.curr.maxDeaths} deaths"),
                    )
                )
            }
        }
    }

    fun getPlayerDeaths(player: Player) = player.scoreboard.getObjective("Deaths")!!.getScore(player)

    fun startGame() {
        if (teams.filter { it.players.isNotEmpty() }.size < 2) {
            world.sendMessage { text("There must be at least two teams before starting game!") }
            return
        }
        startGameNoChecks()
    }
}