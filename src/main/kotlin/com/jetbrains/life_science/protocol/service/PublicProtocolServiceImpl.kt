package com.jetbrains.life_science.protocol.service

import com.jetbrains.life_science.exception.not_found.PublicProtocolNotFoundException
import com.jetbrains.life_science.protocol.entity.PublicProtocol
import com.jetbrains.life_science.protocol.factory.PublicProtocolFactory
import com.jetbrains.life_science.protocol.repository.PublicProtocolRepository
import com.jetbrains.life_science.protocol.search.service.ProtocolSearchUnitService
import org.springframework.stereotype.Service

@Service
class PublicProtocolServiceImpl(
    val repository: PublicProtocolRepository,
    val factory: PublicProtocolFactory,
    val searchUnitService: ProtocolSearchUnitService
) : PublicProtocolService {
    override fun get(id: Long): PublicProtocol {
        return repository.findById(id).orElseThrow {
            PublicProtocolNotFoundException("Public protocol with id $id is not found")
        }
    }

    override fun create(info: PublicProtocolInfo): PublicProtocol {
        val publicProtocol = factory.create(info)
        val savedPublicProtocol = repository.save(publicProtocol)
        searchUnitService.createSearchUnit(savedPublicProtocol)
        return savedPublicProtocol
    }
}
