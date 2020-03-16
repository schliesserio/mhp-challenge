package com.mhp.coding.challenges.mapping;

import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.server.ResponseStatusException;

import com.mhp.coding.challenges.mapping.mappers.ArticleMapper;
import com.mhp.coding.challenges.mapping.repositories.ArticleRepository;
import com.mhp.coding.challenges.mapping.services.ArticleService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArticleServiceTest {

	@Mock
	ArticleRepository repo;
	
	ArticleService service;
	
	@Before
	public void setUp() {
		when(repo.findBy(404L)).thenReturn(null);
		service = new ArticleService(repo, new ArticleMapper());
	}
	
	@Test(expected = ResponseStatusException.class)
	public void testResponseStatusExceptionIfArticleIdNotFound() {
		service.articleForId(404L);
	}

}
