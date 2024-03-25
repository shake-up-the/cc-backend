package com.cc.dictionary.repository;

import com.cc.dictionary.domain.DictionaryPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DictionaryPostRepository  extends JpaRepository<DictionaryPost, Long> {
}
