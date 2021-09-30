package com.jetbrains.life_science.container.approach.entity

import com.jetbrains.life_science.category.entity.Category
import com.jetbrains.life_science.container.protocol.entity.PublicProtocol
import com.jetbrains.life_science.section.entity.Section
import com.jetbrains.life_science.user.credentials.entity.Credentials
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class PublicApproach(
    @Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "public_approach_seq"
    )
    @SequenceGenerator(
        name = "public_approach_seq",
        allocationSize = 1
    )
    override val id: Long,

    name: String,
    aliases: MutableList<String>,
    sections: MutableList<Section>,
    tags: MutableList<String>,
    owner: Credentials,
    creationDate: LocalDateTime,

    @ManyToMany
    var coAuthors: MutableList<Credentials>,

    @OneToMany(mappedBy = "approach", cascade = [CascadeType.REMOVE, CascadeType.PERSIST])
    var protocols: MutableList<PublicProtocol>,

    @ManyToMany(mappedBy = "approaches")
    override var categories: MutableList<Category>

) : Approach(name, aliases, sections, tags, owner, creationDate)
