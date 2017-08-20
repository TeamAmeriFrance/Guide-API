package amerifrance.guideapi.api;

import net.minecraftforge.fml.common.eventhandler.EventPriority;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to mark a class to be handled for Guide registration.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface GuideBook {
    EventPriority priority() default EventPriority.NORMAL;
}
