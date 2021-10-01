package com.jetbrains.life_science.exception.request

class UserAlreadyExistsException(val email: String) : RuntimeException()
