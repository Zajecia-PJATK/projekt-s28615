package com.business.market.simulator.finance.instrument.aspect;

import com.business.market.simulator.finance.transaction.MarketTransaction;

public interface Buyable {
    MarketTransaction buyInstrument();
}