package com.goldskyer.gmxx;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.goldskyer.core.controllers.CoreBaseController;
import com.goldskyer.core.service.BlogService;
import com.goldskyer.core.service.CachedMenuService;
import com.goldskyer.core.service.IniService;
import com.goldskyer.gmxx.front.business.BlogBusiness;


public class BaseController  extends CoreBaseController{
	protected static Log log = LogFactory.getLog(BaseController.class);
	@Autowired
	protected IniService iniService;
	@Autowired
	protected BlogService blogService;
	@Autowired
	@Qualifier("cachedMenuService")
	protected CachedMenuService cachedMenuService;
	
	@Autowired
	protected BlogBusiness blogBusiness;
	
	public static void main(String[] args) {
		System.out.println("xx");
	}


}
