package com.jetbrains.life_science.version.repository

import com.jetbrains.life_science.version.entity.MethodVersion
import com.jetbrains.life_science.version.entity.State
import org.springframework.data.jpa.repository.JpaRepository

interface MethodVersionRepository: JpaRepository<MethodVersion, Long> {

    fun findByMainMethod_IdAndState(id: Long, state: State): MethodVersion?

}
