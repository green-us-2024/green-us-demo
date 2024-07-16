package com.example.greening.domain.item

import jakarta.persistence.*

@Entity
@Table(name="user")
data class User (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_seq")
    val userSeq : Int=0,

    @Column(name="user_name")
    val userName : String?=null,

    @Column(name="user_pwd")
    val userPwd : String?=null,

    @Column(name="user_email")
    val userEmail : String?=null,

    @Column(name="user_addr")
    val userAddr : String?=null,

    @Column(name="user_phone")
    val userPhone : String?=null,

    @Column(name="user_account")
    val userAccount : String?=null,
//
//    @ManyToOne
//    @JoinColumn(name = "admin_seq", referencedColumnName = "admin_seq", nullable = true)
//    val admins: Admin? = null,
//
//    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
//    val reviews: List<Review> = emptyList(),
//
//    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
//    val payments: List<Payment> = emptyList()
)