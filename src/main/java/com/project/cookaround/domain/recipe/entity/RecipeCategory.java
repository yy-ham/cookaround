package com.project.cookaround.domain.recipe.entity;

public enum RecipeCategory {

    KOREAN("한식"),
    WESTERN("양식"),
    JAPANESE("일식"),
    CHINESE("중식"),
    DESSERT("디저트"),
    OTHER("기타")
    ;

    private String description;

    RecipeCategory(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    // String -> Enum 변환 메서드
    public static RecipeCategory fromString(String value) {
        for (RecipeCategory category : RecipeCategory.values()) {
            if (category.name().equalsIgnoreCase(value)) {
                return category;
            }
        }
        throw new IllegalArgumentException(value + "카테고리를 찾을 수 없습니다.");
    }
}
