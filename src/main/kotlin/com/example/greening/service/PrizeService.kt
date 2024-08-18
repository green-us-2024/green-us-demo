package com.example.greening.service

import com.example.greening.domain.item.Prize
import com.example.greening.repository.GreeningRepository
import com.example.greening.repository.ParticipateRepository
import com.example.greening.repository.PrizeRepository
import com.example.greening.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class PrizeService(private val prizeRepository: PrizeRepository,
                   private val userRepository: UserRepository,
                   private val greeningRepository: GreeningRepository,
                   private val participateRepository: ParticipateRepository){

    @Transactional
    fun savePrize(prize: Prize) {
        val user = prize.user?.let { userRepository.findById(it.userSeq)}
        val greening = prize.greening?.let { greeningRepository.findById(it.gSeq)}
        val participate = prize.participate?.let { participateRepository.findById(it.pSeq).orElse(null)}
        if (user != null) {
            prize.user = user
        }
        if (greening != null) {
            prize.greening = greening
        }
        if (participate != null) {
            prize.participate = participate
        }
        prizeRepository.save(prize)
    }

    @Transactional
    fun updatePrize(prizeSeq: Int, newPrize: Prize) {
        val existingPrize = prizeRepository.findById(prizeSeq)
        if (existingPrize != null) { // 필드를 직접 업데이트
            existingPrize.prizeName = newPrize.prizeName ?: existingPrize.prizeName
            existingPrize.prizeMoney = newPrize.prizeMoney ?: existingPrize.prizeMoney
            existingPrize.prizeDate = newPrize.prizeDate ?: existingPrize.prizeDate

            prizeRepository.save(existingPrize)
        } else {
            throw IllegalStateException("상금이 존재하지 않습니다.")
        }
    }


    @Transactional
    fun deletePrize(prizeSeq: Int) {
        prizeRepository.deleteById(prizeSeq)
    }

    fun findPrize(): List<Prize> {
        return prizeRepository.findAll()
    }

    fun findOne(prizeSeq: Int): Prize? {
        return prizeRepository.findById(prizeSeq)
    }

    fun findById(prizeSeq: Int): Prize? {
        return prizeRepository.findById(prizeSeq)
    }

    fun findByUserSeq(userSeq: Int): List<Prize> {
        return prizeRepository.findByUserSeq(userSeq)
    }
}