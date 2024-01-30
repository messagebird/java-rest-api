package com.messagebird.objects.integrations;

import java.util.List;

/**
 * Template Object as integrations API request.
 *
 * @see <a href="https://developers.messagebird.com/api/integrations">Integrations API</a>
 * @author ssk910
 */
public class Template {

  private String name;
  private String language;
  private String wabaID;
  private List<HSMComponent> components;
  private HSMCategory category;
  private boolean ctaURLLinkTrackingOptedOut;

  public Template() {
  }


  public Template(String name, String language, String wabaID,
                  List<HSMComponent> components, HSMCategory category, boolean ctaURLLinkTrackingOptedOut) {
    this.name = name;
    this.language = language;
    this.wabaID = wabaID;
    this.components = components;
    this.category = category;
    this.ctaURLLinkTrackingOptedOut = ctaURLLinkTrackingOptedOut;
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

  public String getWABAID() {
    return wabaID;
  }

  public void setWABAID(String wabaID) {
    this.wabaID = wabaID;
  }

  public List<HSMComponent> getComponents() {
    return components;
  }

  public void setComponents(List<HSMComponent> components) {
    this.components = components;
  }

  public HSMCategory getCategory() {
    return category;
  }

  public void setCategory(HSMCategory category) {
    this.category = category;
  }

  public void setCtaURLLinkTrackingOptedOut (boolean ctaURLLinkTrackingOptedOut) {
    this.ctaURLLinkTrackingOptedOut = ctaURLLinkTrackingOptedOut;
  }

  public boolean getCtaURLLinkTrackingOptedOut () {
    return ctaURLLinkTrackingOptedOut;
  }

  @Override
  public String toString() {
    return "WhatsAppTemplate{" +
        "name='" + name + '\'' +
        ", language='" + language + '\'' +
        ", wabaID='" + wabaID + '\'' +
        ", components=" + components +
        ", category='" + category + '\'' +
        ", ctaURLLinkTrackingOptedOut='" + ctaURLLinkTrackingOptedOut + '\'' +
        '}';
  }

  /**
   * Validate required fields: components, name, language, category
   *
   * @throws IllegalArgumentException if required fields are invalid.
   */
  public void validate() throws IllegalArgumentException {
    this.validateComponents();
    this.validateName();
    this.validateLanguage();
    this.validateWABAID();
    this.validateCategory();
  }

  /**
   * Check if components field is valid.
   *
   * @throws IllegalArgumentException If components field is null or empty list.
   */
  private void validateComponents() throws IllegalArgumentException {
    final boolean componentsNotEmpty = !(this.components == null || this.components.isEmpty());

    if (componentsNotEmpty) {
      for (final HSMComponent component : this.components) {
        component.validateComponent();
      }
    } else {
      throw new IllegalArgumentException("A \"components\" field is required and should not be empty list.");
    }
  }

  /**
   * Check if name field is valid.
   *
   * @throws IllegalArgumentException If name field is null or empty string.
   */
  private void validateName() {
    if (this.name == null) {
      throw new IllegalArgumentException("A \"name\" field is required.");
    } else if (this.name.length() == 0) {
      throw new IllegalArgumentException("A \"name\" field can not be an empty string.");
    }
  }

  /**
   * Check if language field is valid.
   *
   * @throws IllegalArgumentException If language field is null or empty string.
   */
  private void validateLanguage() {
    if (this.language == null) {
      throw new IllegalArgumentException("A \"language\" field is required.");
    } else if (this.language.length() == 0) {
      throw new IllegalArgumentException("A \"language\" field can not be an empty string.");
    }
  }

  /**
   * Check if wabaID field is valid.
   *
   * @throws IllegalArgumentException If wabaID field is null or empty string.
   */
  private void validateWABAID() {
    if (this.wabaID == null) {
      throw new IllegalArgumentException("A \"wabaID\" field is required.");
    } else if (this.wabaID.length() == 0) {
      throw new IllegalArgumentException("A \"wabaID\" field can not be an empty string.");
    }
  }

  /**
   * Check if category field is valid.
   *
   * @throws IllegalArgumentException If category field is null.
   */
  private void validateCategory() {
    if (this.category == null) {
      throw new IllegalArgumentException("A \"category\" field is required.");
    }
  }
}
