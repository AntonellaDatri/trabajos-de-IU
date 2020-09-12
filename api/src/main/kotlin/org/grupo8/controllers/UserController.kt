package org.grupo8.controllers

import data.idGenerator
import domain.ExistsException
import domain.UNQFlix
import domain.User
import io.javalin.http.Context
import org.grupo8.TokenJWT
import org.grupo8.UserLoginMapper
import org.grupo8.UserRegisterMapper
import java.util.NoSuchElementException

class UserController (val unqFlix: UNQFlix, val tokenJWT: TokenJWT) {

    fun userLogin(ctx : Context){
            val login = ctx.bodyValidator<UserLoginMapper>()
                .get()
            try {
                val user = unqFlix.users.first{it.email == login.email && it.password == login.password}
                ctx.header("Authorization", tokenJWT.generateToken(user))
                ctx.status(200)
                ctx.json(mapOf("result" to "ok"))
            }catch (e: NoSuchElementException){
                ctx.status(404)
                ctx.json(mapOf("result" to "error","message" to "User not found"))
            }
    }

    fun userRegister(ctx: Context){
        val registerMapper = ctx.bodyValidator<UserRegisterMapper>()
            .get()
        try{
            val newUser = createUser(registerMapper)
            unqFlix.addUser(newUser)
            ctx.header("Authorization", tokenJWT.generateToken(newUser))
            ctx.status(201)
            ctx.json(mapOf("status" to "ok"))
        } catch (e: ExistsException){
            ctx.status(400)
            ctx.json(mapOf("result" to "error", "message" to "The email is already associated with a user"))
        }

    }

    private fun createUser(newUser: UserRegisterMapper): User {
        return User(
            idGenerator.nextUserId(),
            newUser.name!!,
            newUser.creditCard!!,
            newUser.image!!,
            newUser.email!!,
            newUser.password!!,
            mutableListOf(),
            mutableListOf()
        )
    }
}