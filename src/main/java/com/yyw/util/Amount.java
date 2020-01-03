package com.yyw.util;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author yyw
 * @date 2018/12/11 10:48
 */
public class Amount implements Serializable {


    private static final long serialVersionUID = 2869405137247580483L;
    private final Unit unit;
    private double value;
    private static final String CURRENT_CONFLICT = "currency conflict";

    public final double getValue() {
        return this.value;
    }

    public final long getLongValue() {
        BigDecimal bigValue = BigDecimal.valueOf(this.value);
        return bigValue.longValue();
    }

    /**
     * 返回BigDecimal类型
     *
     * @return BigDecimal
     */
    public final BigDecimal getBigDecimalValue() {
        return new BigDecimal(Double.toString(this.value));
    }

    public final String getStringValue() {
        BigDecimal bigValue = BigDecimal.valueOf(this.value);
        return String.valueOf(bigValue.floatValue());
    }

    public final Unit getUnit() {
        return this.unit;
    }

    public final String getCurrency() {
        return this.unit.getSymbol();
    }

    public final Amount add(Amount amount) {
        if (!this.unit.equals(amount.unit)) {
            throw new UnsupportedOperationException(CURRENT_CONFLICT);
        }

        BigDecimal bigValue = BigDecimal.valueOf(this.value);
        BigDecimal bigValue2 = BigDecimal.valueOf(amount.getValue());
        return new Amount(this.unit, bigValue.add(bigValue2).doubleValue());
    }

    public final void addToSelf(Amount amount) {
        if (!this.unit.equals(amount.unit)) {
            throw new UnsupportedOperationException(CURRENT_CONFLICT);
        }
        BigDecimal bigValue = BigDecimal.valueOf(this.value);
        BigDecimal bigValue2 = BigDecimal.valueOf(amount.getValue());
        this.value = bigValue.add(bigValue2).doubleValue();
    }

    public final Amount subtract(Amount amount) {
        if (!this.unit.equals(amount.unit)) {
            throw new UnsupportedOperationException(CURRENT_CONFLICT);
        }
        BigDecimal bigValue = BigDecimal.valueOf(this.value);
        BigDecimal bigValue2 = BigDecimal.valueOf(amount.getValue());
        return new Amount(this.unit, bigValue.subtract(bigValue2).doubleValue());
    }

    public final void subtractToSelf(Amount amount) {
        if (!this.unit.equals(amount.unit)) {
            throw new UnsupportedOperationException(CURRENT_CONFLICT);
        }
        BigDecimal bigValue = BigDecimal.valueOf(this.value);
        BigDecimal bigValue2 = BigDecimal.valueOf(amount.getValue());
        this.value = bigValue.subtract(bigValue2).doubleValue();
    }

    public final Amount multiply(double d) {
        BigDecimal bigValue = BigDecimal.valueOf(this.value);
        BigDecimal bigValue2 = BigDecimal.valueOf(d);
        return new Amount(this.unit, bigValue.multiply(bigValue2).doubleValue());
    }

    public final Amount divide(double d) {
        BigDecimal bigValue = BigDecimal.valueOf(this.value);
        BigDecimal bigValue2 = BigDecimal.valueOf(d);
        return new Amount(this.unit, bigValue.divide(bigValue2, 2, 4).doubleValue());
    }

    public final Amount opposite() {
        return new Amount(this.unit, -this.value);
    }

    @Override
    public final String toString() {
        return "" + this.value + ' ' + this.unit;
    }

    /**
     * 金额比较大小
     *
     * @param amount 金额
     * @return boolean
     */
    public final boolean valueEquals(Amount amount) {
        return new BigDecimal(Double.toString(this.value)).compareTo(new BigDecimal(Double.toString(amount.value))) == 0;
    }

    /**
     * 比较存管返回的金额与账户中心记录的金额是否相同
     *
     * @param depositoryAmount 存管返回的金额
     * @param recordAmount     账户中心记录的金额
     * @return boolean
     */
    public static boolean compareAmount(BigDecimal depositoryAmount, long recordAmount) {
        Amount moneyDepository = new Amount(depositoryAmount.doubleValue()).multiply(100);
        Amount moneyAccount = new Amount(recordAmount);
        return moneyAccount.valueEquals(moneyDepository);
    }

    /**
     * 元转分
     *
     * @param depositoryAmount 请求存管的金额（元）
     * @return long
     */
    public static long getLongFromYuanToFen(BigDecimal depositoryAmount) {
        return new Amount(depositoryAmount.doubleValue()).multiply(100).getLongValue();
    }

