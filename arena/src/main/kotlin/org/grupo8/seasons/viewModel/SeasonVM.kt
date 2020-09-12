package org.grupo8.seasons.viewModel

import domain.Season
import org.grupo8.general.Erasable
import org.uqbar.commons.model.annotations.Observable
import java.awt.Color
import java.awt.Color.*

@Observable
class SeasonVM (var season : Season? ) : Erasable {
    override var id : String = season?.id?:""
    override var title : String = season?.title?:""
    var description : String = season?.description?:""
    var poster : String = season?.poster?:""
    var chapters : Int = season?.chapters?.size?:0


    //validacion titulo
    var titleMessage : String = "Complete with season title."
    var titleColor : Color = LIGHT_GRAY

    //validacion descipcion
    var descriptionMessage : String = "Complete with season description."
    var descriptionColor : Color = LIGHT_GRAY



    fun actualizeSeason(): Season {
        season!!.title = title
        season!!.description = description
        season!!.poster = poster
        actualizeChapters()
        return season!!
    }

    fun actualizeChapters (){
        chapters = season!!.chapters.size
    }

    fun verifyTitle(): Boolean {
        if (title == "") {
            titleMessage = "The season must have a name."
            titleColor = RED
        }
        else{
            titleMessage = "Complete with season title."
            titleColor = LIGHT_GRAY
        }
        return title != ""
    }

    fun verifyDescription(): Boolean {
        if (description == "") {
            descriptionMessage = "The season must have a description."
            descriptionColor = RED
        }
        else {
            descriptionMessage = "Complete with season description."
            descriptionColor = LIGHT_GRAY
        }
        return description != ""
    }
}