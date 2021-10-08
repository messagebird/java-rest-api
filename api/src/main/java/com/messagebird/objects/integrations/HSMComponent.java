package com.messagebird.objects.integrations;

import java.util.List;

/**
 * A class for HSMComponent object
 *
 * @see <a href="https://developers.messagebird.com/api/integrations/#hsmcomponent-object">HSMComponent</a>
 * @author ssk910
 */
public class HSMComponent {

  private HSMComponentType type;
  private HSMComponentFormat format;
  private String text;
  private List<HSMComponentButton> buttons;
  private HSMExample example;

  public HSMComponentType getType() {
    return type;
  }

  public void setType(HSMComponentType type) {
    this.type = type;
  }

  public HSMComponentFormat getFormat() {
    return format;
  }

  public void setFormat(HSMComponentFormat format) {
    this.format = format;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public List<HSMComponentButton> getButtons() {
    return buttons;
  }

  public void setButtons(List<HSMComponentButton> buttons) {
    this.buttons = buttons;
  }

  public HSMExample getExample() {
    return example;
  }

  public void setExample(HSMExample example) {
    this.example = example;
  }

  @Override
  public String toString() {
    return "HSMComponent{" +
        "type='" + type + '\'' +
        ", format='" + format + '\'' +
        ", text='" + text + '\'' +
        ", buttons=" + buttons +
        ", example=" + example +
        '}';
  }

  /**
   * Check if this component is valid.
   *
   * @throws IllegalArgumentException Occurs when validation is not passed.
   */
  public void validateComponent() throws IllegalArgumentException {
    this.validateButtons();
    this.validateComponentExample();
  }

  /**
   * Check if button list is valid.
   *
   * @throws IllegalArgumentException Occurs when validation is not passed.
   */
  private void validateButtons() throws IllegalArgumentException {
    if (this.buttons == null) {
      return;
    }

    for (final HSMComponentButton button : this.buttons) {
      button.validateButtonExample();
    }
  }

  /**
   * Check for header_text and header_url.
   *
   * @throws IllegalArgumentException Occurs when {@code header_text} or {@code header_url} is not able to use.
   */
  private void validateComponentExample() throws IllegalArgumentException {
    final boolean isExampleNotNull = this.example != null;
    final boolean isHeaderTextNotEmpty =
        isExampleNotNull && !(this.example.getHeader_text() == null || this.example.getHeader_text()
            .isEmpty());
    final boolean isHeaderUrlNotEmpty =
        isExampleNotNull && !(this.example.getHeader_url() == null || this.example.getHeader_url()
            .isEmpty());

    if (isHeaderTextNotEmpty) {
      this.checkHeaderText();
    }

    if (isHeaderUrlNotEmpty) {
      this.checkHeaderUrl();
    }
  }

  /**
   * Check if header_text is able to use.
   *
   * @throws IllegalArgumentException Occurs when type is not {@code HEADER} and format is not {@code TEXT}.
   */
  private void checkHeaderText() throws IllegalArgumentException {
    if (!(type.equals(HSMComponentType.HEADER)
        && format.equals(HSMComponentFormat.TEXT))
    ) {
      throw new IllegalArgumentException("\"header_text\" is available for only HEADER type and TEXT format.");
    }
  }

  /**
   * Check if header_url is able to use.
   *
   * @throws IllegalArgumentException Occurs when type is not {@code HEADER} and format is not {@code IMAGE}.
   */
  private void checkHeaderUrl() throws IllegalArgumentException {
    if (!(type.equals(HSMComponentType.HEADER)
        && format.equals(HSMComponentFormat.IMAGE))
    ) {
      throw new IllegalArgumentException("\"header_url\" is available for only HEADER type and IMAGE format.");
    }
  }
}
