package com.minin.app.service

import com.minin.app.dto.company.InvitationDto
import com.minin.app.repository.InvitationRepository
import java.util.*

interface InvitationService {
    fun invite(inviterId: Long, invitationDto: InvitationDto)

    fun getByKey(key: UUID): InvitationDto

    fun delete(key: UUID)
}

class InvitationServiceImpl(
    private val invitationRepository: InvitationRepository,
    private val mailSender: MailSender,
    private val companyService: CompanyService
) : InvitationService {

    companion object {
        private const val INVITATION = "Invitation!"
        private const val BODY_TEMPLATE = "Your invitation key is: %s"
    }

    override fun invite(inviterId: Long, invitationDto: InvitationDto) {
        invitationDto.apply {
            companyId = companyService.findCompanyIdByUserId(inviterId)
            key = UUID.randomUUID()
        }
        invitationRepository.save(invitationDto)
        mailSender.sendEmail(invitationDto.email, INVITATION, BODY_TEMPLATE.format(invitationDto.key))
    }

    override fun getByKey(key: UUID) = invitationRepository.getByKey(key)

    override fun delete(key: UUID) = invitationRepository.delete(key)
}
