package com.example.greening.controller

import com.example.greening.domain.item.User
import com.example.greening.repository.UserRepository
import com.example.greening.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {

    @GetMapping("/byId/{id}")
    fun getUserById(@PathVariable id: Int): ResponseEntity<User> {
        val user = userService.findOne(id)
        return if (user != null) {
            ResponseEntity.ok(user)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/byEmail/{email}")
    fun getUserByEmail(@PathVariable email: String): ResponseEntity<User> {
        val user = userService.findByEmail(email)
        return if (user != null) {
            ResponseEntity.ok(user)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping("/new")
    fun createUser(@RequestBody user: User) : ResponseEntity<User>{
        userService.save(user)
        return ResponseEntity.status(HttpStatus.CREATED).body(user)
    }

    @PutMapping("/update/{userSeq}")
    fun updateUser(@PathVariable userSeq:Int, @RequestBody user: User){
        userService.updateUser(userSeq,user)
    }

    @DeleteMapping("/delete/{userSeq}")
    fun deleteUser(@PathVariable userSeq: Int) {
        userService.deleteUser(userSeq)
    }

    @GetMapping("/list")
    fun getAllUsers(): ResponseEntity<List<User>> {
        val users = userService.findUsers()
        return ResponseEntity.ok(users)
    }


}