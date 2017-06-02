package com.goldskyer.gmxx.web.spring.controllers;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.goldskyer.common.exceptions.BusinessException;
import com.goldskyer.core.entities.Blog;
import com.goldskyer.core.entities.Comment;
import com.goldskyer.core.entities.Menu;
import com.goldskyer.core.entities.VoteUpLog;
import com.goldskyer.core.service.CommentService;
import com.goldskyer.core.service.VoteUpLogService;
import com.goldskyer.core.vo.Pagination;
import com.goldskyer.gmxx.common.constants.CmsConstant;
import com.goldskyer.gmxx.common.constants.GmxxConstant;
import com.goldskyer.gmxx.front.business.BlogBusiness;
import com.goldskyer.gmxx.front.business.dto.BlogListModel;
import com.goldskyer.gmxx.web.controllers.WebBaseController;

@Controller("web.spring.BlogController")
@RequestMapping("/web/spring/{module}")
public class BlogController extends WebBaseController
{

	@Autowired
	private VoteUpLogService voteUpLogService;
	
	@Autowired
	private CommentService commentService;

	@Autowired
	protected BlogBusiness blogBusiness;

	//	@RequestMapping("/list(view).htm")
	//	public ModelAndView list2(@PathVariable String view, @PathVariable String module, @RequestParam String blogType,
	//			@RequestParam(required = false) Integer pageNum)
	//	{
	//		if (pageNum == null)
	//		{
	//			pageNum = 1;
	//		}
	//		ModelAndView mv = new ModelAndView("/web/spring/" + module + "/list" + view);
	//		Menu cachedMenu = cachedMenuService.queryMenuByName("栏目管理");
	//		mv.addObject("cachedMenu", cachedMenu);
	//		//设置Size（优先级MOUDLE_PAGE_SZIE>PAGE_SIZE）
	//		Integer pageSize = 10;
	//		String typeIni = iniService.getIniValue(blogType + "_PAGE_SZIE");
	//		if (StringUtils.isNotBlank(typeIni))
	//		{
	//			pageSize = Integer.valueOf(typeIni);
	//		}
	//		else
	//		{
	//			pageSize = Integer.valueOf(iniService.getIniValue(GmxxConstant.PAGE_SIZE));
	//		}
	//
	//		List<Blog> blogs = blogService.queryBlogsWithNoContent(blogType, null, (pageNum - 1) * pageSize, pageSize);
	//		long count = blogService.countTotalBlogs(blogType, null);
	//
	//		Pagination pager = new Pagination(pageNum, count, pageSize,
	//				Integer.valueOf(iniService.getIniValue(GmxxConstant.PAGE_BTN_NUM)));
	//		mv.addObject("pager", pager);
	//		mv.addObject("blogs", blogs);
	//		mv.addObject("blogType", blogType);
	//		return mv;
	//
	//	}

	@RequestMapping("/view.htm")
	public ModelAndView detail(@RequestParam String id,@PathVariable String module,@RequestParam(required=false)String accountId)
	{
		ModelAndView mv = new ModelAndView("/web/spring/" + module + "/view");
		initCommonBlock(mv);

		initSideBar(mv);
		buildCommonBlogModel(mv,id);
		blogService.incViewCount(id);
		if(StringUtils.isNotBlank(accountId))
		{
			VoteUpLog voteUpLog = voteUpLogService.queryVoteUpLogbyId(accountId, id, "BLOG");
			if(voteUpLog!=null)
			{
				mv.addObject("voted", true);
			}
		}
		List<Comment> comments = commentService.queryCommentsByObject(id, "BLOG", 0, 100);
		mv.addObject("comments", comments);
		//获取前后也内容
		Blog blog = (Blog) mv.getModel().get("blog");
		Blog nextBlog = blogService.queryNextBlog(blog);
		Blog preBlog = blogService.queryPreBlog(blog);
		mv.addObject("nextBlog", nextBlog);
		mv.addObject("preBlog", preBlog);
		//面包屑
		log.info("crumbs:" + getCrumbs(blog.getType()));
		mv.addObject("crumbs", getCrumbs(blog.getType()));
		mv.addObject("schoolTitle", cmsService.getCmsContent(CmsConstant.SCHOOL_TITLE));
		mv.addObject("footerContact", cmsService.getCmsContent(CmsConstant.FOOTER_CONTACT));
		return mv;
	}
	
	

