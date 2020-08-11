package com.sociocommune.repository;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.sociocommune.model.Chat;
public interface ChatRepository extends MongoRepository<Chat, String>,ChatRepositoryCustom {
    
}

