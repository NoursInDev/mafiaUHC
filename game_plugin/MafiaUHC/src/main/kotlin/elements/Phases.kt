package org.noursindev.mafiauhc.elements

import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.Server

interface Phases {
    val nomphase : String
}

class ConfigPhase(configurateur : CommandSender) : Phases {
    override val nomphase = "Phase de configuration"
    init {
        configurateur.sendMessage("Configuration de la partie")
    }
}