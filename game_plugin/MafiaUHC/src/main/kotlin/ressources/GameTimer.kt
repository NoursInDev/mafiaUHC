package org.noursindev.mafiauhc.ressources

import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.scheduler.BukkitRunnable
import org.noursindev.mafiauhc.MafiaUHC
import org.bukkit.Bukkit
import org.noursindev.mafiauhc.resources.Joueur
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
            val message = TextComponent("Vous recevez la Boite de Cigares. Cliquez sur ce message pour l'ouvrir.")
            message.clickEvent = ClickEvent(ClickEvent.Action.RUN_COMMAND, "/mf ouvrir")
            message.color = net.md_5.bungee.api.ChatColor.DARK_PURPLE

            joueur.player.spigot().sendMessage(message)

            Bukkit.getScheduler().runTaskLater(main, {
                if (joueur.role == null) {
                    roleAttribution(joueur)
                    joueur.player.closeInventory()
                }
                joueur.tour = false
                go = true
            }, 600L)
        }
    }

    private fun roleAttribution(joueur: Joueur) {
        val rolesDisponibles = mutableListOf<RoleSuper>()
        if (main.config.boite.fideles > 0) rolesDisponibles.add(Fidele(main))
        if (main.config.boite.agents > 0) rolesDisponibles.add(Agent(main))
        if (main.config.boite.chauffeurs > 0) rolesDisponibles.add(Chauffeur(main))
        if (main.config.boite.nettoyeurs > 0) rolesDisponibles.add(Nettoyeur(main))
        if (main.config.boite.pierres > 0) rolesDisponibles.add(Voleur(main))

        if (rolesDisponibles.isEmpty()) {
            joueur.role = EnfantDesRues(main)

        } else {
            joueur.role = rolesDisponibles.random()
            when (joueur.role!!) {
                Fidele(main) -> main.config.boite.fideles--
                Agent(main) -> main.config.boite.agents--
                Chauffeur(main) -> main.config.boite.chauffeurs--
                Nettoyeur(main) -> main.config.boite.nettoyeurs--
                Voleur(main) -> main.config.boite.pierres--
            }
        }
        joueur.player.sendMessage("Votre rôle a été définit automatiquement. Vous êtes ${joueur.role!!.nom}.")
    }
}