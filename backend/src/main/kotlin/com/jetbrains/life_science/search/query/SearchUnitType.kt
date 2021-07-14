package com.jetbrains.life_science.search.query

enum class SearchUnitType {

    APPROACH {
        override val presentationName = "Approach"
        override val indexName: String = "approach"
    },

    SECTION {
        override val presentationName = "Section"
        override val indexName: String = "section"
    },

    CONTENT {
        override val presentationName = "Content"
        override val indexName: String = "content"
    },

    CATEGORY {
        override val presentationName = "Category"
        override val indexName: String = "category"
    };

    abstract val presentationName: String

    abstract val indexName: String

    companion object {
        val names = values().map { it.name }.toSet()

        val types = values().map { it.presentationName }.toSet()

        val indices = values().map { it.indexName }.toTypedArray()
    }
}
