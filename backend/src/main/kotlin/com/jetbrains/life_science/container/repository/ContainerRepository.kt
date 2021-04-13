package com.jetbrains.life_science.container.repository

import com.jetbrains.life_science.container.entity.Container
import com.jetbrains.life_science.version.entity.MethodVersion
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ContainerRepository : JpaRepository<Container, Long> {

    fun findAllByMethod(methodId: MethodVersion): List<Container>
}
