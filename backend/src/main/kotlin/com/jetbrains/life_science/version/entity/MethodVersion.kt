package com.jetbrains.life_science.version.entity

import com.jetbrains.life_science.container.entity.Container
import com.jetbrains.life_science.method.entity.Method
import com.jetbrains.life_science.user.entity.User
import javax.persistence.*

@Entity
@Table(indexes = [Index(name = "state_idx", columnList = "state")])
class MethodVersion(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    val name: String,

    var state: State,

    @ManyToOne
    val mainMethod: Method,

    @OneToMany(mappedBy = "method", cascade = [CascadeType.ALL])
    var containers: MutableList<Container> = mutableListOf()

)
