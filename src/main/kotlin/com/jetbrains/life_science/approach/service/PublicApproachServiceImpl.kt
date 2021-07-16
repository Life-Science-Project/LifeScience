package com.jetbrains.life_science.approach.service

import com.jetbrains.life_science.approach.entity.PublicApproach
import com.jetbrains.life_science.approach.factory.PublicApproachFactory
import com.jetbrains.life_science.approach.repository.PublicApproachRepository
import com.jetbrains.life_science.approach.search.service.ApproachSearchUnitService
import com.jetbrains.life_science.exception.not_found.PublicApproachNotFoundException
import org.springframework.stereotype.Service

@Service
class PublicApproachServiceImpl(
    val repository: PublicApproachRepository,
    val factory: PublicApproachFactory,
    val searchUnitService: ApproachSearchUnitService
) : PublicApproachService {
    override fun get(id: Long): PublicApproach {
        return repository.findById(id).orElseThrow {
            PublicApproachNotFoundException("Public approach with id $id is not found")
        }
    }

    override fun create(info: PublicApproachInfo): PublicApproach {
        val publicApproach = factory.create(info)
        val savedPublicApproach = repository.save(publicApproach)
        searchUnitService.createSearchUnit(savedPublicApproach)
        return savedPublicApproach
    }
}
