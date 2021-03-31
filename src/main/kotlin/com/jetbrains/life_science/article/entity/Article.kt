package com.jetbrains.life_science.article.entity

import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
@Indexed
class Article(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,

    @FullTextField
    var text: String
) {
    override fun toString(): String {
        return "Article(id=$id, text='$text')"
    }
}
