package org.grupo8.series.viewModel

import domain.*
import org.grupo8.CategoryVM
import org.grupo8.ContentVM
import org.grupo8.general.Erasable
import org.uqbar.commons.model.annotations.Observable
import java.awt.Color

@Observable
class SerieVM(serie : Serie?) : Erasable {

    override var id : String = serie!!.id
    var state : Boolean = serie?.state!!::class == Available::class
    var stateColor : Color = if (state) Color.GREEN else Color.RED
    var stateString : String = if (state) "✔" else "×"
    var description : String = serie?.description?: ""
    var poster: String = serie?.poster?: ""
    override var title = serie?.title?: ""
    var seasons : MutableList<Season> = serie?.seasons?: mutableListOf()
    var seasonSize = seasons.size
    var categories : MutableList<Category> = serie?.categories?: mutableListOf()
    var relatedContent: MutableList<Content> = serie?.relatedContent?: mutableListOf()
    var serie : Serie = serie!!

    fun updateContent(contentList : MutableList<ContentVM>){
        relatedContent = contentList.map { it!!.content }.toMutableList()
    }

    fun updateCatgories(categoryList: MutableList<CategoryVM>){
        categories = categoryList.map { it!!.category }.toMutableList()
    }

    fun updateSerie() {
        serie.title = title
        serie.description = description
        serie.seasons = seasons
        serie.categories = categories
        serie.relatedContent = relatedContent
        serie.poster = poster
        serie.state = if (state) Available() else Unavailable()
    }
}