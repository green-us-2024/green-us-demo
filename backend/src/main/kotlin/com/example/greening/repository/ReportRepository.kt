package com.example.greening.repository

import com.example.greening.domain.item.Report
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.stereotype.Repository

@Repository
class ReportRepository {

    @PersistenceContext
    private lateinit var em: EntityManager

    fun save(report: Report) {
        if(report.reportSeq == 0) {
            em.persist(report)
        }else{
            em.merge(report)
        }
    }

    fun delete(report: Report) {
        if (em.contains(report)) {
            em.remove(report)
        } else {
            em.remove(em.merge(report))
        }
    }

    fun deleteById(id: Int) {
        val report = findOne(id)
        if (report != null) {
            delete(report)
        }
    }

    fun findOne(id : Int) : Report?{
        return em.find(Report::class.java, id)
    }

    fun findAll() : List<Report>{
        return em.createQuery("select re from Report re", Report::class.java).resultList
    }

    fun findById(reportSeq: Int): Report? {
        return try{
            em.createQuery("select re from Report re where re.reportSeq = :reportSeq", Report::class.java)
                    .setParameter("reportSeq", reportSeq)
                    .singleResult
        }catch(e: IllegalStateException){
            null
        }
    }

    fun findByCertifySeq(certifySeq: Int): List<Report> {
        return em.createQuery("select re from Report re where re.certify.certifySeq = :certifySeq", Report::class.java)
                    .setParameter("certifySeq", certifySeq)
                    .resultList
    }
}