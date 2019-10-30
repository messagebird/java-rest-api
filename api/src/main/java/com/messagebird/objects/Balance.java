package com.messagebird.objects;

import java.io.Serializable;

/**
 * MessageBird provides an API to get the balance and balance information of your account.
 * This object is returned after a balance request to MessageBird
 *
 * Created by rvt on 1/5/15.
 */
public class Balance implements Serializable{

    private static final long serialVersionUID = 3337612669443153675L;

    private String payment;
    private String type;
    private float amount;

    public Balance() {
    }

    @Override
    public String toString() {
        return "Balance{" +
                "payment='" + payment + '\'' +
                ", type='" + type + '\'' +
                ", amount=" + amount +
                '}';
    }

    /**
     * Your payment method. Possible values are: prepaid & postpaid
     * @return
     */
    public String getPayment() {
        return payment;
    }

    /**
     * Your payment type. Possible values are: credits & euros
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     * The amount of balance of the payment type. When postpaid is your payment method, the amount will be 0.
     * @return
     */
    public float getAmount() {
        return amount;
    }
}
