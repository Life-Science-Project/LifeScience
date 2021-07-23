package com.jetbrains.life_science.container.approach.search

import org.springframework.data.annotation.TypeAlias
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import javax.persistence.Id

@Document(indexName = "approach", createIndex = true)
@TypeAlias("Approach")
data class ApproachSearchUnit(

    @Id
    val id: Long,

    @Field
    val names: List<String>,

    @Field
    val context: List<String>
)
