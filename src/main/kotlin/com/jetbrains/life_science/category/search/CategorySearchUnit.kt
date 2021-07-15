package com.jetbrains.life_science.category.search

import org.springframework.data.annotation.TypeAlias
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType
import javax.persistence.Id

@Document(indexName = "category", createIndex = true)
@TypeAlias("Category")
data class CategorySearchUnit(

    @Id
    val id: Long,

    @Field(name = "text", type = FieldType.Text)
    val name: String

)
