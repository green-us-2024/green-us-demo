package com.example.greening.repository

import com.example.greening.domain.item.Review
import jakarta.persistence.EntityManager
import jakarta.persistence.NoResultException
import jakarta.persistence.PersistenceContext
import org.springframework.stereotype.Repository

@Repository
class ReviewRepository {

    @PersistenceContext
    private lateinit var em: EntityManager

    fun save(review: Review) {
        if(review.reviewSeq == 0) {
            em.persist(review)
        }else{
            em.merge(review)
        }
    }

    fun delete(review: Review) {
        if (em.contains(review)) {
            em.remove(review)
        } else {
            em.remove(em.merge(review))
        }
    }

    fun deleteById(id: Int) {
        val review = findOne(id)
        if (review != null) {
            delete(review)
        }
    }

    fun findOne(id : Int) : Review?{
        return em.find(Review::class.java, id)
    }

    fun findAll() : List<Review>{
        return try {
            em.createQuery("select r from Review r", Review::class.java).resultList
        }catch(e: Exception){
            emptyList()
        }
    }

    fun findById(reviewSeq: Int): Review? {
        return try{
            em.createQuery("select r from Review r where r.reviewSeq = :reviewSeq", Review::class.java)
                    .setParameter("reviewSeq", reviewSeq)
                    .singleResult
        }catch(e: NoResultException){
            null
        }
    }

    fun findByGreening(gSeq: Int): List<Review> {
        return try {
            em.createQuery("select r from Review r where r.greening.gSeq = :gSeq", Review::class.java)
                    .setParameter("gSeq", gSeq)
                    .resultList
        } catch(e: Exception){
            emptyList()
        }
    }
    fun findByUser(userSeq: Int): List<Review> {
        return try {
            em.createQuery("select r from Review r where r.user.userSeq = :userSeq", Review::class.java)
                    .setParameter("userSeq", userSeq)
                    .resultList
        }catch (e: Exception){
            emptyList()
        }
    }
}