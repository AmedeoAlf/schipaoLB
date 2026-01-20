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
    override fun run(player: Player, block: Block) {
        entities
            .map {
                block.world.spawnEntity(block.location, EntityType.valueOf(it))
                    .apply { teleport(this.location.add(0.0, height, 0.0)) }
            }
            .windowed(2)
            .forEach { (a, b) -> a.addPassenger(b) }
    }
}