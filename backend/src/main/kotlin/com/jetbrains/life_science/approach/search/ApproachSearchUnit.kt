package com.jetbrains.life_science.approach.search

import org.springframework.data.annotation.TypeAlias
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType
import javax.persistence.Id

@Document(indexName = "approach", createIndex = true)
@TypeAlias("Approach")
data class ApproachSearchUnit(

    @Id
    val id: Long,

    @Field(name = "text", type = FieldType.Text)
    val name: String

)
