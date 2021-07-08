package com.jetbrains.life_science.approach.service

import com.jetbrains.life_science.approach.entity.DraftApproach
import com.jetbrains.life_science.user.credentials.entity.Credentials
import org.springframework.stereotype.Service

@Service
class DraftApproachServiceImpl : DraftApproachService {
    override fun get(id: Long) {
        TODO("Not yet implemented")
    }

    override fun create(info: ApproachInfo): DraftApproach {
        TODO("Not yet implemented")
    }

    override fun update(info: ApproachInfo) {
        TODO("Not yet implemented")
    }

    override fun delete(id: Long) {
        TODO("Not yet implemented")
    }

    override fun addParticipant(draftApproachId: Long, user: Credentials) {
        TODO("Not yet implemented")
    }

    override fun removeParticipant(draftApproachId: Long, user: Credentials) {
        TODO("Not yet implemented")
    }
}
