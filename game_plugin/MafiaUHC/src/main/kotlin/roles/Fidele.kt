package org.noursindev.mafiauhc.roles

class Fidele(nom : String, bInit : Array<Int>, bChoix : Array<Int>, ordre : Array<String>):Role(bInit = bInit, bChoix = bChoix, ordre = ordre) {
    override fun mfrole() : String{
        return "Role Fidele"
    }
}