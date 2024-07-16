package com.example.greening.domain.item

import jakarta.persistence.*

@Entity
@Table(name = "participate")
data class Participate(
        @Id
        @Column(name = "p_seq")
        val pSeq: Int = 0,

        @ManyToOne
        @JoinColumn(name = "user_seq", referencedColumnName = "user_seq")
        val user: User? = null,

        @ManyToOne
        @JoinColumn(name = "g_seq", referencedColumnName = "g_seq")
        val greening: Greening? = null,

        @Column(name = "p_complete")
        val pComplete: String? = null,

        @Column(name = "p_count")
        val pCount: Int? = null
)