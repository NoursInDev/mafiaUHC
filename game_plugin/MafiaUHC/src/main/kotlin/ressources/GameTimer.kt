package org.noursindev.mafiauhc.ressources

import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.scheduler.BukkitRunnable
import org.noursindev.mafiauhc.MafiaUHC
import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import org.noursindev.mafiauhc.resources.Joueur
import org.noursindev.mafiauhc.ressources.roles.*

class GameTimer(private val main : MafiaUHC): BukkitRunnable() {
    private var time: Int = 0
    override fun run() {
        when (time) {
            15 -> {
                println("Tour de la boite.")
                lancerTours()
            }
        }
        time++
    }

    private fun lancerTours() {
        var go : Boolean = true
        while (main.ordre!!.any() { it.role == null }) {
            if (!go) break
            go = false
            val joueur = main.ordre!!.first { it.role == null }
            joueur.tour = true
            val message = TextComponent("Vous recevez la Boite de Cigares. ")
            message.clickEvent = ClickEvent(ClickEvent.Action.RUN_COMMAND, "/mf ouvrir")

            joueur.player.spigot().sendMessage(message)

            Bukkit.getScheduler().runTaskLater(main, Runnable {
                if (joueur.role == null) {
                    roleAttribution(joueur)
                }
                joueur.tour = false
                go = true
            }, 600L)
        }
    }

    private fun roleAttribution(joueur: Joueur) {
            val rolesDisponibles = mutableListOf<RoleSuper>()
            if (main.boite.fideles > 0) rolesDisponibles.add(Fidele(main))
            if (main.boite.agents > 0) rolesDisponibles.add(Agent(main))
            if (main.boite.chauffeurs > 0) rolesDisponibles.add(Chauffeur(main))
            if (main.boite.nettoyeurs > 0) rolesDisponibles.add(Nettoyeur(main))
            if (main.boite.pierres > 0) rolesDisponibles.add(Voleur(main))

            if (rolesDisponibles.isEmpty()) {
                joueur.role = EnfantDesRues(main)

            } else {
                joueur.role = rolesDisponibles.random()
                when (joueur.role!!) {
                    Fidele(main) -> main.boite.fideles--
                    Agent(main) -> main.boite.agents--
                    Chauffeur(main) -> main.boite.chauffeurs--
                    Nettoyeur(main) -> main.boite.nettoyeurs--
                    Voleur(main) -> main.boite.pierres--
                }
        }
        joueur.player.sendMessage("Votre rôle a été définit automatiquement. Vous êtes ${joueur.role!!.nom}.")
    }
}