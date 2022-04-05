package com.cju.cuhaapi.repository;

import com.cju.cuhaapi.repository.entity.member.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    boolean existsByFilename(String filename);
}
