package com.messagebird.objects.integrations;

import java.util.List;

/**
 * HSMComponentCard
 *
 * @author AlexL-mb
 * @see <a href="https://developers.messagebird.com/api/integrations/#hsmcomponentcard-object">HSMComponentCard</a>
 */
public class HSMComponentCard {
    private List<HSMComponent> components;

    public List<HSMComponent> getComponents() {
        return components;
    }

    public void setComponents(List<HSMComponent> components) {
        this.components = components;
    }
}