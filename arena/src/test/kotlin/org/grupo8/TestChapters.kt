package org.grupo8

import data.getUNQFlix
import org.grupo8.capitulos.view.ViewChapters
import org.grupo8.capitulos.viewmodel.ViewChaptersVM
import org.grupo8.seasons.viewModel.SeasonVM
import org.uqbar.arena.Application
import org.uqbar.arena.windows.Window

class TestChapters : Application() {
    override fun createMainWindow(): Window<*> {
        val unqFlix = getUNQFlix()
        val serie = unqFlix.series.last()
        val season = serie.seasons.last()
        val seasonVM = SeasonVM(season)
        seasonVM.season = season
        return ViewChapters(this, ViewChaptersVM(unqFlix,serie, seasonVM))
    }
}

fun main() {
    TestChapters().start()
}