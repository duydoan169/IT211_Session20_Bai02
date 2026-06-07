package org.example.bai2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.bai2.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}