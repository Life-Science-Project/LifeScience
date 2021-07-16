package com.jetbrains.life_science.exception.request

import java.lang.RuntimeException

class RemoveOwnerFromParticipantsException(override val message: String) : RuntimeException(message)
