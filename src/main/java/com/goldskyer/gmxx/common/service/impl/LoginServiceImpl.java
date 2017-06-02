package com.goldskyer.gmxx.common.service.impl;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goldskyer.core.SpringContextHolder;
import com.goldskyer.core.dao.BaseDao;
import com.goldskyer.core.dto.EnvParameter;
import com.goldskyer.core.dto.JsonData;
import com.goldskyer.core.dto.UserDto;
import com.goldskyer.core.entities.Account;
import com.goldskyer.core.entities.Menu;
import com.goldskyer.core.enums.MenuModule;
import com.goldskyer.core.service.AccountService;
import com.goldskyer.core.service.MemoryService;
import com.goldskyer.core.vo.MenuVo;
import com.goldskyer.gmxx.acl.entities.Right;
import com.goldskyer.gmxx.acl.service.AclService;
import com.goldskyer.gmxx.common.helpers.MenuHelper;
import com.goldskyer.gmxx.common.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService
{
	@Autowired
	private BaseDao baseDao;
	@Autowired
	private AccountService accountService;
	@Autowired
	private MemoryService memoryService;
	public JsonData loginSessionSave(Account account, HttpServletRequest request)
	{
		account.setLastLoginDate(new Date());
		baseDao.modify(account); //更新登陆时间
		request.getSession().setAttribute("accountId", account.getId());
		request.getSession().setAttribute("username", account.getUsername());
		request.getSession().setAttribute("domain", account.getDomain());
		JsonData jsonData = JsonData.success();
		UserDto userDto = accountService.getUserDtoByUsername(account.getUsername());
		userDto.setPassword(StringUtils.EMPTY);
		jsonData.data.put("userDto", userDto);
		String token = UUID.randomUUID().toString();
		jsonData.data.put("token", account.getId() + "|" + token);
		jsonData.data.put("sId", request.getSession().getId());
		memoryService.add("token_" + account.getId(), account.getId() + "|" + token);

		request.getSession().setAttribute("account", account);
		//登录完成立即替换domain
		EnvParameter.get().setDomain(account.getDomain());

		AclService aclService = SpringContextHolder.getBean("aclService");
		Map<String, Menu> authMenuMap = aclService.queryAuthMenuMapByAccountId(account.getId());
		Map<String, Right> rightMap = aclService.queryRightMapByAccountId(account.getId());
		MenuVo roledMenu = MenuHelper.buildMenuVo(MenuModule.LANMU.getName(), authMenuMap);
		MenuVo roledEnMenu = MenuHelper.buildMenuVo(MenuModule.ENLANMU.getName(), authMenuMap);//授权管理的英文栏目
		String authMenuLog = "";
		for (Menu m : authMenuMap.values())
		{
			authMenuLog += m.getName() + ",";
		}


		//设置session
		request.getSession().setAttribute("rightMap", rightMap);
		//关联权限叠加
		//		if (rightMap.containsKey("BLOG_AUDIT"))
		//		{
		//			rightMap.put("WORKFLOW_AUDIT", new Right());
		//		}
		request.getSession().setAttribute("authMenuMap", authMenuMap);
		request.getSession().setAttribute("roledMenu", roledMenu); //有权限管理的栏目
		request.getSession().setAttribute("roledEnMenu", roledEnMenu); //有权限管理的英文栏目
		return jsonData;
	}

}
