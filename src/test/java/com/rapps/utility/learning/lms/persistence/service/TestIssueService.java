package com.rapps.utility.learning.lms.persistence.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.rapps.utility.learning.lms.enums.IssueStatusEnum;
import com.rapps.utility.learning.lms.model.IssueModel;
import com.rapps.utility.learning.lms.persistence.bean.Issue;
import com.rapps.utility.learning.lms.persistence.repository.IssueRepository;

@RunWith(MockitoJUnitRunner.class)
public class TestIssueService {

	@InjectMocks
	IssueService service;

	@Mock
	IssueRepository repo;

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
		IssueModel model = service.findByBookIdAndUserIdAndStatus("b1", "u1", IssueStatusEnum.ISSUED);
		assertNotNull("", model);
	}

	@Test
	public void testFindByBookIdAndUserId_Null() {
		when(repo.findByBookIdAndUserIdAndStatus("b1", "u1", IssueStatusEnum.ISSUED)).thenReturn(null);
		IssueModel model = service.findByBookIdAndUserIdAndStatus("b1", "u1", IssueStatusEnum.ISSUED);
		assertNull("", model);
	}
}
