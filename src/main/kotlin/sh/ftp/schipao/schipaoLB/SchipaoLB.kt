package sh.ftp.schipao.schipaoLB

import org.bukkit.plugin.java.JavaPlugin

class SchipaoLB() : JavaPlugin() {

    override fun onEnable() {
        server.pluginManager.registerEvents(BlockDestroyListener(), this)
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}
