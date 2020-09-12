package org.grupo8.seasons.viewModel

import data.idGenerator
import domain.ExistsException
import domain.Season
import domain.UNQFlix
import org.grupo8.IndexVM
import org.grupo8.series.viewModel.SerieVM
import org.uqbar.commons.model.annotations.Observable
import org.uqbar.commons.model.exceptions.UserException

@Observable

class SeasonsMainWindowVM(val unqFlix: UNQFlix,  val mainVM: IndexVM, serieVM: SerieVM){

    var serie = serieVM.serie
    var filterSeasons : MutableSet<SeasonVM> = serie.seasons.map(::SeasonVM).toMutableSet()
    var selected : SeasonVM? = null
    var titleSerie : String = serie.title

// ACTUALIZA LA TABLA
    fun actualize() {
        filterSeasons = serie.seasons.map(::SeasonVM).toMutableSet()
        mainVM.updateTable()
    }
    //AGREAGA UNA NUEVA TEMPORADA Y ACTUALIZA LA TABLA
    fun addNewSeason(seasonVM : SeasonVM){
    val newSeason = Season(idGenerator.nextSeasonId(),seasonVM.title,seasonVM.description,seasonVM.poster)
        try {
            unqFlix.addSeason(serie.id, newSeason)
            actualize()
        } catch(e : Exception) {
            when (e) {
                is ExistsException -> throw UserException("A season with the same title already exists.")
                else -> throw UserException("Unexpected error has occurred.") //teóricamente sería un NotFound o algo que se rompió del lado del modelo, no debería de pasar eso..
            }
        }
    }

    //MODIFICA LA TEMPORADA Y ACTUALIZA LA TABLA
    fun modifiedSeason (seasonVM : SeasonVM){
        seasonVM.actualizeSeason()
        actualize()

    }

    fun deleteSerie(){
        unqFlix.deleteSeason(serie.id, selected!!.id)
        actualize()
    }
}
