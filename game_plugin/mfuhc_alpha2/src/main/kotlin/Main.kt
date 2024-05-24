package org.noursindev.mafiauhc

import org.bukkit.plugin.java.JavaPlugin

class MafiaUHC : JavaPlugin() {
    override fun onEnable() {
        logger.info("MafiaUHC est activé pour votre serveur.")
    }

    override fun onDisable() {
        logger.info("MafiaUHC est désactivé pour votre serveur.")
    }
}