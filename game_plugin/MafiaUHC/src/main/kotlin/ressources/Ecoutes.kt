package org.noursindev.mafiauhc.ressources

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.noursindev.mafiauhc.MafiaUHC
import org.noursindev.mafiauhc.resources.Joueur
import org.noursindev.mafiauhc.ressources.roles.Agent
import org.noursindev.mafiauhc.ressources.roles.Chauffeur
import org.noursindev.mafiauhc.ressources.roles.Fidele
import org.noursindev.mafiauhc.ressources.roles.Nettoyeur

class Ecoutes(private val main : MafiaUHC) : Listener {
    @EventHandler
    fun arriveeJoueur(event: PlayerJoinEvent) {
        if (main.getPhase() == Phases.Configuration) {
            main.joueurs.add(Joueur(event.player as CraftPlayer))
        } else {
            if ((main.joueurs.find { it.player == event.player } == null) && (!event.player.isOp)) {
                event.player.kickPlayer("@${main.name} Une partie est déjà en cours et vous n'êtes pas autorisé à la rejoindre.")
            } else if (main.joueurs.find { it.player.name == event.player.name} == null) {
                event.player.gameMode = org.bukkit.GameMode.SPECTATOR
                event.player.sendMessage("@${main.name} " + ChatColor.RED.toString() + "Vous êtes en mode spectateur car une partie est déjà en cours.")
            } else {
                event.player.sendMessage("@${main.name} " + ChatColor.GREEN.toString() + "Bienvenue dans la partie!")
            }
        }
    }

    @EventHandler
    fun departJoueur(event: PlayerQuitEvent) {
        if (main.getPhase() == Phases.Configuration && main.joueurs.find { it.player == event.player } != null) {
            main.joueurs.remove(main.joueurs.find { it.player == event.player }!!)
        }
    }

    @EventHandler
    private fun onClick(event : InventoryClickEvent) {
        val inv = event.inventory
        val joueur = main.joueurs.find { it.player == event.whoClicked as CraftPlayer }
        val item = event.currentItem

        if (inv == null || inv.name == null || item == null || joueur == null) return

        if (inv.name == "§8Boite de Cigares" ) {
            when (item.itemMeta.displayName) {
                "pierres" -> {
                    val invpierres = Bukkit.createInventory(null, 9, "§8Pierres")

                    val moins = ItemStack(Material.REDSTONE_BLOCK, main.boite.pierres)
                    val moinsmeta : ItemMeta = moins.itemMeta
                    moinsmeta.displayName = "-1"
                    moins.itemMeta = moinsmeta

                    val plus = ItemStack(Material.EMERALD_BLOCK, main.boite.pierres)
                    val plusmeta : ItemMeta = plus.itemMeta
                    plusmeta.displayName = "+1"
                    plus.itemMeta = plusmeta

                    val pierres = ItemStack(Material.STONE, main.boite.pierres)
                    val pierresmeta : ItemMeta = pierres.itemMeta
                    pierresmeta.displayName = "Pierres"
                    pierres.itemMeta = pierresmeta

                    inv.setItem(1, moins)
                    inv.setItem(4, pierres)
                    inv.setItem(7, plus)

                    println(invpierres.toString())

                    joueur.player.closeInventory()

                    joueur.player.openInventory(invpierres)
                }
                "fideles" -> {
                    main.boite.fideles--
                    joueur.role = Fidele(main)
                }
                "agents" -> {
                    main.boite.agents--
                    joueur.role = Agent(main)
                }
                "chauffeurs" -> {
                    main.boite.chauffeurs--
                    joueur.role = Chauffeur(main)
                }
                "nettoyeurs" -> {
                    main.boite.nettoyeurs--
                    joueur.role = Nettoyeur(main)
                }
            }
            joueur.player.closeInventory()
        }

    }
}