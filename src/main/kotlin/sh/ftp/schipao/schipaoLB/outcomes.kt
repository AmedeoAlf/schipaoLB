package sh.ftp.schipao.schipaoLB

import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.Damageable

val outcomes = listOf(
    TeleportOutcome(30.0), MessageOutcome { Component.text("IDK immagina sia un altro drop casuale") }, ItemDropOutcome(
        listOf(
            ItemStack.of(Material.GOLDEN_SWORD).apply {
                itemMeta = (itemMeta as Damageable).apply {
                    damage = Material.GOLDEN_SWORD.maxDurability - 1
                    displayName(Component.text("Hai una chance"))
                }
                addUnsafeEnchantment(Enchantment.SHARPNESS, 200)
            })
    )
)

