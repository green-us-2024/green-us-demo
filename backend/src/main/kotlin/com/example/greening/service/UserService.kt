package com.example.greening.service

import com.example.greening.domain.item.User
import com.example.greening.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserService(private val userRepository: UserRepository) {

    /**
     * 회원 가입
     */
    @Transactional
    fun join(user: User): Int {
        validateDuplicateUser(user) //중복 회원 검사
        userRepository.save(user)
        return user.userSeq
    }

    private fun validateDuplicateUser(user: User) {
        if(user.userEmail!=null) {
            val findUsers = userRepository.findByEmail(user.userEmail)
            if (findUsers.isNotEmpty()) {
                throw IllegalStateException("이미 존재하는 회원입니다.")
            }
        }else{
            throw IllegalStateException("잘못된 접근입니다.")
        }
    }

    // 회원 전체 조회
    fun findUsers(): List<User> {
        return userRepository.findAll()
    }

    fun findOne(userSeq: Int): User? {
        return userRepository.findOne(userSeq)
    }


}