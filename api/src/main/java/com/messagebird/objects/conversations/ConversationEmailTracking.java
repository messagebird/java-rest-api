package com.messagebird.objects.conversations;

public class ConversationEmailTracking {
    private boolean open;
    private boolean click;

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isClick() {
        return click;
    }

    public void setClick(boolean click) {
        this.click = click;
    }

    @Override
    public String toString() {
        return "ConversationEmailTracking{" +
                "open=" + open +
                ", click=" + click +
                '}';
    }
}
