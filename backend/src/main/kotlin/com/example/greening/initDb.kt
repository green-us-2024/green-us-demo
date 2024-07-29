package com.example.greening

import com.example.greening.domain.item.Admin
import com.example.greening.domain.item.Greening
import com.example.greening.domain.item.Participate
import com.example.greening.domain.item.User
import jakarta.annotation.PostConstruct
import jakarta.persistence.EntityManager
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
/*@Component

class initDb(private val initService: InitService) {
    //초반에 테스트용으로 만들어놓은거라서 삭제하셔도 됩니다~!
    @PostConstruct
    fun init() {
        initService.dbInit1()
        initService.dbInit2()
    }
    @Component
    @Transactional
    class InitService(private val em: EntityManager) {

        fun dbInit1() {
            println("Init1" + this)

            val admin1: Admin = createAdmin("admin1","pwd1")
            em.persist(admin1)

            val user1: User = createUser("유저1","password1", "aaa@naver.com", "2", "01011113333","123456", admin1)
            em.persist(user1)

        }

        fun dbInit2() {

            val admin2: Admin = createAdmin("admin2","pwd2")
            em.persist(admin2)


            val user2: User = createUser("유저2", "password2", "bbb@naver.com", "1", "01055553333","12345555", admin2)
            em.persist(user2)

        }

        private fun createUser( name: String, pwd: String, email: String, addr: String, phone: String, account: String, admin:Admin): User {
            val user = User(
                    userName = name,
                    userPwd = pwd,
                    userEmail = email,
                    userAddr = addr,
                    userPhone = phone,
                    userAccount = account,
                    admins = admin
            )
            return user
        }

        private fun createAdmin(id: String, pwd: String) : Admin {
            val admin = Admin(
                    adminId = id,
                    adminPwd = pwd
            )
            return admin
        }

    }

}*/