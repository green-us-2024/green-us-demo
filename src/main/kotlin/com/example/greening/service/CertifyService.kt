package com.example.greening.service

import com.example.greening.domain.item.Admin
import com.example.greening.domain.item.Certify
import com.example.greening.domain.item.User
import com.example.greening.repository.CertifyRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional(readOnly = true)
class CertifyService(
        private val certifyRepository: CertifyRepository,
        private val userService: UserService,
        private val participateService: ParticipateService) {

    @Transactional
    fun saveCertify(userEmail:String, gSeq: Int, certifyDate: LocalDateTime): Certify? {
        val userSeq = userService.findUserSeqByEmail(userEmail)
        val participate = if (userSeq != null) participateService.findByUserSeqAndgSeq(userSeq, gSeq) else null
        val pSeq = if (participate != null) participate.pSeq else -1
        return if (participate != null && participate.pComplete == "N") {
            val certify = Certify(
                    certifySeq = 0,
                    certifyImg = null,
                    certifyDate = certifyDate,
                    userSeq = userSeq,
                    gSeq = gSeq,
                    pSeq = pSeq
            )
            participateService.updateParticipate(pSeq)
            certifyRepository.save(certify)
        }else{
            null
        }
    }

    @Transactional
    fun updateCertify(certifySeq: Int, newCertify: Certify) {
        val existingCertify = certifyRepository.findById(certifySeq).orElse(null)
                ?: throw java.lang.IllegalStateException("인증이 존재하지 않습니다.")
        existingCertify.certifyImg = newCertify.certifyImg ?: existingCertify.certifyImg
        existingCertify.certifyDate = newCertify.certifyDate ?: existingCertify.certifyDate
        certifyRepository.save(existingCertify)
    }


    @Transactional
    fun deleteCertify(certifySeq: Int) {
        certifyRepository.deleteById(certifySeq)
    }

    fun findCertify(): List<Certify> {
        return certifyRepository.findAll()
    }

    fun findOne(certifySeq: Int): Certify? {
        return certifyRepository.findById(certifySeq).orElse(null)
    }

    fun findBypSeq(pSeq: Int): List<Certify> {
        return certifyRepository.findBypSeq(pSeq)
    }

    fun findById(certifySeq: Int): Certify? {
        return certifyRepository.findById(certifySeq).orElse(null)
    }

    fun findByUserId(userSeq: Int): List<Certify> {
        return certifyRepository.findByUserSeq(userSeq)
    }

    fun findByUserSeqAndGSeq(userSeq: Int, gSeq: Int): List<Certify> {
        return certifyRepository.findByUserSeqAndGSeq(userSeq, gSeq)
    }

    fun findByUserSeqAndGSeqAndCertifyDate(userSeq: Int, gSeq: Int, CertifyDate: LocalDateTime): Certify? {
        return certifyRepository.findByUserSeqAndGSeqAndCertifyDate(userSeq, gSeq, CertifyDate)
    }
}