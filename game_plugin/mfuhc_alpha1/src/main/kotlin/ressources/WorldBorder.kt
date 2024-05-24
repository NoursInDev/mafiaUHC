package org.noursindev.mafiauhc.ressources

import org.bukkit.World

fun reduceWorldBorder(world: World, newSize: Double, duration: Long) {
    val border = world.worldBorder

    border.setSize(newSize * 2, duration)
}

fun setBorderDamage(world: World, damage: Double) {
    val border = world.worldBorder

    border.damageAmount = damage
}

fun setWorldBorderCenter(world: World, x: Double, z: Double) {
    val border = world.worldBorder

    border.setCenter(x, z)
}

fun activateWorldBorder(world: World, bordure: Array<Int>) {
    val border = world.worldBorder
    border.damageBuffer = 0.0
    border.damageAmount = bordure[4].toDouble()
    border.size = bordure[2].toDouble() * 2
}