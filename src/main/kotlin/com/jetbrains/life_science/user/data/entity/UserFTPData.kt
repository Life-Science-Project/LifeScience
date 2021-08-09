package com.jetbrains.life_science.user.data.entity

import com.jetbrains.life_science.user.credentials.entity.Credentials
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToOne

@Entity
class UserFTPData(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @OneToOne
    val credentials: Credentials,

    @ElementCollection
    val fileNames: MutableList<String>
)
