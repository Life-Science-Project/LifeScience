package com.jetbrains.life_science.search.query

enum class SearchUnitType {

    ARTICLE,

    SECTION,

    CONTENT;

    companion object{

        private val types = values().map { it.name }.toSet()


    }

}
