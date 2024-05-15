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
import org.noursindev.mafiauhc.ressources.GameTimer
import org.noursindev.mafiauhc.ressources.commandes.CommandesIG


class MafiaUHC : JavaPlugin() {

    private var phase: Phases = Phases.Configuration
    var joueurs: MutableSet<Joueur> = mutableSetOf()
    lateinit var boite: Boite
    var ordre: Array<Joueur>? = null
    var parrain: Joueur? = null
        private set

    override fun onEnable() {
        logger.info("MafiaUHC est activé pour votre serveur.")

        val world = server.worlds[0]
        world.setGameRuleValue("naturalRegeneration", "false")
        world.setGameRuleValue("deathMessages", "false")

        setPhase(Phases.Configuration)
        joueurs = Bukkit.getOnlinePlayers().mapTo(mutableSetOf()) { Joueur(it as CraftPlayer) }
        boite = Boite(this)

        val pm: PluginManager = server.pluginManager
        pm.registerEvents(Ecoutes(this), this)

        this.getCommand("mfc").setExecutor(CommandesConfig(this))
        this.getCommand("mf").setExecutor(CommandesIG(this))
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

    fun setParrain(joueur: Joueur?) {
        joueurs.forEach { it.player.sendMessage("Le parrain est à présent ${joueur?.player?.name}.") }
    }

    fun setRandomParrain() {
        parrain = joueurs.random()
    }

    fun lancePartie() {
        run {
            val igtimer = GameTimer(this)
            igtimer.runTaskTimer(this, 0, 20)
        }
    }

}