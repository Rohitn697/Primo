package com.example.primo.model;

import java.util.List;

public class AllCategories {
    String categoryTitle;
    Integer categoryId;
    private List<categoryItem> categoryItemList = null;

    public AllCategories(Integer categoryId,String categoryTitle,  List<categoryItem> categoryItemList) {
        this.categoryTitle = categoryTitle;
        this.categoryId = categoryId;
        this.categoryItemList = categoryItemList;
    }

    public List<categoryItem> getCategoryItemList() {
        return categoryItemList;
    }

    public void setCategoryItemList(List<categoryItem> categoryItemList) {
        this.categoryItemList = categoryItemList;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }


}
