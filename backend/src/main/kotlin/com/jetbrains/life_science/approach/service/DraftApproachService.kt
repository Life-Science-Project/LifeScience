package com.jetbrains.life_science.approach.service

import com.jetbrains.life_science.approach.entity.DraftApproach
import com.jetbrains.life_science.user.credentials.entity.Credentials

interface DraftApproachService {

    // DraftApproachNotFoundException
    fun get(id: Long)

    // DraftApproachAlreadyExistsException
    fun create(info: DraftApproachInfo): DraftApproach

    // DraftApproachNotFoundException
    fun update(info: DraftApproachInfo)

    // DraftApproachNotFoundException
    fun delete(id: Long)

    // DraftApproachNotFoundException, UserAlreadyExistsException -- в случае если он уже добавлен
    // Над вторым названием над подумать, как вариант -- UserAlreadyParticipantException
    // или UserAlreadyInGroupException
    fun addParticipant(draftApproachId: Long, user: Credentials)

    // DraftApproachNotFoundException, UserNotFoundException -- в случе если такого пользователя нет
    fun removeParticipant(draftApproachId: Long, user: Credentials)
}
