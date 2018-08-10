package com.rapps.utility.learning.lms.filter;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.rapps.utility.learning.lms.annotation.Authorization;
import com.rapps.utility.learning.lms.enums.MessagesEnum;
import com.rapps.utility.learning.lms.enums.UserRoleEnum;
import com.rapps.utility.learning.lms.exception.LmsException;
import com.rapps.utility.learning.lms.global.LmsConstants;
import com.rapps.utility.learning.lms.global.SessionCache;
import com.rapps.utility.learning.lms.helper.SessionMgmtHelper;
import com.rapps.utility.learning.lms.persistence.bean.Session;

/**
 * An intercepter to perform authorization based on API and role of the user
 * access the API.
 * 
 * @author vkirodian
 *
 */
public class AuthorizationInterceptor implements HandlerInterceptor {

	private static final Logger LOG = LoggerFactory.getLogger(AuthorizationInterceptor.class);
	private static final String SKIP_AUTH = System.getenv("SKIP_AUTH");

	private SessionMgmtHelper sessionMgmtHelper;

	public AuthorizationInterceptor(SessionMgmtHelper sessionMgmtHelper) {
		super();
		this.sessionMgmtHelper = sessionMgmtHelper;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO remove skip authorization once stable
		final boolean performAuth = SKIP_AUTH != null ? !Boolean.parseBoolean(SKIP_AUTH) : true;
		if (performAuth) {
			verifySessionAndAuthorize(request, (HandlerMethod) handler);
		}
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}

	protected void verifySessionAndAuthorize(HttpServletRequest request, HandlerMethod handlerMethod)
			throws IOException {
		try {
			Method implMethod = handlerMethod.getMethod();
			String implMethodname = implMethod.getName();
			Class<?>[] paramTypes = implMethod.getParameterTypes();

			Class<?>[] iController = handlerMethod.getBean().getClass().getInterfaces();
			if (iController.length > 1) {
				LOG.error("Controller class should have only one interface");
				throw new IOException("Controller class should have only one interface");
			}
			Method iMethod = iController[0].getMethod(implMethodname, paramTypes);

			Authorization auth = iMethod.getAnnotation(Authorization.class);
			if (auth != null) {
				List<UserRoleEnum> allowedRoles = Arrays.asList(auth.roles());
				if (allowedRoles.isEmpty()) {
					return;
				}
				String sessionId = request.getHeader(LmsConstants.SESSION_ID);
				if (sessionId == null) {
					LOG.error("Session information missing in request");
					throw new IOException(MessagesEnum.SESSION_MISSING.getMessage());
				}
				Session session = SessionCache.sessionExists(sessionId);
				if (session == null) {
					LOG.error("Invalid session in request");
					throw new IOException(MessagesEnum.SESSION_MISSING.getMessage());
				}
				UserRoleEnum userRole = sessionMgmtHelper.getRoleForUserSession(session);
				if (!allowedRoles.contains(userRole)) {
					LOG.error("User role {} is unauthorized to invoke API {}", userRole, request.getRequestURI());
					throw new IOException("Unauthorized");
				}
				sessionMgmtHelper.updateLastAccessTime(session);
			} else {
				LOG.error("API is missing authorization details {}", request.getRequestURI());
				throw new IOException("Unauthorized");
			}
		} catch (LmsException e) {
			LOG.error("Unable to get User Role.");
			throw new IOException(e.getErrorReason());
		} catch (NoSuchMethodException | SecurityException e) {
			LOG.error("Error while fetching authorization details for API {}", request.getRequestURI());
			throw new IOException(e);
		}
	}
}
