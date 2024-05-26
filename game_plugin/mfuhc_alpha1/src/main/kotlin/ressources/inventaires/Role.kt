package org.noursindev.mafiauhc.ressources.inventaires

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.noursindev.mafiauhc.ressources.Boite

fun boiteInvConstruct(boite: Boite, nom : String = "Boite de Cigares", firstplayer : Boolean = false): Inventory {
    val inv: Inventory = Bukkit.createInventory(null, 9, "§d$nom")
    var count = inv.size - 1

    for (element in 0 until boite.retourneBoite().size) {
        if (boite.retourneBoite().values.elementAt(element) > 0) {
            val key = boite.retourneBoite().keys.elementAt(element)
            val playerName = headLink[key] ?: "Notch"
            val stack: ItemStack = createPlayerHead(playerName, boite.retourneBoite().keys.elementAt(element), boite.retourneBoite().values.elementAt(element))
            val stackmeta: ItemMeta = stack.itemMeta
            stackmeta.displayName = boite.retourneBoite().keys.elementAt(element)
            stack.itemMeta = stackmeta
            inv.setItem(count, stack)

            println("Emplacement : $count, Item : ${stack.type}, Nom : ${stackmeta.displayName}, Quantité : ${stack.amount}")
            count--
        }
    }

    if (firstplayer) {
        val eloignement = ItemStack(Material.WOOL, 1)
        val eloignementmeta: ItemMeta = eloignement.itemMeta
        eloignementmeta.displayName = "§cEloigner un role"
        eloignement.itemMeta = eloignementmeta
        inv.setItem(0, eloignement)
    }

    val enfantdesrues: ItemStack = createPlayerHead(headLink["enfants des rues"] ?: "Notch", "enfants des rues", 1)
    val edrmeta: ItemMeta = enfantdesrues.itemMeta
    edrmeta.displayName = "enfants des rues"
    enfantdesrues.itemMeta = edrmeta
    inv.setItem(1, enfantdesrues)

    return inv
}

fun boiteEloigneConstruct(boite: Boite, nom : String = "Eloignement"): Inventory {
    val inv: Inventory = Bukkit.createInventory(null, 9, "§d$nom")
    var count = inv.size - 1

    for (element in 0 until boite.retourneBoite().size) {
        if (boite.retourneBoite().values.elementAt(element) > 0) {
            val key = boite.retourneBoite().keys.elementAt(element)
            val playerName = headLink[key] ?: "Notch"
            val stack: ItemStack = createPlayerHead(
                playerName,
                boite.retourneBoite().keys.elementAt(element),
                boite.retourneBoite().values.elementAt(element)
            )
            val stackmeta: ItemMeta = stack.itemMeta
            stackmeta.displayName = boite.retourneBoite().keys.elementAt(element)
            stack.itemMeta = stackmeta
            if (stack.itemMeta.displayName != "pierres") {
                inv.setItem(count, stack)
            }
            println("Emplacement : $count, Item : ${stack.type}, Nom : ${stackmeta.displayName}, Quantité : ${stack.amount}")
            count--
        }
    }
    return inv
}