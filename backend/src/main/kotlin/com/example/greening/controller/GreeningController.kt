package com.example.greening.controller

import com.example.greening.domain.item.Greening
import com.example.greening.service.GreeningService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/greening")
class GreeningController(private val greeningService: GreeningService) {

    @GetMapping("/byId/{id}")
    fun getGreeningById(@PathVariable id: Int): ResponseEntity<Greening> {
        val greening = greeningService.findOne(id)
        return if (greening != null) {
            ResponseEntity.ok(greening)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping("/new")
    fun createGreening(@RequestBody greening : Greening): ResponseEntity<Greening> {
        greeningService.saveGreening(greening)
        return ResponseEntity.status(HttpStatus.CREATED).body(greening)
    }

    @DeleteMapping("/delete/{gSeq}")
    fun deleteGreening(@PathVariable gSeq: Int) {
        greeningService.deleteGreening(gSeq)
    }

    @PutMapping("/update/{gSeq}")
    fun updateCertify(@PathVariable gSeq: Int, @RequestBody newGreening: Greening): ResponseEntity<Greening> {
        return try {
            greeningService.updateGreening(gSeq, newGreening)
            ResponseEntity.ok(newGreening)
        } catch (e: IllegalStateException) {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/list")
    fun getAllGreening(): ResponseEntity<List<Greening>> {
        val greening = greeningService.findGreening()
        return ResponseEntity.ok(greening)
    }

    @GetMapping("/byGKind/{gKind}")
    fun getGreeningBygKind(@PathVariable gKind: Int): ResponseEntity<List<Greening>> {
        val greening = greeningService.findBygKind(gKind)
        return ResponseEntity.ok(greening)
    }

    @GetMapping("/list/do")
    fun getDoGreeningBygKind(): ResponseEntity<List<Greening>> {
        val greening = greeningService.findDoGreenBygKind()
        return ResponseEntity.ok(greening)
    }

    @GetMapping("/list/buy")
    fun getBuyGreeningBygKind(): ResponseEntity<List<Greening>> {
        val greening = greeningService.findBuyGreenBygKind()
        return ResponseEntity.ok(greening)
    }

    @GetMapping("/list/new")
    fun getNewGreening(): ResponseEntity<List<Greening>> {
        val greening = greeningService.findNewGreen()
        return ResponseEntity.ok(greening)
    }

    @GetMapping("/list/pop")
    fun getPopGreening(): ResponseEntity<List<Greening>> {
        val greening = greeningService.findPopGreen()
        return ResponseEntity.ok(greening)
    }

    @GetMapping("/byUserSeq/{userSeq}")
    fun getGreeningByUserSeq(@PathVariable userSeq: Int): ResponseEntity<List<Greening>> {
        val greening = greeningService.findByUserId(userSeq)
        return ResponseEntity.ok(greening)
    }


}