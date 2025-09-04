package com.project.cookaround.domain.cookingtip.entity;

public enum CookingTipCategory {

    PREPARATION("손질법"),
    STORAGE("보관법"),
    COOKING("조리법")
    ;

    private String description;

    CookingTipCategory(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
