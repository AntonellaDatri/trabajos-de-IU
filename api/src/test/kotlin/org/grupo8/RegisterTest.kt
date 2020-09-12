package org.grupo8

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.extensions.jsonBody
import io.javalin.Javalin
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class RegisterTest {

    private lateinit var api: Javalin

    @BeforeAll
    fun setUp(){
        api = UnqflixAPI(7000).init()
    }

    @AfterAll
    fun tearDown(){
        api.stop()
    }

    @Test @Order(1)
    fun registrarUsuarioRetorna201(){
        val userJSON = ObjectMapper().writeValueAsString(UserRegisterMapper(
            "Juan",
            "juan@gmail.com",
            "1234",
            "image.png",
            "1111 1111 1111 1111"
        ))

        val(_, response, _) = Fuel.post("http://localhost:7000/register").jsonBody(userJSON).response()
        assertEquals(201, response.statusCode)
        assertEquals("ok", ObjectMapper().readTree(String(response.data)).get("status").textValue())
    }

    @Test @Order(2)
    fun registrarUnUsuarioSinTodosLosParametrosRetornaError(){
        val userSinTarjetaJSON = """
            {
                "name": "Carlos",
                "email": "carlos@gmail.com",
                "password": "12345"
            }
        """
        val(_, response, _) = Fuel.post("http://localhost:7000/register").jsonBody(userSinTarjetaJSON).response()
        assertEquals(400, response.statusCode)
        assertTrue(String(response.data).contains("Invalid body: email, password, name and credit card are required"))
    }

    @Test @Order(3)
    fun registrarUnUsuarioQueYaExisteRetornaError(){
        val userRepetidoJSON = ObjectMapper().writeValueAsString(UserRegisterMapper(
            "Juan",
            "juan@gmail.com",
            "1234",
            "image.png",
            "1111 1111 1111 1111"
        ))
        val(_, response, _) = Fuel.post("http://localhost:7000/register").jsonBody(userRepetidoJSON).response()
        assertEquals(400, response.statusCode)
        assertTrue(String(response.data).contains("The email is already associated with a user"))
    }

    @Test @Order(4)
    fun registrarUnUsuarioRetornaUnTokenJWT(){
        val userJSON = ObjectMapper().writeValueAsString(UserRegisterMapper(
            "Pedro",
            "pedro@gmail.com",
            "1234",
            "image.png",
            "1111 1111 1111 1111"
        ))

        val(_, response, _) = Fuel.post("http://localhost:7000/register").jsonBody(userJSON).response()
        assertTrue(response.headers["Authorization"].isNotEmpty())
    }


}

