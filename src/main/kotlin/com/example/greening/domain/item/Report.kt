package com.example.greening.domain.item

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "report")
open class Report(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "report_seq")
        var reportSeq: Int = 0,

        @OneToOne
        @JoinColumn(name = "certify_seq", referencedColumnName = "certify_seq")
        var certify: Certify? = null,

        @Column(name = "report_date")
        var reportDate: LocalDateTime? = null,

        @Column(name = "report_result")
        var reportResult: String? = null
)