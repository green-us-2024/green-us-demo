package com.example.greening.controller

import com.example.greening.domain.item.Report
import com.example.greening.service.ReportService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/report")
class ReportController(private val reportService: ReportService) {

    @GetMapping("/byId/{id}")
    fun getReportById(@PathVariable id: Int): ResponseEntity<Report> {
        val report = reportService.findOne(id)
        return if (report != null) {
            ResponseEntity.ok(report)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping("/new")
    fun createReport(@RequestBody report: Report): ResponseEntity<Report> {
        reportService.saveReport(report)
        return ResponseEntity.status(HttpStatus.CREATED).body(report)
    }

    @PutMapping("/update/{reportSeq}")
    fun updateReport(@PathVariable reportSeq: Int, @RequestBody newReport: Report): ResponseEntity<Report> {
        return try {
            reportService.updateReport(reportSeq, newReport)
            ResponseEntity.ok(newReport)
        } catch (e: IllegalStateException) {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/delete/{reportSeq}")
    fun deleteReport(@PathVariable reportSeq: Int) {
        reportService.deleteReport(reportSeq)
    }

    @GetMapping("/list")
    fun getAllReport(): ResponseEntity<List<Report>> {
        val report = reportService.findReport()
        return ResponseEntity.ok(report)
    }

    @GetMapping("/byCertifySeq/{certifySeq}")
    fun getReportByCertifySeq(@PathVariable certifySeq: Int): ResponseEntity<List<Report>> {
        val report = reportService.findByCertifySeq(certifySeq)
        return ResponseEntity.ok(report)
    }
}