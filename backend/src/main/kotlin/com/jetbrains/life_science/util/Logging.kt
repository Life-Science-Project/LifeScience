package com.jetbrains.life_science.util

import org.slf4j.Logger
import org.slf4j.LoggerFactory

fun <T : Any> T.getLogger(): Logger = LoggerFactory.getLogger(javaClass)
