package com.ortoroverbasso.ortorovebasso.service.auth;

import java.util.Map;

public interface IOAuth2Service {
    String handleOAuth2User(Map<String, Object> attributes, String provider);
}
