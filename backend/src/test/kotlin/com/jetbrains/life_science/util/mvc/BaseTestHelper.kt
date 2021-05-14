package com.jetbrains.life_science.util.mvc

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

abstract class BaseTestHelper {

    protected val jsonMapper = jacksonObjectMapper()

}
