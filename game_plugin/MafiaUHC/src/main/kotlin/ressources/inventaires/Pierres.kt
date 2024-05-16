package org.noursindev.mafiauhc.ressources.inventaires

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.noursindev.mafiauhc.MafiaUHC

fun pierresInvConstruct(main : MafiaUHC) : Inventory {
    val invpierres = Bukkit.createInventory(null, 9, "§dPierres")

    val moins = ItemStack(Material.REDSTONE_BLOCK, 0)
    val moinsmeta: ItemMeta = moins.itemMeta
    moinsmeta.displayName = "-1"
    moins.itemMeta = moinsmeta

    val plus = ItemStack(Material.EMERALD_BLOCK, 0)
    val plusmeta: ItemMeta = plus.itemMeta
    plusmeta.displayName = "+1"
    plus.itemMeta = plusmeta

    val pierres = createPlayerHead(headLink["pierres"] ?: "Notch", "Pierres", main.config.boite.pierres)
    val pierresmeta: ItemMeta = pierres.itemMeta
    pierresmeta.displayName = "Pierres"
    pierres.itemMeta = pierresmeta

    val valider = ItemStack(Material.GLASS, 0)
    val validermeta: ItemMeta = valider.itemMeta
    validermeta.displayName = "Valider"
    valider.itemMeta = validermeta

    val retour = ItemStack(Material.WOOL, 0)
    val retourmeta: ItemMeta = retour.itemMeta
    retourmeta.displayName = "Retour"
    retour.itemMeta = retourmeta

    invpierres.setItem(0, retour)
    invpierres.setItem(1, moins)
    invpierres.setItem(4, pierres)
    invpierres.setItem(7, plus)
    invpierres.setItem(8, valider)

    return invpierres
}