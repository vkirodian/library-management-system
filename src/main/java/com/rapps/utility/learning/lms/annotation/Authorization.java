package com.rapps.utility.learning.lms.annotation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import com.rapps.utility.learning.lms.enums.UserRoleEnum;

/**
 * 
 * @author vkirodian
 *
 */
@Documented
@Retention(RUNTIME)
public @interface Authorization {

	UserRoleEnum[] roles() default {};
}
