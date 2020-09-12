package org.grupo8

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import domain.User
import io.javalin.http.UnauthorizedResponse
import javalinjwt.JWTGenerator
import javalinjwt.JWTProvider

class TokenJWT {
    val algorithm = Algorithm.HMAC256("very_secret")
    val generator = UserGenerator()
    val verifier = JWT.require(algorithm).build()
    val provider = JWTProvider(algorithm, generator, verifier)

    fun generateToken(user: User) : String{
        return provider.generateToken(user)
    }

    fun validate(token : String) : String{
        val myToken = provider.validateToken(token)
        if (!myToken.isPresent) throw UnauthorizedResponse("Invalid token")
        return myToken.get().getClaim("id").asString()
    }
}

class UserGenerator() : JWTGenerator<User>{
    override fun generate(user: User, algorithm: Algorithm): String {
        val token = JWT.create().withClaim("id", user.id)
        return token.sign(algorithm)
    }

}
