package com.example.greening.domain.item

import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "notice")
data class Notice(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name="notice_seq")
        val noticeSeq:Int = 0,

        @JoinColumn(name = "admin_seq", referencedColumnName = "admin_seq", nullable = false)
        val adminSeq:Int? = null,

        @Column(name="notice_title")
        val noticeTitle:String?= null,

        @Column(name="notice_content")
        val noticeContent:String? = null,

        @Column(name="notice_date")
        val noticeDate: LocalDate? = null
)