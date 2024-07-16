package com.example.greening.domain.item

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name="certify")
data class Certify (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name="certify_seq")
        val certifySeq:Int=0,

        @Column(name="certify_img")
        val certifyImg:String?= null,

        @Column(name="certify_date")
        val certifyDate:LocalDateTime? = null,

        @Column(name="user_seq")
        val userSeq:Int,

        @Column(name="g_seq")
        val gSeq: Int,

        @JoinColumn(name="p_seq", referencedColumnName = "p_seq")
        val pSeq:Int,

        @OneToOne(mappedBy = "certify")
        val report: Report

)