package org.noursindev.mafiauhc.ressources.inventaires

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.noursindev.mafiauhc.MafiaUHC

fun boiteInvConstruct(main : MafiaUHC) : Inventory {
    var inv : Inventory = Bukkit.createInventory(null, 18, "§dBoite de Cigares")
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

    val enfantdesrues : ItemStack = ItemStack(Material.STONE, -1)
    val edrmeta : ItemMeta = enfantdesrues.itemMeta
    edrmeta.displayName = "enfants des rues"
    enfantdesrues.itemMeta = edrmeta
    inv.addItem(enfantdesrues)

    return inv
}