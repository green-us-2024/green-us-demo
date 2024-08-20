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

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name="user_seq", referencedColumnName = "user_seq")
        var user: User? = null,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name="g_seq", referencedColumnName = "g_seq")
        var greening: Greening? = null,


        @JoinColumn(name="p_seq", referencedColumnName = "p_seq")
        var pSeq:Int? = null,

        @OneToOne(mappedBy = "certify")
        var report: Report? = null

)