/*
 * Copyright [duowan.com]
 * Web Site: http://www.duowan.com
 * Since 2005 - 2012
 */


package com.fpcms.home.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fpcms.common.BaseController;
import com.fpcms.common.util.WebUtil;
import com.fpcms.model.CmsChannel;
import com.fpcms.model.CmsContent;
import com.fpcms.query.CmsChannelQuery;
import com.fpcms.service.CmsChannelService;
import com.fpcms.service.CmsContentService;


/**
 * [CmsChannel] 的业务操作
 * 
 * @author badqiu email:badqiu(a)gmail.com
 * @version 1.0
 * @since 1.0
 *
 */
@Controller
@RequestMapping("/channel")
public class ChannelController extends BaseController{
	
	private CmsChannelService cmsChannelService;
	private CmsContentService cmsContentService;
	
	private final String LIST_ACTION = "redirect:/admin/cmschannel/refreshParent.jsp";
	
	/** 
	 * 增加setXXXX()方法,spring就可以通过autowire自动设置对象属性,注意大小写
	 **/
	public void setCmsChannelService(CmsChannelService service) {
		this.cmsChannelService = service;
	}
	
	public void setCmsContentService(CmsContentService cmsContentService) {
		this.cmsContentService = cmsContentService;
	}



	/** binder用于bean属性的设置 */
	@InitBinder  
	public void initBinder(WebDataBinder binder) {  
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));  
	}
	   
	/**
	 * 增加了@ModelAttribute的方法可以在本controller方法调用前执行,可以存放一些共享变量,如枚举值,或是一些初始化操作
	 */
	@ModelAttribute
	public void init(ModelMap model) {
	}
	
	/** 列表 */
	@RequestMapping("/show/{channelCode}.do")
	public String show(ModelMap model,CmsChannelQuery query,@PathVariable("channelCode") String channelCode,HttpServletRequest request,HttpServletResponse response) {
		CmsChannel channel = cmsChannelService.findByChannelCode(channelCode);
		model.put("cmsChannel", channel);
		
		if(WebUtil.isNotModified(request, response, channel.getDateLastModified())) {
			return null;
		}
		
		return "/channel/show";
	}
	
	@RequestMapping("/showContentList/{channelCode}.do")
	public String showContentList(ModelMap model,CmsChannelQuery query,HttpServletRequest request,@PathVariable("channelCode") String channelCode) {
		List<CmsContent> cmsContentList = cmsContentService.findByChannelCode(channelCode);
		model.put("cmsContentList", cmsContentList);
		return "/channel/showContentList";
	}
	
}
