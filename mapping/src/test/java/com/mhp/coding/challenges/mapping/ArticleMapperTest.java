package com.mhp.coding.challenges.mapping;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.mhp.coding.challenges.mapping.mappers.ArticleMapper;
import com.mhp.coding.challenges.mapping.models.db.Article;
import com.mhp.coding.challenges.mapping.models.db.Image;
import com.mhp.coding.challenges.mapping.models.db.ImageSize;
import com.mhp.coding.challenges.mapping.models.db.blocks.ArticleBlock;
import com.mhp.coding.challenges.mapping.models.db.blocks.GalleryBlock;
import com.mhp.coding.challenges.mapping.models.db.blocks.TextBlock;
import com.mhp.coding.challenges.mapping.models.dto.ArticleDto;
import com.mhp.coding.challenges.mapping.models.dto.blocks.ArticleBlockDto;
import com.mhp.coding.challenges.mapping.models.dto.blocks.GalleryBlockDto;
import com.mhp.coding.challenges.mapping.models.dto.blocks.UnknownArticleBlockDto;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArticleMapperTest {

	private Set<ArticleBlock> block = new HashSet<ArticleBlock>();
	private Article article = new Article();
	private ArticleDto articleDto;
	private ArticleMapper mapper = new ArticleMapper();

	final String articleDescription = "Testbeschreibung der Mapping Challenge";
	final String articleAuthor = "Philipp Schliesser";
	final Long articleId = 9999L;
	final String articleTitle = "Titel des Artikels";

	@Before
	public void setUp() {
		TextBlock text = new TextBlock();
		text.setText("Testtext");
		text.setSortIndex(2);

		Image image1 = new Image();
		Image image2 = new Image();

		image1.setUrl("https://test.com/image.png");
		image1.setImageSize(ImageSize.LARGE);
		image2.setUrl("https://test.com/image.png");
		image2.setImageSize(ImageSize.SMALL);

		List<Image> images = new ArrayList<Image>();
		images.add(image1);
		images.add(image2);

		GalleryBlock gallery = new GalleryBlock();
		gallery.setImages(images);
		gallery.setSortIndex(1);

		block.add(text);
		block.add(gallery);

		article.setBlocks(block);
		article.setAuthor(articleAuthor);
		article.setId(articleId);
		article.setDescription(articleDescription);
		article.setTitle(articleTitle);
	}

	@Test
	public void testMappingToArticleDto() {
		articleDto = mapper.map(article);
		assertTrue(articleDto.getAuthor() == articleAuthor);
		assertTrue(articleDto.getId() == articleId);
		assertTrue(articleDto.getDescription() == articleDescription);
		assertTrue(articleDto.getTitle() == articleTitle);
		assertTrue(articleDto.getBlocks().size() == block.size());
	}

	@Test
	public void testOrderArticleBlock() {
		articleDto = mapper.map(article);
		Collection<ArticleBlockDto> articleBlocks = articleDto.getBlocks();
		Iterator<ArticleBlockDto> iterator = articleBlocks.iterator();
		ArticleBlockDto previous = iterator.hasNext() ? iterator.next() : null;

		while (iterator.hasNext()) {
			ArticleBlockDto next = iterator.next();
			assertTrue(previous.getSortIndex() <= next.getSortIndex());
			previous = next;
		}
	}

	@Test
	public void testMappingArticleBlock() {
		articleDto = mapper.map(article);
		Collection<ArticleBlockDto> articleBlocks = articleDto.getBlocks();
		Iterator<ArticleBlockDto> iterator = articleBlocks.iterator();
		assertTrue(iterator.next() instanceof GalleryBlockDto);
		assertTrue(iterator.next() instanceof com.mhp.coding.challenges.mapping.models.dto.blocks.TextBlock);
	}

	@Test
	public void testUnknownArticleBlock() {
		ArticleBlock unknown = new FakeArticleBlock();
		block.add(unknown);
		articleDto = mapper.map(article);
		Collection<ArticleBlockDto> articleBlocks = articleDto.getBlocks();
		Iterator<ArticleBlockDto> iterator = articleBlocks.iterator();
		Boolean foundUnkownArticleBlock = false;
		while (iterator.hasNext()) {
			ArticleBlockDto current = iterator.next();
			if (current instanceof UnknownArticleBlockDto) {
				foundUnkownArticleBlock = true;
				UnknownArticleBlockDto warningArticleBlock = (UnknownArticleBlockDto) current;
				assertTrue(warningArticleBlock.getMessage()
						.equals("Something went wrong! There is no dto mapping for FakeArticleBlock"));
				break;
			}
		}
		assertTrue(foundUnkownArticleBlock);
	}

	private static class FakeArticleBlock extends ArticleBlock {
	}

}
