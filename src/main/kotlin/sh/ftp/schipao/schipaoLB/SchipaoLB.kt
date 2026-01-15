package sh.ftp.schipao.schipaoLB

import kotlinx.serialization.Serializable
import org.bukkit.plugin.java.JavaPlugin

/*
  "texture": "",
  "name": "&eLuckyBlock",
  "custom_name": "LUCKYBLOCK",
  "lore": "&7Test your luck",
  "animation": "true",
  "animation_type": "MOBSPAWNER_FLAMES",
  "drop": [
    {
      "BOW": 1,
      "ARROW": 16,
      "LEVER": 2,
      "BREAD": 6
    },
    {
      "DIAMOND_PICKAXE": 1,
      "APPLE": 8,
      "SNOWBALL": 8,
      "STONE": 16
    },
    {
      "STONE_SWORD": {
        "quantity" : 1,
        "name": "Stone sword",
        "enchants": {
          "DAMAGE_ALL": 2
        }
      },
      "WHITE_WOOL": 32,
      "ARROW": 16
    },
    {
      "IRON_SWORD": 1,
      "IRON_CHESTPLATE": 1,
      "SNOWBALL": 8
    },
    {
      "IRON_LEGGINGS": 1,
      "IRON_BOOTS": 1,
      "BOW": 1,
      "EGG": 8,
      "BREAD": 6
    },
    {
      "APPLE": 8,
      "ENDER_PEARL": 1
    },
    {
      "STONE": 11,
      "RED_WOOL": 7,
      "LEVER": 2,
      "FLINT_AND_STEEL": 1
    },
    {
      "LEATHER_CHESTPLATE": {
        "quantity": 1,
        "name": "Leather Chestplate ",
        "enchants": {
          "PROTECTION_ENVIRONMENTAL": 3
        }
      },
      "LEATHER_LEGGINGS": 1,
      "EGG": 8
    },
    {
      "CHAINMAIL_HELMET": 1,
      "CHAINMAIL_LEGGINGS": {
        "quantity" : 1,
        "name": "Chainmail Leggings",
        "enchants": {
          "PROTECTION_ENVIRONMENTAL": 2
        }
      },
      "ARROW": 12
    },
    {
      "APPLE": 8,
      "COBBLESTONE" : 9
    },
    {
      "APPLE": 6,
      "EGG": 16,
      "LEGACY_FIREWORK_CHARGE": 1
    },
    {
      "EGG": 16,
      "TNT": 1,
      "BREAD": 6
    },
    {
      "SNOWBALL": 16,
      "OAK_LOG": 16,
      "TNT": 1,
      "FLINT_AND_STEEL": 1
    },
    {
      "LEGACY_GOLD_CHESTPLATE": 1,
      "CHAINMAIL_BOOTS": 1,
      "BREAD": 6
    },
    {
      "OAK_LOG": 5,
      "LAVA_BUCKET": 1
    },
    {
      "DIAMOND_SWORD": 1,
      "BREAD": 6,
      "LAVA_BUCKET": 1
    },
    {
      "BREAD": 6,
      "IRON_LEGGINGS": {
        "quantity" : 1,
        "name": "Iron leggings",
        "enchants": {
          "PROTECTION_ENVIRONMENTAL": 1
        }
      },
      "OBSIDIAN": 1
    },
    {
      "DIAMOND_HELMET": 1,
      "ARROW": 4,
      "BREAD": 6,
      "OAK_LOG": 13
    },
    {
      "DIAMOND_BOOTS": 1,
      "EGG": 32
    },
    {
      "LEVER": 2,
      "TNT": 2,
      "OAK_LOG": 2,
      "BREAD": 2
    },
    {
      "LAVA_BUCKET": 1,
      "FLINT_AND_STEEL": 1,
      "IRON_CHESTPLATE": 12
    }
  ]
}
 */

@Serializable
data class Drop(val item: String, val quantity: Int, val name: String, val enchantment: List<String>)

@Serializable
data class Data(val drops: List<Map<String, Drop>>)

class SchipaoLB() : JavaPlugin() {

    override fun onEnable() {
        server.pluginManager.registerEvents(BlockDestroyListener(), this)
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}
