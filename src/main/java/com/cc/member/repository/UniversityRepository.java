package com.cc.member.repository;

import com.cc.member.domain.University;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UniversityRepository extends JpaRepository<University, String> {
}
