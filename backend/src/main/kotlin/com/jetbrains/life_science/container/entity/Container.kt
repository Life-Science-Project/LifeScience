package com.jetbrains.life_science.container.entity

import com.jetbrains.life_science.method.entity.Method
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
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

    @Column(nullable = false)
    var name: String,

    @FullTextField(name = "text")
    var description: String?,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "method_id", nullable = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    val method: Method
)
