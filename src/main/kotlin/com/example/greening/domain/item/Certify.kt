package com.example.greening.domain.item

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name="certify")
open class Certify (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name="certify_seq")
        var certifySeq:Int=0,

        @Column(name="certify_img")
        var certifyImg:String?= null,

        @Column(name="certify_date")
        var certifyDate:LocalDateTime? = null,

        @Column(name="user_seq")
        var userSeq:Int? = null,

        @Column(name="g_seq")
        var gSeq: Int? = null,

        @JoinColumn(name="p_seq", referencedColumnName = "p_seq")
        var pSeq:Int? = null,

        @OneToOne(mappedBy = "certify")
        var report: Report? = null

)