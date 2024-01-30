package com.messagebird.objects.integrations;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Template response using integrations API.
 *
 * @author ssk910
 */
public class TemplateResponse implements Serializable {

  private static final long serialVersionUID = 7154209824478715861L;
  private String name;
  private String language;
  private HSMCategory category;
  private List<HSMComponent> components;
  private HSMStatus status;
  private String rejectedReason;
  private String wabaID;
  private String namespace;

  private boolean ctaURLLinkTrackingOptedOut;

  private HSMQualityScore qualityScore;

  private Date createdAt;
  private Date updatedAt;

  public static long getSerialVersionUID() {
    return serialVersionUID;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public HSMCategory getCategory() {
    return category;
  }

  public void setCategory(HSMCategory category) {
    this.category = category;
  }

  public List<HSMComponent> getComponents() {
    return components;
  }

  public void setComponents(List<HSMComponent> components) {
    this.components = components;
  }

  public HSMStatus getStatus() {
    return status;
  }

  public void setStatus(HSMStatus status) {
    this.status = status;
  }

  public String getRejectedReason() {
    return rejectedReason;
  }

  public void setRejectedReason(String rejectedReason) {
    this.rejectedReason = rejectedReason;
  }

  public String getWabaID() {
    return wabaID;
  }

  public void setWabaID(String wabaID) {
    this.wabaID = wabaID;
  }

  public String getNamespace() {
    return namespace;
  }

  public void setNamespace(String namespace) {
    this.namespace = namespace;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  public boolean isCtaURLLinkTrackingOptedOut() {
    return ctaURLLinkTrackingOptedOut;
  }

  public void setCtaURLLinkTrackingOptedOut(boolean ctaURLLinkTrackingOptedOut) {
    this.ctaURLLinkTrackingOptedOut = ctaURLLinkTrackingOptedOut;
  }

  public HSMQualityScore getQualityScore() {
    return qualityScore;
  }

  public void setQualityScore(HSMQualityScore qualityScore) {
    this.qualityScore = qualityScore;
  }

  @Override
  public String toString() {
    return "WhatsAppTemplateResponse{" +
        "name='" + name + '\'' +
        ", language='" + language + '\'' +
        ", category='" + category + '\'' +
        ", components=" + components +
        ", status='" + status + '\'' +
        ", rejectedReason='" + rejectedReason + '\'' +
        ", wabaID='" + wabaID + '\'' +
        ", namespace='" + namespace + '\'' +
        ", ctaURLLinkTrackingOptedOut='" + ctaURLLinkTrackingOptedOut + '\'' +
        ", qualityScore='" + qualityScore + '\'' +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        '}';
  }

}
