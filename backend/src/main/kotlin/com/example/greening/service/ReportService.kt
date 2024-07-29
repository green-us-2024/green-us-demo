package com.example.greening.service

import com.example.greening.domain.item.Report
import com.example.greening.repository.ReportRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ReportService(private val reportRepository: ReportRepository) {

    @Transactional
    fun saveReport(report: Report) {
        reportRepository.save(report)
    }

    @Transactional
    fun updateReport(reportSeq: Int, newReport: Report) {
        val existingReport = reportRepository.findOne(reportSeq)
        if (existingReport != null) { // 필드를 직접 업데이트
            existingReport.reportDate = newReport.reportDate
            existingReport.reportResult = newReport.reportResult
            existingReport.reportSeq = newReport.reportSeq
            existingReport.certify = newReport.certify

            reportRepository.save(existingReport)
        } else {
            throw IllegalStateException("신고가 존재하지 않습니다.")
        }
    }


    @Transactional
    fun deleteReport(reportSeq: Int) {
        reportRepository.deleteById(reportSeq)
    }

    fun findReport(): List<Report> {
        return reportRepository.findAll()
    }

    fun findOne(reportSeq: Int): Report? {
        return reportRepository.findOne(reportSeq)
    }

    fun findById(reportSeq: Int): Report? {
        return reportRepository.findById(reportSeq)
    }

    fun findByCertifySeq(certifySeq: Int): List<Report> {
        return reportRepository.findByCertifySeq(certifySeq)
    }
}