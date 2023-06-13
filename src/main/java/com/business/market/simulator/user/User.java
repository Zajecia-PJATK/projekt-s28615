package com.business.market.simulator.user;

import com.business.market.simulator.finance.instrument.active.ActiveInstrument;
import com.business.market.simulator.finance.transaction.MarketTransaction;
import com.business.market.simulator.finance.transaction.entity.LegalEntity;
import com.business.market.simulator.utils.BigDecimalToStringConverter;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity(name = "users")
public class User implements LegalEntity {
    @Id
    @GeneratedValue
    private long userId;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String passwordHash;
    @Convert(converter = BigDecimalToStringConverter.class)
    private BigDecimal balance = new BigDecimal(0);
    private boolean isDeleted = false;
    @OneToMany
    private List<ActiveInstrument> ownedInstruments = new ArrayList<>(0);
    @ManyToMany(mappedBy = "transactionParticipants")
    private List<MarketTransaction> userTransactions = new ArrayList<>(0);

    @Override
    public Long getEntityId() {
        return userId;
    }

    public class UserOperations {
        public void addToBalance(double value) throws IllegalArgumentException {
            if (value < 0) {
                throw new IllegalArgumentException("Value added to balance can't be a negative number");
            }
            balance = balance.add(BigDecimal.valueOf(value));
        }

        public void addToBalance(BigDecimal value) throws IllegalArgumentException {
            if (value.doubleValue() < 0) {
                throw new IllegalArgumentException("Value added to balance can't be a negative number");
            }
            balance = balance.add(value);
        }

        public BigDecimal withdrawBalance(double value) throws IllegalArgumentException {
            if (value < 0) {
                throw new IllegalArgumentException("Value to withdraw from balance can't be a negative number");
            }
            if (value > balance.doubleValue()) {
                throw new IllegalArgumentException("Value to withdraw can't exceed balance");
            }
            BigDecimal subtrahend = BigDecimal.valueOf(value);
            balance = balance.subtract(subtrahend);
            return subtrahend;
        }

        public BigDecimal withdrawBalance(BigDecimal value) throws IllegalArgumentException {
            double doubleValue = value.doubleValue();
            if (doubleValue < 0) {
                throw new IllegalArgumentException("Value to withdraw from balance can't be a negative number");
            }
            if (doubleValue > balance.doubleValue()) {
                throw new IllegalArgumentException("Value to withdraw can't exceed balance");
            }
            balance = balance.subtract(value);
            return value;
        }
    }

}
