package sh.ftp.schipao.schipaoLB

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.block.Block
import org.bukkit.entity.Entity
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player

@SerialName("mob")
@Serializable
class MobOutcome(val entity: String, val passenger: MobOutcome?) : LBOutcome {
    override fun run(player: Player, block: Block) {
        var entity: Entity? = null
        var curr: MobOutcome? = this
        while (curr != null) {
            val newEntity = block.world.spawnEntity(block.location, EntityType.valueOf(curr.entity))
            entity?.addPassenger(newEntity)
            entity = newEntity
            curr = curr.passenger
        }
    }
}