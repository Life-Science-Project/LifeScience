package com.jetbrains.life_science.method.entity

import com.jetbrains.life_science.container.entity.Container
import com.jetbrains.life_science.section.entity.Section
import org.hibernate.annotations.Cascade
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed
import org.springframework.data.annotation.TypeAlias
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.OneToOne
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
) {

    @OneToOne
    @Cascade(org.hibernate.annotations.CascadeType.PERSIST)
    lateinit var generalInfo: Container

}
