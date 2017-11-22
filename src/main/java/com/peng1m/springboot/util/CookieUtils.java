package com.peng1m.springboot.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Utility methods to handle the cookie.
 */
public class CookieUtils {
    public static void setSessionCookie(HttpServletResponse response, String cookieName,
                                        String domain, String cookieValue, int maxAge) {
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setDomain(domain);
        maxAge = maxAge > 0 ? maxAge : 0;
        cookie.setMaxAge(maxAge);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public static void clearSessionCookie(HttpServletResponse response, String cookieName,
                                          String domain) {
        Cookie cookie = new Cookie(cookieName, "");
        cookie.setMaxAge(0);
        cookie.setDomain(domain);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public static void refreshSessionCookie(HttpServletRequest request, HttpServletResponse response,
                                            String cookieName, String domain, int maxAge) {
        Cookie cookie = getSessionCookie(request, cookieName);
        if (cookie != null) {
            cookie.setMaxAge(maxAge);
            cookie.setDomain(domain);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
    }

    public static String getSessionCookieValue(HttpServletRequest request, String cookieName) {
        Cookie cookie = getSessionCookie(request, cookieName);
        if (cookie != null) {
            return cookie.getValue();
        }
        return null;
    }

    private static Cookie getSessionCookie(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                if (cookieName.equals(cookies[i].getName())) {
                    return cookies[i];
                }
            }
        }
        return null;
    }
}
