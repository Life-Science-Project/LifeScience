package com.jetbrains.life_science.version.adapters

import com.jetbrains.life_science.container.service.ContainerCreationInfo
import com.jetbrains.life_science.version.entity.MethodVersion

class ContainerEmptyCreationToInfoAdapter(
    val version: MethodVersion
) : ContainerCreationInfo {
    override val method: MethodVersion
        get() = version
    override val name: String
        get() = "General information"
    override val description: String
        get() = ""
}
