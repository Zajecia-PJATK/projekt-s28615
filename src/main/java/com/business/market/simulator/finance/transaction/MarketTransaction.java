package com.business.market.simulator.finance.transaction;


import com.business.market.simulator.finance.instrument.active.ActiveInstrument;
import com.business.market.simulator.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity(name = "market_transactions")
public class MarketTransaction {
    @Id
    @GeneratedValue
    private Long transactionId;
    @ManyToOne
    private ActiveInstrument targetInstrument;
    private Timestamp transactionTimestamp;
    private BigDecimal transactionValue;
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "transaction_participants",
            joinColumns = @JoinColumn(name = "transactionId"),
            inverseJoinColumns = @JoinColumn(name = "userId")
    )
    private Set<User> transactionParticipants = new HashSet<>(2, 1);
    private String buyerId;
    private String sellerId;
}
