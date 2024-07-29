package com.example.greening.domain.item

import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.time.LocalDate

@Entity
@Table(name = "prize")
open class Prize(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "prize_seq")
        var prizeSeq: Int = 0,

        @ManyToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
        @JoinColumn(name = "user_seq", referencedColumnName = "user_seq")
        var user: User? = null,

        @ManyToOne(cascade = [CascadeType.PERSIST, CascadeType.MERGE], fetch = FetchType.LAZY)
        @JoinColumn(name = "g_seq", referencedColumnName = "g_seq")
        @OnDelete(action = OnDeleteAction.SET_NULL)
        var greening: Greening? = null,

        @ManyToOne(cascade = [CascadeType.PERSIST, CascadeType.MERGE], fetch = FetchType.LAZY)
        @JoinColumn(name = "p_seq", referencedColumnName = "p_seq")
        @OnDelete(action = OnDeleteAction.SET_NULL)
        var participate: Participate? = null,

        @Column(name = "prize_name")
        var prizeName: String? = null,

        @Column(name = "prize_money")
        var prizeMoney: Int? = null,

        @Column(name = "prize_date")
        var prizeDate: LocalDate? = null
)