package com.example.greening.domain.item

import jakarta.persistence.*

@Entity
@Table(name = "Ranking")
data class Ranking(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "rank_seq")
        val rankSeq: Int = 0,

        @OneToOne
        @JoinColumn(name = "user_seq", referencedColumnName = "user_seq")
        val user: User
)