package com.jetbrains.life_science.exceptions

class MethodVersionNotFoundException(id: Long) : AbstractNotFoundException("method version with id: $id not found")
