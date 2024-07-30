package com.example.greening.service

import com.example.greening.domain.item.Admin
import com.example.greening.domain.item.Certify
import com.example.greening.domain.item.User
import com.example.greening.repository.CertifyRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class CertifyService(private val certifyRepository: CertifyRepository) {

    @Transactional
    fun saveCertify(certify: Certify) {
        certifyRepository.save(certify)
    }

    @Transactional
    fun updateCertify(certifySeq: Int, newCertify: Certify) {
        val existingCertify = certifyRepository.findOne(certifySeq)
        if (existingCertify != null) {
            existingCertify.certifyImg = newCertify.certifyImg ?: existingCertify.certifyImg
            existingCertify.certifyDate = newCertify.certifyDate ?: existingCertify.certifyDate
            existingCertify.userSeq = existingCertify.userSeq  // 유지해야 할 기존 정보
            existingCertify.gSeq = existingCertify.gSeq
            existingCertify.pSeq = existingCertify.pSeq

            certifyRepository.save(existingCertify)
        } else {
            throw IllegalStateException("인증이 존재하지 않습니다.")
        }
    }


    @Transactional
    fun deleteCertify(certifySeq: Int) {
        certifyRepository.deleteById(certifySeq)
    }

    fun findCertify(): List<Certify> {
        return certifyRepository.findAll()
    }

    fun findOne(certifySeq: Int): Certify? {
        return certifyRepository.findOne(certifySeq)
    }

    fun findBypSeq(pSeq: Int): List<Certify> {
        return certifyRepository.findBypSeq(pSeq)
    }

    fun findById(certifySeq: Int): List<Certify> {
        return certifyRepository.findById(certifySeq)
    }

    fun findByUserId(userSeq: Int): List<Certify> {
        return certifyRepository.findByUserId(userSeq)
    }

    fun findByGreeningUser(userSeq: Int, gSeq: Int): List<Certify> {
        return certifyRepository.findByGreeningUser(userSeq, gSeq)
    }
}