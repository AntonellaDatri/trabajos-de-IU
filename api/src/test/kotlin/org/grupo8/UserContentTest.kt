package org.grupo8

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.extensions.jsonBody
import io.javalin.Javalin
import junit.framework.TestCase.assertEquals
import org.junit.jupiter.api.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class UserContentTest {

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
    fun getUserContentConUnTokenValidoRetorna200(){
        val(_, response, _) = Fuel
            .get("http://localhost:7000/user")
            .header("Authorization",tokenForAuth)
            .response()

        assertEquals(200, response.statusCode)
    }

    @Test @Order(2)
    fun getUserContentSinTokenValidoRetorna401(){
        val(_, response, _) = Fuel
            .get("http://localhost:7000/user")
            .response()

        assertEquals(401, response.statusCode)
    }

    @Test @Order(3)
    fun getUserContentConUnTokenValidoRetornaElUserContent(){
        val(_, response, _) = Fuel
            .get("http://localhost:7000/user")
            .header("Authorization",tokenForAuth)
            .response()

        val userContent = ObjectMapper().readTree(String(response.data))
        assertEquals("Sergio", userContent.get("name").textValue())
        assertEquals("image.png", userContent.get("image").textValue())
        assertEquals(0, userContent.get("favorites").size())
        assertEquals(0, userContent.get("lastSeen").size())
    }

    @Test @Order(4)
    fun postUserFavConTokenValidoRetorna200() {
        val favId = TestHelper.getRandomContent(tokenForAuth)["id"].textValue()

        val(_, response, _) = Fuel.post("http://localhost:7000/user/fav/$favId")
                                        .header("Authorization",tokenForAuth)
                                        .response()

        assertEquals(200, response.statusCode)
        assertEquals("ok", ObjectMapper().readTree(String(response.data)).get("result").textValue())
    }

    @Test @Order(5)
    fun postUserFavSinTokenValidoRetorna401(){
        val favId = TestHelper.getRandomContent(tokenForAuth)

        val(_, response, _) = Fuel.post("http://localhost:7000/user/fav/$favId")
                                        .response()

        assertEquals(401, response.statusCode)
    }

    @Test @Order(6)
    fun postUserFavDebeEliminarElFavoritoSiyaExiste() {
        val favContent = TestHelper.getRandomContent(tokenForAuth)
        //llamada al /user para traer sus contenidos y ver que ya esta agregado (se agregó en un test anterior).
        val(_, oldContentResponse, _) = Fuel.get("http://localhost:7000/user")
            .header("Authorization",tokenForAuth)
            .response()

        val existentUserFavorites = ObjectMapper().readTree(String(oldContentResponse.data)).get("favorites")
        assertEquals(1,existentUserFavorites.size())
        assertEquals(favContent["id"].textValue(), existentUserFavorites[0]["id"].textValue())
        assertEquals(favContent["title"].textValue(), existentUserFavorites[0]["title"].textValue())

        //Vuelvo a enviar post para el mismo id.

        val(_, postResponse, _) = Fuel.post("http://localhost:7000/user/fav/" + favContent["id"].textValue())
            .header("Authorization",tokenForAuth)
            .response()

        //Reviso que saliera Ok.
        assertEquals(200, postResponse.statusCode)
        assertEquals("ok", ObjectMapper().readTree(String(postResponse.data)).get("result").textValue())

        //Traigo los nuevos contenidos.
        val (_, newContentResponse, _) = Fuel.get("http://localhost:7000/user")
            .header("Authorization",tokenForAuth)
            .response()

        assertEquals(0,ObjectMapper().readTree(String(newContentResponse.data)).get("favorites").size())
    }

    @Test @Order(7)
    fun postUserFavDebeAgregarElFavoritoSiNoExiste(){
        val favContent = TestHelper.getRandomContent(tokenForAuth)
        //llamada al /user para traer sus contenidos y ver que no esta agregado.
        val(_, oldContentResponse, _) = Fuel.get("http://localhost:7000/user")
            .header("Authorization",tokenForAuth)
            .response()
                assertEquals(0,ObjectMapper().readTree(String(oldContentResponse.data)).get("favorites").size())

        //Enviar post para agregar el favorito.

        val(_, postResponse, _) = Fuel.post("http://localhost:7000/user/fav/" + favContent["id"].textValue())
            .header("Authorization",tokenForAuth)
            .response()

        //Reviso que saliera Ok.
        assertEquals(200, postResponse.statusCode)
        assertEquals("ok", ObjectMapper().readTree(String(postResponse.data)).get("result").textValue())

        //Traigo los nuevos contenidos.
        val (_, newContentResponse, _) = Fuel.get("http://localhost:7000/user")
            .header("Authorization",tokenForAuth)
            .response()

        val newUserFavorites = ObjectMapper().readTree(String(newContentResponse.data)).get("favorites")
        assertEquals(1,newUserFavorites.size())
        assertEquals(favContent["id"].textValue(), newUserFavorites[0]["id"].textValue())
        assertEquals(favContent["title"].textValue(), newUserFavorites[0]["title"].textValue())
    }

    @Test @Order(8)
    fun postUserLastSeenConTokenValidoRetorna200YAgregarElLastSeen() {
        val someContent = TestHelper.getRandomContent(tokenForAuth)
        val lastSeenId = someContent["id"].textValue()
        val lastSeenTitle = someContent["title"].textValue()
        val lastSeenJSON = ObjectMapper().writeValueAsString(LastSeenIdMapper(
            lastSeenId
        ))

        //llamada al /user para traer sus contenidos y ver que no esta agregado.
        val(_, oldContentResponse, _) = Fuel.get("http://localhost:7000/user")
            .header("Authorization",tokenForAuth)
            .response()
        assertEquals(0,ObjectMapper().readTree(String(oldContentResponse.data)).get("lastSeen").size())

        //Post para agregarlo.
        val(_, response, _) = Fuel.post("http://localhost:7000/user/lastSeen")
            .header("Authorization",tokenForAuth)
            .jsonBody(lastSeenJSON)
            .response()

        //Sale ok.
        assertEquals(200, response.statusCode)
        assertEquals("ok", ObjectMapper().readTree(String(response.data)).get("result").textValue())

        //Traigo los nuevos contenidos.
        val (_, newContentResponse, _) = Fuel.get("http://localhost:7000/user")
            .header("Authorization",tokenForAuth)
            .response()

        val newUserLastSeen = ObjectMapper().readTree(String(newContentResponse.data)).get("lastSeen")
        assertEquals(1,newUserLastSeen.size())
        assertEquals(lastSeenId, newUserLastSeen[0]["id"].textValue())
        assertEquals(lastSeenTitle, newUserLastSeen[0]["title"].textValue())


    }

    @Test @Order(9)
    fun postUserLastSeenSinTokenValidoRetorna401(){
        val lastSeenId = TestHelper.getRandomContent(tokenForAuth)["id"].textValue()
        val lastSeenJSON = ObjectMapper().writeValueAsString(LastSeenIdMapper(
            lastSeenId
        ))

        val(_, response, _) = Fuel.post("http://localhost:7000/user/lastSeen")
            .jsonBody(lastSeenJSON)
            .response()

        assertEquals(401, response.statusCode)
    }

    @Test @Order(10)
    fun postUserLastSeenConTokenValidoYUnContenidoInexistenteRetorna404(){
        val lastSeenId = "mov_inexistente"
        val lastSeenJSON = ObjectMapper().writeValueAsString(LastSeenIdMapper(
            lastSeenId
        ))

        val(_, response, _) = Fuel.post("http://localhost:7000/user/lastSeen")
            .header("Authorization",tokenForAuth)
            .jsonBody(lastSeenJSON)
            .response()

        assertEquals(404, response.statusCode)
        assertEquals("Contenido no encontrado", ObjectMapper().readTree(String(response.data)).get("error").textValue())
    }

    @Test @Order(11)
    fun postUserFavConTokenValidoYUnContenidoInexistenteRetorna404(){
        val favId = "mov_inexistente"

        val(_, response, _) = Fuel.post("http://localhost:7000/user/fav/$favId")
            .header("Authorization",tokenForAuth)
            .response()

        assertEquals(404, response.statusCode)
        assertEquals("Contenido no encontrado", ObjectMapper().readTree(String(response.data)).get("error").textValue())
    }
}
