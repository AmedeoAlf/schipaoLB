package sh.ftp.schipao.schipaoLB

import sh.ftp.schipao.schipaoLB.outcomes.EffectOutcome
import sh.ftp.schipao.schipaoLB.outcomes.ItemDropOutcome
import sh.ftp.schipao.schipaoLB.outcomes.LBOutcome
import sh.ftp.schipao.schipaoLB.outcomes.MessageOutcome
import sh.ftp.schipao.schipaoLB.outcomes.MobOutcome
import sh.ftp.schipao.schipaoLB.outcomes.MultiOutcome
import sh.ftp.schipao.schipaoLB.outcomes.StructureOutcome
import sh.ftp.schipao.schipaoLB.outcomes.TeleportOutcome
import sh.ftp.schipao.schipaoLB.outcomes.TitleOutcome
import sh.ftp.schipao.schipaoLB.outcomes.itemStackFromString

val defaultOutcomeList: List<LBOutcome> = listOf(
    TeleportOutcome(30.0),
    MessageOutcome("No luck?"),
    ItemDropOutcome(listOf(
            itemStackFromString("CHAINMAIL_HELMET;;;minecraft:protection"),
            itemStackFromString("CHAINMAIL_CHESTPLATE;;;minecraft:protection"),
            itemStackFromString("CHAINMAIL_LEGGINGS;;;minecraft:protection"),
            itemStackFromString("CHAINMAIL_BOOTS;;;minecraft:protection")
    )),
    ItemDropOutcome(listOf(
        itemStackFromString("DIAMOND_HELMET"),
        itemStackFromString("DIAMOND_CHESTPLATE")
    )),
    ItemDropOutcome(listOf(
        itemStackFromString("NETHERITE_HELMET")
    )),
    ItemDropOutcome(listOf(
        itemStackFromString("NETHERITE_CHESTPLATE")
    )),
    ItemDropOutcome(listOf(
        itemStackFromString("NETHERITE_LEGGINGS")
    )),
    ItemDropOutcome(listOf(
        itemStackFromString("NETHERITE_BOOTS")
    )),
    ItemDropOutcome(listOf(
        itemStackFromString("WOODEN_SWORD;;Buona Fortuna Soldato"),
        itemStackFromString("LEATHER_HELMET"),
        itemStackFromString("LEATHER_CHESTPLATE"),
        itemStackFromString("LEATHER_LEGGINGS"),
        itemStackFromString("LEATHER_BOOTS")
    )),
    ItemDropOutcome(listOf(
        itemStackFromString("GOLDEN_LEGGINGS;Barcollo ma non mollo;Corri lentamente mi raccomando;minecraft:swift_sneak/150"),
    )),
    ItemDropOutcome(listOf(
        itemStackFromString("TURTLE_HELMET")
    )),
    ItemDropOutcome(listOf(
        itemStackFromString("GOLDEN_SWORD;Hai una chance;UCCIDI un nemico, mi dispiace non abbiamo budget;minecraft:sharpness/200;31")
    )),
    ItemDropOutcome(listOf(
        itemStackFromString("IRON_SWORD"),
        itemStackFromString("IRON_AXE"),
        itemStackFromString("IRON_PICKAXE"),
        itemStackFromString("IRON_SHOVEL")
    )),
    ItemDropOutcome(listOf(
        itemStackFromString("NETHERITE_HOE;COMBATTI")
    )),
    ItemDropOutcome(listOf(
        itemStackFromString("NETHERITE_SWORD")
    )),
    ItemDropOutcome(listOf(
        itemStackFromString("NETHERITE_AXE;Leggermente Dell'Altra Sponda")
    )),
    ItemDropOutcome(listOf(
        itemStackFromString("DIAMOND_SWORD"),
        itemStackFromString("GOLDEN_APPLE")
    )),
    ItemDropOutcome(listOf(
        itemStackFromString("WIND_CHARGE/12")
    )),
    ItemDropOutcome(listOf(
        itemStackFromString("ENDER_PEARL/5")
    )),
    ItemDropOutcome(listOf(
        itemStackFromString("SHIELD;Codardo"),
        itemStackFromString("COPPER_AXE"),
        itemStackFromString("COPPER_PICKAXE")
    )),
    ItemDropOutcome(listOf(
        itemStackFromString("TRIDENT;VAI POSEIDONE;;minecraft:loyalty")
    )),
    ItemDropOutcome(listOf(
        itemStackFromString("TOTEM_OF_UNDYING")
    )),
    ItemDropOutcome(listOf(
        itemStackFromString("BOW;;;minecraft:infinity"),
        itemStackFromString("ARROW")
    )),
    ItemDropOutcome(listOf(
        itemStackFromString("DIAMOND/5"),
        itemStackFromString("EMERALD/7"),
        itemStackFromString("GOLD_INGOT/11"),
        itemStackFromString("IRON_INGOT/4")
    )),
    ItemDropOutcome(listOf(
        itemStackFromString("CREEPER_SPAWN_EGG/2"),
        itemStackFromString("ZOMBIE_SPAWN_EGG/3")
    )),
    ItemDropOutcome(listOf(
        itemStackFromString("COBBLESTONE/64")
    )),
    ItemDropOutcome(listOf(
        itemStackFromString("DIRT/64"),
        itemStackFromString("DIRT/64")
    )),
    ItemDropOutcome(listOf(
        itemStackFromString("NETHERRACK/24"),
        itemStackFromString("NETHER_QUARTZ_ORE/13"),
        itemStackFromString("NETHER_BRICKS/7"),
        itemStackFromString("RED_NETHER_BRICKS/9"),
        itemStackFromString("QUARTZ_BLOCK/2")
    )),
    ItemDropOutcome(listOf(
        itemStackFromString("PISTON"),
        itemStackFromString("STICKY_PISTON/3"),
        itemStackFromString("SLIME_BLOCK/6"),
        itemStackFromString("OBSERVER/2"),
        itemStackFromString("HOPPER/2"),
        itemStackFromString("LEVER"),
        itemStackFromString("STONE_BUTTON/3"),
        itemStackFromString("REDSTONE/24"),
        itemStackFromString("REPEATER/4"),
        itemStackFromString("COMPARATOR/2")
    )),
    ItemDropOutcome(listOf(itemStackFromString("CHEST/3"))),
    ItemDropOutcome(listOf(itemStackFromString("COOKED_BEEF/16"))),
    ItemDropOutcome(listOf(itemStackFromString("GOLDEN_CARROT/4"))),
    ItemDropOutcome(listOf(itemStackFromString("CAKE"))),
    ItemDropOutcome(listOf(itemStackFromString("MACE"))),
    ItemDropOutcome(listOf(
        itemStackFromString("CYAN_WOOL/3"),
        itemStackFromString("RED_WOOL/3"),
        itemStackFromString("PINK_WOOL/3"),
        itemStackFromString("YELLOW_WOOL/3"),
        itemStackFromString("BLUE_WOOL/3"),
        itemStackFromString("GREEN_WOOL/3")
    )),
    ItemDropOutcome(listOf(itemStackFromString("ENDER_CHEST"))),
    ItemDropOutcome(listOf(itemStackFromString("ELYTRA"))),
    ItemDropOutcome(listOf(
        itemStackFromString("FIREWORK_ROCKET/12")
    )),
    ItemDropOutcome(listOf(
        itemStackFromString("OBSIDIAN/20")
    )),
    MobOutcome(listOf("CREEPER/4", "LIGHTNING_BOLT")),
    MobOutcome(listOf("MAGMA_CUBE")),
    MobOutcome(listOf("GIANT")),
    MobOutcome(listOf("WITCH", "BAT/30")),
    MobOutcome(listOf("ENDERMAN")),
    MobOutcome(listOf("ELDER_GUARDIAN")),
    MobOutcome(listOf("IRON_GOLEM")),
    MobOutcome(listOf("PIG+PIG+PIG+PIG+PIG+PIG+PIG+PIG+PIG+PIG")),
    MobOutcome(listOf("GHAST+RAVAGER+COW+PIG+BLAZE+CREEPER+SKELETON+CHICKEN")),
    MultiOutcome(listOf(
        ItemDropOutcome(listOf(
            itemStackFromString("BONE/32")
        )),
        MobOutcome(listOf("WOLF/10")),
        TitleOutcome("§8Tame Them All", "Non Maltrattarli")
    )),
    MultiOutcome(listOf(
        ItemDropOutcome(listOf(
            itemStackFromString("WATER_BUCKET")
        )),
        TeleportOutcome(40.0),
        TitleOutcome("Be quick...", "§3C'è un secchio d'acqua nel tuo inventario ;-)"),
    )),
    MultiOutcome(listOf(
        EffectOutcome("blindness", 200, 3, true),
        ItemDropOutcome(listOf(
            itemStackFromString("WOODEN_SWORD"))
        ),
        MobOutcome(listOf(
            "ZOMBIE/4",
            "SKELETON/2",
            "SPIDER/3",
            "CREEPER",
            "SLIME/4"
        )),
        TitleOutcome("§4SURVIVE", "§7Fight till the end...")
    )),
    EffectOutcome("slowness", 200, 2),
    EffectOutcome("strength", 1200, 1),
    EffectOutcome("haste", 100, 10),
    EffectOutcome("infested", 300, 2),
    EffectOutcome("nausea", 200, 3),
    EffectOutcome("nausea", 200, 3),
    EffectOutcome("invisibility", 1000, 1),
    StructureOutcome("anvil_cage"),
    StructureOutcome("tnt_pillar", entities = true),
    StructureOutcome("lava_cage"),
    StructureOutcome("temple"),
    StructureOutcome("loot_crate"),
    StructureOutcome("spawner"),
    StructureOutcome("workshop"),
    StructureOutcome("horse_farm", entities = true)
)