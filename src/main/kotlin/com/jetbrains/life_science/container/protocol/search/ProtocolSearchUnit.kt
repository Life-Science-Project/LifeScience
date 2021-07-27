package com.jetbrains.life_science.container.protocol.search

import org.springframework.data.annotation.TypeAlias
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import javax.persistence.Id

@Document(indexName = "protocol", createIndex = true)
@TypeAlias("Protocol")
data class ProtocolSearchUnit(

    @Id
    val id: Long,

    val approachId: Long,

    @Field
    val names: List<String>,

    @Field
    val context: List<String>
)
