package amerifrance.guideapi.util;

import net.minecraft.util.StatCollector;

public class Utils {

    public static String localizeFormatted(String key, Object ... info) {
        return String.format(StatCollector.translateToLocal(key), (String[]) info);
    }
}
