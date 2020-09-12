/*
package org.grupo8

import data.getUNQFlix
import data.idGenerator
import domain.Category
import domain.ContentState
import domain.Serie
import org.grupo8.seasons.view.SeasonsMainWindow
import org.grupo8.seasons.viewModel.SeasonsMainWindowVM
import org.uqbar.arena.Application
import org.uqbar.arena.windows.Window

class TestSeason : Application() {
    override fun createMainWindow(): Window<*> {
        var category : MutableList<Category> = mutableListOf()
        var unqFlix = getUNQFlix()
        var serie: Serie = Serie(idGenerator.nextSerieId(), "miSerieDePrueba", "descripcion", "poster", ContentStatex, category)
        unqFlix.addSerie(serie)

     return SeasonsMainWindow(this, SeasonsMainWindowVM(unqFlix, serie))
    }
}



object ContentStatex : ContentState(){}

fun main() {
    TestSeason().start()
    //IngresarNombreYApellidoWindow(IngresarDatosAppModel()).startApplication()
}*/
