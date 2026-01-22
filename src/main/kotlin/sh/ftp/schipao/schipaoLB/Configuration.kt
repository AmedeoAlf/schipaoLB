package sh.ftp.schipao.schipaoLB

import kotlinx.serialization.Serializable
import org.bukkit.Location
import org.bukkit.World

@Serializable
data class SimplePos(val x: Double, val y: Double, val z: Double) {
    companion object {
        val ZERO = SimplePos(0.0, 0.0, 0.0)
    }

    fun toLocation(world: World) = Location(world, x, y, z)
}

@Serializable
data class Configuration(val spawnPos: SimplePos) {
    companion object {
        val DEFAULT = Configuration(SimplePos.ZERO)
        var current = this.DEFAULT
    }
}