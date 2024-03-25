package com.cc.dictionary.service;

import com.cc.dictionary.domain.DictionaryCategory;
import com.cc.dictionary.repository.DictionaryCategoryRepository;
import com.cc.exception.CategoryTitleAlreadyExistsException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DictionaryService {

    private final DictionaryCategoryRepository dictionaryCategoryRepository;

    @Transactional
    public void addDictionaryCategory(String title) {
        if (dictionaryCategoryRepository.existsByTitle(title)) {
            throw new CategoryTitleAlreadyExistsException();
        }
        dictionaryCategoryRepository.save(DictionaryCategory.builder().title(title).build());
    }

    public List<DictionaryCategory> getDictionaryCategory() {
        return dictionaryCategoryRepository.findAll();
    }
}
