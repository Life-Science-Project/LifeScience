package com.jetbrains.life_science.version.search

import org.springframework.data.annotation.TypeAlias
import org.springframework.data.elasticsearch.annotations.Document
import javax.persistence.Id

@Document(indexName = "method", createIndex = true)
@TypeAlias("Method")
class MethodVersionSearchUnit(

    @Id
    val id: Long,

    val name: String

)
