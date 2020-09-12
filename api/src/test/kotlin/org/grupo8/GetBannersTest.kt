package org.grupo8

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.kittinunf.fuel.Fuel
import io.javalin.Javalin
import junit.framework.TestCase
import junit.framework.TestCase.assertEquals
import org.junit.jupiter.api.*
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class GetBannersTest {

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
    fun getBannersConUnTokenValidoRetorna200(){
        val(_, response, _) = Fuel
            .get("http://localhost:7000/banners")
            .header("Authorization","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6InVfMSJ9._KhWXeXAoIRAbYJDiCxbsnHSniq7FP8B8DcY8sRDow8")
            .response()

        assertEquals(200, response.statusCode)
    }

    @Test @Order(2)
    fun getBannersSinTokenValidoRetorna401(){
        val(_, response, _) = Fuel
            .get("http://localhost:7000/banners")
            .response()

        assertEquals(401, response.statusCode)

    }

    @Test @Order(3)
    fun getBannersConTokenValidoRetornaLosBanners(){
        val(_, response, _) = Fuel
            .get("http://localhost:7000/banners")
            .header("Authorization","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6InVfMSJ9._KhWXeXAoIRAbYJDiCxbsnHSniq7FP8B8DcY8sRDow8")
            .response()

        assertEquals("Klaus", ObjectMapper().readTree(String(response.data))[0].get("title").textValue())

    }

}

