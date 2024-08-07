package com.example.greening.domain.item

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "withdraw")
open class Withdraw(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "withdraw_seq")
        var withdrawSeq: Int = 0,

        //@JsonBackReference
        @ManyToOne(cascade = [CascadeType.ALL])
        @JoinColumn(name = "user_seq", referencedColumnName = "user_seq")
        var user: User? = null,

        @Column(name = "withdraw_content", length = 200)
        var withdrawContent: String? = null,

        @Column(name = "withdraw_date")
        var withdrawDate: LocalDate? = null
)