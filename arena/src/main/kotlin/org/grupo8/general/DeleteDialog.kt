package org.grupo8.general

import org.uqbar.arena.kotlin.extensions.*
import org.uqbar.arena.widgets.Button
import org.uqbar.arena.widgets.Label
import org.uqbar.arena.widgets.Panel
import org.uqbar.arena.windows.Dialog
import org.uqbar.arena.windows.WindowOwner

class DeleteDialog (owner: WindowOwner, model: Erasable?) : Dialog<Erasable>(owner, model) {
    override fun createFormPanel(mainPanel: Panel) {
        title = "Confirm Delete"
        mainPanel.asVertical()

        Label(mainPanel) with {
            text = "Are you sure to delete ${modelObject.id} (${modelObject.title})?"
        }

        Panel(mainPanel) with {
            asHorizontal()
            Button(it) with {
                caption = "Accept"
                onClick { accept() }
            }
            Button(it) with {
                caption = "Cancel"
                onClick { cancel() }
            }
        }
    }
}