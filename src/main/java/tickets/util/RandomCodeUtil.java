package tickets.util;

import java.util.UUID;

public class RandomCodeUtil {
    public static String generateUniqueCode() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
