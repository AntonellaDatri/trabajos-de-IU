package org.grupo8

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.extensions.jsonBody

class TestHelper {
    companion object {
        fun getTokenForNewUser(): String {
            val userToRegister = ObjectMapper().writeValueAsString(UserRegisterMapper(
                "Sergio",
                "sergio@gmail.com",
                "1234",
                "image.png",
                "1111 1111 1111 1111"
            ))
            val (_, response, _) = Fuel.post("http://localhost:7000/register").jsonBody(userToRegister).response()
            return response.headers["Authorization"].elementAt(0)
        }

        fun getRandomContent(token: String): JsonNode {
            val (_, content_response,_) = Fuel.get("http://localhost:7000/content")
                .header("Authorization", token)
                .response()
            return ObjectMapper().readTree(String(content_response.data))[0]
        }
    }

}
