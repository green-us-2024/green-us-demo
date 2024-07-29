package com.example.greening.controller

import com.example.greening.domain.item.Participate
import com.example.greening.service.ParticipateService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/participate")
class ParticipateController(private val participateService: ParticipateService) {

    @GetMapping("/byId/{id}")
    fun getParticipateById(@PathVariable id: Int): ResponseEntity<Participate> {
        val participate = participateService.findOne(id)
        return if (participate != null) {
            ResponseEntity.ok(participate)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping("/new")
    fun createParticipate(@RequestBody participate: Participate): ResponseEntity<Participate> {
        participateService.saveParticipate(participate)
        return ResponseEntity.status(HttpStatus.CREATED).body(participate)
    }

    @DeleteMapping("/delete/{pSeq}")
    fun deleteParticipate(@PathVariable pSeq: Int) {
        participateService.deleteParticipate(pSeq)
    }

    @PutMapping("/update/{pSeq}")
    fun updateParticipate(@PathVariable pSeq: Int, @RequestBody newParticipate: Participate): ResponseEntity<Participate> {
        return try {
            participateService.updateParticipate(pSeq, newParticipate)
            ResponseEntity.ok(newParticipate)
        } catch (e: IllegalStateException) {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/list")
    fun getAllParticipate(): ResponseEntity<List<Participate>> {
        val participate = participateService.findParticipate()
        return ResponseEntity.ok(participate)
    }

    @GetMapping("/byUserSeq/{UserSeq}")
    fun getParticipateByUserSeq(@PathVariable userSeq: Int): ResponseEntity<List<Participate>> {
        val participate = participateService.findByUserSeq(userSeq)
        return ResponseEntity.ok(participate)
    }

    @GetMapping("/byGSeq/{gSeq}")
    fun getParticipateBygSeq(@PathVariable gSeq: Int): ResponseEntity<List<Participate>> {
        val participate = participateService.findBygSeq(gSeq)
        return ResponseEntity.ok(participate)
    }
}