package com.cc.dictionary.dto;

public record GetDictionaryPostDto(
        Long id,
        String title,
        String content
) {
}
