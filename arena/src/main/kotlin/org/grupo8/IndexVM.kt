package org.grupo8

import data.getUNQFlix
import domain.UNQFlix
import org.grupo8.series.viewModel.SerieVM
import org.uqbar.commons.model.annotations.Observable


@Observable
class IndexVM {
    val unqFlix: UNQFlix = getUNQFlix()
    var selected: SerieVM? = null
    var filteredSeries = unqFlix.series.map(::SerieVM).toMutableList()
    var searchTerm: String? = ""
        set(value) {
            field = value.toString()
            updateTable()
        }

    public fun updateTable() {
        filteredSeries = unqFlix.searchSeries(searchTerm.toString()).map(::SerieVM).toMutableList()
    }

    fun deleteSerie(idSerie : String){
        unqFlix.deleteSerie(idSerie)
        updateTable()
    }

}
