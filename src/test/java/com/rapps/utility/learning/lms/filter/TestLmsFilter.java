package com.rapps.utility.learning.lms.filter;

import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.servlet.FilterChain;
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
import com.rapps.utility.learning.lms.exception.LmsException.ErrorType;
import com.rapps.utility.learning.lms.helper.SessionMgmtHelper;
import com.rapps.utility.learning.lms.model.GenericOutput;

import junit.framework.TestCase;

@RunWith(MockitoJUnitRunner.class)
public class TestLmsFilter extends TestCase {

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
