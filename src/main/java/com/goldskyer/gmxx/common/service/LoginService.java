package com.goldskyer.gmxx.common.service;

import javax.servlet.http.HttpServletRequest;

import com.goldskyer.core.dto.JsonData;
import com.goldskyer.core.entities.Account;

public interface LoginService
{
	public JsonData loginSessionSave(Account account, HttpServletRequest request);

}
