package com.yyw.util;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author laiweigeng
 * @date 2018/12/11 10:48
 */
public class Unit implements Serializable {

    private static final long serialVersionUID = -4369366311455845580L;

    public final String symbol;

    public String getSymbol() {
        return this.symbol;
    }

    @Override
    public String toString() {
        return this.symbol;
    }

    @Override
    public boolean equals(Object obj) {
        return ((obj instanceof Unit)) && (this.symbol.equals(((Unit) obj).getSymbol()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.symbol);
    }

    public Unit(String s) {
        this.symbol = s;
    }
}
