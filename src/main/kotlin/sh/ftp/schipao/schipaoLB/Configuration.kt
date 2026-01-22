package sh.ftp.schipao.schipaoLB

import io.papermc.paper.math.Position
import kotlinx.serialization.Serializable

@Serializable
data class Configuration(val spawnPos: Position) {
    companion object {
        val DEFAULT = Configuration(Position.BLOCK_ZERO)
        var current = DEFAULT
    }
}