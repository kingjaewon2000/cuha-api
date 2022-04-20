package com.cju.cuhaapi.member.domain.repository;

import com.cju.cuhaapi.member.domain.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    boolean existsByFilename(String filename);

    @Query("select p from Profile p where p.filename = 'no-profile.gif'")
    Profile defaultProfile();
}
