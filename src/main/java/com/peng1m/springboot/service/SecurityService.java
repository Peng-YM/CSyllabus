package com.peng1m.springboot.service;

public interface SecurityService {
    String findLoggedInUsername();
    void autoLogin(String username, String password);
}
