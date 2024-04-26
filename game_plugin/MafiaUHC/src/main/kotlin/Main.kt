package org.noursindev.mafiauhc

import org.bukkit.Bukkit
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer
import org.bukkit.plugin.PluginManager
import org.bukkit.plugin.java.JavaPlugin
import org.noursindev.mafiauhc.resources.Joueur
import org.noursindev.mafiauhc.ressources.Ecoutes
import org.noursindev.mafiauhc.ressources.Phases
import org.noursindev.mafiauhc.ressources.commandes.CommandesConfig
import org.noursindev.mafiauhc.ressources.Boite


class MafiaUHC : JavaPlugin() {

    private var phase: Phases = Phases.Configuration
    var joueurs: MutableSet<Joueur> = mutableSetOf()
    lateinit var boite: Boite

    override fun onEnable() {
        logger.info("MafiaUHC est activé pour votre serveur.")
        setPhase(Phases.Configuration)
        joueurs = Bukkit.getOnlinePlayers().mapTo(mutableSetOf()) { Joueur(it as CraftPlayer) }
        boite = Boite(this)

        val pm: PluginManager = server.pluginManager
        pm.registerEvents(Ecoutes(this), this)

        this.getCommand("mfc").setExecutor(CommandesConfig(this))
    }

    override fun onDisable() {
        logger.info("MafiaUHC est désactivé pour votre serveur.")
    }

    fun setPhase(phase: Phases) {
        this.phase = phase
    }

    fun getPhase(): Phases {
        return phase
    }

}