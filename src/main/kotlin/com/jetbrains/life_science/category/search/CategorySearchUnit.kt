package com.jetbrains.life_science.category.search

import org.springframework.data.annotation.TypeAlias
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import javax.persistence.Id

@Document(indexName = "category", createIndex = true)
@TypeAlias("Category")
data class CategorySearchUnit(

    @Id
    val id: Long,

    @Field
    val names: List<String>,

    @Field
    val context: List<String>
)
