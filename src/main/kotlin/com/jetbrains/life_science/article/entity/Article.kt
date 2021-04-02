package com.jetbrains.life_science.article.entity

import com.jetbrains.life_science.method.entity.Method
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexedEmbedded
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToOne

@Entity
@Indexed
class Article(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,

    @FullTextField
    var text: String,

) {
    @OneToOne
    @IndexedEmbedded
    lateinit var method: Method
}
