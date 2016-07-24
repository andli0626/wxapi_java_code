package org.liufeng.course.test;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.wltea.analyzer.lucene.IKAnalyzer;

/**
 * 演示分词
 * 
 * @author liufeng
 * @date 2013-11-27
 */
public class AnalyzerTest {
	public static void main(String[] args) throws Exception {
		String content = "IK Analyzer分词测试：生科院的研究生研究生命科学。";
		// true:智能切分，false:细粒度切分
		Analyzer analyzer = new IKAnalyzer(false);
		// 对content进行分词，得到的结果是分词流
		TokenStream ts = analyzer.tokenStream("text", content);
		ts.reset();

		CharTermAttribute attr = null;
		// 遍历分词流
		while (ts.incrementToken()) {
			attr = ts.getAttribute(CharTermAttribute.class);
			System.out.print(attr.toString() + " ");
		}
	}
}