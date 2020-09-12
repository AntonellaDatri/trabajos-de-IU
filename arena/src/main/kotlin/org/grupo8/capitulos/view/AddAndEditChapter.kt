package org.grupo8.capitulos.view

import org.grupo8.capitulos.viewmodel.ChapterVM
import org.uqbar.arena.kotlin.extensions.*
import org.uqbar.arena.widgets.Button
import org.uqbar.arena.widgets.Label
import org.uqbar.arena.widgets.Panel
import org.uqbar.arena.widgets.TextBox
import org.uqbar.arena.windows.Dialog
import org.uqbar.arena.windows.WindowOwner

class AddAndEditChapter (owner: WindowOwner, model: ChapterVM?) : Dialog<ChapterVM>(owner, model) {

    override fun createFormPanel(mainPanel: Panel?) {
        //campo de titulo
        Label(mainPanel) with {
            text = "Title"
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
            text = "Description"
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

        //campo de duration
        Label(mainPanel) with {
            text = "Duration"
        }
        TextBox(mainPanel) with {
            height = 20
            width = 200
            bindTo("duration")
            withFilter {event -> event.potentialTextResult.matches(Regex("[0-9]*"))}
        }
        Label(mainPanel) with {
            bindTo("durationMessage")
            bindColorTo("durationColor")
            align = "left"
        }

        //campo de thumbnail
        Label(mainPanel) with {
            text = "Thumbnail"
        }
        TextBox(mainPanel) with {
            height = 20
            width = 200
            bindTo("thumbnail")
        }
        Label(mainPanel) with {
            bindTo("thumbnailMessage")
            bindColorTo("thumbnailColor")
            align = "left"
        }

        //campo de video
        Label(mainPanel) with {
            text = "Video"
        }
        TextBox(mainPanel) with {
            height = 20
            width = 200
            bindTo("video")
        }
        Label(mainPanel) with {
            bindTo("videoMessage")
            bindColorTo("videoColor")
            align = "left"
        }
    }

    override fun addActions(actionsPanel: Panel?) {
        Button(actionsPanel) with { caption = "Accept" ; onClick{ validateFields() } }
        Button(actionsPanel) with { caption = "Cancel" ; onClick{cancel()} }
    }

    private fun validateFields() {
        if (modelObject.verifyTitle() and modelObject.verifyDescription() and modelObject.verifyDuration() and modelObject.verifyThumbnail() and modelObject.verifyVideo()){
            accept()
        }
    }
}