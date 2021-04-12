package com.jetbrains.life_science.method.search

import org.springframework.data.annotation.TypeAlias
import org.springframework.data.elasticsearch.annotations.Document

@Document(indexName = "method", createIndex = true)
@TypeAlias("Method")
class MethodSearchUnit(

    val id: Long,

    var name: String,
)
