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

    public static CookingTipCategory fromString(String value) {
        for (CookingTipCategory category : CookingTipCategory.values()) {
            if (category.name().equalsIgnoreCase(value)) {
                return category;
            }
        }
        throw new IllegalArgumentException(value + "카테고리를 찾을 수 없습니다.");
    }
}
