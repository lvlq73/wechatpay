package com.wechat.test;

import com.wechat.interfaces.IOrderAfterSuccess;
import com.wechat.interfaces.IPayAfterSuccess;

import java.util.HashMap;

public class TestAfterSuccsess implements IOrderAfterSuccess,IPayAfterSuccess {
    public void afterSuccess(HashMap<String, Object> result) {
        System.out.println(result.get("test"));
    }

    public void afterPaySuccess(HashMap<String, Object> result) {
        System.out.println(result.get("test"));
    }
}
