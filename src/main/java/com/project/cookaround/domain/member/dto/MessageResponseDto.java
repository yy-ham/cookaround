package com.project.cookaround.domain.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MessageResponseDto {

    private String message;
    private String redirectUrl;

}