	/**
	 * 所有的资源型内容列表通用这个接口
	 *  /web/tv/list.htm 对应 /web/tv/list.jsp
	 *  /web/tv/list_image.htm /web/tv/list_image.jsp
	 * @param view
	 * @param module
	 * @param blogType
	 * @param pageNum
	 * @return
	 */
	@RequestMapping("/list{view}.htm")
	public ModelAndView list(@PathVariable String view, @PathVariable String module, @RequestParam String blogType,
			@RequestParam(required = false) Integer pageNum)
	{
		if (pageNum == null || pageNum == 0)
		{
			pageNum = 1;
		}
		ModelAndView mv = new ModelAndView("/web/spring/" + module + "/list" + view);
		initCommonBlock(mv);
		Integer pageSize = 10;
		String typeIni = iniService.getIniValue(blogType + "_PAGE_SZIE");
		if (StringUtils.isNotBlank(typeIni))
		{
			pageSize = Integer.valueOf(typeIni);
		}
		else
		{
			pageSize = Integer.valueOf(iniService.getIniValue(GmxxConstant.PAGE_SIZE));
		}
		BlogListModel blogListModel = blogBusiness.buildCommonBlogListModel(mv, blogType, pageNum, pageSize);
		//设置左侧菜单
		boolean isTab = false; //
		Integer menuDeep = getMenuDeep(blogType); //菜单深度
		Menu menu = cachedMenuService.queryMenuByName(blogType);
		if (menuDeep == 4)
		{
			mv.addObject("leftMenus", menu.getParent().getChildren());
		}
		else if (menuDeep == 3)
		{
			mv.addObject("leftMenus", menu.getChildren());
			if (menu.getChildren() == null || menu.getChildren().size() == 0)
			{
				List<Menu> tmp = new ArrayList<>();
				tmp.add(menu);
				mv.addObject("leftMenus", tmp); //如果三层菜单没有子节点，就显示当前节点列表
			}
			isTab = true;
		}
		List<List<Blog>> blogTabs = new ArrayList<>();
		List<String> tabNames = new ArrayList<>();
		if (isTab)
		{
			for (Menu m : (List<Menu>) mv.getModel().get("leftMenus"))
			{
				blogTabs.add(blogBusiness.queryBlogsWithNoContent(m.getName(), null, 0, 8));
				tabNames.add(m.getName());
			}
		}
		else
		{
			blogTabs.add(blogBusiness.queryBlogsWithNoContent(blogType, null, (pageNum - 1) * pageSize, pageSize));
			tabNames.add(blogType);

		}
		mv.addObject("blogTabs", blogTabs);
		mv.addObject("tabNames", tabNames);
		//
		mv.addObject("crumbs", getCrumbs(blogType));
		//加入分页标签
		if (!isTab)
		{
			Pagination pager = new Pagination(pageNum, blogListModel.getCount(), pageSize,
					Integer.valueOf(iniService.getIniValue(GmxxConstant.PAGE_BTN_NUM)));
			mv.addObject("pager", pager);
		}
		mv.addObject("schoolTitle", cmsService.getCmsContent(CmsConstant.SCHOOL_TITLE));
		mv.addObject("footerContact", cmsService.getCmsContent(CmsConstant.FOOTER_CONTACT));
		return mv;
	}
	
	

	
	protected  void buildCommonBlogModel(ModelAndView mv,String blogId)
	{
		Blog blog = blogService.queryBlog(blogId);
		if(blog==null)
		{
			throw new BusinessException("当前请求记录不存在");
		}
		Menu menu = cachedMenuService.queryMenuByName(blog.getType());
		mv.addObject("mainMenu", cachedMenuService.getMainMenu());
		mv.addObject("menu", menu);
		mv.addObject("blog",blog);
	}
	
	
	
	
	
}
