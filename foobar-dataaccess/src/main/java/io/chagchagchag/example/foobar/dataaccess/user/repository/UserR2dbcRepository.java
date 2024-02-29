package io.chagchagchag.example.foobar.dataaccess.user.repository;

import io.chagchagchag.example.foobar.dataaccess.user.entity.UserEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface UserR2dbcRepository extends R2dbcRepository<UserEntity, Long> {
}
