package com.example.greening.service

import com.example.greening.domain.item.Withdraw
import com.example.greening.repository.WithdrawRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class WithdrawService(private val withdrawRepository: WithdrawRepository) {

    @Transactional
    fun saveWithdraw(withdraw: Withdraw) {
        withdrawRepository.save(withdraw)
    }

    @Transactional
    fun updateWithdraw(withdrawSeq: Int, newWithdraw: Withdraw) {
        val existingWithdraw = withdrawRepository.findOne(withdrawSeq)
        if (existingWithdraw != null) { // 필드를 직접 업데이트
            existingWithdraw.withdrawContent = newWithdraw.withdrawContent ?: existingWithdraw.withdrawContent
            existingWithdraw.withdrawDate = newWithdraw.withdrawDate ?: existingWithdraw.withdrawDate

            withdrawRepository.save(existingWithdraw)
        } else {
            throw IllegalStateException("출금이 존재하지 않습니다.")
        }
    }


    @Transactional
    fun deleteWithdraw(withdrawSeq: Int) {
        withdrawRepository.deleteById(withdrawSeq)
    }

    fun findWithdraw(): List<Withdraw> {
        return withdrawRepository.findAll()
    }

    fun findOne(withdrawSeq: Int): Withdraw? {
        return withdrawRepository.findOne(withdrawSeq)
    }

    fun findById(withdrawSeq: Int): Withdraw? {
        return withdrawRepository.findById(withdrawSeq)
    }

    fun findByUserSeq(userSeq: Int): List<Withdraw> {
        return withdrawRepository.findByUser(userSeq)
    }
}