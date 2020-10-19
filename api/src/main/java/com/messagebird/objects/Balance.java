package com.messagebird.objects;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * MessageBird provides an API to get the balance and balance information of your account.
 * This object is returned after a balance request to MessageBird
 *
 * Created by rvt on 1/5/15.
 */
@Getter
@NoArgsConstructor
@ToString
public class Balance implements Serializable{

    private static final long serialVersionUID = 3337612669443153675L;

    private String payment;
    private String type;
    private float amount;

}
