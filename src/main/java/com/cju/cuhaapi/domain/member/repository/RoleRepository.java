package com.cju.cuhaapi.domain.member.repository;

import com.cju.cuhaapi.domain.member.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}