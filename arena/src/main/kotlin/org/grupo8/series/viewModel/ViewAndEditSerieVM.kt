package org.grupo8.series.viewModel

import domain.Serie
import domain.UNQFlix
import org.grupo8.CategoryVM
import org.grupo8.ContentVM
import org.grupo8.IndexVM
import org.uqbar.commons.model.annotations.Observable

@Observable
class ViewAndEditSerieVM(var aSerie: Serie, var unqFlix: UNQFlix, val mainVM: IndexVM) {

    var serie = SerieVM(aSerie)
    var editMode = false
    var showMode = !editMode
    var categories = serie.categories.map(::CategoryVM).toMutableList()
    var relatedContent = serie.relatedContent.map(::ContentVM).toMutableList()

    var possibleContent: MutableList<ContentVM> = mutableListOf()
    var possibleCategories = unqFlix.categories.map(::CategoryVM)

    var selectedContent: ContentVM? = null
    var selectedCategory: CategoryVM? = null


    init {
        possibleContent
            .addAll(unqFlix.movies.map(::ContentVM).toMutableList())
        possibleContent
            .addAll(unqFlix.series.map(::ContentVM).toMutableList())
    }

    fun swithEditable() {
        editMode = !editMode
        showMode = !showMode
    }

    fun addSelectedCategory() {
        if (! categories.contains(selectedCategory!!)) categories.add(selectedCategory!!)
    }

    fun removeSelectedCategory() {
        categories.remove(selectedCategory!!)
    }

    fun addSelectedContent() {
        if (! relatedContent.contains(selectedContent!!)) relatedContent.add(selectedContent!!)
    }

    fun removeSelectedContent() {
        relatedContent.remove(selectedContent!!)
    }

    fun saveChanges() {
        serie.updateCatgories(categories)
        serie.updateContent(relatedContent)
        serie.updateSerie()
        mainVM.updateTable()
    }


}
