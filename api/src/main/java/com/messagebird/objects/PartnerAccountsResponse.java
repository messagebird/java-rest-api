package com.messagebird.objects;

import java.util.List;

public class PartnerAccountsResponse {
    private List<ChildAccountResponse> childAccountResponses;

    public List<ChildAccountResponse> getChildAccountResponses() {
        return childAccountResponses;
    }

    public void setChildAccountResponses(List<ChildAccountResponse> childAccountResponses) {
        this.childAccountResponses = childAccountResponses;
    }
}
