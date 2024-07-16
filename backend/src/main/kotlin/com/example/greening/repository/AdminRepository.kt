package com.example.greening.repository

import com.example.greening.domain.item.Admin
import com.example.greening.domain.item.User
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.transaction.Transactional
import org.springframework.stereotype.Repository

@Repository
class AdminRepository {

    @PersistenceContext
    private lateinit var em: EntityManager
    @Transactional
    fun save(admin: Admin) {
        em.persist(admin)
    }

    fun findOne(id : Int) : Admin?{
        return em.find(Admin::class.java, id)
    }

    fun findAll() : List<Admin>{
        return em.createQuery("select a from Admin a", Admin::class.java).resultList
    }

    fun findById(adminId: String): List<Admin> {
        return em.createQuery("select a from Admin a where a = :adminId", Admin::class.java)
                .setParameter("adminId", adminId)
                .resultList
    }
}