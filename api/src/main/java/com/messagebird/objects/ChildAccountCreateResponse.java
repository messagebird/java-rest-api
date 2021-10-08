package com.messagebird.objects;

import java.util.List;

public class ChildAccountCreateResponse {
    private String id;
    private String name;
    private List<AccessKey> accessKeys;
    private String signingKey;
    private String invoiceAggregation;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AccessKey> getAccessKeys() {
        return accessKeys;
    }

    public void setAccessKeys(List<AccessKey> accessKeys) {
        this.accessKeys = accessKeys;
    }

    public String getSigningKey() {
        return signingKey;
    }

    public void setSigningKey(String signingKey) {
        this.signingKey = signingKey;
    }

    public String getInvoiceAggregation() {
        return invoiceAggregation;
    }

    public void setInvoiceAggregation(String invoiceAggregation) {
        this.invoiceAggregation = invoiceAggregation;
    }
}
