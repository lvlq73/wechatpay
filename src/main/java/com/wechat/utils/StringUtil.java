/***********************************************************************   
 *   
 *   StringUtil   
 *   
 *   @copyright     福建邮科通信
 *   @project-name  jeebms
 *   @creator       kangzhishan
 *   @email         kangzhisan@126.com    
 *   @create-time   2010-8-13 下午05:15:32   
 *   @version         
 ***********************************************************************/

package com.wechat.utils;




import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

@SuppressWarnings( { "all" })
public class StringUtil {

	/**
	 * 给生成唯�?��符串使用
	 */
	private static String[] strArray = new String[] { "a", "b", "c", "d", "e",
			"f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r",
			"s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E",
			"F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
			"S", "T", "U", "V", "W", "X", "Y", "Z", "0", "1", "2", "3", "4",
			"5", "6", "7", "8", "9", "!", "@", "#", "$", "%", "^", "&", "(",
			")" };

	/**
	 * 生成唯一字符�?
	 * 
	 * @param length
	 *            �?��长度
	 * @param symbol
	 *            是否允许出现特殊字符 -- !@#$%^&*()
	 * @return
	 */
	public static String getUniqueString(int length, boolean symbol)
			throws Exception {
		Random ran = new Random();
		int num = ran.nextInt(61);
		String returnString = "";
		String str = "";
		for (int i = 0; i < length;) {
			if (symbol)
				num = ran.nextInt(70);
			else
				num = ran.nextInt(61);
			str = strArray[num];
			if (!(returnString.indexOf(str) >= 0)) {
				returnString += str;
				i++;
			}
		}
		return returnString;
	}

	/**
	 * 生成唯一字符�?会已时间 加上你需要数量的随机字母 �?getUniqueString(6,true,"yyyyMMddHHmmss")
	 * 返回:20080512191554juHkn4
	 * 
	 * @param length
	 *            �?��长度
	 * @param symbol
	 *            是否允许出现特殊字符 -- !@#$%^&*()
	 * @param dateformat
	 *            时间格式字符�?
	 * @return
	 */
	public static String getUniqueString(int length, boolean symbol,
			String dateformat) throws Exception {
		Random ran = new Random();
		int num = ran.nextInt(61);
		Calendar d = Calendar.getInstance();
		Date nowTime = d.getTime();
		SimpleDateFormat sf = new SimpleDateFormat(dateformat);
		String returnString = sf.format(nowTime);
		String str = "";
		for (int i = 0; i < length;) {
			if (symbol)
				num = ran.nextInt(70);
			else
				num = ran.nextInt(61);
			str = strArray[num];
			if (!(returnString.indexOf(str) >= 0)) {
				returnString += str;
				i++;
			}
		}
		return returnString;
	}

	/**
	 * 左补空格或零
	 * 
	 * @param strSrcLength
	 * @param strSrc
	 * @param flag
	 *            1: "0" 2: " "
	 * @return strReturn
	 */
	public static String leftPading(int strSrcLength, String strSrc, String flag) {
		String strReturn = "";
		String strtemp = "";
		int curLength = strSrc.trim().length();
		if (strSrc != null && curLength > strSrcLength) {
			strReturn = strSrc.trim().substring(0, strSrcLength);
		} else if (strSrc != null && curLength == strSrcLength) {
			strReturn = strSrc.trim();
		} else {
			for (int i = 0; i < (strSrcLength - curLength); i++) {
				strtemp = strtemp + flag;
			}
			strReturn = strtemp + strSrc.trim();
		}
		return strReturn;
	}

	/**
	 * 右补空格或零（文字，"0"�?0�?
	 * 
	 * @param strSrcLength
	 * @param strSrc
	 * @param flag
	 *            1: "0" 2: " "
	 * @return strReturn
	 */
	public static String rightPading(int strSrcLength, String strSrc,
			String flag) {
		String strReturn = "";
		String strtemp = "";
		int curLength = strSrc.trim().length();
		if (strSrc != null && curLength > strSrcLength) {
			strReturn = strSrc.trim().substring(0, strSrcLength);
		} else if (strSrc != null && curLength == strSrcLength) {
			strReturn = strSrc.trim();
		} else {
			for (int i = 0; i < (strSrcLength - curLength); i++) {
				strtemp = strtemp + flag;
			}
			strReturn = strSrc.trim() + strtemp;
		}
		return strReturn;
	}

	/**
	 * 验证手机号是否是电信手机�?
	 * 
	 * @param mobile
	 * @return
	 */
	public static boolean validDxMobile(String mobile) {
		//String patternStr = "((13[3]\\d{8})|(15[3]\\d{8})|(18[9]\\d{8}))";
		String patternStr = "(1\\d{10})";
		Pattern pattern = Pattern.compile(patternStr);
		return pattern.matcher(mobile).matches();
	}
	
