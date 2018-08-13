package com.rapps.utility.learning.lms.annotation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import com.rapps.utility.learning.lms.enums.AccessTypeEnum;

/**
 * 
 * @author vkirodian
 *
 */
@Documented
@Retention(RUNTIME)
public @interface Authorization {

	AccessTypeEnum accessType();

	boolean skipSession() default false;
}
