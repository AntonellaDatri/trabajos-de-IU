package org.grupo8

import data.getUNQFlix
import org.grupo8.series.view.ViewSerieWindow
import org.grupo8.series.viewModel.ViewAndEditSerieVM
import org.uqbar.arena.Application
import org.uqbar.arena.windows.Window

class TestAppViewSerie : Application() {

    override fun createMainWindow(): Window<*> {
        val unqFlix = getUNQFlix()
        val aSerie = unqFlix.series.last()
        val modelObject = IndexVM()
        return ViewSerieWindow(this, ViewAndEditSerieVM(aSerie, unqFlix, modelObject))
    }


}

fun main(){
    TestAppViewSerie().start()
}