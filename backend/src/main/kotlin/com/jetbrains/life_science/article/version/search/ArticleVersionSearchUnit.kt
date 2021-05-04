package com.jetbrains.life_science.article.version.search

import org.springframework.data.annotation.TypeAlias
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType
import javax.persistence.Id

@Document(indexName = "article", createIndex = true)
@TypeAlias("Article")
data class ArticleVersionSearchUnit(

    @Id
    val id: Long,

    val articleId: Long,

    @Field(name = "text", type = FieldType.Text)
    val name: String

)
