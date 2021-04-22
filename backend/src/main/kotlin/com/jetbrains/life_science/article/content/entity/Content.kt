package com.jetbrains.life_science.article.content.entity

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.TypeAlias
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType

@Document(indexName = "content", createIndex = true)
@TypeAlias("Content")
class Content(

    var sectionId: Long,

    @Field(type = FieldType.Text)
    var text: String,

    @Field
    var tags: MutableList<String>,

    @Field
    var references: MutableList<String>,

    @Id
    val id: String? = null

)
