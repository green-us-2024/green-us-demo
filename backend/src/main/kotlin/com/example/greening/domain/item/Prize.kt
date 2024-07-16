package com.example.greening.domain.item

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "prize")
data class Prize(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "prize_seq")
        val prizeSeq: Int = 0,

        @ManyToOne
        @JoinColumns(
                JoinColumn(name = "user_seq", referencedColumnName = "user_seq"),
                JoinColumn(name = "g_seq", referencedColumnName = "g_seq"),
                JoinColumn(name = "p_seq", referencedColumnName = "p_seq")
        )
        val participate: Participate,

        @Column(name = "prize_name")
        val prizeName: String? = null,

        @Column(name = "prize_money")
        val prizeMoney: Int? = null,

        @Column(name = "prize_date")
        val prizeDate: LocalDate? = null
)