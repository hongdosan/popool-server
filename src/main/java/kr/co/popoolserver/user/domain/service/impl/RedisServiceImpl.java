package kr.co.popoolserver.user.domain.service.impl;

import kr.co.popoolserver.user.domain.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public String getValue(String key) {
        //TODO Get Value
        return null;
    }

    @Override
    public void createData(String key, String value, long expired) {
        //TODO Create Data
    }

    @Override
    public void deleteData(String key) {
        //TODO Delete Data
    }

    @Override
    public void checkValue(String refreshToken, String redisToken) {
        //TODO Check Value
    }
}