	/**
	 * 把手机号码中间四位用星号代替
	 * @param src
	 * @param begPos
	 * @param endPos
	 * @return
	 */
	public static String strToPassword(String src, int begPos, int endPos) {
		src = src.substring(0,begPos) + "****" + src.substring(endPos, src.length());

		return src;
	}
	
	static Map<Integer, Integer[]> randomMap = new HashMap<Integer, Integer[]>();
	static {
		randomMap.put(1, new Integer[]{1,10});
		randomMap.put(2, new Integer[]{10,99});
		randomMap.put(3, new Integer[]{100,999});
		randomMap.put(4, new Integer[]{1000,9999});
		randomMap.put(5, new Integer[]{10000,99999});
		randomMap.put(6, new Integer[]{100000,999999});
		randomMap.put(7, new Integer[]{1000000,9999999});
		randomMap.put(8, new Integer[]{10000000,99999999});
		randomMap.put(9, new Integer[]{100000000,999999999});
	}
	
	private static Integer randomInt(int upLimit, int downLimit) {
		return (int)(Math.random()*(upLimit-downLimit))+downLimit;
	}
	
	/**
	 * 随机整形数据-目前�?��9位数
	 * @param len
	 * @return
	 */
	public static int randomInt(int len) {
		Integer[] baseInt = (Integer[]) randomMap.get(len);
		return StringUtil.randomInt(baseInt[0], baseInt[1]);
	}
	
	public static String unqualify(String name) {
		return unqualify(name, '.');
	}

	public static String unqualify(String name, char sep) {
		return name.substring(name.lastIndexOf(sep) + 1, name.length());
	}

	public static boolean isEmpty(String string) {
		return string == null || string.trim().length() == 0;
	}

	public static String nullIfEmpty(String string) {
		return isEmpty(string) ? null : string;
	}

	public static String emptyIfNull(String string) {
		return string == null ? "" : string;
	}
	
	public static String toString(Object component) {
		try {
			PropertyDescriptor[] props = Introspector.getBeanInfo(
					component.getClass()).getPropertyDescriptors();
			StringBuilder builder = new StringBuilder();
			for (PropertyDescriptor descriptor : props) {
				builder.append(descriptor.getName()).append("=").append(
						descriptor.getReadMethod().invoke(component)).append(
						"; ");
			}
			return builder.toString();
		} catch (Exception e) {
			return "";
		}
	}

	public static String[] split(String strings, String delims) {
		if (strings == null) {
			return new String[0];
		} else {
			StringTokenizer tokens = new StringTokenizer(strings, delims);
			String[] result = new String[tokens.countTokens()];
			int i = 0;
			while (tokens.hasMoreTokens()) {
				result[i++] = tokens.nextToken();
			}
			return result;
		}
	}

	public static String toString(Object... objects) {
		return toString(" ", objects);
	}

	public static String toString(String sep, Object... objects) {
		if (objects.length == 0)
			return "";
		StringBuilder builder = new StringBuilder();
		for (Object object : objects) {
			builder.append(sep).append(object);
		}
		return builder.substring(2);
	}

	public static String toClassNameString(String sep, Object... objects) {
		if (objects.length == 0)
			return "";
		StringBuilder builder = new StringBuilder();
		for (Object object : objects) {
			builder.append(sep);
			if (object == null) {
				builder.append("null");
			} else {
				builder.append(object.getClass().getName());
			}
		}
		return builder.substring(2);
	}

	public static String toString(String sep, Class... classes) {
		if (classes.length == 0)
			return "";
		StringBuilder builder = new StringBuilder();
		for (Class clazz : classes) {
			builder.append(sep).append(clazz.getName());
		}
		return builder.substring(2);
	}

	public static String toString(InputStream in) throws IOException {
		StringBuffer out = new StringBuffer();
		byte[] b = new byte[4096];
		for (int n; (n = in.read(b)) != -1;) {
			out.append(new String(b, 0, n));
		}
		return out.toString();
	}

	public static String capfirstChar(String msg) {
		char[] msgs = msg.toCharArray();
		msgs[0] = (char) (msgs[0] - 32);
		return String.valueOf(msgs);
	}
	
	public static String subString(String str, int beg, int end) {
		String result = null;
		if (beg<0 || end<0) {
			return null;
		}
		
		if (!isEmpty(str)) {
			int len = str.length();
			if (end > len) {
				return null;
			}
			result = str.substring(beg, end);
		}
		
		return result;
	}
	
	/**
	 * 剪切文本。如果进行了剪切，则在文本后加上"..."
	 * 
	 * @param s
	 *            剪切对象。
	 * @param len
	 *            编码小于256的作为一个字符，大于256的作为两个字符。
	 * @return
	 */
	public static String textCut(String s, int len, String append) {
		if (s == null) {
			return null;
		}
		int slen = s.length();
		if (slen <= len) {
			return s;
		}
		// 最大计数（如果全是英文）
		int maxCount = len * 2;
		int count = 0;
		int i = 0;
		for (; count < maxCount && i < slen; i++) {
			if (s.codePointAt(i) < 256) {
				count++;
			} else {
				count += 2;
			}
		}
		if (i < slen) {
			if (count > maxCount) {
				i--;
			}
			if (!StringUtil.isEmpty(append)) {
				if (s.codePointAt(i - 1) < 256) {
					i -= 2;
				} else {
					i--;
				}
				return s.substring(0, i) + append;
			} else {
				return s.substring(0, i);
			}
		} else {
			return s;
		}
	}

