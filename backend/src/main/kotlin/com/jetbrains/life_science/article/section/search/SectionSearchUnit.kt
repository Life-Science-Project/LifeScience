package com.jetbrains.life_science.article.section.search

import org.springframework.data.annotation.TypeAlias
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field

@Document(indexName = "section", createIndex = true)
@TypeAlias("Section")
class SectionSearchUnit(

    val id: Long,

    @Field(name = "text")
    var description: String

)
