package com.jetbrains.life_science.exception

class MethodNotFoundException(id: Long) : AbstractNotFoundException("method with id: $id not found")
