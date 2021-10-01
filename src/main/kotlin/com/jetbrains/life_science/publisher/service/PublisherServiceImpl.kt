package com.jetbrains.life_science.publisher.service

import com.jetbrains.life_science.container.ContainsSections
import com.jetbrains.life_science.container.approach.entity.DraftApproach
import com.jetbrains.life_science.container.approach.entity.PublicApproach
import com.jetbrains.life_science.container.approach.service.DraftApproachService
import com.jetbrains.life_science.container.approach.service.PublicApproachService
import com.jetbrains.life_science.edit_record.entity.ApproachEditRecord
import com.jetbrains.life_science.edit_record.entity.EditRecord
import com.jetbrains.life_science.edit_record.entity.ProtocolEditRecord
import com.jetbrains.life_science.edit_record.service.ApproachEditRecordService
import com.jetbrains.life_science.edit_record.service.ProtocolEditRecordService
import com.jetbrains.life_science.container.protocol.entity.DraftProtocol
import com.jetbrains.life_science.container.protocol.entity.PublicProtocol
import com.jetbrains.life_science.container.protocol.service.DraftProtocolService
import com.jetbrains.life_science.container.protocol.service.PublicProtocolService
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
    val protocolEditRecordService: ProtocolEditRecordService,
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

    @Transactional
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

    @Transactional
    override fun publishApproachEditRecord(approachEditRecord: ApproachEditRecord): PublicApproach {
        val approach = approachEditRecord.approach
        processEditRecord(approachEditRecord, publicApproachService, approach.sections, approach.id, approachEditRecordService::clear)
        return approach
    }

    @Transactional
    override fun publishProtocolEditRecord(protocolEditRecord: ProtocolEditRecord): PublicProtocol {
        val protocol = protocolEditRecord.protocol
        processEditRecord(protocolEditRecord, publicProtocolService, protocol.sections, protocol.id, protocolEditRecordService::clear)
        return protocol
    }

    private fun processEditRecord(
        editRecord: EditRecord,
        entityService: ContainsSections,
        sections: List<Section>,
        entityId: Long,
        editRecordClearById: (Long) -> (Unit)
    ) {
        val toCreate = editRecord.createdSections.toList()
        val toDelete = editRecord.deletedSections.toList()

        editRecordClearById(editRecord.id)

        toDelete.forEach {
            entityService.removeSection(entityId, it)
            sectionService.deleteById(it.id, sections)
        }
        toCreate.forEach {
            entityService.addSection(entityId, it)
            sectionService.publish(it.id)
        }
    }

    private fun publishSections(sections: List<Section>) = sections.forEach { sectionService.publish(it.id) }
}
