package org.grupo8

import data.getUNQFlix
import data.idGenerator
import domain.*
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*

object Main {
    @JvmStatic
    fun main(args: Array<String>) {

        UnqflixAPI(7000).init()
    }

}