package com.example.greening.repository

import com.example.greening.domain.item.Certify
import com.example.greening.domain.item.Greening
import jakarta.persistence.EntityManager
import jakarta.persistence.NoResultException
import jakarta.persistence.PersistenceContext
import org.springframework.stereotype.Repository

@Repository
class GreeningRepository {

    @PersistenceContext
    private lateinit var em: EntityManager

    fun save(greening: Greening) {
        if(greening.gSeq == 0) {
            em.persist(greening)
        }else{
            em.merge(greening)
        }
    }

    fun delete(greening: Greening) {
        if (em.contains(greening)) {
            em.remove(greening)
        } else {
            em.remove(em.merge(greening))
        }
    }

    fun deleteById(id: Int) {
        val greening = findOne(id)
        if (greening != null) {
            delete(greening)
        }
    }

    fun findOne(id : Int) : Greening?{
        return try{
            em.find(Greening::class.java, id)
        }catch(e: Exception){
            null
        }
    }

    fun findAll() : List<Greening>{
        return try {
            em.createQuery("select g from Greening g", Greening::class.java).resultList
        }catch(e: Exception){
            emptyList()
        }
    }

    fun findById(gSeq: Int): Greening? {
        return try {
            em.createQuery("select g from Greening g where g.gSeq = :gSeq", Greening::class.java)
                    .setParameter("gSeq", gSeq)
                    .singleResult
        } catch (e: NoResultException) {
            null
        }
    }

    fun findByUserId(userSeq: Int): List<Greening> {
        return try{
            em.createQuery("select g from Greening g where g.user.userSeq = :userSeq", Greening::class.java)
                    .setParameter("userSeq", userSeq)
                    .resultList
        }catch(e: Exception){
            emptyList()
        }
    }

}