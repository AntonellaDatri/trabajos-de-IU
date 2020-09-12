package org.grupo8.seasons.view

import org.grupo8.seasons.viewModel.SeasonVM
import org.uqbar.arena.kotlin.extensions.*
import org.uqbar.arena.widgets.Button
import org.uqbar.arena.widgets.Label
import org.uqbar.arena.widgets.Panel
import org.uqbar.arena.widgets.TextBox
import org.uqbar.arena.windows.Dialog
import org.uqbar.arena.windows.WindowOwner


class SeasonWindow (owner: WindowOwner, model: SeasonVM?) : Dialog<SeasonVM>(owner, model) {
    override fun createFormPanel(mainPanel: Panel?) {
        //campo de titulo
        Label(mainPanel) with {
            text = "Title:"
            align = "left"
        }

        TextBox(mainPanel) with {
            height = 20
            width = 200
            bindTo("title")
        }
        Label(mainPanel) with {
            bindTo("titleMessage")
            bindColorTo("titleColor")
            align = "left"
        }

        //campo de descrpcion
        Label(mainPanel) with {
            text = "Description:"
            align = "left"
        }
        TextBox(mainPanel) with {
            height = 60
            width = 200
            bindTo("description")
        }
        Label(mainPanel) with {
            bindTo("descriptionMessage")
            bindColorTo("descriptionColor")
            align = "left"
        }

        //campo de poster
        Label(mainPanel) with {
            text = "Poster:"
            align = "left"
        }
        TextBox(mainPanel) with {
            height = 20
            width = 200
            bindTo("poster")
        }
    }

    override fun addActions(actionsPanel: Panel?) {
        Button(actionsPanel) with { caption = "Aceptar" ; onClick{
            validField()
//            accept()
            }
        }
        Button(actionsPanel) with { caption = "Cancelar" ; onClick{cancel()} }
    }

    fun validField(){
        if (modelObject.verifyTitle() and modelObject.verifyDescription()){
            accept()
        }
    }

}