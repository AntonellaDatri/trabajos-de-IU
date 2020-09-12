package org.grupo8

import domain.User
import io.javalin.core.security.AccessManager
import io.javalin.core.security.Role
import io.javalin.http.*

class JWTAccessManager (val tokenJWT: TokenJWT, val users : List<User>) : AccessManager {
    override fun manage(handler: Handler, ctx: Context, roles: MutableSet<Role>) {
            val token =ctx.header("Authorization")
        when {
            (token == null || token == "") && roles.contains(Roles.ANYONE) -> handler.handle(ctx)
            (token == null || token == "") -> throw UnauthorizedResponse( "Token not found" )
            roles.contains(Roles.ANYONE) -> handler.handle(ctx)
            roles.contains(Roles.USER) -> {
                getUser(token)
                handler.handle(ctx)
            }
        }
    }

    fun getUser(token:String) : User{
        try{
            val userId = tokenJWT.validate(token)
            val user = users.firstOrNull{ it.id == userId } ?: throw NotFoundResponse("User not found")
            return user
        }catch (e: NotFoundResponse){
            throw UnauthorizedResponse(e.localizedMessage)
        }catch (e: BadRequestResponse){
            throw UnauthorizedResponse("Token not found")
        }
    }
}