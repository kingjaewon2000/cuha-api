package com.cju.cuhaapi.repository;

import com.cju.cuhaapi.entity.member.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}