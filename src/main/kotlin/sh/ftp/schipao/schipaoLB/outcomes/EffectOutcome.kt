package sh.ftp.schipao.schipaoLB.outcomes

import io.papermc.paper.registry.RegistryAccess
import io.papermc.paper.registry.RegistryKey
import io.papermc.paper.registry.TypedKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.kyori.adventure.key.Key
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect

@SerialName("effect")
@Serializable
class EffectOutcome(val effect: String, val forTicks: Int, val amplifier: Int, val particles: Boolean) : LBOutcome {
    override fun run(player: Player, block: Block) {
        val key = TypedKey.create(RegistryKey.MOB_EFFECT, Key.key(effect))
        val effectType = RegistryAccess.registryAccess().getRegistry(RegistryKey.MOB_EFFECT).get(key)!!
        player.addPotionEffect(
            PotionEffect(effectType, forTicks, amplifier, false, particles, true)
        )
    }
}