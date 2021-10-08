package com.messagebird.objects;

public class ChildAccountDetailedResponse extends ChildAccountResponse{
    private String invoiceAggregation;

    public String getInvoiceAggregation() {
        return invoiceAggregation;
    }

    public void setInvoiceAggregation(String invoiceAggregation) {
        this.invoiceAggregation = invoiceAggregation;
    }
}
