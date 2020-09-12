package org.grupo8

import data.getUNQFlix
import data.idGenerator
import domain.UNQFlix
import domain.User
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder
import io.javalin.core.security.Role
import org.grupo8.controllers.ContentController
import org.grupo8.controllers.UserController

class UnqflixAPI(private val port: Int) {

    fun init(): Javalin {
        val unqFlix : UNQFlix = getUNQFlix()
        val tokenJWT = TokenJWT()
        val app = Javalin.create(){
            it.accessManager(JWTAccessManager(tokenJWT, unqFlix.users))
            it.defaultContentType = "application/json"
            it.enableCorsForAllOrigins()
        }.start(port)


        val userController = UserController(unqFlix, tokenJWT)
        val contentController = ContentController(unqFlix, tokenJWT)
        /*usuario de prueba*/
        val id = idGenerator.nextUserId()
        unqFlix.addUser(User(id,"UsuarioPrueba1","creditCard","image","UsuarioPrueba1@email.com","UsuarioPrueba1"))
        unqFlix.addOrDeleteFav(id,"ser_15")

        app.before(){
            it.header("Access-Control-Expose-Headers", "*")
        }

        app.routes {
            ApiBuilder.path("register"){
                ApiBuilder.post(userController::userRegister, mutableSetOf<Role>(Roles.ANYONE))
            }

            ApiBuilder.path("login") {
                ApiBuilder.post(userController::userLogin, mutableSetOf<Role>(Roles.ANYONE))
            }

            ApiBuilder.path("content") {
                ApiBuilder.get(contentController::getAllContent, mutableSetOf<Role>(Roles.USER))
                ApiBuilder.path(":id") {
                    ApiBuilder.get(contentController::getContent, mutableSetOf<Role>(Roles.USER))
                }
            }

            ApiBuilder.path("search") {
                ApiBuilder.get(contentController::getSearchResults, mutableSetOf<Role>(Roles.USER))
            }

            ApiBuilder.path("banners"){
                ApiBuilder.get(contentController::getBanners, mutableSetOf<Role>(Roles.USER))
            }

            ApiBuilder.path("user"){
                ApiBuilder.get(contentController::getUserContent, mutableSetOf<Role>(Roles.USER))
                ApiBuilder.path("fav") {
                    ApiBuilder.path(":contentId") {
                        ApiBuilder.post(contentController::postUserFav, mutableSetOf<Role>(Roles.USER))
                    }
                }
                ApiBuilder.path("lastSeen") {
                    ApiBuilder.post(contentController::postLastSeen, mutableSetOf<Role>(Roles.USER))
                }
            }
        }
        return app
    }
}

enum class Roles : Role {
    ANYONE, USER
}