package com.jetbrains.life_science.exception

class MethodVersionNotFoundException(id: Long) : AbstractNotFoundException("method version with id: $id not found")
