package org.grupo8

import domain.*

data class UserLoginMapper(
    val email: String,
    val password : String
)

data class UserRegisterMapper(
    val name: String,
    val email : String,
    val password: String,
    val image: String,
    val creditCard: String
)

data class ContentMapper(
    val id : String,
    val desciption : String,
    val title : String,
    val state: Boolean
)

data class MovieMapper(
    val id: String,
    val title: String,
    val desciption: String,
    val poster: String,
    val viedeo : String,
    val duration: Int,
    val actores: MutableList<String>,
    val directors: MutableList<String>,
    val categories: MutableList<Category>,
    val relatedContent: List<ContentMapper>)


class SerieMapper(
    val id: String,
    val title: String,
    val desciption: String,
    val poster: String,
    val categories: MutableList<Category>,
    val relatedContent: List<ContentMapper>,
    val season : MutableList<Season>)

data class LastSeenIdMapper(
    val id: String
)

data class UserContentMapper (
    val name: String,
    val image: String,
    val favorites: List<ContentMapper>,
    val lastSeen: List<ContentMapper>
)