	/**
	 * 高效替换字符串
	 * @param template	被替换格式：${key}
	 * @param params	key要跟template里面的key一样
	 * @return
	 */
	/*public static String replaceByReg(String template, Map<String, String> params) {
		String patternString = "\\$\\{(" + StringUtils.join(params.keySet(), "|") + ")\\}";
		 
		Pattern pattern = Pattern.compile(patternString);
		Matcher matcher = pattern.matcher(template);
		//两个方法：appendReplacement, appendTail
	    StringBuffer sb = new StringBuffer();
	    while(matcher.find()) {
	        matcher.appendReplacement(sb, params.get(matcher.group(1)));
	    }
	    matcher.appendTail(sb);
		return sb.toString();
	}*/
	/**
	 * 将字符串通过分隔符转化成List集合
	 * @param str 需处理字符串
	 * @param separator 分隔符
	 * @return
	 */
	public static List<String> toList(String str, String separator){
		String[] strs = str.split(separator);
		List<String> list = new ArrayList<String>();
		for(String s : strs){
			list.add(s);
		}
		return list;
	}
	
	/**
	 * 判断字符是中文还是字母
	 * @param c
	 * @return
	 */
	public static boolean isLetter(char c) {   
	    int k = 0x80;   
	    return c / k == 0 ? true : false;   
	}
	
	
  /**  
    * 获取字符串真实长度,一个汉字或日韩文长度为2,英文字符长度为1  
    * @param String s 需要得到长度的字符串  
    * @return int 得到的字符串长度  
    */   
   public static int byteLength(String s) {  
       if (s == null)  
           return 0;  
       char[] c = s.toCharArray();  
       int len = 0;  
       for (int i = 0; i < c.length; i++) {  
           len++;  
           if (!isLetter(c[i])) {  
               len++;  
           }  
       }  
       return len;  
   }  
	
	/**
	 * 移除 sql中包含1=1
	 * @param dataSource
	 * @return
	 */
	public static String replaceCondition(String sqlStr){
		sqlStr = sqlStr.replace("and (1=1 or 1=1 or 1=1 or 1=1)", "");
		sqlStr = sqlStr.replace("and (1=1 or 1=1 or 1=1)", "");
		sqlStr = sqlStr.replace("and (1=1 or 1=1)", "");
		sqlStr = sqlStr.replace("and 1=1", "");
		sqlStr = sqlStr.replace("where (1=1 or 1=1 or 1=1) and", "where ");
		sqlStr = sqlStr.replace("where (1=1 or 1=1) and", "where ");
		sqlStr = sqlStr.replace("where 1=1 or 1=1 and", "where ");
		sqlStr = sqlStr.replace("where  1=1  and", "where ");
		sqlStr = sqlStr.replace("where 1=1  and", "where ");
		sqlStr = sqlStr.replace("where 1=1 and", "where ");
		sqlStr = sqlStr.replace("where 1=1 or 1=1 or 1=1", "");
		sqlStr = sqlStr.replace("where 1=1 or 1=1", "");
		sqlStr = sqlStr.replace("where 1=1", "");
		return sqlStr;
	}
	
	/**
	* 用特殊方法重新计算字符串，每newLineNum个字符换一次行
	* 有带中文和英文的字符串，重新计算其长度，在特定的长度下进行换行 
	* 如果有中文，则每个中文字符计为2位，英文1位
	* @param str 指定的字符串
	* @param newLineNum 多少个字符开始换行
	* @return 字符串的长度
	*/
	public static List<HashMap<String,Object>> getStrLenAndNewLine(String str,int newLineNum) {
		List<HashMap<String,Object>> listMap = new ArrayList<HashMap<String,Object>>();//返回一个list,存放在字符串下哪个位置进行换行
		int newLineCount = 0; //换行次数 
		int strLength = 0;//字符串重新计算的长度
		String chinese = "[\u0391-\uFFE5]";
		//int count=0;
		/* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
		for (int i = 0; i < str.length(); i++) {
			/* 获取一个字符 */
			String temp = str.substring(i, i + 1);
			/* 判断是否为中文字符 */
			if (temp.matches(chinese)) {
				/* 中文字符长度为2 */
				strLength += 2;
			} else {
				/* 其他字符长度为1 */
				strLength += 1;
			}
			if((strLength/newLineNum) > newLineCount){//newLineNum个字符换行，将换行的下标放入map
				HashMap<String,Object> map = new HashMap<String,Object>();
				map.put("newLineLength", i);
				listMap.add(map);
				newLineCount = strLength/newLineNum;
			}
		}
		return listMap;
	}
}
