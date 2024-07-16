package com.example.greening.domain.item

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "review")
data class Review(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "review_seq")
        val reviewSeq: Int = 0,

        @ManyToOne
        @JoinColumn(name = "user_seq", referencedColumnName = "user_seq")
        val user: User,

        @ManyToOne
        @JoinColumn(name = "g_seq", referencedColumnName = "g_seq")
        val greening: Greening,

        @Column(name = "review_content")
        val reviewContent: String? = null,

        @Column(name = "review_date")
        val reviewDate: LocalDate? = null,

        @Column(name = "review_rate")
        val reviewRate: Float? = null
)