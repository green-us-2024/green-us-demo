package com.example.greening.controller

import com.example.greening.domain.item.User
import com.example.greening.repository.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(private val userRepository: UserRepository) {

    @GetMapping("/byId/{id}")
    fun getUserById(@PathVariable id: Int): ResponseEntity<User> {
        val user = userRepository.findOne(id)
        return if (user != null) {
            ResponseEntity.ok(user)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/byEmail/{email}")
    fun getUserByEmail(@PathVariable email: String): ResponseEntity<List<User>> {
        val users = userRepository.findByEmail(email)
        return if (users != null) {
            ResponseEntity.ok(users)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping("/new")
    fun createUser(@RequestBody user: User): ResponseEntity<User> {
       userRepository.save(user)
        return ResponseEntity.status(HttpStatus.CREATED).body(user)
    }

    @GetMapping("/list")
    fun getAllUsers(): ResponseEntity<List<User>> {
        val users = userRepository.findAll()
        return ResponseEntity.ok(users)
    }
}