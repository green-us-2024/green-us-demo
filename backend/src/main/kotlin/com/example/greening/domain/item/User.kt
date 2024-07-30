package com.example.greening.domain.item

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*

@Entity
@Table(name="user")
open class User (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_seq")
    var userSeq : Int=0,

    @Column(name="user_name")
    var userName : String?=null,

    @Column(name="user_pwd")
    var userPwd : String?=null,

    @Column(name="user_email")
    var userEmail : String?=null,

    @Column(name="user_addr")
    var userAddr : String?=null,

    @Column(name="user_phone")
    var userPhone : String?=null,

    @Column(name="user_photo")
    var userPhoto : String?=null,

//    @Column(name="user_account")
//    var userAccount : String?=null,

    @Column(name="user_w_count")
    var userWCount : Int?=null,

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_seq", referencedColumnName = "admin_seq", nullable = true)
    var admins: Admin? = null,

    @JsonManagedReference
    @OneToMany(mappedBy = "user", cascade = [CascadeType.REMOVE], orphanRemoval = true, fetch = FetchType.LAZY)
    val reviews: List<Review> = mutableListOf(),

    @JsonManagedReference
    @OneToMany(mappedBy = "user", cascade = [CascadeType.REMOVE], orphanRemoval = true, fetch = FetchType.LAZY)
    val payments: List<Payment> = mutableListOf(),

    @JsonManagedReference
    @OneToMany(mappedBy = "user", cascade = [CascadeType.DETACH, CascadeType.MERGE], orphanRemoval = true, fetch = FetchType.LAZY)
    val greenings: List<Greening> = mutableListOf()
)