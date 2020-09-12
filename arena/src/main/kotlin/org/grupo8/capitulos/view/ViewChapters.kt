package org.grupo8.capitulos.view

import org.grupo8.general.DeleteDialog
import org.grupo8.capitulos.viewmodel.ChapterVM
import org.grupo8.capitulos.viewmodel.ViewChaptersVM
import org.uqbar.arena.kotlin.extensions.*
import org.uqbar.arena.widgets.Button
import org.uqbar.arena.widgets.Label
import org.uqbar.arena.widgets.Panel
import org.uqbar.arena.windows.SimpleWindow
import org.uqbar.arena.windows.WindowOwner
import org.uqbar.commons.model.exceptions.UserException

class ViewChapters (owner: WindowOwner, viewChaptersVM : ViewChaptersVM) : SimpleWindow<ViewChaptersVM>(owner, viewChaptersVM) {

    override fun addActions(mainPanel: Panel?) {}

    override fun createFormPanel(mainPanel: Panel) {
        Label(mainPanel) with {
            text = "${modelObject.season().title} chapters"
        }

        createChaptersTable(mainPanel)

        Panel(mainPanel) with {
            asHorizontal()

            Button(it) with {
                text = "Add a new chapter"
                var thisModelObject =thisWindow.modelObject
                onClick {
                    var chapter = ChapterVM(null)
                    AddAndEditChapter(owner, chapter) with {
                        onAccept {addChapter(chapter) ; thisModelObject.seasonVM.actualizeChapters()}
                        onCancel {}
                        open()
                    }
                }
            }

            Button(it) with {
                text = "Edit chapter"
                onClick {
                    checkSelected()
                    val chapter = ChapterVM(thisWindow.modelObject.selected!!.chapter)
                    AddAndEditChapter(owner, chapter) with {
                        onAccept {editChapter(chapter)}
                        onCancel { }
                        open()
                    }
                }
            }

            Button(it) with {
                text = "Delete chapter"
                var thisModelObject =thisWindow.modelObject
                onClick {
                    checkSelected()
                    DeleteDialog(owner, thisWindow.modelObject.selected) with {
                        onAccept { deleteChapter() ; thisModelObject.seasonVM.actualizeChapters()}
                        onCancel {}
                        open()
                    }
                }
            }
        }
    }

    private fun createChaptersTable(mainPanel : Panel) {
        table<ChapterVM>(mainPanel) {
            bindItemsTo("filterChapters")
            bindSelectionTo("selected")
            visibleRows = 10
            column {
                title = "#"
                fixedSize = 100
                bindContentsTo("id")
            }
            column {
                title = "Title"
                fixedSize = 300
                bindContentsTo("title")
            }
            column {
                title = "Duration"
                fixedSize = 100
                bindContentsTo("duration")
            }
        }
    }

    private fun addChapter(chapter : ChapterVM) {
        modelObject.addChapter(chapter)
        updateTable()
    }

    private fun deleteChapter() {
        modelObject.deleteChapter(modelObject.selected!!.id)
        updateTable()
    }

    private fun editChapter(chapter : ChapterVM) {
        modelObject.editChapter(chapter)
        updateTable()
    }

    private val checkSelected = { if (modelObject.selected == null) throw UserException("No chapter selected, please select one.") }

    private fun updateTable() {
        modelObject.updateTable()
    }

}