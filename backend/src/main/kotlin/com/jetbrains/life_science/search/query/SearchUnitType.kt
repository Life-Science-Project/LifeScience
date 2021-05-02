package com.jetbrains.life_science.search.query

enum class SearchUnitType {

    ARTICLE {
        override val presentationName = "Article"
    },

    SECTION {
        override val presentationName = "Section"
    },

    CONTENT {
        override val presentationName = "Content"
    };

    abstract val presentationName: String;

    companion object {

        val types = values().map { it.presentationName }.toSet()

    }

}
