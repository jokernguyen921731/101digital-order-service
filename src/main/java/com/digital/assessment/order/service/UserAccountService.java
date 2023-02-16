package com.digital.assessment.order.service;

import com.digital.assessment.order.domain.LoginData;
import com.digital.assessment.order.web.rest.request.user.UserCreateRequest;
import com.digital.assessment.order.web.rest.request.user.UserLoginRequest;

import javax.servlet.http.HttpServletRequest;

public interface UserAccountService {
    Void create(UserCreateRequest request);
    LoginData login(HttpServletRequest httpServletRequest, UserLoginRequest request) throws Exception;
}
