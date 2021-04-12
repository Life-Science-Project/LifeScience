package com.jetbrains.life_science.container.search

import org.springframework.data.annotation.TypeAlias
import org.springframework.data.elasticsearch.annotations.Document

@Document(indexName = "container", createIndex = true)
@TypeAlias("Container")
class ContainerSearchUnit(

    val id: Long,

    var description: String

)
