package com.jetbrains.life_science.method.entity

import com.jetbrains.life_science.container.entity.Container
import com.jetbrains.life_science.section.entity.Section
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
class Method(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

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
