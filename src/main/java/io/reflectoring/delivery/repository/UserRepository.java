package io.reflectoring.delivery.repository;

import io.reflectoring.delivery.modal.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Integer> {
    Optional<UserModel> findByEmailAndPassword(String email, String password);

    Optional<UserModel> findFirstByEmail(String email);
}