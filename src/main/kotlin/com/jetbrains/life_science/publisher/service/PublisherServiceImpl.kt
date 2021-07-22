package com.jetbrains.life_science.publisher.service

import com.jetbrains.life_science.approach.entity.DraftApproach
import com.jetbrains.life_science.approach.entity.PublicApproach
import com.jetbrains.life_science.approach.service.DraftApproachService
import com.jetbrains.life_science.approach.service.PublicApproachService
import com.jetbrains.life_science.edit_record.entity.ApproachEditRecord
import com.jetbrains.life_science.edit_record.service.ApproachEditRecordService
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
    val approachEditRecordService: ApproachEditRecordService,
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

    override fun publishApproachEditRecord(approachEditRecord: ApproachEditRecord): PublicApproach {
        val approach = approachEditRecord.approach
        val toCreate = approachEditRecord.createdSections.toList()
        val toDelete = approachEditRecord.deletedSections.toList()

        // Clear
        approachEditRecordService.clear(approachEditRecord.id)

        // Delete
        toDelete.forEach {
            publicApproachService.removeSection(approach.id, it)
            sectionService.deleteById(it.id)
        }

        // Create
        toCreate.forEach {
            publicApproachService.addSection(approach.id, it)
            sectionService.publish(it.id)
        }

        return approach
    }

    override fun publishProtocolEditRecord(protocolEditRecord: ApproachEditRecord): PublicProtocol {
        TODO("Not yet implemented")
    }

    private fun publishSections(sections: List<Section>) = sections.forEach { sectionService.publish(it.id) }
}
