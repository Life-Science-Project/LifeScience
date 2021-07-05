package com.jetbrains.life_science.protocol.entity

import com.jetbrains.life_science.approach.entity.Approach
import com.jetbrains.life_science.article.section.entity.Section
import com.jetbrains.life_science.section.entity.ProtocolSection
import com.jetbrains.life_science.user.master.entity.User
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Inheritance
import javax.persistence.InheritanceType
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.OneToOne

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
abstract class Protocol(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    var name: String,

    @ManyToOne
    var approach: Approach,

    @OneToMany
    var sections: MutableList<ProtocolSection>,

    @OneToOne
    var owner: User
)
