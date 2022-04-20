package com.cju.cuhaapi.member.domain.repository;

import com.cju.cuhaapi.member.domain.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoleRepository extends JpaRepository<Role, Long> {


    @Query("select r from Role r where r.role = 'ROLE_MEMBER'")
    Role defaultRole();
}