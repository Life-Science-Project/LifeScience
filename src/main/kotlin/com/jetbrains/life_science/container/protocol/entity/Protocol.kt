package com.jetbrains.life_science.container.protocol.entity

import com.jetbrains.life_science.container.approach.entity.PublicApproach
import com.jetbrains.life_science.ftp.entity.FTPFile
import com.jetbrains.life_science.section.entity.Section
import com.jetbrains.life_science.user.credentials.entity.Credentials
import javax.persistence.*

@MappedSuperclass
abstract class Protocol(

    var name: String,

    @ManyToOne
    var approach: PublicApproach,

    @OneToMany
    var sections: MutableList<Section>,

    @ManyToOne
    var owner: Credentials,

    @OneToMany
    val files: MutableList<FTPFile>
) {
    abstract val id: Long
}
