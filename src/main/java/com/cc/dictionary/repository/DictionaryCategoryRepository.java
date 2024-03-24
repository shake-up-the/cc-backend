package com.cc.dictionary.repository;

import com.cc.dictionary.domain.DictionaryCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DictionaryCategoryRepository extends JpaRepository<DictionaryCategory, Long> {
    boolean existsByTitle(String title);
}
