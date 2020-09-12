package org.grupo8

import org.uqbar.arena.Application
import org.uqbar.arena.windows.Window

class TestGral : Application() {

    override fun createMainWindow(): Window<*> {
        return IndexWindow(this, IndexVM())
    }


}

fun main(){
    TestGral().start()
}