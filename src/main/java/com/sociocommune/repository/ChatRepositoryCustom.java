package com.sociocommune.repository;
import java.util.List;
import com.sociocommune.model.*;
public interface ChatRepositoryCustom {

	public List<Chat> findChats(String sen,String rec);
    
}