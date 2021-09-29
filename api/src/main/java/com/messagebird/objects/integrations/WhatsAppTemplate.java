package com.messagebird.objects.integrations;

import com.messagebird.exceptions.GeneralException;
import java.util.List;

/**
 * WhatsApp Template Object as integrations API request.
 *
 * @see <a href="https://developers.messagebird.com/api/integrations">Integrations API</a>
 * @author ssk910
 */
public class WhatsAppTemplate {

  private String name;
  private String language;
  private List<HSMComponent> components;
  private HSMCategory category;

  public WhatsAppTemplate() {
  }

  public WhatsAppTemplate(String name, String language,
      List<HSMComponent> components, HSMCategory category) {
    this.name = name;
    this.language = language;
    this.components = components;
    this.category = category;
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

  /**
   * Check if components field is valid.
   *
   * @throws GeneralException Occurs when it is invalid.
   */
  public void validate() throws GeneralException {
    if (this.components == null) {
      return;
    }

    for (final HSMComponent component : this.components) {
      component.validateComponent();
    }
  }

  @Override
  public String toString() {
    return "WhatsAppTemplate{" +
        "name='" + name + '\'' +
        ", language='" + language + '\'' +
        ", components=" + components +
        ", category='" + category + '\'' +
        '}';
  }
}
