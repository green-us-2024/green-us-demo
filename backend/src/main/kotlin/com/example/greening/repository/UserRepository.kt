package com.example.greening.repository

import com.example.greening.domain.item.User
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.transaction.Transactional
import org.springframework.stereotype.Repository

@Repository
class UserRepository {

    @PersistenceContext
    private lateinit var em: EntityManager

    @Transactional
    fun save(user: User): User {
        return em.merge(user)
    }

    fun findOne(id : Int) : User?{
        return em.find(User::class.java, id)
    }

    fun findAll() : List<User>{
        return em.createQuery("select u from User u", User::class.java).resultList
    }

    fun findByEmail(userEmail: String): List<User> {
        return em.createQuery("select u from User u where u.userEmail = :userEmail", User::class.java)
                .setParameter("userEmail", userEmail)
                .resultList
    }

}