package com.example.greening.controller

import com.example.greening.domain.item.User
import com.example.greening.repository.UserRepository
import com.example.greening.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
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
    fun getUserByEmail(@PathVariable email: String): ResponseEntity<List<User>> {
        val users = userService.findByEmail(email)
        return if (users != null) {
            ResponseEntity.ok(users)
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

    @PostMapping("/delete/{userSeq}")
    fun deleteUser(@PathVariable userSeq: Int): String {
        userService.deleteUser(userSeq)
        return "redirect:/users/list" // 삭제 후 회원 목록 페이지로 리디렉션
    }

    @GetMapping("/list")
    fun listUsers(model: Model): String {
        val users = userService.findUsers()
        model.addAttribute("users", users)
        return "user" // user-list.html을 반환
    }


}