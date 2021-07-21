package com.jetbrains.life_science.publisher.service

import com.jetbrains.life_science.approach.entity.DraftApproach
import com.jetbrains.life_science.approach.entity.PublicApproach
import com.jetbrains.life_science.approach.service.DraftApproachService
import com.jetbrains.life_science.approach.service.PublicApproachService
import com.jetbrains.life_science.protocol.entity.DraftProtocol
import com.jetbrains.life_science.protocol.entity.PublicProtocol
import com.jetbrains.life_science.protocol.service.DraftProtocolService
import com.jetbrains.life_science.protocol.service.PublicProtocolService
import com.jetbrains.life_science.section.entity.Section
import com.jetbrains.life_science.section.service.SectionService
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class PublisherServiceImpl(
    val publicApproachService: PublicApproachService,
    val publicProtocolService: PublicProtocolService,
    val draftApproachService: DraftApproachService,
    val draftProtocolService: DraftProtocolService,
    val sectionService: SectionService
) : PublisherService {
    override fun publishDraftApproach(draftApproach: DraftApproach): PublicApproach {
        // Create public entity
        val publicApproach = publicApproachService.create(
            draftApproach
        )

        // Delete draft entity
        draftApproachService.delete(draftApproach.id)

        // Publish sections in public entity
        publishSections(publicApproach.sections)

        return publicApproach
    }

    override fun publishDraftProtocol(draftProtocol: DraftProtocol): PublicProtocol {

        // Create public entity
        val publicProtocol = publicProtocolService.create(
            draftProtocol
        )

        // Delete draft entity
        draftProtocolService.delete(draftProtocol.id)

        // Publish sections in public entity
        publishSections(publicProtocol.sections)

        return publicProtocol
    }

    override fun publishApproachEditRecord() {
        TODO("Not yet implemented")
    }

    override fun publishProtocolEditRecord() {
        TODO("Not yet implemented")
    }

    private fun publishSections(sections: List<Section>) = sections.forEach { sectionService.publish(it.id) }
}