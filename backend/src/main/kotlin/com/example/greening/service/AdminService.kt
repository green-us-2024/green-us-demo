package com.example.greening.service

import com.example.greening.domain.item.Admin
import com.example.greening.repository.AdminRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class AdminService(private val adminRepository: AdminRepository) {

    @Transactional
    fun saveAdmin(admin: Admin) {
        adminRepository.save(admin)
    }

    @Transactional
    fun updateAdmin(adminSeq: Int, id: String, pw: String) {
        val admin = adminRepository.findOne(adminSeq)
        if (admin != null) {
            admin.adminId = id
        }
        if (admin != null) {
            admin.adminPwd = pw
        }
    }

    fun findAdmins(): List<Admin> {
        return adminRepository.findAll()
    }

    fun findOne(adminSeq: Int): Admin? {
        return adminRepository.findOne(adminSeq)
    }
}