package org.noursindev.mafiauhc.ressources.inventaires

import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.noursindev.mafiauhc.ressources.Boite

fun boiteInvConstruct(boite: Boite, nom : String = "Boite de Cigares"): Inventory {
    val inv: Inventory = Bukkit.createInventory(null, 18, "§d$nom")
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

    val enfantdesrues: ItemStack = createPlayerHead(headLink["enfants des rues"] ?: "Notch", "enfants des rues", 1)
    val edrmeta: ItemMeta = enfantdesrues.itemMeta
    edrmeta.displayName = "enfants des rues"
    enfantdesrues.itemMeta = edrmeta
    inv.addItem(enfantdesrues)

    return inv
}