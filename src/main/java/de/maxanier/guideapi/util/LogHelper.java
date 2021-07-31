package de.maxanier.guideapi.util;

import de.maxanier.guideapi.GuideConfig;
import de.maxanier.guideapi.GuideMod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogHelper {

    private static final Logger logger = LogManager.getLogger(GuideMod.NAME);

    /**
     * @param info - String to log to the info level
     */

    public static void info(Object info) {
        if (GuideConfig.COMMON.enableLogging.get())
            logger.info(info);
    }

    /**
     * @param error - String to log to the error level
     */

    public static void error(Object error) {
        if (GuideConfig.COMMON.enableLogging.get())
            logger.error(error);
    }

    /**
     * @param debug - String to log to the debug level
     */

    public static void debug(Object debug) {
        if (GuideConfig.COMMON.enableLogging.get())
            logger.debug(debug);
    }
}
