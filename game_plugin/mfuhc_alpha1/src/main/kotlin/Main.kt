package org.noursindev.mafiauhc

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer
import org.bukkit.plugin.PluginManager
import org.bukkit.plugin.java.JavaPlugin
import org.noursindev.mafiauhc.ressources.Joueur
import org.noursindev.mafiauhc.ressources.*
import org.noursindev.mafiauhc.ressources.commandes.CommandesConfig
import org.noursindev.mafiauhc.ressources.commandes.CommandesDev
import org.noursindev.mafiauhc.ressources.commandes.CommandesIG


class MafiaUHC : JavaPlugin() {
    var devmode = false
    lateinit var config: Configuration
    private var phase: Phases = Phases.Configuration
    var ordre: Array<Joueur>? = null
    var scoreboards = ScoreboardsGestionnaire(this)

    override fun onEnable() {
        logger.info("MafiaUHC est activé pour votre serveur.")

        config = Configuration(mutableSetOf(), Boite(this))
        config.world = server.worlds[0]
        config.world!!.setGameRuleValue("naturalRegeneration", "false")
        config.world!!.setGameRuleValue("deathMessages", "false")

        for (player in server.onlinePlayers) {
            config.joueurs.add(Joueur(player as CraftPlayer))
        }

        setPhase(Phases.Configuration)

        val pm: PluginManager = server.pluginManager
        pm.registerEvents(Ecoutes(this), this)

        for (player in server.onlinePlayers) {
            scoreboards.waitingScoreboard(player)
        }

        this.getCommand("mfc").executor = CommandesConfig(this)
        this.getCommand("mf").executor = CommandesIG(this)
        this.getCommand("mfd").executor = CommandesDev(this)
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
        config.initialBoite = config.boite.clone() as Boite
    }
// THE SOONEST : ScoreBoard, NEXT : Tab, FINAL : Tests
}