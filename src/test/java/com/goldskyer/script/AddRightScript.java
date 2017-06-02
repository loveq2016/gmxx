package com.goldskyer.script;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import com.goldskyer.gmxx.acl.entities.Right;
import com.goldskyer.gmxx.acl.entities.RoleRight;
import com.goldskyer.gmxx.service.BaseTest;

public class AddRightScript extends BaseTest
{
	@Test
	@Rollback(false)
	public void addDepartmentRight()
	{
		String domain = "smart.goldskyer.com";
		String adminRoleId = "8a53b78255ddead90155ddeae7b40004";
		Right right = new Right("DEPT_ADD", "部门添加", "部门管理", domain);
		baseDao.add(right);
		Right right2 = new Right("DEPT_EDIT", "部门修改", "部门管理", domain);
		baseDao.add(right2);
		Right right3 = new Right("DEPT_LIST", "部门查看", "部门管理", domain);
		baseDao.add(right3);
		Right right4 = new Right("DEPT_DELETE", "部门删除", "部门管理", domain);
		baseDao.add(right4);
		
		//添加到超级管理员权限
		baseDao.add(new RoleRight(adminRoleId, right.getId()));
		baseDao.add(new RoleRight(adminRoleId, right2.getId()));
		baseDao.add(new RoleRight(adminRoleId, right3.getId()));
		baseDao.add(new RoleRight(adminRoleId, right4.getId()));

	}

	@Test
	@Rollback(false)
	public void addRight()
	{
		String domain = "smart.goldskyer.com";
		String adminRoleId = "8a53b78255ddead90155ddeae7b40004";
		//String domain = "xcxx.goldskyer.com";
		//String adminRoleId = "8a72bb9b551a111f01551a33cbbd0014";
		Right right = new Right("DEPT_ADD", "部门添加", "部门管理", domain);
		baseDao.add(right);
		Right right2 = new Right("DEPT_EDIT", "部门修改", "部门管理", domain);
		baseDao.add(right2);
		Right right3 = new Right("DEPT_VIEW", "部门查看", "部门管理", domain);
		baseDao.add(right3);
		Right right4 = new Right("DEPT_DELETE", "部门删除", "部门管理", domain);
		baseDao.add(right4);

		//添加到超级管理员权限
		baseDao.add(new RoleRight(adminRoleId, right.getId()));
		baseDao.add(new RoleRight(adminRoleId, right2.getId()));
		baseDao.add(new RoleRight(adminRoleId, right3.getId()));
		baseDao.add(new RoleRight(adminRoleId, right4.getId()));
	}

	@Test
	@Rollback(false)
	public void addWorkflow()
	{
		//String domain = "smart.goldskyer.com";
		//String adminRoleId = "8a53b78255ddead90155ddeae7b40004";
		String domain = "xcxx.goldskyer.com";
		String adminRoleId = "8a72bb9b551a111f01551a33cbbd0014";
		Right right = new Right("WORKFLOW_ADD", "流程申请", "流程管理", domain);
		baseDao.add(right);
		Right right2 = new Right("WORKFLOW_AUDIT", "流程审核", "流程管理", domain);
		baseDao.add(right2);
		Right right3 = new Right("WORKFLOW_VIEW", "流程查看", "流程管理", domain);
		baseDao.add(right3);
		//		Right right4 = new Right("WORKFLOW_COMMENT", "流程评论", "流程管理", domain);
		//		baseDao.add(right4);

		//添加到超级管理员权限
		baseDao.add(new RoleRight(adminRoleId, right.getId()));
		baseDao.add(new RoleRight(adminRoleId, right2.getId()));
		baseDao.add(new RoleRight(adminRoleId, right3.getId()));
		//baseDao.add(new RoleRight(adminRoleId, right4.getId()));
	}

	@Test
	@Rollback(false)
	public void addWorkflowTemplate()
	{
		//String domain = "smart.goldskyer.com";
		//String adminRoleId = "8a53b78255ddead90155ddeae7b40004";
		//		String domain = "xcxx.goldskyer.com";
		//		String adminRoleId = "8a72bb9b551a111f01551a33cbbd0014";
		String domain = "gmxx.goldskyer.com";
		String adminRoleId = "admin";
		Right right = new Right("WORKFLOW_TEMPLATE_ADD", "流程模板添加", "流程模板管理", domain);
		baseDao.add(right);
		Right right2 = new Right("WORKFLOW_TEMPLATE_VIEW", "流程模板查看", "流程模板管理", domain);
		baseDao.add(right2);
		Right right3 = new Right("WORKFLOW_TEMPLATE_EDIT", "流程模板编辑", "流程模板管理", domain);
		baseDao.add(right3);
		Right right4 = new Right("WORKFLOW_TEMPLATE_DELETE", "流程模板删除", "流程模板管理", domain);
		baseDao.add(right4);

		//添加到超级管理员权限
		baseDao.add(new RoleRight(adminRoleId, right.getId()));
		baseDao.add(new RoleRight(adminRoleId, right2.getId()));
		baseDao.add(new RoleRight(adminRoleId, right3.getId()));
		baseDao.add(new RoleRight(adminRoleId, right4.getId()));
	}

