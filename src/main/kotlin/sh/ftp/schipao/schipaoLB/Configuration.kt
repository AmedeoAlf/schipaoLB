package sh.ftp.schipao.schipaoLB

import io.papermc.paper.math.BlockPosition
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.DoubleArraySerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.bukkit.DyeColor
import org.bukkit.Location
import org.bukkit.World

@Serializable(with = SLSerializer::class)
data class SimpleLocation(val x: Double, val y: Double, val z: Double, val yaw: Float = 0f) {
    companion object {
        val ZERO = SimpleLocation(0.0, 0.0, 0.0, 0f)
    }

    fun toLocation(world: World, pitch: Float = 0f) = Location(world, x, y, z, yaw, pitch)

    override fun equals(other: Any?): Boolean {
        val (x, y, z) = when (other) {
            is SimpleLocation -> listOf(other.x, other.y, other.z)
            is BlockPosition -> listOf(other.blockX(), other.blockY(), other.blockZ()).map {it.toDouble()}
            else -> {
                return false
            }
        }
        return x == this.x && y == this.y && z == this.z
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        result = 31 * result + z.hashCode()
        result = 31 * result + yaw.hashCode()
        return result
    }
}

object SLSerializer : KSerializer<SimpleLocation> {
    private val delegateSerializer = DoubleArraySerializer()
    override val descriptor: SerialDescriptor =
        SerialDescriptor("sh.ftp.schipao.schipaoLB", delegateSerializer.descriptor)

    override fun serialize(
        encoder: Encoder, value: SimpleLocation
    ) = encoder.encodeSerializableValue(
        delegateSerializer, doubleArrayOf(value.x, value.y, value.z, value.yaw.toDouble())
    )

    override fun deserialize(decoder: Decoder) = decoder.decodeSerializableValue(delegateSerializer).let {
        SimpleLocation(it[0], it[1], it[2], it.getOrElse(3) { 0.0 }.toFloat())
    }
}

@Serializable
data class Configuration(
    val spawnPos: SimpleLocation,
    val joinSign: SimpleLocation,
    val luckyBlockSpawns: List<SimpleLocation>,
    val colors: List<DyeColor>
) {
    companion object {
        val DEFAULT = Configuration(
            spawnPos = SimpleLocation.ZERO,
            joinSign = SimpleLocation.ZERO,
            luckyBlockSpawns = listOf(SimpleLocation.ZERO),
            colors = listOf(DyeColor.PURPLE, DyeColor.CYAN, DyeColor.YELLOW, DyeColor.GREEN)
        )
        var curr = this.DEFAULT
    }
}