package com.su.service;

import java.util.Map;

public interface SmsService {
    boolean sendSmsPhone(String phone, String codes, Map<String, String> map);

    boolean sendSmsEmail(String email, String code);
}
