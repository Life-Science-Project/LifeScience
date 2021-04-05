package com.jetbrains.life_science.container.entity

import com.jetbrains.life_science.method.entity.Method
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed
import org.springframework.data.annotation.TypeAlias
import javax.persistence.*


@Entity
@TypeAlias("Container")
@Indexed(index = "container")
class Container(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,

    @FullTextField(name = "text")
    @Column(nullable = false)
    var name: String,

    var description: String?,

    @ManyToOne
    val method: Method
)
