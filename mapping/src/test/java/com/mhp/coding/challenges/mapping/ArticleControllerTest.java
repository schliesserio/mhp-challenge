package com.mhp.coding.challenges.mapping;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import com.mhp.coding.challenges.mapping.controllers.ArticleController;
import com.mhp.coding.challenges.mapping.mappers.ArticleMapper;
import com.mhp.coding.challenges.mapping.repositories.ArticleRepository;
import com.mhp.coding.challenges.mapping.services.ArticleService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ArticleControllerTest {

	final String BASE_URL = "/article";

	@Mock
	private ArticleRepository repo;

	@Autowired
	private ArticleMapper mapper;

	@Autowired
	private ArticleService service;

	@Autowired
	private ArticleController controller;

	@Autowired
	private MockMvc mvc;

	@Test
	public void testGetArticle() throws Exception {
		ResultActions resultActions = invokeAllArticles();
		resultActions.andExpect(status().is(200));
		resultActions.andExpect(jsonPath("$", hasSize(5)));
		resultActions.andExpect(jsonPath("$[0].id", is(1001)));
		resultActions.andExpect(jsonPath("$[1].id", is(2002)));
		resultActions.andExpect(jsonPath("$[1].title", is("Article Nr.: 2002")));
		resultActions.andExpect(jsonPath("$[1].description", is("Article Description 2002")));
		resultActions.andExpect(jsonPath("$[1].author", is("Max Mustermann")));
		resultActions.andExpect(jsonPath("$[1].blocks", hasSize(6)));
	}

	@Test
	public void testGetArticleById() throws Exception {
		ResultActions resultActions = invokeOneArticle(2002L);
		resultActions.andExpect(status().is(200));
		resultActions.andExpect(jsonPath("$.id", is(2002)));
		resultActions.andExpect(jsonPath("$.title", is("Article Nr.: 2002")));
		resultActions.andExpect(jsonPath("$.description", is("Article Description 2002")));
		resultActions.andExpect(jsonPath("$.author", is("Max Mustermann")));
		resultActions.andExpect(jsonPath("$.blocks", hasSize(6)));
	}

	@Test
	public void testGetArticleByIdNoArticleFound() throws Exception {
		when(repo.findBy(404L)).thenReturn(null);
		service = new ArticleService(repo, mapper);
		ReflectionTestUtils.setField(controller, "articleService", service);

		ResultActions resultActions = invokeOneArticle(404L);
		resultActions.andExpect(status().is(404));
	}

	private ResultActions invokeAllArticles() throws Exception {
		return mvc.perform(get(BASE_URL).accept(MediaType.APPLICATION_JSON));
	}

	private ResultActions invokeOneArticle(Long id) throws Exception {
		return mvc.perform(get(BASE_URL + "/" + id).accept(MediaType.APPLICATION_JSON));
	}
}
