package org.noursindev.mafiauhc.ressources.inventaires

import org.bukkit.inventory.Inventory
import org.bukkit.Bukkit
import org.bukkit.inventory.ItemStack
import org.bukkit.Material

fun configInvConstructeur() : Inventory {
    val inventaire : Inventory = Bukkit.createInventory(null, 5*9, "§dConfiguration")
    val joueurs : ItemStack = createPlayerHead("Steve", "§aJoueurs", 0)
    val roles : ItemStack = createPlayerHead("illusion96", "§9Roles", 0)
    val bordure : ItemStack = ItemStack(Material.GLASS, 1)
    val lanceur : ItemStack = ItemStack(Material.COMPASS, 1)
    val stuff : ItemStack = ItemStack(Material.CHEST, 1)

    val joueursmeta = joueurs.itemMeta
    val rolesmeta = roles.itemMeta
    val borduremeta = bordure.itemMeta
    val lanceurmeta = lanceur.itemMeta
    val stuffmeta = stuff.itemMeta

    return inventaire
}