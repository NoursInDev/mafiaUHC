package org.noursindev.mafiauhc.ressources.commandes

import org.bukkit.Bukkit
import org.bukkit.command.CommandExecutor
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.meta.ItemMeta
import org.noursindev.mafiauhc.MafiaUHC
import org.noursindev.mafiauhc.ressources.roles.*

class CommandesIG(private val main : MafiaUHC) : CommandExecutor {

    private fun setInvItems(inv: Inventory) {
        var count = inv.size - 1
        for (element in 0 until main.boite.retourneBoite().size) {
            if (main.boite.retourneBoite().values.elementAt(element) > 0) {
                val stack : ItemStack = ItemStack(Material.STONE, main.boite.retourneBoite().values.elementAt(element))
                val stackmeta : ItemMeta = stack.itemMeta
                stackmeta.displayName = main.boite.retourneBoite().keys.elementAt(element)
                stack.itemMeta = stackmeta
                inv.setItem(count, stack)
                println("Emplacement : $count, Item : ${stack.type}, Nom : ${stackmeta.displayName}, Quantité : ${stack.amount}")
                count--
            }
        }
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        val joueur = main.joueurs.find { it.player.name == sender.name }
        if (args.size > 0) {
            when(args[0]) {
                "ordre" -> {
                    sender.sendMessage("Ordre des joueurs:")
                    for (joueur in 0 until (main.ordre?.size ?: 0)) {
                        sender.sendMessage("${joueur+1}. ${main.ordre?.get(joueur)?.player?.name}")
                    }
                }
                "ouvrir" -> {
                    if (joueur != null && joueur.tour) {
                        var inv : Inventory = Bukkit.createInventory(null, 9, "§8Boite de Cigares")
                        setInvItems(inv)
                        joueur.player.openInventory(inv)
                    } else {
                        sender.sendMessage("Vous ne pouvez pas ouvrir la Boite")
                    }
                }
                "prendre" -> {
                    if (args.size > 2 && joueur != null && joueur.tour) {
                        when (args[1]) {
                            "pierres" -> {
                                if (args.size > 2 || main.boite.pierres >= args[2].toInt()) {
                                    main.boite.pierres -= args[2].toInt()
                                    joueur.role = Voleur(main)
                                } else {
                                    sender.sendMessage("Il n'y a pas assez de pierres dans la boite.")
                                }
                            }
                            "fidele" -> {
                                if (main.boite.fideles > 0) {
                                    main.boite.fideles--
                                    joueur.role = Fidele(main)
                                } else {
                                    sender.sendMessage("Il n'y a plus de fidèles dans la boite")
                                }
                            }
                            "agent" -> {
                                if (main.boite.agents > 0) {
                                    main.boite.agents--
                                    joueur.role = Agent(main)
                                } else {
                                    sender.sendMessage("Il n'y a plus d'agents dans la boite")
                                }
                            }
                            "chauffeur" -> {
                                if (main.boite.chauffeurs > 0) {
                                    main.boite.chauffeurs--
                                    joueur.role = Chauffeur(main)
                                } else {
                                    sender.sendMessage("Il n'y a plus de chauffeurs dans la boite")
                                }
                            }
                            "nettoyeur" -> {
                                if (main.boite.nettoyeurs > 0) {
                                    main.boite.nettoyeurs--
                                    joueur.role = Nettoyeur(main)
                                } else {
                                    sender.sendMessage("Il n'y a plus de nettoyeurs dans la boite")
                                }
                            }
                            "enfantdesrues" -> {
                                if (main.ordre?.lastOrNull() == joueur || main.boite.retourneBoite().all { it.value == 0 }) {
                                    joueur?.role = EnfantDesRues(main)
                                } else {
                                    sender.sendMessage("Il reste des roles dans la boite")
                                }
                            }
                        }
                    }
                }
                else -> {
                    sender.sendMessage("Usage: /mf <ordre|ouvrir|prendre>")
                }
            }
        }
        return true
    }
}