	@Test
	@Rollback(false)
	public void addBlogRight()
	{
		//		String domain = "xcxx.goldskyer.com";
		//		String adminRoleId = "8a72bb9b551a111f01551a33cbbd0014";
		String domain = "gmxx.goldskyer.com";
		String adminRoleId = "admin";
		Right right = new Right("BLOG_ADD", "资源添加", "资源管理", domain);
		baseDao.add(right);
		Right right2 = new Right("BLOG_EDIT", "资源修改", "资源管理", domain);
		baseDao.add(right2);
		Right right3 = new Right("BLOG_VIEW", "资源查看", "资源管理", domain);
		baseDao.add(right3);
		Right right4 = new Right("BLOG_DELETE", "资源删除", "资源管理", domain);
		baseDao.add(right4);
		Right right5 = new Right("BLOG_AUDIT", "资源审核", "资源管理", domain);
		baseDao.add(right5);


		//添加到超级管理员权限
		baseDao.add(new RoleRight(adminRoleId, right.getId()));
		baseDao.add(new RoleRight(adminRoleId, right2.getId()));
		baseDao.add(new RoleRight(adminRoleId, right3.getId()));
		baseDao.add(new RoleRight(adminRoleId, right4.getId()));
		baseDao.add(new RoleRight(adminRoleId, right5.getId()));

	}

	@Test
	@Rollback(false)
	public void testAddSingleRight()
	{
		Right right5 = new Right("BLOG_AUDIT", "资源删除", "资源管理", "xcxx.goldskyer.com");
		baseDao.add(right5);
	}

	@Test
	@Rollback(false)
	public void addINIRight()
	{
		//String domain = "xcxx.goldskyer.com";
		//String adminRoleId = "8a72bb9b551a111f01551a33cbbd0014";
		String domain = "gmxx.goldskyer.com";
		String adminRoleId = "admin";
		Right right = new Right("INI_ADD", "配置项添加", "配置项管理", domain);
		baseDao.add(right);
		Right right2 = new Right("INI_EDIT", "配置项修改", "配置项管理", domain);
		baseDao.add(right2);
		Right right3 = new Right("INI_VIEW", "配置项查看", "配置项管理", domain);
		baseDao.add(right3);
		Right right4 = new Right("INI_DELETE", "配置项删除", "配置项管理", domain);
		baseDao.add(right4);

		//添加到超级管理员权限
		baseDao.add(new RoleRight(adminRoleId, right.getId()));
		baseDao.add(new RoleRight(adminRoleId, right2.getId()));
		baseDao.add(new RoleRight(adminRoleId, right3.getId()));
		baseDao.add(new RoleRight(adminRoleId, right4.getId()));
	}

	@Test
	@Rollback(false)
	public void addCMSRight()
	{
		//		String domain = "xcxx.goldskyer.com";
		//		String adminRoleId = "8a72bb9b551a111f01551a33cbbd0014";
		String domain = "gmxx.goldskyer.com";
		String adminRoleId = "admin";
		Right right = new Right("CMS_ADD", "CMS添加", "CMS管理", domain);
		baseDao.add(right);
		Right right2 = new Right("CMS_EDIT", "CMS修改", "CMS管理", domain);
		baseDao.add(right2);
		Right right3 = new Right("CMS_VIEW", "CMS查看", "CMS管理", domain);
		baseDao.add(right3);
		Right right4 = new Right("CMS_DELETE", "CMS删除", "CMS管理", domain);
		baseDao.add(right4);

		//添加到超级管理员权限
		baseDao.add(new RoleRight(adminRoleId, right.getId()));
		baseDao.add(new RoleRight(adminRoleId, right2.getId()));
		baseDao.add(new RoleRight(adminRoleId, right3.getId()));
		baseDao.add(new RoleRight(adminRoleId, right4.getId()));
	}

	@Test
	@Rollback(false)
	public void addOARight()
	{
		String domain = "xcxx.goldskyer.com";
		String adminRoleId = "8a72bb9b551a111f01551a33cbbd0014";
		Right right = new Right("CMS_ADD", "CMS添加", "校园OA", domain);
		baseDao.add(right);
		Right right2 = new Right("CMS_EDIT", "CMS修改", "校园OA", domain);
		baseDao.add(right2);
		Right right3 = new Right("CMS_VIEW", "CMS查看", "校园OA", domain);
		baseDao.add(right3);
		Right right4 = new Right("CMS_DELETE", "CMS删除", "校园OA", domain);
		baseDao.add(right4);

		//添加到超级管理员权限
		baseDao.add(new RoleRight(adminRoleId, right.getId()));
		baseDao.add(new RoleRight(adminRoleId, right2.getId()));
		baseDao.add(new RoleRight(adminRoleId, right3.getId()));
		baseDao.add(new RoleRight(adminRoleId, right4.getId()));
	}
}
