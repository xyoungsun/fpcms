package com.fpcms.common.webcrawler.htmlparser;

import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;

import com.fpcms.common.util.KeywordUtil;
import com.fpcms.common.webcrawler.htmlparser.HtmlPage.Anchor;

public class HtmlPageTitleUtil {
	public static String smartGetTitle(Anchor anchor, String pageTitle) {
		if(StringUtils.isNotBlank(anchor.getText()) && anchor.getText().length() >= 6) {
			return extrectMainTitle(pageTitle,true,anchor.getText().length());
		}
		
		return smartGetTitle(pageTitle);
	}
	
	public static String smartGetTitle(String pageTitle) {
		String result = extrectMainTitle(pageTitle,true,0);
		
		//english
		if(pageTitle.matches("[\\s\\w-_:|]+")) {
			ArrayList<String> tokenizerList = KeywordUtil.toTokenizerList(result);
			if(tokenizerList.size() < 5) {
				return extrectMainTitle(pageTitle,false,pageTitle.length());
			}
		}else {
			if(result.length() < 5) {
				return extrectMainTitle(pageTitle,false,pageTitle.length());
			}
		}
		
		return result;
	}

	private static char[] titleSeperator = {'_','-',':','|','>','：','—','-','|'};
	static String extrectMainTitle(String title,boolean isIndexOf,int fromIndex) {
		title = title.trim();
		for(char c : titleSeperator) {
			int indexOf = isIndexOf ? title.indexOf(c,fromIndex) : title.lastIndexOf(c, fromIndex);
			if(indexOf >= 0) {
				return title.substring(0,indexOf).trim();
			}
		}
		return title;
	}
}