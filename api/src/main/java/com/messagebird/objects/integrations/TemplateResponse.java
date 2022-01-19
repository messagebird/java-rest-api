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

  @Override
  public String toString() {
    return "WhatsAppTemplateResponse{" +
        "name='" + name + '\'' +
        ", language='" + language + '\'' +
        ", category='" + category + '\'' +
        ", components=" + components +
        ", status='" + status + '\'' +
        ", rejectedReason='" + rejectedReason + '\'' +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        '}';
  }
}
