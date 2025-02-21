package com.minin.app.service

import com.minin.app.repository.CompanyRepository
import com.minin.app.repository.UserRepository

interface CompanyService {
    fun create(ownerId: Long, title: String)

    fun findCompanyIdByUserId(userId: Long): Long
}

class CompanyServiceImpl(
    private val companyRepository: CompanyRepository,
    private val userRepository: UserRepository
) : CompanyService {
    override fun create(ownerId: Long, title: String) {
        val companyId = companyRepository.create(title)
        userRepository.addCompany(ownerId, companyId)
    }

    override fun findCompanyIdByUserId(userId: Long) = companyRepository.findCompanyIdByUserId(userId)
}
