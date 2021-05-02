package com.jetbrains.life_science.search.query

interface SearchQueryInfo {

    val text: String

    val exclusionTypes: Array<String>

    val from: Int

    val size: Int

}
