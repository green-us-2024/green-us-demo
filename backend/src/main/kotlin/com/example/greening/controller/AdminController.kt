package com.example.greening.controller

import com.example.greening.domain.item.Admin
import com.example.greening.repository.AdminRepository
import com.example.greening.service.AdminService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/admins")
class AdminController(
    private val adminRepository: AdminRepository,
    private val adminService: AdminService
) {

    @GetMapping("/byId/{id}")
    fun getAdminById(@PathVariable id: Int): ResponseEntity<Admin> {
        val admin = adminRepository.findOne(id)
        return if (admin != null) {
            ResponseEntity.ok(admin)
        } else {
            ResponseEntity.notFound().build()
        }
    }

//    @GetMapping("/new")
//    fun createForm(model: Model): String {
//        model.addAttribute("adminForm", AdminForm())
//        return "admins/createUserForm"
//    }


    @PostMapping("/new")
    fun createAdmin(@RequestBody admin : Admin): ResponseEntity<Admin> {
        adminService.saveAdmin(admin)
        return ResponseEntity.status(HttpStatus.CREATED).body(admin)
    }

    @GetMapping("/list")
    fun getAllAdmins(): ResponseEntity<List<Admin>> {
        val admins = adminRepository.findAll()
        return ResponseEntity.ok(admins)
    }
}
