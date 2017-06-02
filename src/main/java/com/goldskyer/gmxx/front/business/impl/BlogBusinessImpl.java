package com.goldskyer.gmxx.front.business.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import com.goldskyer.common.exceptions.BusinessException;
import com.goldskyer.core.dto.EnvParameter;
import com.goldskyer.core.entities.Blog;
import com.goldskyer.core.entities.Menu;
import com.goldskyer.core.service.BlogService;
import com.goldskyer.core.service.CachedMenuService;
import com.goldskyer.gmxx.common.dtos.CreateTaskDto;
import com.goldskyer.gmxx.common.entities.WorkFlowTask;
import com.goldskyer.gmxx.common.entities.WorkFlowTemplate;
import com.goldskyer.gmxx.common.service.WorkFlowService;
import com.goldskyer.gmxx.front.business.BlogBusiness;
import com.goldskyer.gmxx.front.business.dto.BlogListModel;
import com.goldskyer.gmxx.workflow.service.WorkflowTemplateService;

@Service("blogBusiness")
public class BlogBusinessImpl implements BlogBusiness{
	@Autowired
	private WorkFlowService workFlowService;
	@Autowired
	private WorkflowTemplateService workflowTemplateService;
	@Autowired
	private BlogService blogService;
	@Autowired
	@Qualifier("cachedMenuService")
	private CachedMenuService cachedMenuService;
	
	public BlogListModel buildCommonBlogListModel(ModelAndView mv, String blogType, Integer pageNum, Integer pageSize)
	{
		BlogListModel blogListModel = new BlogListModel();
		if(mv==null || StringUtils.isBlank(blogType) )
		{
			throw new BusinessException("构建Blog公共数据模型异常");
		}
		if(pageNum==null)
		{
			pageNum=1;
		}
		Menu menu = cachedMenuService.queryMenuByName(blogType);
		mv.addObject("mainMenu", cachedMenuService.getMainMenu());
		mv.addObject("menu", menu);
		//BlogBusiness blogBusiness = SpringContextHolder.getBean("blogBusiness");
		List<Blog> blogs = queryBlogsWithNoContent(blogType, null,
				(pageNum - 1) * pageSize, pageSize);
		formatBlogs(blogs);
		long count = blogService.countTotalBlogs(blogType, null);
		blogListModel.setCount(count);
		mv.addObject("blogType", blogType);
		if(count>0)
		{
			mv.addObject("blogs", blogs);
		}
		mv.addObject("blogListModel", blogListModel);
		return blogListModel;
	}

	@Cacheable(value =
	{ "blog" }, key = "T(com.goldskyer.core.dto.EnvParameter).get().getAccountId().concat(#type).concat(#start).concat(#limit)")
	public List<Blog> queryBlogsWithNoContent(String type, String subType, Integer start, Integer limit)
	{
		return blogService.queryBlogsWithNoContent(type, subType, start, limit);
	}

	@Cacheable(value =
	{ "blog" }, key = "T(com.goldskyer.core.dto.EnvParameter).get().getAccountId().concat(#type).concat(#start).concat(#limit).concat('types')")
	public List<Blog> queryBlogsWithNoContentByTypes(String type, Integer start, Integer limit)
	{
		List<String> types = cachedMenuService.queryAllTypes(type, EnvParameter.get().getDomain());
		return blogService.queryBlogsWithNoContent(types, start, limit);
	}
	public void formatBlogs(List<Blog> blogs)
	{
		//设置缩略图
		//		for (Blog blog : blogs)
		//		{
		//			if (StringUtils.isNotBlank(blog.getCoverImage()))
		//			{
		//				blog.setCoverImage(
		//						blog.getCoverImage().replace("storage.goldskyer.com", "storage.goldskyer.com/thumb"));
		//			}
		//		}
	}

	@Transactional
	public String addBlog(Blog blog, String accountId)
	{
		blog.setAccountId(accountId);
		blog.setAuditStatus("审核通过");
		String blogId = blogService.addBlog(blog);
		WorkFlowTemplate template = workflowTemplateService.queryTemplateByType("内容审核", blog.getType());
		if (template != null)
		{
			CreateTaskDto createTaskDto = new CreateTaskDto();
			createTaskDto.setAccountId(accountId);
			createTaskDto.setObjectId(blogId);
			createTaskDto.setObjectType("内容审核");
			createTaskDto.setSubType(blog.getType());
			createTaskDto.setTaskName(blog.getTitle());
			workFlowService.createTask(createTaskDto);
			WorkFlowTask task = workFlowService.queryTaskByObjectId(blogId);
			Blog blog2 = blogService.queryBlog(blogId);
			blog2.setAuditStatus(task.getStatus());
			blogService.modifyBlog(blog2);
		}
		return blogId;
	}


}
