package com.example.greening.service

import com.example.greening.domain.item.Participate
import com.example.greening.repository.GreeningRepository
import com.example.greening.repository.ParticipateRepository
import com.example.greening.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ParticipateService(private val participateRepository: ParticipateRepository, private val userRepository: UserRepository, private val greeningRepository: GreeningRepository) {

    @Transactional
    fun saveParticipate(participate: Participate) {
        val user = participate.user?.let { userRepository.findById(it.userSeq)}
        val greening = participate.greening?.let { greeningRepository.findById(it.gSeq)}
        if (user != null) {
            participate.user = user
        }
        if (greening != null) {
            participate.greening = greening
        }
        participateRepository.save(participate)
    }

    @Transactional
    fun updateParticipate(pSeq: Int, newParticipate: Participate) {
        val existingParticipate = participateRepository.findOne(pSeq)
        if (existingParticipate != null) { // 필드를 직접 업데이트
            existingParticipate.pComplete = newParticipate.pComplete ?: existingParticipate.pComplete
            existingParticipate.pCount = newParticipate.pCount ?: existingParticipate.pCount

            participateRepository.save(existingParticipate)
        } else {
            throw IllegalStateException("참여가 존재하지 않습니다.")
        }
    }


    @Transactional
    fun deleteParticipate(pSeq: Int) {
        participateRepository.deleteById(pSeq)
    }

    fun findParticipate(): List<Participate> {
        return participateRepository.findAll()
    }

    fun findOne(pSeq: Int): Participate? {
        return participateRepository.findOne(pSeq)
    }

    fun findById(pSeq: Int): Participate? {
        return participateRepository.findById(pSeq)
    }

    fun findByUserSeq(userSeq: Int): List<Participate> {
        return participateRepository.findByUserSeq(userSeq)
    }

    fun findBygSeq(gSeq: Int): List<Participate> {
        return participateRepository.findBygSeq(gSeq)
    }

}