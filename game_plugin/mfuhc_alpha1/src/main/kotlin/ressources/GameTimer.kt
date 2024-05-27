package org.noursindev.mafiauhc.ressources

import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.scheduler.BukkitRunnable
import org.noursindev.mafiauhc.MafiaUHC
import org.bukkit.Bukkit
import org.noursindev.mafiauhc.ressources.roles.*

class GameTimer(private val main: MafiaUHC) : BukkitRunnable() {
    private var time: Int = 0
    override fun run() {
        when (time) {
            15 -> {
                println("Tour de la boite.")
                lancerTours()
            }

            60 -> {
                main.setPhase(Phases.Active)
            }

            main.config.bordure[0] * 60 -> {
                reduceWorldBorder(
                    main.config.world!!,
                    main.config.bordure[3].toDouble(),
                    ((main.config.bordure[1] - main.config.bordure[0]) * 60).toLong()
                )
            }
        }
        time++
    }

    private fun lancerTours() {
        var go = true
        while (main.ordre!!.any { it.role == null }) {
            if (!go) break

            go = false
            val joueur = main.ordre!!.first { it.role == null }
            joueur.tour = true
            println("Tour de ${joueur.player.name}.")
            val message = TextComponent("§eClickez sur ce message pour ouvrir la boite, ou effecutez /mf ouvrir.")
            message.clickEvent = ClickEvent(ClickEvent.Action.RUN_COMMAND, "/mf ouvrir")
            joueur.player.spigot().sendMessage(message)

            Bukkit.getScheduler().runTaskLater(main, {
                if (joueur.role == null) {
                    roleAttribution(joueur)
                    joueur.player.closeInventory()
                }
                joueur.tour = false
                go = true
            }, 600L)

            if (joueur.role is Agent) {
                joueur.player.maxHealth = 26.0
            }
        }
        main.config.passage = true
        if (main.config.boite.pierres > 0) {
            main.config.parrain?.role?.pierres = main.config.parrain?.role?.pierres?.plus(main.config.boite.pierres)!!
        }

        main.scoreboards.createGameScoreboard()
    }

    private fun roleAttribution(joueur: Joueur) {
        val rolesDisponibles = mutableListOf<RoleSuper>()
        if (main.config.boite.fideles > 0) rolesDisponibles.add(Fidele(main))
        if (main.config.boite.agents > 0) rolesDisponibles.add(Agent(main))
        if (main.config.boite.chauffeurs > 0) rolesDisponibles.add(
            Chauffeur(
                main,
                main.config.joueurs.filter { it.player != joueur.player }.random()
            )
        )
        if (main.config.boite.nettoyeurs > 0) rolesDisponibles.add(Nettoyeur(main))
        if (main.config.boite.pierres > 0) rolesDisponibles.add(Voleur(main))

        if (rolesDisponibles.isEmpty()) {
            joueur.role = EnfantDesRues(main)

        } else {
            joueur.role = rolesDisponibles.random()
            when (joueur.role!!) {
                is Nettoyeur -> {
                    main.config.boite.nettoyeurs--
                    return
                }
                is Fidele -> main.config.boite.fideles--
                is Agent -> main.config.boite.agents--
                is Chauffeur -> main.config.boite.chauffeurs--
                is Voleur -> main.config.boite.pierres--
            }
        }
        joueur.player.sendMessage("Votre rôle a été définit automatiquement. Vous êtes ${joueur.role!!.nom}.")
    }
}