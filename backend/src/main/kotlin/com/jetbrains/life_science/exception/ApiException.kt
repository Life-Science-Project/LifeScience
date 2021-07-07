package com.jetbrains.life_science.exception

abstract class ApiException(
    val httpCode: Int,
    val code: Int,
    val arguments: List<String> = emptyList()
) : RuntimeException()