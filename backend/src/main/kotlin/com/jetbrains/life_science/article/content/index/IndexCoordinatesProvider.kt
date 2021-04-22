package com.jetbrains.life_science.article.content.index

import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates

interface IndexCoordinatesProvider {

    val indexCoordinates: IndexCoordinates
}
