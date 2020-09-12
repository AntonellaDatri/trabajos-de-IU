package org.grupo8.series.viewModel

import data.idGenerator
import domain.*
import org.grupo8.CategoryVM
import org.grupo8.ContentVM
import org.uqbar.commons.model.annotations.Observable
import org.uqbar.commons.model.exceptions.UserException
import java.awt.Color

@Observable
class AddSerieVM(val unqflix: UNQFlix, val actionOnClose: () -> Unit) {

    val availableCategories = unqflix.categories.map(::CategoryVM)
    val availableContent: MutableList<ContentVM> = mutableListOf()

    var title: String = ""
    var description: String = ""
    var poster: String = ""
    var state: Boolean = false
    var categoriesToAdd: MutableList<CategoryVM?> = mutableListOf()
    var selectedCategoryToAdd: CategoryVM? = null
    var selectedCategoryToRemove: CategoryVM? = null
    var contentToAdd: MutableList<ContentVM?> = mutableListOf()
    var selectedContentToAdd: ContentVM? = null
    var selectedContentToRemove: ContentVM? = null

    //validacion titulo
    var titleMessage : String = "Complete with serie title."
    var titleColor : Color = Color.LIGHT_GRAY

    //validacion descipcion
    var descriptionMessage : String = "Complete with serie description."
    var descriptionColor : Color = Color.LIGHT_GRAY

    init {
        availableContent
                .addAll(unqflix.movies.map { ContentVM(it) }.toMutableList())
        availableContent
                .addAll(unqflix.series.map { ContentVM(it) }.toMutableList())
    }

    fun addSerie() {
        try {
            val serie = this.buildSerie()
            unqflix.addSerie(serie)
        } catch (e: ExistsException) {
            throw UserException("A serie with the same title already exists.")
        }
    }

    fun addSelectedCategory() {
        if (!categoriesToAdd.contains(selectedCategoryToAdd)) categoriesToAdd.add(selectedCategoryToAdd) //Se debe checkear que la categoria no haya sido agregada a esta lista ya, y que no intentemos poner null como categoria
    }

    fun addSelectedContent() {
        if (!contentToAdd.contains(selectedContentToAdd)) contentToAdd.add(selectedContentToAdd)
    }

    private fun buildSerie(): Serie {
  //      checkTitleAndDescription()
        return Serie(idGenerator.nextSerieId(),
                title,
                description,
                poster,
                checkState(),
                categoriesToAdd.map { it!!.category }.toMutableList(),
                mutableListOf(),
                contentToAdd.map {it!!.content}.toMutableList())

    }
//
//    private fun checkTitleAndDescription() {
//        if (title.isBlank() || description.isBlank()) throw UserException("Title or description hasn't been provided")
//    }

    private fun checkState(): ContentState {
        return if (state) Available() else Unavailable()
    }

    // VERIFICACIONES
    fun verifyTitle(): Boolean {
        if (title == "") {
            titleMessage = "The serie must have a name."
            titleColor = Color.RED
        }
        else{
            titleMessage = "Complete with serie title."
            titleColor = Color.LIGHT_GRAY
        }
        return title != ""
    }

    fun verifyDescription(): Boolean {
        if (description == "") {
            descriptionMessage = "The serie must have a description."
            descriptionColor = Color.RED
        }
        else {
            descriptionMessage = "Complete with serie description."
            descriptionColor = Color.LIGHT_GRAY
        }
        return description != ""
    }

    fun removeSelectedCategory() {
        if (categoriesToAdd.contains(selectedCategoryToRemove)) categoriesToAdd.remove(selectedCategoryToRemove)
    }

    fun removeSelectedContent() {
        if (contentToAdd.contains(selectedContentToRemove)) contentToAdd.remove(selectedContentToRemove)
    }

}