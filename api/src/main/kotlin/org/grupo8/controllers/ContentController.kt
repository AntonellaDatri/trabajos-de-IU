package org.grupo8.controllers

import domain.*
import io.javalin.http.BadRequestResponse
import io.javalin.http.Context
import io.javalin.http.NotFoundResponse
import org.grupo8.*
import java.lang.Exception

class ContentController(val unqFlix: UNQFlix, val tokenJWT: TokenJWT) {

    fun getSearchResults(ctx: Context){
        val queryParam = ctx.queryParam("text")
        if (queryParam == null || queryParam == "") throw BadRequestResponse()
        val searchResults: MutableList<Content> = unqFlix.searchMovies(queryParam).toMutableList()
        searchResults.addAll(unqFlix.searchSeries(queryParam))
        val jsonResults = searchResults.map{
            ContentMapper(
                it.id,
                it.description,
                it.title,
                it.state::class == Available::class
            )
        }
        jsonResults.sortedBy { it.title }

        ctx.json(jsonResults)
    }

    fun getBanners(ctx: Context){
        val banners = unqFlix.banners.map {
            ContentMapper(
                it.id,
                it.description,
                it.title,
                it.state::class == Available::class
            )
        }
        ctx.json(banners)
    }

    fun getAllContent(ctx: Context) {
        val movie = unqFlix.movies.map {
            ContentMapper(
                it.id,
                it.description,
                it.title,
                it.state::class == Available::class
            )
        }.filter { it.state }

        val serie = unqFlix.series.map {
            ContentMapper(
                it.id,
                it.description,
                it.title,
                it.state::class == Available::class
            )
        }.filter { it.state }

        val content = (movie + serie).sortedBy { it.title }
        ctx.json(content)
    }


    fun getContent(ctx: Context) {
        try {
            val id = ctx.pathParam("id")
            if (id.first().equals('m', true)) {
                val movie = unqFlix.movies.first { it.id == id }
                ctx.json(createMovie(movie))
            } else {
                val serie = unqFlix.series.first { it.id == id }
                ctx.json(createSerie(serie))
            }
        } catch (e: NoSuchElementException) {
            ctx.status(404)
            ctx.json(mapOf("message" to "Invalid ID"))
        }
    }

    fun createSerie(serie: Serie): SerieMapper {
        val serieMapper = SerieMapper(
            serie.id,
            serie.title,
            serie.description,
            serie.poster,
            serie.categories,
            serie.relatedContent.map {
                ContentMapper(
                    it.id,
                    it.description,
                    it.title,
                    it.state::class == Available::class
                )
            },
            serie.seasons
        )
        return serieMapper
    }

    fun createMovie(movie: Movie): MovieMapper {
        val movieMapper = MovieMapper(movie.id,
            movie.title,
            movie.description,
            movie.poster,
            movie.video,
            movie.duration,
            movie.actors,
            movie.directors,
            movie.categories,
            movie.relatedContent.map {
                ContentMapper(
                    it.id,
                    it.description,
                    it.title,
                    it.state::class == Available::class
                )
            })
        return movieMapper
    }

    private fun createUserContent(user: User) : UserContentMapper {
        return UserContentMapper(
            user.name,
            user.image,
            user.favorites.map {
                ContentMapper(
                    it.id,
                    it.description,
                    it.title,
                    it.state::class == Available::class
                )
            },
            user.lastSeen.map {
                ContentMapper(
                    it.id,
                    it.description,
                    it.title,
                    it.state::class == Available::class
                )
            })
    }

    fun getUserContent(ctx: Context) {
        try {
            val userId = tokenJWT.validate(ctx.header("Authorization")!!)
            val userContent = unqFlix.users.first { it.id == userId }
            ctx.status(200)
            ctx.json(createUserContent(userContent))
        } catch (e: NoSuchElementException) {
            ctx.status(404)
        }
    }

    fun postUserFav(ctx: Context){
        try {
            val contentId = ctx.pathParam("contentId")
            val userId = tokenJWT.validate(ctx.header("Authorization")!!)
            unqFlix.addOrDeleteFav(userId, contentId)
            ctx.status(200)
            ctx.json(mapOf("result" to "ok"))
        } catch (e : NotFoundException) {
            ctx.status(404)
            ctx.json(mapOf("error" to "Contenido no encontrado"))
        }
    }

    fun postLastSeen(ctx: Context){
        try {
            val lastSeenId = ctx.bodyValidator<LastSeenIdMapper>()
                .get().id
            val userId = tokenJWT.validate(ctx.header("Authorization")!!)
            unqFlix.addLastSeen(userId, lastSeenId!!)
            ctx.status(200)
            ctx.json(mapOf("result" to "ok"))
        } catch (e : NotFoundException) {
            ctx.status(404)
            ctx.json(mapOf("error" to "Contenido no encontrado"))
        }
    }
}