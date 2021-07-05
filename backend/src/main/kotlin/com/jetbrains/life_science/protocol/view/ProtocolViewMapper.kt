package com.jetbrains.life_science.protocol.view

import com.jetbrains.life_science.protocol.entity.Protocol
import com.jetbrains.life_science.section.view.SectionViewMapper
import org.springframework.stereotype.Component

@Component
class ProtocolViewMapper(
    val sectionViewMapper: SectionViewMapper
) {
    fun createView(protocol: Protocol): ProtocolView {
        return ProtocolView(
            id = protocol.id,
            name = protocol.name,
            approachId = protocol.approach.id,
            sections = protocol.sections.map { sectionViewMapper.createView(it) }
        )
    }
}
