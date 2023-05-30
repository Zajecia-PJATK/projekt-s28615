package com.business.market.simulator.finance.transaction;


import com.business.market.simulator.finance.instrument.ActiveInstrument;
import com.business.market.simulator.user.User;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Set;

@Entity
public class MarketTransaction {
    @Id
    private Long transactionId;
    @ManyToOne
    private ActiveInstrument targetInstrument;
    private Timestamp timestamp;
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "transaction_participants",
            joinColumns = @JoinColumn(name = "transactionId"),
            inverseJoinColumns = @JoinColumn(name = "userId")
    )
    private Set<User> transactionParticipants;
}