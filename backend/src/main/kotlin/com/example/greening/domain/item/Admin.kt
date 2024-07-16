package com.example.greening.domain.item

import jakarta.persistence.*

@Entity
@Table(name = "admin")
data class Admin(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="admin_seq")
    val adminSeq:Int = 0,

    @Column(name="admin_id")
    var adminId:String="",

    @Column(name="admin_pwd")
    var adminPwd:String=""
)