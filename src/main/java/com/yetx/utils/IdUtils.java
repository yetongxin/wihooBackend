package com.yetx.utils;

import java.util.UUID;

public class IdUtils {
    public static String getNewId(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
