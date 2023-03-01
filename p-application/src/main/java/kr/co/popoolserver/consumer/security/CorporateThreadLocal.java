package kr.co.popoolserver.consumer.security;

import kr.co.popoolserver.persistence.entity.CorporateEntity;

public class CorporateThreadLocal {
    private static final ThreadLocal<CorporateEntity> corporateThreadLocal;

    static {
        corporateThreadLocal = new ThreadLocal<>();
    }

    public static CorporateEntity get(){
        return corporateThreadLocal.get();
    }

    public static void set(CorporateEntity corporateEntity){
        corporateThreadLocal.set(corporateEntity);
    }

    public static void remove(){
        corporateThreadLocal.remove();
    }
}
