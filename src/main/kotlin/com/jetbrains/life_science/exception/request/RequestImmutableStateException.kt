package com.jetbrains.life_science.exception.request

import java.lang.RuntimeException

class RequestImmutableStateException(override val message: String) : RuntimeException(message)
