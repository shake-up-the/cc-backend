package com.cc.dictionary.repository;

import com.cc.dictionary.domain.DictionaryPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DictionaryPostRepository  extends JpaRepository<DictionaryPost, Long> {

    List<DictionaryPost> findByCategoryId(Long categoryId);
}
