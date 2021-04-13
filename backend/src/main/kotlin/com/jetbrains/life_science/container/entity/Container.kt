package com.jetbrains.life_science.container.entity

import com.jetbrains.life_science.version.entity.MethodVersion
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import javax.persistence.*

@Entity
class Container(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,

    @Column(nullable = false)
    var name: String,

    var description: String?,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "method_id", nullable = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    var method: MethodVersion
)
