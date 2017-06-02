package com.goldskyer.gmxx.front.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.goldskyer.gmxx.BaseController;

@Controller
@RequestMapping("/front/class/")
public class ClassCommunityController extends BaseController
{
	@RequestMapping("/view.htm")
	public ModelAndView list(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView("/front/class/list");
		blogBusiness.buildCommonBlogListModel(mv, "班级社区", 1, 200);
		return mv;
	}
}
