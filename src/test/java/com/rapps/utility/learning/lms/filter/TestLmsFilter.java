package com.rapps.utility.learning.lms.filter;

import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.rapps.utility.learning.lms.enums.MessagesEnum;
import com.rapps.utility.learning.lms.enums.UserRole;
import com.rapps.utility.learning.lms.exception.LmsException;
import com.rapps.utility.learning.lms.exception.LmsException.ErrorType;
import com.rapps.utility.learning.lms.global.LmsConstants;
import com.rapps.utility.learning.lms.global.SessionCache;
import com.rapps.utility.learning.lms.helper.SessionMgmtHelper;
import com.rapps.utility.learning.lms.model.GenericOutput;
import com.rapps.utility.learning.lms.persistence.bean.Session;

import junit.framework.TestCase;

@RunWith(MockitoJUnitRunner.class)
public class TestLmsFilter extends TestCase {

	private static final String SESSION_ID_1 = "session_id_1";

	@InjectMocks
	LmsFilter filter = new LmsFilter();

	@Mock
	HttpServletRequest httpRequest;
	@Mock
	SessionMgmtHelper sessionMgmtHelper;
	@Mock
	JsonNode obj;
	@Mock
	HttpServletRequest request; 
	@Mock
	ServletResponse response; 
	@Mock
	FilterChain chain;

	//@Test
	public void testDoFilter() throws IOException, ServletException {
		filter.doFilter(request, response, chain);
	}
	
	@Test(expected = IOException.class)
	public void testVerifySessionAndAuthorize_SessionMissingInHeader() throws IOException {
		filter.verifySessionAndAuthorize(httpRequest);
		when(httpRequest.getHeader(LmsConstants.SESSION_ID)).thenReturn(SESSION_ID_1);
	}

	@Test(expected = IOException.class)
	public void testVerifySessionAndAuthorize_SessionNotFoundInCache() throws IOException {
		when(httpRequest.getHeader(LmsConstants.SESSION_ID)).thenReturn(SESSION_ID_1);
		SessionCache.removeAllSessions();
		filter.verifySessionAndAuthorize(httpRequest);
	}

	@Test(expected = IOException.class)
	public void testVerifySessionAndAuthorize_UserRoleNotFound() throws IOException, LmsException {
		when(httpRequest.getHeader(LmsConstants.SESSION_ID)).thenReturn(SESSION_ID_1);
		when(sessionMgmtHelper.getRoleForUserSession(SESSION_ID_1))
				.thenThrow(new LmsException(ErrorType.FAILURE, MessagesEnum.USER_NOT_FOUND));
		SessionCache.removeAllSessions();
		Session s = new Session();
		s.setSessionId(SESSION_ID_1);
		SessionCache.addSessionToCache(s);
		filter.verifySessionAndAuthorize(httpRequest);
	}

	@Test(expected = IOException.class)
	public void testVerifySessionAndAuthorize_UnauthorizedUserRole() throws IOException, LmsException {
		when(httpRequest.getHeader(LmsConstants.SESSION_ID)).thenReturn(SESSION_ID_1);
		when(sessionMgmtHelper.getRoleForUserSession(SESSION_ID_1)).thenReturn(UserRole.LIBRARIAN);
		when(httpRequest.getRequestURI()).thenReturn("/lms/user/users");
		SessionCache.removeAllSessions();
		Session s = new Session();
		s.setSessionId(SESSION_ID_1);
		SessionCache.addSessionToCache(s);
		filter.verifySessionAndAuthorize(httpRequest);
	}

	@Test
	public void testVerifySessionAndAuthorize_AuthorizedUserRole() throws IOException, LmsException {
		when(httpRequest.getHeader(LmsConstants.SESSION_ID)).thenReturn(SESSION_ID_1);
		when(sessionMgmtHelper.getRoleForUserSession(SESSION_ID_1)).thenReturn(UserRole.SUPER_ADMIN);
		when(httpRequest.getRequestURI()).thenReturn("/lms/user/users");
		SessionCache.removeAllSessions();
		Session s = new Session();
		s.setSessionId(SESSION_ID_1);
		SessionCache.addSessionToCache(s);
		filter.verifySessionAndAuthorize(httpRequest);
	}

	@Test
	public void testGetResponseStatus() {
		GenericOutput nullOutput = filter.getResponseStatus(null);
		assertEquals("", ErrorType.SUCCESS, nullOutput.getOutputStatus().getErrorType());
		assertNull("", nullOutput.getData());

		when(obj.get("errorType")).thenReturn(null);
		GenericOutput emptyOutput = filter.getResponseStatus(new ObjectNode(null));
		assertEquals("", ErrorType.SUCCESS, emptyOutput.getOutputStatus().getErrorType());
		assertNotNull("", emptyOutput.getData());
	}

	//@Test
	/*public void testGetResponseStatus_error() {
		ObjectNode et = new ObjectNode(null);
		ObjectNode em = new ObjectNode(null);
		ObjectNode er = new ObjectNode(null);
		ObjectNode erc = new ObjectNode(null);
		when(obj.get("errorType")).thenReturn(et);
		when(obj.get("errorMsg")).thenReturn(em);
		when(obj.get("errorReason")).thenReturn(er);
		when(obj.get("errorReasonCode")).thenReturn(erc);
		when(et.asText()).thenReturn("FAILURE");
		when(em.asText()).thenReturn("Error Message");
		when(er.asText()).thenReturn("Error Reason");
		when(erc.asText()).thenReturn("Reason Code");
		GenericOutput output = filter.getResponseStatus(new ObjectNode(null));
		assertEquals("", ErrorType.SUCCESS, output.getOutputStatus().getErrorType());
		assertNull("", output.getData());
	}*/

	@Test
	public void testGetNode() throws IOException {
		JsonNode nullNode = filter.getJsonNode(null);
		JsonNode emptyNode = filter.getJsonNode("");
		String json1 = "{\"loginId\":\"admin\",\"password\":\"Admin@123\"}";
		JsonNode node = filter.getJsonNode(json1);
		assertNull("GetNode failed", nullNode);
		assertNull("GetNode failed", emptyNode);
		assertNotNull("GetNode failed", node);
	}

	@Test(expected = IOException.class)
	public void testGetNode_Exception() throws IOException {
		String json1 = "lms";
		filter.getJsonNode(json1);
	}

	@Test
	public void testGetBytes() throws JsonProcessingException {
		GenericOutput response = new GenericOutput();
		response.setData("Hello World");
		String serialized = new ObjectMapper().writeValueAsString(response);
		byte[] baExpected = serialized.getBytes();
		byte[] ba = filter.getBytes(response);
		assertEquals("Get bytes failed", new String(baExpected), new String(ba));
	}

	@Test
	public void testMaskPassword() {
		String json1 = "{\"loginId\":\"admin\",\"password\":\"Admin@123\"}";
		String masked1 = filter.maskPassword(json1);
		assertTrue("Mask password failed", masked1.contains("\"password\":\"****\""));

		String json2 = "{\"loginId\":\"admin\",\"domain\":\"Password\"}";
		String masked2 = filter.maskPassword(json2);
		assertTrue("Mask password failed", !masked2.contains("****"));
	}
}
