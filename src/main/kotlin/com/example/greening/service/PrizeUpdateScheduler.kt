package com.example.greening.service

import com.example.greening.repository.GreeningRepository
import com.example.greening.repository.ParticipateRepository
import com.example.greening.repository.PrizeRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class PrizeUpdateScheduler(
        private val greeningService: GreeningService,
        private val participateService: ParticipateService,
        private val prizeService: PrizeService
) {

    // 매일 00:00에 실행
    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    fun updatePrizesAtMidnight() {
        val today = LocalDate.now()

        // gEndDate가 오늘인 Greening을 찾음
        val greenings = greeningService.findByGEndDate(today)
        greenings.forEach { greening ->
            // Greening과 연관된 모든 Participate 객체들을 찾음
            val participates = participateService.findBygSeq(greening.gSeq)
            val completedParticipates = participates.filter { it.pComplete == "Y" }
            val notCompletedParticipates = participates.filter { it.pComplete == "N" }

            if (completedParticipates.isNotEmpty()) {
                // 보상금 계산: (pComplete가 "N"인 참가자의 수 * Greening.gDeposit) / pComplete가 "Y"인 참가자의 수
                val depositAmount = (notCompletedParticipates.size * (greening.gDeposit ?: 0)) / completedParticipates.size

                // 각 pComplete가 "Y"인 참가자의 상금을 업데이트
                completedParticipates.forEach { participate ->
                    val prize = prizeService.findByParticipate_PSeq(participate.pSeq)
                    prize?.let {
                        it.prizeMoney = (it.prizeMoney ?: 0) + depositAmount
                        prizeService.updatePrize(it.prizeSeq, it)
                    }
                }
            }
        }
    }
}