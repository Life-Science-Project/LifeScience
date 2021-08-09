package com.jetbrains.life_science.container.approach.entity

import com.jetbrains.life_science.ftp.entity.FTPFile
import com.jetbrains.life_science.section.entity.Section
import com.jetbrains.life_science.user.credentials.entity.Credentials
import java.time.LocalDateTime
import javax.persistence.*

@MappedSuperclass
abstract class Approach(

    var name: String,

    @ElementCollection
    var aliases: MutableList<String>,

    @OneToMany
    var sections: MutableList<Section>,

    @ElementCollection
    var tags: MutableList<String>,

    @ManyToOne
    var owner: Credentials,

    val creationDate: LocalDateTime,

    @OneToMany
    val files: MutableList<FTPFile>
) {
    abstract val id: Long
}
