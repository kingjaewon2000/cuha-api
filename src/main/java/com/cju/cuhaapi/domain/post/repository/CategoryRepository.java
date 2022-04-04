package com.cju.cuhaapi.domain.post.repository;

import com.cju.cuhaapi.domain.post.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByName(String name);
}
