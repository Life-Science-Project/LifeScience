package com.jetbrains.life_science.protocol.service

import com.jetbrains.life_science.protocol.entity.DraftProtocol
import com.jetbrains.life_science.user.data.entity.UserPersonalData

interface DraftProtocolService {
    // DraftProtocolNotExistException - если не существует
    fun get(draftProtocolId: Long): DraftProtocol

    fun create(protocolInfo: DraftProtocolInfo): DraftProtocol

    // DraftApproachNotExistException - не найден approach, к которому прикрепить протокол
    fun update(protocolInfo: DraftProtocolInfo): DraftProtocol

    // DraftApproachNotExistException - не найден approach, к которому прикрепить протокол
    fun addParticipant(draftProtocolId: Long, userPersonalData: UserPersonalData)

    // DraftApproachNotExistException - не найден approach, к которому прикрепить протокол
    fun removeParticipant(draftProtocolId: Long, userPersonalData: UserPersonalData)

    // DraftProtocolNotExistException - если не существует
    fun delete(draftProtocolId: Long)
}
