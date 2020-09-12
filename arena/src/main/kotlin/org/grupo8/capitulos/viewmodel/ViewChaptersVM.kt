package org.grupo8.capitulos.viewmodel

import data.idGenerator
import domain.*
import org.grupo8.seasons.viewModel.SeasonVM
import org.uqbar.commons.model.annotations.Observable
import org.uqbar.commons.model.exceptions.UserException

@Observable
class ViewChaptersVM (val unqFlix: UNQFlix, val serie: Serie, val seasonVM: SeasonVM) {
    var filterChapters : List<ChapterVM> = season().chapters.map(::ChapterVM)
    var selected : ChapterVM? = null


    fun season() : Season = seasonVM.season!!


    @Throws(UserException::class)
    fun addChapter(chapterVM : ChapterVM) {
        val chapter = Chapter(idGenerator.nextChapterId(), chapterVM.title, chapterVM.description, chapterVM.duration, chapterVM.video, chapterVM.thumbnail)
        try {
            unqFlix.addChapter(serie.id, season().id, chapter)
        } catch(e : Exception) {
            when (e) {
                is ExistsException -> throw UserException("A chapter with the same title already exists.")
                else -> throw UserException("Unexpected error has occurred.") //teóricamente sería un NotFound o algo que se rompió del lado del modelo, no debería de pasar eso..
            }
        }
    }

    fun deleteChapter(chapterID : String) {
        unqFlix.deleteChapter(serie.id, season().id, chapterID)
    }

    fun editChapter(chapterVM: ChapterVM) {
        var chapter = chapterVM.chapter!!
        chapter.title = chapterVM.title
        chapter.description = chapterVM.description
        chapter.duration = chapterVM.duration
        chapter.thumbnail = chapterVM.thumbnail
        chapter.video = chapterVM.video
    }

    fun updateTable() {
        filterChapters = season().chapters.map(::ChapterVM)
    }
}