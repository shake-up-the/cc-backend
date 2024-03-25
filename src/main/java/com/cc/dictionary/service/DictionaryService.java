package com.cc.dictionary.service;

import com.cc.auth.SecurityUtil;
import com.cc.dictionary.domain.DictionaryCategory;
import com.cc.dictionary.domain.DictionaryPost;
import com.cc.dictionary.dto.GetDictionaryCategoryDto;
import com.cc.dictionary.dto.GetDictionaryPostDto;
import com.cc.dictionary.repository.DictionaryCategoryRepository;
import com.cc.dictionary.repository.DictionaryPostRepository;
import com.cc.exception.CategoryNotFoundException;
import com.cc.exception.CategoryTitleAlreadyExistsException;
import com.cc.exception.MemberNotFoundException;
import com.cc.member.domain.Member;
import com.cc.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DictionaryService {

    private final DictionaryCategoryRepository dictionaryCategoryRepository;
    private final DictionaryPostRepository dictionaryPostRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void addDictionaryCategory(String title) {
        if (dictionaryCategoryRepository.existsByTitle(title)) {
            throw new CategoryTitleAlreadyExistsException();
        }
        dictionaryCategoryRepository.save(DictionaryCategory.builder().title(title).build());
    }

    public List<GetDictionaryCategoryDto> getDictionaryCategory() {
        return dictionaryCategoryRepository.findAll()
                .stream()
                .map(x -> new GetDictionaryCategoryDto(x.getId(), x.getTitle()))
                .toList();
    }

    @Transactional
    public void addDictionaryPost(Long categoryId, String title, String content) {
        DictionaryCategory dictionaryCategory = dictionaryCategoryRepository.findById(categoryId)
                .orElseThrow(CategoryNotFoundException::new);
        Member member = memberRepository.findByCustomId(SecurityUtil.getLoginId())
                .orElseThrow(MemberNotFoundException::new);

        DictionaryPost dictionaryPost = DictionaryPost.builder()
                .category(dictionaryCategory)
                .member(member)
                .title(title)
                .content(content)
                .build();
        dictionaryPostRepository.save(dictionaryPost);
    }

    public List<GetDictionaryPostDto> getDictionaryPost(Long categoryId) {
        return dictionaryPostRepository.findByCategoryId(categoryId)
                .stream()
                .map(x -> new GetDictionaryPostDto(x.getId(), x.getTitle(), x.getContent()))
                .toList();
    }
}
