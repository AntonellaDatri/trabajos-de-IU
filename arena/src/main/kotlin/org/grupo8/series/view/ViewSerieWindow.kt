package org.grupo8.series.view

import domain.Category
import org.grupo8.seasons.view.SeasonsMainWindow
import org.grupo8.seasons.viewModel.SeasonsMainWindowVM
import org.grupo8.CategoryVM
import org.grupo8.ContentVM
import org.grupo8.series.viewModel.ViewAndEditSerieVM
import org.uqbar.arena.kotlin.extensions.*
import org.uqbar.arena.widgets.*
import org.uqbar.arena.windows.SimpleWindow
import org.uqbar.arena.windows.WindowOwner

class ViewSerieWindow(owner: WindowOwner, model: ViewAndEditSerieVM) : SimpleWindow<ViewAndEditSerieVM>(owner, model) {

    override fun addActions(mainPanel: Panel?) {
        Button(mainPanel) with {
            caption = "Edit"
            bindVisibleTo("showMode")
            onClick { modelObject.swithEditable() }
        }

        Button(mainPanel) with {
            caption = "Save"
            bindVisibleTo("editMode")
            onClick {
                modelObject.saveChanges()
                thisWindow.close()
            }
        }
    }

    override fun createFormPanel(mainPanel: Panel?) {

        Panel(mainPanel) with {
            width = 300
            asHorizontal()
            createTitle(this)
            createPoster(this)
        }

        createDescription(mainPanel)

        createStateCheckbox(mainPanel)

        createCategoryList(mainPanel)

        createRelatedContentList(mainPanel)

        createShowSeasonsButton()

    }

    private fun createShowSeasonsButton() {
        Button(this) with {
            caption = "Show seasons"
            val unqFlix = thisWindow.modelObject.unqFlix
            onClick {
                SeasonsMainWindow(thisWindow, SeasonsMainWindowVM(unqFlix,  modelObject.mainVM, modelObject.serie)).open() //,
            }
        }
    }

    private fun createRelatedContentList(mainPanel: Panel?) {
        Panel(mainPanel) with {
            asHorizontal()

            Label(mainPanel) with {
                text = "Related Content"
                width = 200
            }

            List<Category>(this) with {
                bindItemsTo("relatedContent").adaptWithProp<ContentVM>("name")
                bindSelectedTo("selectedContent")
                height = 150
                width = 200
            }

            Button(this) with {
                caption = "<<"
                onClick { thisWindow.modelObject.addSelectedContent() }
                bindVisibleTo("editMode")
            }

            Button(this) with {
                caption = ">>"
                onClick { thisWindow.modelObject.removeSelectedContent() }
                bindVisibleTo("editMode")
            }

            List<Category>(this) with {
                bindItemsTo("possibleContent").adaptWithProp<ContentVM>("name")
                bindSelectedTo("selectedContent")
                height = 150
                width = 200
                bindVisibleTo("editMode")
            }
        }
    }

    private fun createCategoryList(mainPanel: Panel?) {

        Panel(mainPanel) with {
            asHorizontal()

            Label(mainPanel) with {
                text = "Categories"
                width = 200
            }

            List<Category>(this) with {
                bindItemsTo("categories").adaptWithProp<CategoryVM>("name")
                bindSelectedTo("selectedCategory")
                height = 150
                width = 200
            }

            Button(this) with {
                caption = "<<"
                onClick { thisWindow.modelObject.addSelectedCategory() }
                bindVisibleTo("editMode")
            }

            Button(this) with {
                caption = ">>"
                onClick { thisWindow.modelObject.removeSelectedCategory() }
                bindVisibleTo("editMode")
            }

            List<Category>(this) with {
                bindItemsTo("possibleCategories").adaptWithProp<CategoryVM>("name")
                bindSelectedTo("selectedCategory")
                height = 150
                width = 200
                bindVisibleTo("editMode")
            }
        }
    }

    private fun createStateCheckbox(mainPanel: Panel?) {

        Panel(mainPanel) with {
            asHorizontal()
            Label(this) with {
                text = "Is available?"
                width = 100
            }

            CheckBox(this) with {
                bindTo("serie.state")
                bindEnabledTo("editMode")
                width = 30
            }
        }

    }

    private fun createPoster(mainPanel: Panel?) {

        Label(mainPanel) with {
            text = "Poster:"
            width = 50
        }

        TextBox(mainPanel) with {
            bindTo("serie.poster")
            bindEnabledTo("editMode")
            width = 175
        }
    }

    private fun createDescription(mainPanel: Panel?) {

        Label(mainPanel) with {
            text = "Description:"
            width = 50
        }

        TextBox(mainPanel) with {
            bindTo("serie.description")
            bindEnabledTo("editMode")
            isMultiLine = true
            width = 175
            height = 75
        }
    }

    private fun createTitle(mainPanel: Panel?) {

        Label(mainPanel) with {
            text = "Title:"
            width = 50
        }

        TextBox(mainPanel) with {
            bindTo("serie.title")
            bindEnabledTo("editMode")
            width = 175
        }
    }

}