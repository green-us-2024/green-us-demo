package com.example.greening.repository

import com.example.greening.domain.item.Certify
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.stereotype.Repository

@Repository
class CertifyRepository {

    @PersistenceContext
    private lateinit var em: EntityManager

    fun save(certify: Certify) {
        if(certify.certifySeq == 0) {
            em.persist(certify)
        }else{
            em.merge(certify)
        }
    }

    fun delete(certify: Certify) {
        if (em.contains(certify)) {
            em.remove(certify)
        } else {
            em.remove(em.merge(certify))
        }
    }

    fun deleteById(id: Int) {
        val certify = findOne(id)
        if (certify != null) {
            delete(certify)
        }
    }

    fun findOne(id : Int) : Certify?{
        return try{
            em.find(Certify::class.java, id)
        }catch(e: Exception){
            null
        }
    }

    fun findAll() : List<Certify>{
        return try {
            em.createQuery("select c from Certify c", Certify::class.java).resultList
        }catch(e: Exception){
            emptyList()
        }
    }

    fun findById(certifySeq: Int): List<Certify> {
        return  try {
            em.createQuery("select c from Certify c where c.certifySeq = :certifySeq", Certify::class.java)
                    .setParameter("certifySeq", certifySeq)
                    .resultList
        }catch(e: Exception){
            emptyList()
        }
    }

    fun findByUserId(userSeq: Int): List<Certify> {
        return try {
            em.createQuery("select c from Certify c where c.userSeq = :userSeq", Certify::class.java)
                    .setParameter("userSeq", userSeq)
                    .resultList
        }catch(e: Exception){
            emptyList()
        }
    }

    fun findBypSeq(pSeq: Int): List<Certify> {
        return try{
            em.createQuery("select c from Certify c where c.pSeq = :pSeq", Certify::class.java)
                    .setParameter("pSeq", pSeq)
                    .resultList
        }catch(e: Exception){
            emptyList()
        }
    }

    fun findByGreeningUser(userSeq: Int, gSeq: Int): List<Certify> {
        return try {
            em.createQuery("select c from Certify c where c.userSeq = :userSeq and c.gSeq = :gSeq", Certify::class.java)
                    .setParameter("userSeq", userSeq)
                    .setParameter("gSeq", gSeq)
                    .resultList
        }catch(e: Exception){
            emptyList()
        }
    }

}