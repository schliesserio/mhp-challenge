package com.mhp.coding.challenges.mapping.models.dto.blocks;

public class ArticleBlockDto implements Comparable<ArticleBlockDto>{
    private int sortIndex;

    public int getSortIndex() {
        return sortIndex;
    }

    public void setSortIndex(int sortIndex) {
        this.sortIndex = sortIndex;
    }

	@Override
	public int compareTo(ArticleBlockDto arg0) {
		if(this.sortIndex > arg0.sortIndex) {
			return 1;
		}else if(this.sortIndex < arg0.sortIndex) {
			return -1;
		}else {
			return 0;
		}
	}
}
