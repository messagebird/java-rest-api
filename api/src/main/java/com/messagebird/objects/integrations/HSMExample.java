package com.messagebird.objects.integrations;

import java.util.List;

/**
 * HSMExample object
 *
 * @see <a href="https://developers.messagebird.com/api/integrations/#hsmexample-object">HSMExample object</a>
 * @author ssk910
 */
public class HSMExample {

  /* Example values for HEADER type components, TEXT format */
  private List<String> header_text;

  /* Example set of values for the body text variables */
  private List<List<String>> body_text;

  /* Example values for HEADER type components, IMAGE format */
  private List<String> header_url;

  public List<String> getHeader_text() {
    return header_text;
  }

  public void setHeader_text(List<String> header_text) {
    this.header_text = header_text;
  }

  public List<List<String>> getBody_text() {
    return body_text;
  }

  public void setBody_text(List<List<String>> body_text) {
    this.body_text = body_text;
  }

  public List<String> getHeader_url() {
    return header_url;
  }

  public void setHeader_url(List<String> header_url) {
    this.header_url = header_url;
  }

  @Override
  public String toString() {
    return "HSMExample{" +
        "header_text=" + header_text +
        ", body_text=" + body_text +
        ", header_url=" + header_url +
        '}';
  }
}
