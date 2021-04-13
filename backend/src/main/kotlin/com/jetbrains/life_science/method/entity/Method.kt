package com.jetbrains.life_science.method.entity

import com.jetbrains.life_science.container.entity.Container
import com.jetbrains.life_science.section.entity.Section
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed
import org.springframework.data.annotation.TypeAlias
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
@TypeAlias("Method")
@Indexed(index = "method")
class Method(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @FullTextField(name = "text")
    @NotBlank
    var name: String,

    @ManyToOne
    var section: Section,

    @OneToMany(mappedBy = "method", cascade = [CascadeType.ALL])
    var containers: MutableList<Container> = mutableListOf()

) {

    @OneToOne(cascade = [CascadeType.ALL])
    lateinit var generalInfo: Container
}