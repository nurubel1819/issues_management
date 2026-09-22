package com.example.issues_management.auth.repository;

import com.example.issues_management.auth.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
	List<UserRole> findByUserId(Long userId);
	Optional<UserRole> findByUserIdAndRoleId(Long userId, Long roleId);

	@Modifying
	@Query("DELETE FROM UserRole ur WHERE ur.user.id = :userId")
	void deleteByUserId(@Param("userId") Long userId);

	void deleteByUserIdAndRoleId(Long userId, Long roleId);
}
