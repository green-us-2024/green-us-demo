package com.example.greening.repository

import com.example.greening.domain.item.Certify
import com.example.greening.domain.item.Participate
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.servlet.http.Part
import org.springframework.stereotype.Repository

@Repository
class ParticipateRepository {

    @PersistenceContext
    private lateinit var em: EntityManager

    fun save(participate: Participate) {
        if(participate.pSeq == 0) {
            em.persist(participate)
        }else{
            em.merge(participate)
        }
    }

    fun delete(participate: Participate) {
        if (em.contains(participate)) {
            em.remove(participate)
        } else {
            em.remove(em.merge(participate))
        }
    }

    fun deleteById(id: Int) {
        val participate = findOne(id)
        if (participate != null) {
            delete(participate)
        }
    }

    fun findOne(id : Int) : Participate?{
        return try{
            em.find(Participate::class.java, id)
        }catch(e: Exception){
            null
        }
    }

    fun findAll() : List<Participate>{
        return try{
            em.createQuery("select p from Participate p", Participate::class.java).resultList
        }catch(e: Exception){
            emptyList()
        }
    }

    fun findById(pSeq: Int): Participate? {
        return try{
            em.createQuery("select p from Participate p where p.pSeq = :pSeq", Participate::class.java)
                    .setParameter("pSeq", pSeq)
                    .singleResult
        }catch(e: IllegalStateException){
            null
        }
    }

    fun findByUserSeq(userSeq: Int): List<Participate> {
        return try{
            em.createQuery("select p from Participate p where p.user.userSeq = :userSeq", Participate::class.java)
                    .setParameter("userSeq", userSeq)
                    .resultList
        }catch(e: Exception){
            emptyList()
        }
    }

    fun findBygSeq(gSeq: Int): List<Participate> {
        return try {
            em.createQuery("select p from Participate p where p.greening.gSeq = :gSeq", Participate::class.java)
                    .setParameter("gSeq", gSeq)
                    .resultList
        }catch(e: Exception){
            emptyList()
        }
    }
}