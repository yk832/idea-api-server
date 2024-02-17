package com.ideate.ideaapiserver.repository;

import com.ideate.ideaapiserver.entity.LoginHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LoginHistoryRepository extends MongoRepository<LoginHistory, String> {
}
