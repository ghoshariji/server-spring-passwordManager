package com.server.server.dbinheritence;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.server.server.model.UserModel;

public interface UserRepository extends MongoRepository<UserModel, String> {
  boolean existsByEmail(String email);

  UserModel findByEmail(String email);

}
