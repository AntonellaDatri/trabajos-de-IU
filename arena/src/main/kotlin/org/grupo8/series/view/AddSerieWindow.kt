package org.grupo8.series.view

import domain.Category
import domain.Content
import org.grupo8.CategoryVM
import org.grupo8.series.viewModel.AddSerieVM
import org.grupo8.ContentVM
import org.uqbar.arena.kotlin.extensions.*
import org.uqbar.arena.widgets.*
import org.uqbar.arena.widgets.List
import org.uqbar.arena.windows.SimpleWindow
import org.uqbar.arena.windows.WindowOwner

class AddSerieWindow(owner: WindowOwner, model: AddSerieVM) : SimpleWindow<AddSerieVM>(owner, model) {

    override fun addActions(mainPanel: Panel?) {
        createAcceptButton(mainPanel)
    }

    private fun createAcceptButton(mainPanel: Panel?) {
        Button(mainPanel) with {
            caption = "Accept"
            onClick {
                validateFields()

            }
        }
    }
    fun validateFields(){
        if(modelObject.verifyTitle() and modelObject.verifyDescription()){
            modelObject.addSerie()
            modelObject.actionOnClose.invoke()
            thisWindow.close()
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

    }

    private fun createRelatedContentList(mainPanel: Panel?) {

        Label(mainPanel) with {
            text = "Related content"
            width = 200
        }

        Panel(mainPanel) with {
            asHorizontal()

            List<Category>(this) with {
                bindItemsTo("availableContent").adaptWithProp<ContentVM>("name")
                bindSelectedTo("selectedContentToAdd")
                height = 150
                width = 200
            }

            Panel(this) with {
                asVertical()
                Button(this) with {
                    caption = ">>"
                    onClick { thisWindow.modelObject.addSelectedContent() }
                }

                Button(this) with {
                    caption = "<<"
                    onClick { thisWindow.modelObject.removeSelectedContent() }
                }
            }


            List<Content>(this) with {
                bindItemsTo("contentToAdd").adaptWithProp<ContentVM>("name")
                bindSelectedTo("selectedContentToRemove")
                height = 150
                width = 200
            }
        }
    }

    private fun createCategoryList(mainPanel: Panel?) {
        Label(mainPanel) with {
            text = "Categories"
            width = 200
        }

        Panel(mainPanel) with {
            asHorizontal()



            List<Category>(this) with {
                bindItemsTo("availableCategories").adaptWithProp<CategoryVM>("name")
                bindSelectedTo("selectedCategoryToAdd")
                height = 150
                width = 200
            }

            Panel(this) with{
                asVertical()
                Button(this) with {
                    caption = ">>"
                    onClick { thisWindow.modelObject.addSelectedCategory() }
                }

                Button(this) with {
                    caption = "<<"
                    onClick { thisWindow.modelObject.removeSelectedCategory() }
                }
            }


            List<Category>(this) with {
                bindItemsTo("categoriesToAdd").adaptWithProp<CategoryVM>("name")
                bindSelectedTo("selectedCategoryToRemove")
                height = 150
                width = 200
            }
        }
    }

    private fun createStateCheckbox(mainPanel: Panel?) {
        Label(mainPanel) with {
            text = "Is available?"
            width = 100
        }
        CheckBox(mainPanel) with {
            bindTo("state")
            width = 30
        }
    }

    private fun createPoster(mainPanel: Panel?) {
        Label(mainPanel) with {
            text = "Poster:"
            width = 50
        }
        TextBox(mainPanel) with {
            bindTo("poster")
            width = 175
        }
    }

    private fun createDescription(mainPanel: Panel?) {
        Label(mainPanel) with {
            text = "Description:"
            width = 50
        }
        TextBox(mainPanel) with {
            bindTo("description")
            isMultiLine = true
            width = 175
            height = 75
        }
        Label(mainPanel) with {
            bindTo("descriptionMessage")
            bindColorTo("descriptionColor")
            align = "left"
        }
    }

    private fun createTitle(mainPanel: Panel?) {
        Label(mainPanel) with {
            text = "Title:"
            width = 50
        }
        TextBox(mainPanel) with {
            bindTo("title")
            width = 175
        }
        Label(mainPanel) with {
            bindTo("titleMessage")
            bindColorTo("titleColor")
            align = "left"
        }
    }

}