package org.noursindev.mafiauhc

import org.bukkit.plugin.PluginManager
import org.bukkit.plugin.java.JavaPlugin
import org.noursindev.mafiauhc.ressources.Joueur
import org.noursindev.mafiauhc.ressources.*
import org.noursindev.mafiauhc.ressources.commandes.CommandesConfig
import org.noursindev.mafiauhc.ressources.commandes.CommandesIG


class MafiaUHC : JavaPlugin() {

    lateinit var config: Configuration
    private var phase: Phases = Phases.Configuration
    var ordre: Array<Joueur>? = null

    override fun onEnable() {
        logger.info("MafiaUHC est activé pour votre serveur.")

        val world = server.worlds[0]
        world.setGameRuleValue("naturalRegeneration", "false")
        world.setGameRuleValue("deathMessages", "false")

        config = Configuration(mutableSetOf(), Boite(this))

        setPhase(Phases.Configuration)

        val pm: PluginManager = server.pluginManager
        pm.registerEvents(Ecoutes(this), this)

        this.getCommand("mfc").executor = CommandesConfig(this)
        this.getCommand("mf").executor = CommandesIG(this)
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

    fun lancePartie() {
        run {
            val igtimer = GameTimer(this)
            igtimer.runTaskTimer(this, 0, 20)
        }
        config.initialBoite = config.boite
    }

}