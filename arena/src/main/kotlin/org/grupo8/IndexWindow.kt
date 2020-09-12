package org.grupo8

import org.grupo8.general.DeleteDialog
import org.grupo8.series.viewModel.SerieVM
import org.grupo8.series.view.AddSerieWindow
import org.grupo8.series.view.ViewSerieWindow
import org.grupo8.series.viewModel.AddSerieVM
import org.grupo8.series.viewModel.ViewAndEditSerieVM
import org.uqbar.arena.kotlin.extensions.*
import org.uqbar.arena.widgets.*
import org.uqbar.arena.windows.SimpleWindow
import org.uqbar.arena.windows.WindowOwner
import org.uqbar.commons.model.exceptions.UserException

class IndexWindow (owner: WindowOwner, indexVM: IndexVM) : SimpleWindow<IndexVM>(owner, indexVM) {

    override fun addActions(mainPanel: Panel) {
        //Add
        Button(mainPanel) with{
            caption = "Add serie"
            onClick {
                AddSerieWindow(owner, AddSerieVM(modelObject.unqFlix) {modelObject.updateTable()}).open()
            }
        }
        //Show
        Button(mainPanel) with{
            caption = "Show serie"
            onClick {
                checkSelected()
                val viewSerieWindow = ViewSerieWindow(owner, ViewAndEditSerieVM(modelObject.selected?.serie!!, modelObject.unqFlix, modelObject))
                checkSelected()
                viewSerieWindow.open()
            }
        }
        //Delete
        Button(mainPanel) with{
            caption = "Delete serie"
            onClick {
                checkSelected()
                DeleteDialog(owner, modelObject.selected!!) with {
                    onAccept { deleteSerie()}
                    onCancel {}
                    open()
                }
            }
        }
    }

    override fun createFormPanel(mainPanel: Panel) {
        Label(mainPanel) with {
            text = "Filter by title."
        }

        TextBox(mainPanel) with {
            bindTo("searchTerm")
        }

        table<SerieVM>(mainPanel) {
            bindItemsTo("filteredSeries")
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
                title = "#seasons"
                fixedSize = 60
                bindContentsTo("seasonSize")
            }
            column {
                title = "State"
                fixedSize = 80
                align("center")
                bindContentsTo("stateString")
                bindColorTo("stateColor")
            }
        }
    }

    private val checkSelected = { if (modelObject.selected == null) throw UserException("Please select a serie.") }

    private fun deleteSerie(){
        modelObject.deleteSerie(modelObject.selected!!.id)
    }
}