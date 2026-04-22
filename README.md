# schipaoLB

**A PaperMC luckyblocks plugin written in Kotlin** 

<p>
  <!-- Badges -->
  <img src="https://img.shields.io/badge/Minecraft-1.21.11-brightgreen" />
  <img src="https://img.shields.io/badge/Paper-Plugin-blue" />
</p>

---

## Features

- Customizables drops
- Batteries included (loot table present)
- Flexible team managment
- World protector to restore the map (`/wp` command)
- Minimal lobby implementation

## Installation
### Prerequisites

  - [PaperMC 1.21.11](https://papermc.io/)
  - Java 21

### Setup
1. Download the latest release from the **Releases** tab
> You can download our custom texturepack [here](http://schipao.ftp.sh/storage/LuckyBlocks.zip)
   
2. Place the `.jar` file into your `plugins/` folder 
3. Start your server

## Configuration

Current loot tables synthax

```json

[
   {
      "type":"<teleport/message/title/mob/itemdrop/multi/effect/structure>",
      "lucky": <from -1 to 1, optional>
   }
]
```

**Title configuration examples**
```json

[
   {
      "type":"teleport",
      "yDistance": <y coordinate>
      ...
   },
   {
      "type":"message",
      "message":"message that appears in chat",
      ...
   },
   {
      "type":"itemdrop",
      "items":[
         "<item name>/<quantity>;<display name>;<item lore>;<enchant>/<enchant level>;<item damage>",
         "CREEPER_SPAWN_EGG",
         "WOODEN_SWORD/1;;Buona Fortuna Soldato",
         "SHIELD/1;Codardo",
         "BOW/1;;;minecraft:infinity/1",
         "TRIDENT/1;VAI POSEIDONE;;minecraft:loyalty/1",
         ...
      ],
      ...
   },
   {
      "type":"mob",
          "entities":[
             "<mob name>/<mob counter>",
             ...
      ],
      ...
   },
   {
      "type":"effect",
      "effect":"<effect name>",
      "forTicks":<ticks>,
      "amplifier":<effect level>
      ...
   },
   {
      "type":"structure",
      "structure":"<nbt file name>"
      ...
   },
   {
      "type":"title",
      "title":"<title message>",
      "subtitle":"<subtitle message>"
      ...
   },
   {
      "type":"multi",
      "outcomes":[
         {
            "type":"<type name>",
            ...
         },
         ...
      ],
      ...
   },
   ...
]

```
