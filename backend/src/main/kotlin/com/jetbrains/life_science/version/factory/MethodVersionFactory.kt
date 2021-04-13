package com.jetbrains.life_science.version.factory

import com.jetbrains.life_science.method.entity.Method
import com.jetbrains.life_science.version.entity.MethodVersion
import com.jetbrains.life_science.version.entity.State
import com.jetbrains.life_science.version.service.MethodVersionInfo
import org.springframework.stereotype.Component

@Component
class MethodVersionFactory {

    fun create(info: MethodVersionInfo, method: Method): MethodVersion {
        return MethodVersion(0, info.name, State.EDITING, method, info.user)
    }

    fun createCopy(methodVersion: MethodVersion): MethodVersion {
        return MethodVersion(
            id = 0,
            name = methodVersion.name,
            state = State.EDITING,
            mainMethod = methodVersion.mainMethod,
            author = methodVersion.author
        )
    }

}
