package com.mhp.coding.challenges.mapping.mappers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.mhp.coding.challenges.mapping.Application;
import com.mhp.coding.challenges.mapping.models.db.Article;
import com.mhp.coding.challenges.mapping.models.db.blocks.ArticleBlock;
import com.mhp.coding.challenges.mapping.models.db.blocks.GalleryBlock;
import com.mhp.coding.challenges.mapping.models.dto.ArticleDto;
import com.mhp.coding.challenges.mapping.models.dto.blocks.ArticleBlockDto;
import com.mhp.coding.challenges.mapping.models.dto.blocks.GalleryBlockDto;
import com.mhp.coding.challenges.mapping.models.dto.blocks.ImageBlock;
import com.mhp.coding.challenges.mapping.models.dto.blocks.TextBlock;
import com.mhp.coding.challenges.mapping.models.dto.blocks.UnknownArticleBlockDto;
import com.mhp.coding.challenges.mapping.models.dto.blocks.VideoBlock;

@Component
public class ArticleMapper {
	
    private static final Logger LOGGER=LoggerFactory.getLogger(Application.class);

	private ModelMapper	m = new ModelMapper();
		
    public ArticleDto map(Article article){
    	ArticleDto articleDto = m.map(article, ArticleDto.class);
    	articleDto.setBlocks(mapArticleBlock(article.getBlocks()));
    	return articleDto;
    }

    public Article map(ArticleDto articleDto) {
        // Nicht Teil dieser Challenge.
        return new Article();
    }
   
    private Collection<ArticleBlockDto> mapArticleBlock(Collection<ArticleBlock> articleBlock){
		List<ArticleBlockDto> result = new ArrayList<ArticleBlockDto>();
    	
    	for(ArticleBlock block : articleBlock) {
    		switch(block.getClass().getSimpleName()) {
    		case "TextBlock":
    			com.mhp.coding.challenges.mapping.models.db.blocks.TextBlock textBlock = (com.mhp.coding.challenges.mapping.models.db.blocks.TextBlock) block;
    			TextBlock text = m.map(textBlock,TextBlock.class);
    			result.add(text);
    			break;
    		case "VideoBlock":
    			com.mhp.coding.challenges.mapping.models.db.blocks.VideoBlock videoBlock = (com.mhp.coding.challenges.mapping.models.db.blocks.VideoBlock) block;
    			VideoBlock video = m.map(videoBlock,VideoBlock.class);
    			result.add(video);
    			break;
    		case "GalleryBlock":
    			GalleryBlock galleryBlock = (GalleryBlock) block;
    			GalleryBlockDto gallery = m.map(galleryBlock,GalleryBlockDto.class);
    			result.add(gallery);
    			break;
    		case "ImageBlock":
    			com.mhp.coding.challenges.mapping.models.db.blocks.ImageBlock imageBlock = (com.mhp.coding.challenges.mapping.models.db.blocks.ImageBlock) block;
    			ImageBlock image = m.map(imageBlock,ImageBlock.class);
    			result.add(image);
    			break;
    		default:
    			LOGGER.warn("Dto mapping of type ["+block.getClass()+"] is missing");
    			UnknownArticleBlockDto unknownArticleBlock = new UnknownArticleBlockDto();
    			unknownArticleBlock.setMessage("Something went wrong! There is no dto mapping for "+block.getClass().getSimpleName());
    			result.add(unknownArticleBlock);
    		}
    	}
    	Collections.sort(result);
    	return (Collection<ArticleBlockDto>) result;
    	   	
    }
}
