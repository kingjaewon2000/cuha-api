package com.cju.cuhaapi.domain.member.repository;

import com.cju.cuhaapi.domain.member.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    boolean existsByFilename(String filename);
}
