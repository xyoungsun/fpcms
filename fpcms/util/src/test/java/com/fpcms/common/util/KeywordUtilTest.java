package com.fpcms.common.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import org.junit.Assert;
import org.junit.Test;

public class KeywordUtilTest extends Assert{

	@Test
	public void test() {
		String result = KeywordUtil.getPerfectKeyword("1234123,中国,中国人民银行,191811", "中国");
		assertEquals("中国人民银行",result);
		
		result = KeywordUtil.getPerfectKeyword("1234123,中国,中国人民银行,191811", "hibernate");
		assertEquals(null,result);
		
		result = KeywordUtil.getPerfectKeyword("1234123,中国,中国人民银行,191811", null);
		assertEquals(null,result);
	}
	
	@Test
	public void test_getPerfectKeyword() {
		String str = KeywordUtil.getPerfectKeyword("com/2012/06/1老妇疑吃方便面中毒身亡/", "老妇");
		System.out.println(str);
		assertEquals("1老妇疑吃方便面中毒身亡",str);
		
		System.out.println(KeywordUtil.toTokenizerList("lq=末日走你"));
		assertEquals("[lq, 末日走你]",KeywordUtil.toTokenizerList("lq=末日走你").toString());
	}
	
	@Test
	public void test_isSensitiveKeyword() {
		assertTrue(KeywordUtil.isSensitiveKeyword("乳交"));
		assertFalse(KeywordUtil.isSensitiveKeyword("xxxxx"));
	}
	
	@Test
	public void test_filterSensitiveKeyword() {
		List<String> list = new ArrayList(Arrays.asList("乳交","xxxx"));
		KeywordUtil.filterSensitiveKeyword(list);
		assertEquals("[xxxx]",list.toString());
	}
}