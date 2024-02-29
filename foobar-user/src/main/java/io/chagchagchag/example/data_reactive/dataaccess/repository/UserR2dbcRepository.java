package io.chagchagchag.example.data_reactive.dataaccess.repository;

import io.chagchagchag.example.data_reactive.dataaccess.entity.UserEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface UserR2dbcRepository extends R2dbcRepository<UserEntity, Long> {
}