    /**
     * 获取Bigdecimal金额(分)转化成BigDecimal(元)
     *
     * @param amount 金额(分)
     * @return BigDecimal
     */
    public static BigDecimal getBigDecimalFromFenToYuan(BigDecimal amount) {
        return new Amount(amount.longValue()).divide(100).getBigDecimalValue();
    }

    /**
     * 获取Bigdecimal金额(分)转化成BigDecimal(元)
     *
     * @param amount 金额(分)
     * @return BigDecimal
     */
    public static BigDecimal getBigDecimalFromFenToYuan(Long amount) {
        return new BigDecimal(amount).divide(new BigDecimal(100), 2, 4);
    }

    @Override
    public final boolean equals(Object obj) {
        if (!(obj instanceof Amount)) {
            return false;
        }

        Amount amount = (Amount) obj;
        return (this.unit.equals(amount.unit)) && (new BigDecimal(Double.toString(this.value)).compareTo(new BigDecimal(Double.toString(amount.value))) == 0);
    }

    @Override
    public final int hashCode() {
        return Double.valueOf((this.value)).hashCode();
    }

    public final boolean isGreaterThan(Amount amount) {
        if (!this.unit.equals(amount.unit)) {
            throw new UnsupportedOperationException(CURRENT_CONFLICT);
        }
        return this.value > amount.value;
    }

    public final boolean isGreaterOrEqualThan(Amount amount) {
        if (!this.unit.equals(amount.unit)) {
            throw new UnsupportedOperationException(CURRENT_CONFLICT);
        }
        return this.value >= amount.value;
    }

    public final boolean isLessThan(Amount amount) {
        if (!this.unit.equals(amount.unit)) {
            throw new UnsupportedOperationException(CURRENT_CONFLICT);
        }
        return this.value < amount.value;
    }

    public final boolean isLessOrEqualThan(Amount amount) {
        if (!this.unit.equals(amount.unit)) {
            throw new UnsupportedOperationException(CURRENT_CONFLICT);
        }
        return this.value <= amount.value;
    }

    public static final Amount parseAmount(String cry, String amtStr) {
        return new Amount(cry, Double.parseDouble(amtStr));
    }

    public static final Amount parseAmount(String strAmt) {
        int i = strAmt.trim().indexOf(' ');
        if (i < 1) {
            throw new NumberFormatException();
        }
        double amount = new BigDecimal(strAmt.substring(0, i)).doubleValue();
        Unit unit = new Unit(strAmt.substring(i + 1));
        return new Amount(unit, amount);
    }

    public Amount(double d) {
        this.unit = new Unit("CNY");
        this.value = round(d, 2);
    }

    public Amount(Unit unit1, double d) {
        this.unit = unit1;
        this.value = round(d, 2);
    }

    public Amount(String unitStr, double d) {
        this(new Unit(unitStr), d);
    }

    public static double round(double amt) {
        return BigDecimal.valueOf(amt).setScale(2, 4).doubleValue();
    }

    private double round(double d, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }

        BigDecimal b = new BigDecimal(Double.toString(d));

        BigDecimal one = new BigDecimal("1");

        return b.divide(one, scale, 4).doubleValue();
    }

    public boolean isNegative() {
        return BigDecimal.valueOf(this.value).compareTo(BigDecimal.ZERO) < 0;
    }

    public boolean isZero() {
        return BigDecimal.valueOf(this.value).compareTo(BigDecimal.ZERO) == 0;
    }

    public boolean isPositive() {
        return BigDecimal.valueOf(this.value).compareTo(BigDecimal.ZERO) > 0;
    }

    public double floor(int scale) {
        return BigDecimal.valueOf(this.value).setScale(scale, 1).doubleValue();
    }

    public double floor() {
        return floor(2);
    }

    public double ceiling(int scale) {
        return BigDecimal.valueOf(this.value).setScale(scale, 0).doubleValue();
    }

    public double ceiling() {
        return ceiling(2);
    }

    public Amount getAbsoluteValue() {
        if ((isZero()) || (isPositive())) {
            return this;
        }
        if (isNegative()) {
            return getOppositeValue();
        }

        throw new IllegalArgumentException("unknown amount process.");
    }

    public Amount getOppositeValue() {
        if (isZero()) {
            return this;
        }

        return multiply(-1.0D);
    }

}
