package com.rapps.utility.learning.lms.persistence.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.dao.EmptyResultDataAccessException;

import com.rapps.utility.learning.lms.enums.IssueStatusEnum;
import com.rapps.utility.learning.lms.exception.LmsException;
import com.rapps.utility.learning.lms.model.IssueModel;
import com.rapps.utility.learning.lms.model.RequestModel;
import com.rapps.utility.learning.lms.persistence.bean.Issue;
import com.rapps.utility.learning.lms.persistence.bean.Request;
import com.rapps.utility.learning.lms.persistence.repository.IssueRepository;
import com.rapps.utility.learning.lms.persistence.repository.RequestRepository;

@RunWith(MockitoJUnitRunner.class)
public class TestIssueService {

	@InjectMocks
	IssueService service;

	@Mock
	IssueRepository repo;
	@Mock
	RequestRepository requestRepository;

	@Test
	public void testSaveIssue() {
		service.saveIssue(new IssueModel());
	}

	@Test
	public void testUpdateIssue() {
		service.updateIssue(new IssueModel());
	}

	@Test
	public void testFindByBookIdAndUserId_NotNull() {
		when(repo.findByBookIdAndUserIdAndStatus("b1", "u1", IssueStatusEnum.ISSUED)).thenReturn(new Issue());
		IssueModel model = service.findIssueByBookIdAndUserIdAndStatus("b1", "u1", IssueStatusEnum.ISSUED);
		assertNotNull("", model);
	}

	@Test
	public void testFindByBookIdAndUserId_Null() {
		when(repo.findByBookIdAndUserIdAndStatus("b1", "u1", IssueStatusEnum.ISSUED)).thenReturn(null);
		IssueModel model = service.findIssueByBookIdAndUserIdAndStatus("b1", "u1", IssueStatusEnum.ISSUED);
		assertNull("", model);
	}

	@Test
	public void testSaveRequest() {
		service.saveRequest(new RequestModel());
	}

	@Test
	public void testUpdateRequest() {
		service.updateRequest(new RequestModel());
	}

	@Test
	public void testfindRequestByBookIdAndUserId_Null() {
		RequestModel model = service.findRequestByBookIdAndUserId("b1", "u1");
		assertNull(model);
	}

	@Test
	public void testfindRequestByBookIdAndUserId_NotNull() {
		Request request = new Request();
		request.setBookId("b1");
		request.setRequestDate(System.currentTimeMillis());
		request.setRequestId("r1");
		request.setUserId("u1");
		when(requestRepository.findByBookIdAndUserId("b1", "u1")).thenReturn(request);
		RequestModel model = service.findRequestByBookIdAndUserId("b1", "u1");
		assertNotNull(model);
	}

	@Test
	public void testFindOldestRequesterForABook_Null() {
		RequestModel model = service.findOldestRequesterForABook("b1");
		assertNull(model);
	}

	@Test
	public void testFindOldestRequesterForABook_NotNull() {
		Request request = new Request();
		request.setBookId("b1");
		request.setRequestDate(System.currentTimeMillis());
		request.setRequestId("r1");
		request.setUserId("u1");
		when(requestRepository.findTop1ByBookIdOrderByRequestDate("b1")).thenReturn(request);
		RequestModel model = service.findOldestRequesterForABook("b1");
		assertNotNull(model);
	}

	@Test(expected = LmsException.class)
	public void testDeleteRequestById_Error() throws LmsException {
		doThrow(new EmptyResultDataAccessException(1)).when(requestRepository).deleteById("r1");
		service.deleteRequestById("r1");
	}

	@Test
	public void testDeleteRequestById_Success() throws LmsException {
		service.deleteRequestById("r1");
	}
}
