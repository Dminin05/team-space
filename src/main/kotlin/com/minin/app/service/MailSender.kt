package com.minin.app.service

import com.minin.infrastructure.config.AppConfig
import org.apache.commons.mail.DefaultAuthenticator
import org.apache.commons.mail.SimpleEmail

interface MailSender {
    fun sendEmail(address: String, subject: String, body: String)
}

class MailSenderImpl(
    private val config: AppConfig,
) : MailSender {
    override fun sendEmail(address: String, subject: String, body: String) {
        SimpleEmail().apply {
            this.hostName = config.mail.host
            this.setSmtpPort(465)
            this.authenticator = DefaultAuthenticator(config.mail.username, config.mail.password)
            this.isSSLOnConnect = true
            this.setFrom(config.mail.senderEmail)
            this.subject = subject
            this.setMsg(body)
            this.addTo(address)
        }.send()
    }
}
