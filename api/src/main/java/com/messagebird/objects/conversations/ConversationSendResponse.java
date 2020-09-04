package com.messagebird.objects.conversations;

public class ConversationSendResponse {
    private String id;  //messageID
    private String status;
    private FallbackOptionResponse fallback;

    public static class FallbackOptionResponse{
         private String id;


         public String getId() {
             return id;
         }

         public void setId(String id) {
             this.id = id;
         }

         @Override
         public String toString() {
             return "FallbackOptionResponse{" +
                     "id='" + id + '\'' +
                     '}';
         }
     }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public FallbackOptionResponse getFallback() {
        return fallback;
    }

    public void setFallback(FallbackOptionResponse fallback) {
        this.fallback = fallback;
    }

    @Override
    public String toString() {
        return "ConversationSendResponse{" +
                "id='" + id + '\'' +
                ", status='" + status + '\'' +
                ", fallback=" + fallback +
                '}';
    }
}
