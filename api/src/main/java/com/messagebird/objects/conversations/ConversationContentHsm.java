package com.messagebird.objects.conversations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Highly structured message, only for WhatsApp channels. This is a
 * pre-approved, reusable message template required when messaging over
 * WhatsApp. It allows you to just send the required parameter values instead
 * of the full message. It also allows for localization of the message and
 * decreases the possibility of being blocked on the first contact as the
 * message is pre-approved by WhatsApp.
 */
public class ConversationContentHsm {

    private String namespace;
    private String templateName;
    private ConversationHsmLanguage language;
    private List<ConversationHsmLocalizableParameter> params;

    public ConversationContentHsm(
            final String namespace,
            final String templateName,
            final ConversationHsmLanguage language,
            final List<ConversationHsmLocalizableParameter> params
    ) {
        this.namespace = namespace;
        this.templateName = templateName;
        this.language = language;
        this.params = params;
    }

    public ConversationContentHsm(
            final String namespace,
            final String templateName,
            final ConversationHsmLanguage language
    ) {
        this.namespace = namespace;
        this.templateName = templateName;
        this.language = language;
    }

    public ConversationContentHsm() {
        //
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(final String namespace) {
        this.namespace = namespace;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(final String templateName) {
        this.templateName = templateName;
    }

    public ConversationHsmLanguage getLanguage() {
        return language;
    }

    public void setLanguage(final ConversationHsmLanguage language) {
        this.language = language;
    }

    public List<ConversationHsmLocalizableParameter> getParams() {
        return params;
    }

    public void addParams(final ConversationHsmLocalizableParameter... params) {
        if (this.params == null) {
            this.params = new ArrayList<>(params.length);
        }

        Collections.addAll(this.params, params);
    }

    public void setParams(final List<ConversationHsmLocalizableParameter> params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "ConversationContentHsm{" +
                "namespace='" + namespace + '\'' +
                ", templateName='" + templateName + '\'' +
                ", language=" + language +
                ", params=" + params +
                '}';
    }
}
