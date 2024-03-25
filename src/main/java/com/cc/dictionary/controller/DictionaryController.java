package com.cc.dictionary.controller;

import com.cc.dictionary.dto.AddDictionaryCategoryDto;
import com.cc.dictionary.service.DictionaryService;
import com.cc.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "dictionary", description = "사전방 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dictonaries")
public class DictionaryController {

    private final DictionaryService dictionaryService;

    @Operation(summary = "사전 카테고리 만들기")
    @PostMapping("/create-category")
    public Response addDictionaryCategory(@Valid @RequestBody AddDictionaryCategoryDto addDictionaryCategoryDto) {
        dictionaryService.addDictionaryCategory(addDictionaryCategoryDto.title());
        return Response.success();
    }

    @Operation(summary = "사전 카테고리 가져오기")
    @GetMapping("/get-category")
    public Response getDictionaryCategory() {
        return Response.success(dictionaryService.getDictionaryCategory());
    }
}
