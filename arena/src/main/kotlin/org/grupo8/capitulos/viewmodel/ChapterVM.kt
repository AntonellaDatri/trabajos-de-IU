package org.grupo8.capitulos.viewmodel

import domain.Chapter
import org.grupo8.general.Erasable
import org.uqbar.commons.model.annotations.Observable
import java.awt.Color

@Observable
class ChapterVM (var chapter: Chapter?) : Erasable {
    override var id : String = chapter?.id?:""
    override var title : String = chapter?.title?:""
    var description : String = chapter?.description?:""
    var duration : Int = chapter?.duration?:0
    var thumbnail : String = chapter?.thumbnail?:""
    var video : String = chapter?.video?:""

    //validacion titulo
    var titleMessage : String = "Complete with chapter title."
    var titleColor : Color = Color.LIGHT_GRAY

    //validacion descipcion
    var descriptionMessage : String = "Complete with chapter description."
    var descriptionColor : Color = Color.LIGHT_GRAY

    //validacion duration
    var durationMessage : String = "Complete with chapter duration."
    var durationColor : Color = Color.LIGHT_GRAY

    //validacion thumbnail
    var thumbnailMessage : String = "Complete with chapter thumbnail."
    var thumbnailColor : Color = Color.LIGHT_GRAY

    //validacion video
    var videoMessage : String = "Complete with chapter video."
    var videoColor : Color = Color.LIGHT_GRAY


    fun verifyTitle(): Boolean {
        if (title.isEmpty()) {
            titleMessage = "The chapter must have a name."
            titleColor = Color.RED
        }else{
            titleMessage = "Complete with chapter title."
            titleColor = Color.LIGHT_GRAY
        }
        return title.isNotEmpty()
    }

    fun verifyDescription(): Boolean {
        if (description.isEmpty()) {
            descriptionMessage = "The chapter must have a description."
            descriptionColor = Color.RED
        }else{
            descriptionMessage = "Complete with chapter description."
            descriptionColor = Color.LIGHT_GRAY
        }
        return description.isNotEmpty()
    }

    fun verifyDuration(): Boolean {
        if (duration == 0) {
            durationMessage = "The chapter must have a duration."
            durationColor = Color.RED
        }else{
            durationMessage = "Complete with chapter duration."
            durationColor = Color.LIGHT_GRAY
        }
        return duration != 0
    }

    fun verifyThumbnail(): Boolean {
        if (thumbnail.isEmpty()) {
            thumbnailMessage = "The chapter must have a thumbnail."
            thumbnailColor = Color.RED
        }else{
            thumbnailMessage = "Complete with chapter thumbnail."
            thumbnailColor = Color.LIGHT_GRAY
        }
        return thumbnail.isNotEmpty()
    }

    fun verifyVideo(): Boolean {
        if (video.isEmpty()) {
            videoMessage = "The chapter must have a video."
            videoColor = Color.RED
        }else{
            videoMessage = "Complete with chapter video."
            videoColor = Color.LIGHT_GRAY
        }
        return video.isNotEmpty()
    }

}