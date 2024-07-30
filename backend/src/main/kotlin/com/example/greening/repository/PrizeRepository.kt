package com.example.greening.repository

import com.example.greening.domain.item.Payment
import com.example.greening.domain.item.Prize
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.stereotype.Repository

@Repository
class PrizeRepository {

    @PersistenceContext
    private lateinit var em: EntityManager

    fun save(prize: Prize) {
        if(prize.prizeSeq == 0) {
            em.persist(prize)
        }else{
            em.merge(prize)
        }
    }

    fun delete(prize: Prize) {
        if (em.contains(prize)) {
            em.remove(prize)
        } else {
            em.remove(em.merge(prize))
        }
    }

    fun deleteById(id: Int) {
        val prize = findOne(id)
        if (prize != null) {
            delete(prize)
        }
    }

    fun findOne(id : Int) : Prize?{
        return try{
            em.find(Prize::class.java, id)
        }catch(e: Exception){
            null
        }
    }

    fun findAll() : List<Prize>{
        return try{
            em.createQuery("select prize from Prize prize", Prize::class.java).resultList
        }catch(e: Exception){
            emptyList()
        }
    }

    fun findById(prizeSeq: Int): Prize? {
        return try{
            em.createQuery("select prize from Prize prize where prize.prizeSeq = :prizeSeq", Prize::class.java)
                    .setParameter("prizeSeq", prizeSeq)
                    .singleResult
        }catch(e: IllegalStateException){
            null
        }
    }

    fun findByUserSeq(userSeq: Int): List<Prize> {
        return try{
            em.createQuery("select p from Prize p where p.participate.user.userSeq = :userSeq", Prize::class.java)
                    .setParameter("userSeq", userSeq)
                    .resultList
        }catch(e: Exception){
            emptyList()
        }
    }
}