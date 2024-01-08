package com.messagebird.objects.integrations;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * HSMComponentButton
 *
 * @see <a href="https://developers.messagebird.com/api/integrations/#hsmcomponentbutton-object">HSMComponentButton</a>
 * @author ssk910
 */
public class HSMComponentButton {

    private HSMComponentButtonType type;
    private String text;
    private String url;
    private String phone_number;
    private List<String> example;

    //fields used by the authentification template
    @JsonProperty("otp_type")
    private HSMOTPButtonType otpType;
    @JsonProperty("autofill_text")
    private String autofillText;
    @JsonProperty("package_name")
    private String packageName;
    @JsonProperty("signature_hash")
    private String signatureHash;

    public HSMComponentButtonType getType() {
        return type;
    }

    public void setType(HSMComponentButtonType type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public List<String> getExample() {
        return example;
    }

    public void setExample(List<String> example) {
        this.example = example;
    }
    public HSMOTPButtonType getOtpType() {
        return otpType;
    }

    public void setOtpType(HSMOTPButtonType otpType) {
        this.otpType = otpType;
    }

    public String getAutofillText() {
        return autofillText;
    }

    public void setAutofillText(String autofillText) {
        this.autofillText = autofillText;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getSignatureHash() {
        return signatureHash;
    }

    public void setSignatureHash(String signatureHash) {
        this.signatureHash = signatureHash;
    }
    @Override
    public String toString() {
        return "HSMComponentButton{" +
                "type=" + type +
                ", text='" + text + '\'' +
                ", url='" + url + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", example=" + example +
                '}';
    }

    /**
     * Check if example field is able to use.
     *
     * @throws IllegalArgumentException Occurs if button type is not {@code URL} or {@code QUICK_REPLY}.
     */
    public void validateButtonExample() throws IllegalArgumentException {
        final boolean isExampleEmpty = this.example == null || this.example.isEmpty();
        final boolean isNotProperType = !(this.type.equals(HSMComponentButtonType.URL)
                || this.type.equals(HSMComponentButtonType.QUICK_REPLY)
                || this.type.equals(HSMComponentButtonType.COPY_CODE)
        );

        if (isExampleEmpty) {
            return;
        }

        if (isNotProperType) {
            throw new IllegalArgumentException("An example field in HSMComponentButton is available for only URL or QUICK_REPLY button types.");
        }
    }
}
