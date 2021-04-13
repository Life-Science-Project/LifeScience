package com.jetbrains.life_science.container.service

import com.jetbrains.life_science.version.entity.MethodVersion

interface ContainerCreationInfo {

    val method: MethodVersion

    val name: String

    val description: String

}
