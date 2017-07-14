package org.wltea.analyzer.sample;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.wltea.analyzer.cfg.Configuration;
import org.wltea.analyzer.cfg.DefaultConfig;
import org.wltea.analyzer.dic.Dictionary;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashSet;

public class IkSegmentation {
	//创建分词对象
	static Analyzer anal=new IKAnalyzer(true);

	public static String Seg(String sentence) throws IOException {

		String text="";

		StringReader reader=new StringReader(sentence);
		//分词
		TokenStream ts=anal.tokenStream("", reader);
		CharTermAttribute term=ts.getAttribute(CharTermAttribute.class);
		//遍历分词数据
		while(ts.incrementToken()){
			text+=term.toString()+"|";
		}
		reader.close();
		return text.trim()+"\n";
	}

	public static void main(String[] args) throws IOException {

		HashSet<String> set=new HashSet<String>();
		set.add("金窝窝");
		set.add("杨鹤");
		DicUtil.addDic(set);
		HashSet<String> setd=new HashSet<String>();
//		setd.add("杨鹤");
		DicUtil.delDic(setd);
		IkSegmentation ik = new IkSegmentation();
		System.out.println("找屌丝小萝莉帅哥奥运会金窝窝杨鹤");
		System.out.println(ik.Seg("找屌丝小萝莉帅哥奥运会金窝窝杨鹤"));
		System.out.println(ik.Seg("找屌丝小萝莉帅哥奥运会金窝窝杨鹤"));
	}
}

/**
 * 词典工具类
 * @author 廖天宇
 *
 */
class DicUtil {
	static Dictionary dic;
	/**
	 * 动态添加词典
	 * @param set
	 */
	public static void addDic( HashSet<String> set){
		try{
			dic= Dictionary.getSingleton();
		}catch(IllegalStateException e){
			Configuration cfg= DefaultConfig.getInstance();
			System.out.println(cfg.getMainDictionary());
			Dictionary.initial(cfg);
			dic= Dictionary.getSingleton();
		}
		dic.addWords(set);
	}

	/**
	 * 动态删除词典
	 * @param set
	 */
	public static void delDic( HashSet<String> set){
		try{
			dic= Dictionary.getSingleton();
		}catch(IllegalStateException e){
			Configuration cfg= DefaultConfig.getInstance();
			System.out.println(cfg.getMainDictionary());
			Dictionary.initial(cfg);
			dic= Dictionary.getSingleton();
		}
		dic.disableWords(set);
	}
}