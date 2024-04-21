package org.noursindev.mafiauhc.roles

abstract class Role(bInit : Array<Int>, bChoix : Array<Int>, ordre : Array<String>) {
    var pierres : Int = 0
    val bInit : Array<Int>
    val bChoix : Array<Int>
    val ordre : Array<String>

    init {
        this.bInit = bInit
        this.bChoix = bChoix
        this.ordre = ordre
    }

    // @commandes
    fun mfhelp() {
        //todo
    }

    fun mfboite(n : Int) : Array<Int> {
        when (n) {
            1 -> return bInit
            2 -> return bChoix
            else -> return arrayOf(-1, -1, -1, -1, -1)
        }
    }

    fun mfnbp() : Int {
        return pierres
    }

    fun mfdonnepierres(nb : Int?) {
        //todo
    }

    fun mfordre() : Array<String> {
        return ordre
    }

    abstract fun mfrole() : String

    open fun mfactivate() : Boolean {
        return false
    }

}