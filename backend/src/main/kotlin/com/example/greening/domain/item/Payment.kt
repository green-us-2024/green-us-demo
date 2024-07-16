package com.example.greening.domain.item

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "payment")
data class Payment(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "payment_seq")
        val paymentSeq: Int = 0,

        @ManyToOne
        @JoinColumn(name = "user_seq", referencedColumnName = "user_seq")
        val user: User? = null,

        @Column(name = "payment_content")
        val paymentContent: String? = null,

        @Column(name = "payment_method")
        val paymentMethod: String? = null,

        @Column(name = "payment_date")
        val paymentDate: LocalDate? = null,

        @Column(name = "payment_money")
        val paymentMoney: Int? = null
)