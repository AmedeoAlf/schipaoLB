package sh.ftp.schipao.schipaoLB.outcomes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.block.Block
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import sh.ftp.schipao.schipaoLB.parseQuantity

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
                            .apply { teleport(block.location.add(0.0, height, 0.0)) }
                    }
                    .windowed(2)
                    .forEach { (a, b) -> a.addPassenger(b) }
            } else {
                val (type, qty) = parseQuantity(it)
                (1..qty).forEach { _ ->
                    block.world.spawnEntity(block.location, EntityType.valueOf(type))
                        .apply { teleport(this.location.add(0.0, height, 0.0)) }
                }
            }
        }
    }
}