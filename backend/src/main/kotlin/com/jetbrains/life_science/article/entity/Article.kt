package com.jetbrains.life_science.article.entity

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.TypeAlias
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType

@TypeAlias("Article")
@Document(indexName = "article", createIndex = true)
class Article(

    var containerId: Long,

    @Field(type = FieldType.Text)
    var text: String,

    @Field
    var tags: MutableList<String>,

    @Field
    var references: MutableList<String>,

    @Id
    val id: String? = null

) {
    @Field(name = "_entity_type")
    val type: String = "Article"
}
