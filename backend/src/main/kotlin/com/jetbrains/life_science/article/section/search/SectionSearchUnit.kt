package com.jetbrains.life_science.article.section.search

import org.springframework.data.annotation.TypeAlias
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType

@Document(indexName = "section", createIndex = true)
@TypeAlias("Section")
data class SectionSearchUnit(

    val id: Long,

    @Field(name = "text", type = FieldType.Text)
    var description: String,

    val articleVersionId: Long

)
