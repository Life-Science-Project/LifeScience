package com.jetbrains.life_science.util

import java.security.Principal

val Principal.email: String get() = name
