package com.rapps.utility.learning.lms.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rapps.utility.learning.lms.exception.LmsException.ErrorType;
import com.rapps.utility.learning.lms.helper.SessionMgmtHelper;
import com.rapps.utility.learning.lms.model.GenericOutput;
import com.rapps.utility.learning.lms.model.OutputStatus;

/**
 * This filter is used to perform the following:<br>
 * Log Request and response.<br>
 * Authorize the API against the session information.<br>
 * Wrap the response in {@link ResponseStatusModel} class.
 * 
 * @author vkirodian
 *
 */
@Component
public class LmsFilter implements Filter {

	private static final Logger LOG = LoggerFactory.getLogger(LmsFilter.class);
	private static String jsonPattern = "(?i)(\"password\":)(.+?)(\")";

	@Autowired
	SessionMgmtHelper sessionMgmtHelper;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		final HttpServletRequest httpRequest = (HttpServletRequest) request;
		BufferedRequestWrapper bufferedRequest = new BufferedRequestWrapper(httpRequest);
		LOG.debug("{} {}", bufferedRequest.getMethod(), bufferedRequest.getRequestURI());
		LOG.debug("Query Param : {}", bufferedRequest.getQueryString());
		if (LOG.isDebugEnabled()) {
			LOG.debug("Post Body : \n{}", new String(bufferedRequest.getBuffer()));
		}

		JsonResponseWrapper jsonResponseWrapper = new JsonResponseWrapper((HttpServletResponse) response);
		chain.doFilter(bufferedRequest, jsonResponseWrapper);
		String maskedResponse = maskPassword(jsonResponseWrapper.getResponseAsString());
		response.getOutputStream().write(getBytes(getResponseStatus(getJsonNode(maskedResponse))));
	}

	@Override
	public void destroy() {
	}

	protected JsonNode getJsonNode(String response) throws IOException {
		if (response != null && !response.isEmpty()) {
			return new ObjectMapper().readTree(response);
		}
		return null;
	}

	protected GenericOutput getResponseStatus(JsonNode obj) {
		GenericOutput model = new GenericOutput();
		if (obj != null && obj.get("errorType") != null) {
			OutputStatus status = new OutputStatus();
			status.setErrorType(ErrorType.valueOf(obj.get("errorType").asText()));
			status.setErrorMsg(obj.get("errorMsg").asText());
			status.setErrorReason(obj.get("errorReason").asText());
			status.setErrorReasonCode(obj.get("errorReasonCode").asText());
			model.setOutputStatus(status);
		} else {
			OutputStatus status = new OutputStatus();
			status.setErrorType(ErrorType.SUCCESS);
			model.setData(obj);
			model.setOutputStatus(status);
		}
		return model;
	}

	protected byte[] getBytes(GenericOutput response) throws JsonProcessingException {
		String serialized = new ObjectMapper().writeValueAsString(response);
		LOG.debug("Response : {}", serialized);
		return serialized.getBytes();
	}

	protected String maskPassword(String jsonResponse) {
		return jsonResponse.replaceAll(jsonPattern, "$1" + "\"****" + "$3");
	}
}
