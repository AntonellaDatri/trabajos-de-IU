package org.grupo8.seasons.view

import org.grupo8.general.DeleteDialog
import org.grupo8.capitulos.view.ViewChapters
import org.grupo8.capitulos.viewmodel.ViewChaptersVM
import org.grupo8.seasons.viewModel.SeasonVM
import org.grupo8.seasons.viewModel.SeasonsMainWindowVM
import org.uqbar.commons.model.exceptions.UserException
import org.uqbar.arena.kotlin.extensions.*
import org.uqbar.arena.widgets.Button
import org.uqbar.arena.widgets.Label
import org.uqbar.arena.widgets.Panel
import org.uqbar.arena.windows.SimpleWindow
import org.uqbar.arena.windows.WindowOwner

class SeasonsMainWindow (owner: WindowOwner, seasonsMainWindowVM: SeasonsMainWindowVM) : SimpleWindow<SeasonsMainWindowVM>(owner, seasonsMainWindowVM){
    override fun addActions(mainPanel: Panel?) {

        Button(mainPanel) with {
            text = "add new season"
            onClick{
                var season = SeasonVM(null)
                SeasonWindow(owner, season) with {
                    onAccept { addNewSeason(season) }
                    onCancel {}
                    open()
                }
            }
        }

        Button(mainPanel) with {
            text = "modified season"
            onClick {
                verifyNull()
                var seasonSelect = SeasonVM(thisWindow.modelObject.selected!!.season)
                SeasonWindow(thisWindow, seasonSelect) with {
                    onAccept { editSeason(seasonSelect) }
                    onCancel {}
                    open()
                }
            }
        }

        Button(mainPanel) with {
            text = "Delete season"
            onClick {
                verifyNull()
                DeleteDialog(owner, modelObject.selected!!) with {
                    onAccept { deleteSerie()}
                    onCancel {}
                    open()
                }
            }
        }
        Button(mainPanel) with {

            text = "Show Chapters"
            onClick {
                var unqFlix = thisWindow.modelObject.unqFlix
                var serie = thisWindow.modelObject.serie
                var seasonVM = thisWindow.modelObject.selected
                verifyNull()
                ViewChapters(thisWindow, ViewChaptersVM(unqFlix, serie, seasonVM!!)).open()
            }
        }

    }

    override fun createFormPanel(mainPanel: Panel) {

        Label(mainPanel) with {
            bindTo("titleSerie")
            align = "left"
        }

        createSeasonTable(mainPanel)

    }
//TABLA
    fun createSeasonTable(mainPanel : Panel){
        table<SeasonVM>(mainPanel) {
            bindItemsTo("filterSeasons")
            bindSelectionTo("selected")
            visibleRows = 10
            column {
                title = "#"
                fixedSize = 70
                bindContentsTo("id")
            }
            column {
                title = "Title"
                fixedSize = 300
                bindContentsTo("title")
            }
            column {
                title = "#chapters"
                fixedSize = 60
                bindContentsTo("chapters")
            }
        }
    }
//
    private fun addNewSeason (season: SeasonVM){
        modelObject.addNewSeason(season)
    }
    private fun editSeason(season: SeasonVM){
        modelObject.modifiedSeason(season)
    }
    private fun deleteSerie(){
        modelObject.deleteSerie()
    }

    ///MENSAJE DE ERROR
    fun verifyNull (){
        if (modelObject.selected == null) {
            throw UserException("No season selected, please select one.")
        }
    }
}