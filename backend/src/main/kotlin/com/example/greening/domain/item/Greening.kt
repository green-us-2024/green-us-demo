package com.example.greening.domain.item

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name="greening")
data class Greening (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name="g_seq")
        val gSeq:Int=0,

        @ManyToOne
        @JoinColumn(name = "user_seq", referencedColumnName = "user_seq")
        val user: User? = null,

        @Column(name="g_name")
        val gName:String?=null,

        @Column(name="g_start_date")
        val gStartDate:LocalDateTime?=null,

        @Column(name="g_end_date")
        val gEndDate:LocalDateTime?=null,

        @Column(name="g_certi_way")
        val gCertiWay:String?=null,

        @Column(name="g_info")
        val gInfo:String?=null,

        @Column(name="g_member_num")
        val gMemberNum:Int?=null,

        @Column(name="g_freq")
        val gFreq:String?=null,

        @Column(name = "g_deposit")
        val gDeposit: Int? = null,

        @Column(name = "g_total_count")
        val gTotalCount: Int? = null,

        @Column(name = "g_number")
        val gNumber: Int? = null,

        @OneToMany(mappedBy = "greening", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
        val reviews: List<Review> = emptyList()

)