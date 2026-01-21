package sh.ftp.schipao.schipaoLB

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.block.Block
import org.bukkit.entity.Entity
import org.bukkit.entity.EntityType
import org.bukkit.entity.Ghast
import org.bukkit.entity.Player

@SerialName("mob")
@Serializable
class MobOutcome(val entities: List<String>) : LBOutcome {
    // "PIG+PIG+PIG+PIG" => una stack di 4 PIG
    // "PIG/4" => 4 PIG
    override fun run(player: Player, block: Block) {
        entities.forEach {
            if (it.contains("+")) {
                it.split("+")
                    .map {
                        block.world.spawnEntity(block.location, EntityType.valueOf(it))
                            .apply { teleport(this.location.add(0.0, height, 0.0)) }
                    }
                    .windowed(2)
                    .map { (a, b) -> a.addPassenger(b) }
            } else {
                val (type, qty) = it.split("/") + "1"
                (0..qty.toInt()).forEach { _ ->
                    block.world.spawnEntity(block.location, EntityType.valueOf(type))
                        .apply { teleport(this.location.add(0.0, height, 0.0)) }
                }
            }
        }
    }
}