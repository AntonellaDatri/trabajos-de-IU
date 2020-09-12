package org.grupo8

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.extensions.jsonBody
import io.javalin.Javalin
import junit.framework.TestCase.assertEquals
import org.junit.jupiter.api.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class SearchTest {

    private lateinit var tokenForAuth: String
    private lateinit var api: Javalin

    @BeforeAll
    fun setUp(){
        api = UnqflixAPI(7000).init()
        tokenForAuth = TestHelper.getTokenForNewUser()
    }

    @AfterAll
    fun tearDown(){
        api.stop()
    }

    @Test @Order(1)
    fun getSearchConUnTokenValidoRetorna201(){
        val(_, response, _) = Fuel
            .get("http://localhost:7000/search?text=godfather")
            .header("Authorization",tokenForAuth)
            .response()

        assertEquals(200, response.statusCode)
    }

    @Test @Order(2)
    fun getSearchSinTokenValidoRetorna401(){
        val(_, response, _) = Fuel
            .get("http://localhost:7000/search?text=godfather")
            .response()

        assertEquals(401, response.statusCode)
    }

    @Test @Order(3)
    fun getSearchDevuelveContenidoConEseNombre(){
        val(_, response, _) = Fuel
            .get("http://localhost:7000/search?text=godfather")
            .header("Authorization",tokenForAuth)
            .response()

        assertEquals("The Godfather", ObjectMapper().readTree(String(response.data))[0].get("title").textValue())
    }

    @Test @Order(4)
    fun getSearchSinParametrosRetorna400(){
        val(_, response, _) = Fuel
            .get("http://localhost:7000/search?text=")
            .header("Authorization",tokenForAuth)
            .response()

        assertEquals(400, response.statusCode)
    }

}