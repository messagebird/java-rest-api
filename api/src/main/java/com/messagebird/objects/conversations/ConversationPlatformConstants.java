package com.messagebird.objects.conversations;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Platforms are communication channels that a conversation can communicate through.
 */
public class ConversationPlatformConstants {
    private static final Set<String> VALID_PLATFORMS = new HashSet<>(Arrays.asList(
            "sms", "whatsapp", "facebook", "telegram", "line", "wechat", "email", "events", "whatsapp_sandbox"
    ));

    public static boolean isValidPlatform(String platform) {
        return VALID_PLATFORMS.contains(platform);
    }
}