package com.example.greening.controller


import com.example.greening.domain.item.Certify
import com.example.greening.service.CertifyService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("/certify")
class CertifyController(private val certifyService: CertifyService) {

    @GetMapping("/byId/{id}")
    fun getCertifyById(@PathVariable id: Int): ResponseEntity<Certify> {
        val certify = certifyService.findOne(id)
        return if (certify != null) {
            ResponseEntity.ok(certify)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping("/new")
    fun createCertify(@RequestParam userEmail:String,@RequestParam gSeq: Int,@RequestParam certifyDate: LocalDateTime): ResponseEntity<Certify> {
        val certify = certifyService.saveCertify(userEmail, gSeq, certifyDate)
        return ResponseEntity.status(HttpStatus.CREATED).body(certify)
    }

    @PutMapping("/update/{certifySeq}")
    fun updateCertify(@PathVariable certifySeq: Int, @RequestBody newCertify: Certify): ResponseEntity<Certify> {
        return try {
            certifyService.updateCertify(certifySeq, newCertify)
            ResponseEntity.ok(newCertify)
        } catch (e: IllegalStateException) {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/delete/{certifySeq}")
    fun deleteCertify(@PathVariable certifySeq: Int) {
        certifyService.deleteCertify(certifySeq)
    }

    @GetMapping("/list")
    fun getAllCertify(): ResponseEntity<List<Certify>> {
        val certifies = certifyService.findCertify()
        return ResponseEntity.ok(certifies)
    }

    @GetMapping("/bypSeq/{pSeq}")
    fun getCertifyBypSeq(@PathVariable pSeq: Int): ResponseEntity<List<Certify>> {
        val certifies = certifyService.findBypSeq(pSeq)
        return ResponseEntity.ok(certifies)
    }

    @GetMapping("/byUserSeq/{userSeq}")
    fun getCertifyByUserSeq(@PathVariable userSeq: Int): ResponseEntity<List<Certify>> {
        val certifies = certifyService.findByUserId(userSeq)
        return ResponseEntity.ok(certifies)
    }

    @GetMapping("/byGreeningUser/{userSeq}/{gSeq}")
    fun getCertifyByUserSeqAndGSeq(@PathVariable userSeq: Int, @PathVariable gSeq: Int): ResponseEntity<List<Certify>> {
        val certifies = certifyService.findByUserSeqAndGSeq(userSeq, gSeq)
        return ResponseEntity.ok(certifies)
    }

    @GetMapping("/byUserSeqAndGSeqAneCertifyDate/{userSeq}/{gSeq}/{CertifyDate}")
    fun findByUserSeqAndGSeqAndCertifyDate(userSeq: Int, gSeq: Int, CertifyDate: LocalDateTime): ResponseEntity<Certify> {
        val certify = certifyService.findByUserSeqAndGSeqAndCertifyDate(userSeq, gSeq, CertifyDate)
        return ResponseEntity.ok(certify)
    }
}