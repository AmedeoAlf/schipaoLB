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
    TeleportOutcome(30.0, lucky = -0.9f),
    MessageOutcome("No luck?", lucky = -0.2f),
    ItemDropOutcome(listOf(
            itemStackFromString("CHAINMAIL_HELMET;;;minecraft:protection"),
            itemStackFromString("CHAINMAIL_CHESTPLATE;;;minecraft:protection"),
            itemStackFromString("CHAINMAIL_LEGGINGS;;;minecraft:protection"),
            itemStackFromString("CHAINMAIL_BOOTS;;;minecraft:protection")
    ), lucky = -0.1f),
    ItemDropOutcome(listOf(
        itemStackFromString("DIAMOND_HELMET"),
        itemStackFromString("DIAMOND_CHESTPLATE")
    )),
    ItemDropOutcome(listOf(
        itemStackFromString("NETHERITE_HELMET")
    ), lucky = 0.4f),
    ItemDropOutcome(listOf(
        itemStackFromString("NETHERITE_CHESTPLATE")
    ), lucky = 0.2f),
    ItemDropOutcome(listOf(
        itemStackFromString("NETHERITE_LEGGINGS")
    ), lucky = 0.4f),
    ItemDropOutcome(listOf(
        itemStackFromString("NETHERITE_BOOTS")
    ), lucky = 0.3f),
    ItemDropOutcome(listOf(
        itemStackFromString("WOODEN_SWORD;;Buona Fortuna Soldato"),
        itemStackFromString("LEATHER_HELMET"),
        itemStackFromString("LEATHER_CHESTPLATE"),
        itemStackFromString("LEATHER_LEGGINGS"),
        itemStackFromString("LEATHER_BOOTS")
    ), lucky = -0.1f),
    ItemDropOutcome(listOf(
        itemStackFromString("GOLDEN_LEGGINGS;Barcollo ma non mollo;Corri lentamente mi raccomando;minecraft:swift_sneak/150"),
    ), lucky = 0.1f),
    ItemDropOutcome(listOf(
        itemStackFromString("TURTLE_HELMET")
    )),
    ItemDropOutcome(listOf(
        itemStackFromString("GOLDEN_SWORD;Hai una chance;UCCIDI un nemico, mi dispiace non abbiamo budget;minecraft:sharpness/200;31")
    ), lucky = 1f),
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
    ), lucky = 0.9f),
    ItemDropOutcome(listOf(
        itemStackFromString("NETHERITE_AXE;Leggermente Dell'Altra Sponda")
    ), lucky = 1f),
    ItemDropOutcome(listOf(
        itemStackFromString("DIAMOND_SWORD"),
        itemStackFromString("GOLDEN_APPLE")
    ), lucky = 0.2f),
    ItemDropOutcome(listOf(
        itemStackFromString("WIND_CHARGE/12")
    ), lucky = 0.1f),
    ItemDropOutcome(listOf(
        itemStackFromString("ENDER_PEARL/5")
    ), lucky = 0.5f),
    ItemDropOutcome(listOf(
        itemStackFromString("SHIELD;Codardo"),
        itemStackFromString("COPPER_AXE"),
        itemStackFromString("COPPER_PICKAXE")
    ), lucky = 0.2f),
    ItemDropOutcome(listOf(
        itemStackFromString("TRIDENT;VAI POSEIDONE;;minecraft:loyalty")
    ), lucky = 0.4f),
    ItemDropOutcome(listOf(
        itemStackFromString("TOTEM_OF_UNDYING")
    ), lucky = 0.9f),
    ItemDropOutcome(listOf(
        itemStackFromString("BOW;;;minecraft:infinity"),
        itemStackFromString("ARROW")
    ), lucky = 0.1f),
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
    ), lucky = 0.1f),
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
    ItemDropOutcome(listOf(itemStackFromString("ELYTRA;;;;200")), lucky = 1f),
    ItemDropOutcome(listOf(
        itemStackFromString("FIREWORK_ROCKET/12")
    ), lucky = 0.9f),
    ItemDropOutcome(listOf(
        itemStackFromString("OBSIDIAN/20")
    )),
    MobOutcome(listOf("CREEPER/4", "LIGHTNING_BOLT"), lucky = -1f),
    MobOutcome(listOf("MAGMA_CUBE"), lucky = -0.3f),
    MobOutcome(listOf("GIANT"), lucky = -0.3f),
    MobOutcome(listOf("WITCH", "BAT/30"), lucky = -0.6f),
    MobOutcome(listOf("ENDERMAN")),
    MobOutcome(listOf("ELDER_GUARDIAN"), lucky = -0.9f),
    MobOutcome(listOf("IRON_GOLEM")),
    MobOutcome(listOf("PIG+PIG+PIG+PIG+PIG+PIG+PIG+PIG+PIG+PIG")),
    MobOutcome(listOf("GHAST+RAVAGER+COW+PIG+BLAZE+CREEPER+SKELETON+CHICKEN"), lucky = -0.8f),
    MultiOutcome(listOf(
        ItemDropOutcome(listOf(
            itemStackFromString("BONE/32")
        )),
        MobOutcome(listOf("WOLF/10")),
        TitleOutcome("<dark_gray>Tame Them All</dark_gray>", "Non Maltrattarli")
    ), lucky = 0.7f),
    MultiOutcome(listOf(
        ItemDropOutcome(listOf(
            itemStackFromString("WATER_BUCKET")
        )),
        TeleportOutcome(40.0),
        TitleOutcome("Be quick...", "<dark_aqua>C'è un secchio d'acqua nel tuo inventario ;-)</dark_aqua>"),
    ), lucky = -0.9f),
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
        TitleOutcome("<dark_red>SURVIVE</dark_red>", "<gray>Fight till the end...</gray>")
    ), lucky = -1f),
    EffectOutcome("slowness", 200, 2),
    EffectOutcome("strength", 1200, 1, lucky = 0.2f),
    EffectOutcome("haste", 100, 10, lucky = 0.2f),
    EffectOutcome("infested", 300, 2),
    EffectOutcome("nausea", 200, 3),
    EffectOutcome("invisibility", 1000, 1),
    StructureOutcome("anvil_cage"),
    StructureOutcome("tnt_pillar", entities = true, lucky = -0.7f),
    StructureOutcome("lava_cage"),
    StructureOutcome("temple"),
    StructureOutcome("loot_crate"),
    StructureOutcome("spawner"),
    StructureOutcome("workshop"),
    StructureOutcome("horse_farm", entities = true)
)