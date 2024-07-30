package com.example.greening.domain.item

import jakarta.persistence.*

@Entity
@Table(name = "participate")
open class Participate(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "p_seq")
        var pSeq: Int = 0,

        @ManyToOne(cascade = [CascadeType.ALL])
        @JoinColumn(name = "user_seq", referencedColumnName = "user_seq")
        var user: User? = null,

        @ManyToOne(cascade = [CascadeType.ALL])
        @JoinColumn(name = "g_seq", referencedColumnName = "g_seq")
        var greening: Greening? = null,

        @Column(name = "p_complete")
        var pComplete: String? = null,

        @Column(name = "p_count")
        var pCount: Int? = null
)