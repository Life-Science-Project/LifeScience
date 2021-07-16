package com.jetbrains.life_science.search.query

enum class SearchUnitType {

    APPROACH {
        override val presentationName = "Approach"
        override val indexName = "approach"
        override val order = Order(20L)
    },

    CONTENT {
        override val presentationName = "Content"
        override val indexName = "content"
        override val order = Order(30L)
    },

    CATEGORY {
        override val presentationName = "Category"
        override val indexName = "category"
        override val order = Order(10L)
    };

    abstract val presentationName: String

    abstract val indexName: String

    abstract val order: Order

    inline class Order(val value: Long) : Comparable<Order> {
        override fun compareTo(other: Order) = value.compareTo(other.value)
    }

    companion object {
        val names = values().map { it.name }.toSet()

        val types = values().map { it.presentationName }.toSet()

        val indices = values().map { it.indexName }.toTypedArray()
    }
}
