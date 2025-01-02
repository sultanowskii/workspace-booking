package org.wb.components.user;

import java.util.Optional;

import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends org.wb.components.common.Repository<User> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByRole(User.Role role);
}