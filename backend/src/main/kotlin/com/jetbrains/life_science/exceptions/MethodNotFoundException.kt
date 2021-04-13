package com.jetbrains.life_science.exceptions

class MethodNotFoundException(id: Long) : AbstractNotFoundException("method with id: $id not found")
