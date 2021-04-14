package com.jetbrains.life_science.section.search.service

import com.jetbrains.life_science.section.entity.Section
import com.jetbrains.life_science.section.search.factory.SectionSearchUnitFactory
import com.jetbrains.life_science.section.search.repository.SectionSearchUnitRepository
import com.jetbrains.life_science.exception.search_unit.ContainerSearchUnitNotFoundException
import org.springframework.stereotype.Service

@Service
class SectionSearchUnitServiceImpl(
    val repository: SectionSearchUnitRepository,
    val factory: SectionSearchUnitFactory
) : SectionSearchUnitService {

    override fun create(section: Section) {
        val searchUnit = factory.create(section)
        repository.save(searchUnit)
    }

    override fun delete(id: Long) {
        checkExistsById(id)
        repository.deleteById(id)
    }

    override fun update(section: Section) {
        checkExistsById(section.id)
        val searchUnit = factory.create(section)
        repository.save(searchUnit)
    }

    private fun checkExistsById(id: Long) {
        if (!repository.existsById(id)) {
            throw ContainerSearchUnitNotFoundException("Container search unit with id $id not found")
        }
    }
}
