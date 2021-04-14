package com.jetbrains.life_science.section.search

import org.springframework.data.annotation.TypeAlias
import org.springframework.data.elasticsearch.annotations.Document

@Document(indexName = "section", createIndex = true)
@TypeAlias("Section")
class SectionSearchUnit(

    val id: Long,

    var description: String

)
