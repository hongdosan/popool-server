package kr.co.popoolserver.infrastructure.shared;

import kr.co.popoolserver.user.entity.UserEntity;

public class UserThreadLocal {

    private static final ThreadLocal<UserEntity> userThreadLocal;

    static {
        userThreadLocal = new ThreadLocal<>();
    }

    public static UserEntity get(){
        return userThreadLocal.get();
    }

    public static void set(UserEntity userEntity){
        userThreadLocal.set(userEntity);
    }

    public static void remove(){
        userThreadLocal.remove();
    }
}
