package com.jetbrains.life_science.exception.maker

import com.jetbrains.life_science.exception.handler.ApiExceptionView

fun makeExceptionView(code: Int, vararg arguments: Any): ApiExceptionView {
    return ApiExceptionView(
        systemCode = code,
        arguments = arguments.map { listOf(it.toString()) }
    )
}
