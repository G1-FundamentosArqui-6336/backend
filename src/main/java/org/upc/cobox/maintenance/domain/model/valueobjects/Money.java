package org.upc.cobox.maintenance.domain.model.valueobjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.math.BigDecimal;

@Embeddable
@Getter
public class Money {
    @Column(precision = 19, scale = 4)
    private BigDecimal amount;

    @Column(length = 3)
    private String currency;

    protected Money() {
    }

    public Money(BigDecimal amount, String currency) {
        if (amount == null || amount.signum() < 0) throw new IllegalArgumentException("amount>=0");
        if (currency == null || currency.length() != 3) throw new IllegalArgumentException("ISO4217");
        this.amount = amount;
        this.currency = currency.toUpperCase();
    }

    public Money add(Money other) {
        if (!this.currency.equals(other.currency)) throw new IllegalArgumentException("currency mismatch");
        return new Money(this.amount.add(other.amount), this.currency);
    }
}
