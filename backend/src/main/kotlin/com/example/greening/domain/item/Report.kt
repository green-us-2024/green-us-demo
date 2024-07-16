package com.example.greening.domain.item

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "report")
data class Report(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "report_seq")
        val reportSeq: Int = 0,

        @OneToOne
        @JoinColumn(name = "certify_seq", referencedColumnName = "certify_seq")
        val certify: Certify,

        @Column(name = "report_date")
        val reportDate: LocalDateTime? = null,

        @Column(name = "report_result")
        val reportResult: String? = null
)