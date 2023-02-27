package kr.co.popoolserver.service;

import kr.co.popoolserver.error.exception.JwtTokenExpiredException;
import kr.co.popoolserver.error.model.ErrorCode;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    public String getValue(String key) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(key);
    }

    public void createData(String key,
                           String value,
                           long expired) {
        redisTemplate.opsForValue().set(key, value, expired, TimeUnit.MILLISECONDS);
    }

    public void deleteData(String key) {
        redisTemplate.delete(key);
    }

    public void checkValue(String refreshToken,
                           String redisToken) {
        if(!refreshToken.equals(redisToken)) {
            throw new JwtTokenExpiredException(ErrorCode.FAIL_EXPIRE);
        }
    }
}
