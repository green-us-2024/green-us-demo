package com.example.greening.domain.item

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name="greening")
open class Greening (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name="g_seq")
        var gSeq:Int=0,

        @ManyToOne(cascade = [CascadeType.PERSIST, CascadeType.MERGE])
        @JoinColumn(name = "user_seq", referencedColumnName = "user_seq", nullable = true)
        var user: User? = null,

        @Column(name="g_name")
        var gName:String?=null,

        @Column(name="g_start_date")
        var gStartDate:LocalDateTime?=null,

        @Column(name="g_end_date")
        var gEndDate:LocalDateTime?=null,

        @Column(name="g_certi_way")
        var gCertiWay:String?=null,

        @Column(name="g_info")
        var gInfo:String?=null,

        @Column(name="g_member_num")
        var gMemberNum:Int?=null,

        @Column(name="g_freq")
        var gFreq:String?=null,

        @Column(name = "g_deposit")
        var gDeposit: Int? = null,

        @Column(name = "g_total_count")
        var gTotalCount: Int? = null,

        @Column(name = "g_number")
        var gNumber: Int? = null,

        @OneToMany(mappedBy = "greening", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
        val reviews: List<Review> = mutableListOf()

)