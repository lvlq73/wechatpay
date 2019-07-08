package com.wechat.utils;

import org.bouncycastle.util.Strings;
import org.bouncycastle.util.encoders.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 通用静态函数库,不可继承
 * 用于常用静态函数定义
 */
public class Usual
{
	private final static Logger logger = LoggerFactory.getLogger(Usual.class);
	
	/**
	 * 保存动态执行函数的静态Class对象
	 */
	private final static HashMap<String, Class<?>> cht_st = new HashMap<String, Class<?>>();
	/**
	 * 保存动态执行函数的静态Class对象锁
	 */
	private static Lock cht_stLock = new ReentrantLock();
	/**
	 * 保存动态执行函数的New Class对象
	 */
	private final static HashMap<String, Object> cht_obj = new HashMap<String, Object>();
	/**
	 * 保存动态执行函数的New Class对象锁
	 */
	private static Lock cht_objLock = new ReentrantLock();
	/**
	 * 静态空字段,""
	 */
	public final static String mEmpty = "";
	/**
	 * 静态空字符,' '
	 */
	public final static char mBlankSpaceChar = ' ';
	/**
	 * 静态空字段," "
	 */
	public final static String mBlankSpace = " ";
	/**
	 * 双引号字符
	 */
	public final static String mDoubleQuote = "\"";
	/**
	 * 静态空Byte数组
	 */
	public final static byte[] mEmptyBytes = new byte[0];
	/**
	 * 静态空对象
	 */
	public final static Object mEmptyObject = new Object();
	/**
	 * Http POST/GET连接超时时间,毫秒,默认3秒
	 */
	public static int mUrlConTime = 1000 * 3 * 1;
	/**
	 * Http POST/GET连接超时时间,毫秒,默认5分钟
	 */
	public static int mUrlReadTime = 1000 * 60 * 5;
	/**
	 * UTF-8名称
	 */
	public final static String mUTF8Name = "UTF-8";
	/**
	 * GB2312名称
	 */
	public final static String mGB2312Name = "GB2312";
	/**
	 * ASCII名称
	 */
	public final static String mASCIIName = "ASCII";
	/**
	 * 采用UTF-8作为基础字符格式
	 */
	public final static Charset mCharset_utf8 = Charset.forName(mUTF8Name);
	/**
	 * 采用C#,Java,Window Mobile,Java Mobile都支持的字符加密格式 采用gb2312字符格式,也可以设定为unicode
	 */
	public final static Charset mCharset_gb2312 = Charset.forName(mGB2312Name);
	/**
	 * Http模拟请求WebService使用
	 */
	public final static String mContentTypeBaseWithCharset = "text/plain;charset=utf-8";
	/**
	 * 默认Http Content Type
	 */
	public final static String mContentTypeBase = mContentTypeBaseWithCharset;
	/**
	 * Http Content Type JSON
	 */
	public final static String mContentTypeJson ="text/json";
	/**
	 * Http Content Type urlencoded
	 */
	public final static String mContentTypeUrl ="application/x-www-form-urlencoded; charset=UTF-8";
	/**
	 * 基础数据大小,4K
	 */
	public final static int mByteBaseSize = 1024 * 4;
	/**
	 * GZip名称
	 */
	public final static String mGZipName = "GZIP";
	/**
	 * nitobi Grid 页面大小字段
	 */
	public static final String mgPageSize = "PageSize";
	/**
	 * nitobi Grid 排序字段
	 */
	public static final String mgSortColumn = "SortColumn";
	/**
	 * nitobi Grid 排序顺序
	 */
	public static final String mgSortDirection = "SortDirection";
	/**
	 * nitobi Grid 当前页面开始数目
	 */
	public static final String mgStart = "start";
	/**
	 * nitobi Grid 当前页面开始数目,不确定
	 */
	public static final String mgStartRecordIndex = "StartRecordIndex";
	/**
	 * SQL: select
	 */
	public static final String msSelect = " select ";
	/**
	 * SQL: from
	 */
	public static final String msFrom = " from ";
	/**
	 * SQL: where
	 */
	public static final String msWhere = " where ";
	/**
	 * SQL: group by
	 */
	public static final String msGroupBy = " group by ";
	/**
	 * SQL: order by
	 */
	public static final String msOrderBy = " order by ";
	/**
	 * 格式化Date,为yyyy-MM-dd HH:mm:ss格式
	 */
	public final static DateFormat mfAll = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 * 格式化Date,为yyyy-MM-dd HH:mm:ss.SSS格式
	 */
	public final static DateFormat mfAllMS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	/**
	 * 格式化Date,为yyyy-MM-dd格式
	 */
	public final static DateFormat mfYMD = new SimpleDateFormat("yyyy-MM-dd");
	/**
	 * 格式化Date,为yyyyMMdd格式
	 */
	public final static DateFormat mfYMDSimple = new SimpleDateFormat("yyyyMMdd");
	/**
	 * 格式化Date,为yyyy-MM-dd 格式
	 */
	public final static DateFormat mfYMD_HM = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	/**
	 * 格式化Date,为Oracle日期yyyy-MM-dd HH:mm:ss SSS格式
	 */
	public final static DateFormat mfYMD_Oracle = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
	/**
	 * 格式化Date,为SQLServer日期yyyy-MM-dd HH:mm:ss.SSS格式
	 */
	public final static DateFormat mfYMD_SQLServer = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	/**
	 * 格式化Date,为JS日期yyyy-MM-ddTHH:mm:ss格式
	 */
	public final static DateFormat mfYMD_JS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	// 安全线程池变量组
	/**
	 * 5000个核心工作线程
	 */
	public static final int mCORE_POOL_SIZE = 5000;
	/**
	 * Usual线程池最多65535个工作线程
	 */
	public static final int mMAXIMUM_POOL_SIZE = 65535;
	/**
	 * 空闲线程的超时时间3秒
	 */
	public static final int mKEEP_ALIVE = 3;
	/**
	 * 线程工厂对象
	 */
	public static final ThreadFactory mThreadFactory = new ThreadFactory()
	{

		// private final AtomicInteger mCount = new AtomicInteger(1);
		public Thread newThread(Runnable r)
		{
			// return new Thread(r, "PreReadTask #" + mCount.getAndIncrement());
			return new Thread(r, "SingleTask_" + Usual.getUUID());
		}
	};
	/**
	 * 线程等待65535队列长度 mExecutorService.shutdown(); mExecutorService.submit(...)
	 */
	public static final BlockingQueue<Runnable> mWorkQueue = new LinkedBlockingQueue<Runnable>(65535);
	/**
	 * 线程池对象,5000个核心线程,65535个最大线程,65535个线程队列
	 */
	public static final ExecutorService mExecutorService = new ThreadPoolExecutor(mCORE_POOL_SIZE, mMAXIMUM_POOL_SIZE, mKEEP_ALIVE, TimeUnit.SECONDS, mWorkQueue, mThreadFactory);
	
	/**
	 * String to Byte[],并进行编码
	 * 
	 * @param str String数据
	 * @param charset 字符编码
	 * @return byte[]数组
	 */
	public static byte[] toBytes(String str, Charset charset)
	{
		byte[] bts = null;
		try
		{
			if (charset.name().equals(mUTF8Name))
			{
				bts = toBytes(str);
			}
			else
			{
				bts = str.getBytes(charset);
			}
		}
		catch (Exception e)
		{
			bts = new byte[0];
		}
		return bts;
	}
	/**
	 * String to Byte[],并进行编码
	 * 
	 * @param str
	 * @param charset
	 * @return
	 */
	public static byte[] toBytes(String str, String charset)
	{
		byte[] bts = null;
		try
		{
			if (charset.toUpperCase().equals(mUTF8Name))
			{
				bts = toBytes(str);
			}
			else
			{
				bts = str.getBytes(charset);
			}
		}
		catch (Exception e)
		{
			bts = new byte[0];
		}
		return bts;
	}
	/**
	 * String to Byte[],并进行编码,utf-8格式
	 * 
	 * @param str String数据
	 * @return byte[]数组
	 */
	public static byte[] toBytes(String str)
	{
		byte[] bts = null;
		try
		{
			bts = Strings.toUTF8ByteArray(str);
			// bts = str.getBytes(mCharset_utf8);
		}
		catch (Exception e)
		{
			bts = new byte[0];
		}
		return bts;
	}
	/**
	 * byte[]转化为String数据
	 * 
	 * @param bts byte[]数据
	 * @param charset 编码格式
	 * @return String数据
	 */
	public static String fromBytes(byte[] bts, Charset charset)
	{
		String str = Usual.mEmpty;
		try
		{
			if (charset.name().equals(mUTF8Name))
			{
				str = fromBytes(bts);
			}
			else
			{
				str = new String(bts, charset);
			}
		}
		catch (Exception e)
		{
			str = Usual.mEmpty;
		}
		return str;
	}
	/**
	 * byte[]转化为String数据,默认utf-8字符格式
	 * 
	 * @param bts byte[]数据
	 * @return String数据
	 */
	public static String fromBytes(byte[] bts)
	{
		String str = new String(bts);
		try
		{
			str = Strings.fromUTF8ByteArray(bts);
			// str = new String(bts, mCharset_utf8);
		}
		catch (Exception e)
		{
			str = Usual.mEmpty;
		}
		return str;
	}
	/**
	 * byte[]数据转化为Base64String数据格式String
	 * 
	 * @param bts byte[]数据
	 * @return Base64String数据格式String
	 */
	public static String toBase64String(byte[] bts)
	{
		String str = Usual.mEmpty;
		if (Usual.isNullOrZeroBytes(bts))
		{
			return str;
		}
		// BASE64Encoder encoder = new BASE64Encoder();
		try
		{
			byte[] mBytes_Base64 = org.bouncycastle.util.encoders.Base64.encode(bts);
			// str =Usual.f_fromBytes(bts);
			str = Strings.fromUTF8ByteArray(mBytes_Base64);
			// str = encoder.encode(bts);
		}
		catch (Exception e)
		{
			str = Usual.mEmpty;
		}
		// finally
		// {
		// encoder = null;
		// }
		return str;
	}
	/**
	 * Base64String数据格式String转化为byte[]数据
	 * 
	 * @param base64str Base64String数据格式String
	 * @return byte[]数据
	 */
	public static byte[] fromBase64String(String base64str)
	{
		byte[] bts = new byte[0];
		if (Usual.isNullOrWhiteSpace(base64str))
		{
			return bts;
		}
		// BASE64Decoder decoder = new BASE64Decoder();
		try
		{
			// bts = decoder.decodeBuffer(base64str);
			bts = org.bouncycastle.util.encoders.Base64.decode(base64str);
		}
		catch (Exception e)
		{
			bts = new byte[0];
		}
		// finally
		// {
		// decoder = null;
		// }
		return bts;
	}
	/**
	 * 判断字符串为null或者为"",返回true;否则返回false
	 * 
	 * @param instr
	 * @return
	 */
	public static Boolean isNullOrEmpty(String instr)
	{
		if (instr == null || instr.length() == 0)
		{
			return true;
		}
		return false;
	}
	/**
	 * 判断字符串为null或者为""或者由空白组成,返回true;否则返回false
	 * 
	 * @param instr
	 * @return
	 */
	public static Boolean isNullOrWhiteSpace(String instr)
	{
		if (instr == null || instr.trim().length() == 0)
		{
			return true;
		}
		return false;
	}
	/**
	 * 判断byte[]数组是否为null或者长度为0
	 * 
	 * @param bts
	 * @return
	 */
	public static Boolean isNullOrZeroBytes(byte[] bts)
	{
		if (bts == null || bts.length == 0)
		{
			return true;
		}
		return false;
	}
	/**
	 * 判断String[]数组是否为null或者长度为0
	 * 
	 * @param bts
	 * @return
	 */
	public static Boolean isNullOrZeroStrings(String[] bts)
	{
		if (bts == null || bts.length == 0)
		{
			return true;
		}
		return false;
	}
	/**
	 * 判断List是否为null或者长度为0
	 * 
	 * @param bts
	 * @return
	 */
	public static Boolean isNullOrZeroList(List<?> bts)
	{
		if (bts == null || bts.size() == 0)
		{
			return true;
		}
		return false;
	}
	/**
	 * 去除字符串空白
	 * 
	 * @param instring
	 * @return
	 */
	public static String stringTrim(String instring)
	{
		String outstring = Usual.mEmpty;
		try
		{
			outstring = instring.trim();
		}
		catch (Exception e)
		{
			return Usual.mEmpty;
		}
		return outstring;
	}
	/**
	 * 去除多余的0
	 * @param number
	 * @return
	 */
	public static String getPrettyNumber(String number) {
	    return BigDecimal.valueOf(Double.parseDouble(number)).stripTrailingZeros().toPlainString();
	}
	/**
	 * Hex Decode
	 * 
	 * @param str
	 * @return
	 */
	public static byte[] decodeHex(String str)
	{
		byte[] bts = null;
		try
		{
			bts = Hex.decode(str);
		}
		catch (Exception e)
		{
			bts = new byte[0];
		}
		return bts;
	}
	/**
	 * Hex Decode
	 * 
	 * @param inbts
	 * @return
	 */
	public static byte[] decodeHex(byte[] inbts)
	{
		byte[] bts = null;
		try
		{
			bts = Hex.decode(inbts);
		}
		catch (Exception e)
		{
			bts = new byte[0];
		}
		return bts;
	}
	/**
	 * Hex Encode
	 * 
	 * @param bts
	 * @return
	 */
	public static byte[] encodeHex(byte[] bts)
	{
		byte[] rebts = null;
		try
		{
			rebts = Hex.decode(bts);
		}
		catch (Exception e)
		{
			rebts = new byte[0];
		}
		return rebts;
	}
	/**
	 * 返回UUID 等同.Net Guid.NewGuid()
	 * 
	 * @return
	 */
	public static String getUUID()
	{
		UUID uuid = UUID.randomUUID();
		String str = uuid.toString();
		return str;
	}
	/**
	 * 返回大写UUID 等同.Net Guid.NewGuid() 与Oracle数据库存储更加高效配合
	 * 
	 * @return
	 */
	public static String getUUIDUpper()
	{
		UUID uuid = UUID.randomUUID();
		String str = uuid.toString().toUpperCase();
		return str;
	}
	//内存回收及监控函数
	/**
	 * 打印内存使用情况
	 * @param title 标题
	 */
	public static void printMemoryUse(String title)
	{
		Runtime rtime = Runtime.getRuntime();
		long mem_total0 = rtime.totalMemory();
		long mem_free0 = rtime.freeMemory();
		long mem_used0 = mem_total0 - mem_free0;
		Date date = new Date();
		String now = Usual.mfAll.format(date);
		StringBuilder sb = new StringBuilder();
		sb.append(now);
		sb.append(" MemoryUsage(MB){Total(系统分配)|Free(空闲)|Used(使用)}: ");
		if (!Usual.isNullOrWhiteSpace(title))
		{
			sb.append("[");
			sb.append(title);
			sb.append("]");
		}
		sb.append("{");
		sb.append(mem_total0 / 0x100000 + "M|");
		sb.append(mem_free0 / 0x100000 + "M|");
		sb.append(mem_used0 / 0x100000+"M");
		sb.append("}");
		System.out.println(sb.toString());
	}
	/**
	 * 打印内存使用情况
	 */
	public static void printMemoryUse()
	{
		printMemoryUse(mEmpty);
	}
	/**
	 * 手动内存回收
	 */
	public static void collectGarbage()
	{
		printMemoryUse("System内存手动回收开始");
		long t0 = System.currentTimeMillis();
		// 垃圾回收
		System.gc();
		long t1 = System.currentTimeMillis();
		Date date = new Date();
		String now = Usual.mfAll.format(date);
		String printString = now.toString() + " Garbage Collected: " + (t1 - t0) + "ms";
		System.out.println(printString);
		printMemoryUse("System内存手动回收结束");
	}
	


	/**
	 * ���캯��
	 */
	public Usual()
	{
	}
	/**
	 * ��ִ̬�о�̬����,���غ���ִ�н��
	 * 
	 * @param clsName
	 * @param methodName
	 * @param types
	 * @param objs
	 * @return ִ�к���󷵻ض���
	 * @throws Exception
	 */
	public static Object f_evalMethodStatic(String clsName, String methodName, Class<?>[] types, Object[] objs)
	{
		Object rObj = null;
		Class<?> curClass = null;
		try
		{
			boolean mKeyState = false;
			cht_stLock.lock();
			try
			{
				mKeyState = cht_st.containsKey(clsName);
			}
			finally
			{
				cht_stLock.unlock();
			}
			// ����Class����,�´β��ö�̬���
			if (mKeyState)
			{
				curClass = cht_st.get(clsName);
			}
			else
			{
				// ���Class��̬������ö���
				curClass = Class.forName(clsName);
				if (curClass != null)
				{
					cht_stLock.lock();
					try
					{
						cht_st.put(clsName, curClass);
					}
					finally
					{
						cht_stLock.unlock();
					}
				}
				else
				{
					return rObj;
				}
			}
			rObj = f_evalMethodStatic(curClass, methodName, types, objs);
		}
		catch (Exception e)
		{
			rObj = null;
		}
		finally
		{
			curClass = null;
		}
		return rObj;
	}
	/**
	 * ��ִ̬�о�̬����,���غ���ִ�н��
	 * 
	 * @param cls
	 * @param methodName
	 * @param types
	 * @param objs
	 * @return ִ�к���󷵻ض���
	 */
	public static Object f_evalMethodStatic(Class<?> cls, String methodName, Class<?>[] types, Object[] objs)
	{
		Object rObj = null;
		Method mMethod = null;
		try
		{
			// ��ɶ�Ӧ����
			mMethod = cls.getMethod(methodName, types);
			if (mMethod != null)
			{
				// ִ�к���
				rObj = mMethod.invoke(null, objs);
			}
			else
			{
				String clsNameString = cls.getName();
				throw new Exception("Init Failed from class" + clsNameString + System.getProperty("line.seperator", "\n") + "method " + methodName + "exists.");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			rObj = null;
		}
		finally
		{
			mMethod = null;
		}
		return rObj;
	}
	/**
	 * ��ִ̬�о�̬����,���غ���ִ�н��
	 * 
	 * @param clsName
	 * @param methodName
	 * @param types
	 * @param objs
	 * @return ִ�к���󷵻ض���
	 * @throws Exception
	 */
	public static Object f_evalMethod(String clsName, String methodName, Class<?>[] types, Object[] objs)
	{
		Object rObj = null;
		Object obj = null;
		try
		{
			Class<?> curClass = null;
			boolean mKeyState = false;
			cht_objLock.lock();
			try
			{
				mKeyState = cht_obj.containsKey(clsName);
			}
			finally
			{
				cht_objLock.unlock();
			}
			// ����Class����,�´β��ö�̬���
			if (mKeyState)
			{
				obj = cht_obj.get(clsName);
			}
			else
			{
				curClass = Class.forName(clsName);
				obj = curClass.newInstance();
				if (obj != null)
				{
					cht_objLock.lock();
					try
					{
						cht_obj.put(clsName, obj);
					}
					finally
					{
						cht_objLock.unlock();
					}
				}
				else
				{
					return rObj;
				}
			}
			rObj = f_evalMethod(obj, methodName, types, objs);
		}
		catch (Exception e)
		{
			rObj = null;
		}
		finally
		{
			obj = null;
		}
		return rObj;
	}
	/**
	 * ��ִ̬�о�̬����,���غ���ִ�н��
	 * 
	 * @param cls
	 * @param methodName
	 * @param types
	 * @param objs
	 * @return ִ�к���󷵻ض���
	 */
	public static Object f_evalMethod(Object obj, String methodName, Class<?>[] types, Object[] objs)
	{
		Object rObj = null;
		Method nMethod = null;
		Class<?> clsClass = null;
		try
		{
			clsClass = obj.getClass();
			// ��ɶ�Ӧ����
			nMethod = clsClass.getMethod(methodName, types);
			if (nMethod != null)
			{
				// ִ�к���
				rObj = nMethod.invoke(obj, objs);
			}
			else
			{
				String clsNameString = clsClass.getName();
				throw new Exception("Init Failed from class" + clsNameString + System.getProperty("line.seperator", "\n") + "method " + methodName + "exists.");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			rObj = null;
		}
		finally
		{
			nMethod = null;
			clsClass = null;
		}
		return rObj;
	}
	/**
	 * String to Byte[],�����б���
	 * 
	 * @param str String���
	 * @param charset �ַ����
	 * @return byte[]����
	 */
	public static byte[] f_toBytes(String str, Charset charset)
	{
		byte[] bts = null;
		try
		{
			if (charset.name().equals(mUTF8Name))
			{
				bts = f_toBytes(str);
			}
			else
			{
				bts = str.getBytes(charset);
			}
		}
		catch (Exception e)
		{
			bts = new byte[0];
		}
		return bts;
	}
	/**
	 * String to Byte[],�����б���
	 * 
	 * @param str
	 * @param charset
	 * @return
	 */
	public static byte[] f_toBytes(String str, String charset)
	{
		byte[] bts = null;
		try
		{
			if (charset.toUpperCase().equals(mUTF8Name))
			{
				bts = f_toBytes(str);
			}
			else
			{
				bts = str.getBytes(charset);
			}
		}
		catch (Exception e)
		{
			bts = new byte[0];
		}
		return bts;
	}
	/**
	 * String to Byte[],�����б���,utf-8��ʽ
	 * 
	 * @param str String���
	 * @return byte[]����
	 */
	public static byte[] f_toBytes(String str)
	{
		byte[] bts = null;
		try
		{
			bts = Strings.toUTF8ByteArray(str);
			// bts = str.getBytes(mCharset_utf8);
		}
		catch (Exception e)
		{
			bts = new byte[0];
		}
		return bts;
	}
	/**
	 * byte[]ת��ΪString���
	 * 
	 * @param bts byte[]���
	 * @param charset �����ʽ
	 * @return String���
	 */
	public static String f_fromBytes(byte[] bts, Charset charset)
	{
		String str = Usual.mEmpty;
		try
		{
			if (charset.name().equals(mUTF8Name))
			{
				str = f_fromBytes(bts);
			}
			else
			{
				str = new String(bts, charset);
			}
		}
		catch (Exception e)
		{
			str = Usual.mEmpty;
		}
		return str;
	}
	/**
	 * byte[]ת��ΪString���,Ĭ��utf-8�ַ��ʽ
	 * 
	 * @param bts byte[]���
	 * @return String���
	 */
	public static String f_fromBytes(byte[] bts)
	{
		String str = new String(bts);
		try
		{
			str = Strings.fromUTF8ByteArray(bts);
			// str = new String(bts, mCharset_utf8);
		}
		catch (Exception e)
		{
			str = Usual.mEmpty;
		}
		return str;
	}
	/**
	 * byte[]���ת��ΪBase64String��ݸ�ʽString
	 * 
	 * @param bts byte[]���
	 * @return Base64String��ݸ�ʽString
	 */
	public static String f_toBase64String(byte[] bts)
	{
		String str = Usual.mEmpty;
		if (Usual.f_isNullOrZeroBytes(bts))
		{
			return str;
		}
		// BASE64Encoder encoder = new BASE64Encoder();
		try
		{
			byte[] mBytes_Base64 = org.bouncycastle.util.encoders.Base64.encode(bts);
			// str =Usual.f_fromBytes(bts);
			str = Strings.fromUTF8ByteArray(mBytes_Base64);
			// str = encoder.encode(bts);
		}
		catch (Exception e)
		{
			str = Usual.mEmpty;
		}
		// finally
		// {
		// encoder = null;
		// }
		return str;
	}
	/**
	 * Base64String��ݸ�ʽStringת��Ϊbyte[]���
	 * 
	 * @param base64str Base64String��ݸ�ʽString
	 * @return byte[]���
	 */
	public static byte[] f_fromBase64String(String base64str)
	{
		byte[] bts = new byte[0];
		if (Usual.f_isNullOrWhiteSpace(base64str))
		{
			return bts;
		}
		// BASE64Decoder decoder = new BASE64Decoder();
		try
		{
			// bts = decoder.decodeBuffer(base64str);
			bts = org.bouncycastle.util.encoders.Base64.decode(base64str);
		}
		catch (Exception e)
		{
			bts = new byte[0];
		}
		// finally
		// {
		// decoder = null;
		// }
		return bts;
	}
	/**
	 * �ж��ַ�Ϊnull����Ϊ"",����true;���򷵻�false
	 * 
	 * @param instr
	 * @return
	 */
	public static Boolean f_isNullOrEmpty(String instr)
	{
		if (instr == null || instr.length() == 0)
		{
			return true;
		}
		return false;
	}
	/**
	 * �ж��ַ�Ϊnull����Ϊ""�����ɿհ����,����true;���򷵻�false
	 * 
	 * @param instr
	 * @return
	 */
	public static Boolean f_isNullOrWhiteSpace(String instr)
	{
		if (instr == null || instr.trim().length() == 0)
		{
			return true;
		}
		return false;
	}
	/**
	 * �ж�byte[]�����Ƿ�Ϊnull���߳���Ϊ0
	 * 
	 * @param bts
	 * @return
	 */
	public static Boolean f_isNullOrZeroBytes(byte[] bts)
	{
		if (bts == null || bts.length == 0)
		{
			return true;
		}
		return false;
	}
	/**
	 * �ж�String[]�����Ƿ�Ϊnull���߳���Ϊ0
	 * 
	 * @param bts
	 * @return
	 */
	public static Boolean f_isNullOrZeroStrings(byte[] bts)
	{
		if (bts == null || bts.length == 0)
		{
			return true;
		}
		return false;
	}
	/**
	 * ȥ���ַ�հ�
	 * 
	 * @param instring
	 * @return
	 */
	public static String f_stringTrim(String instring)
	{
		String outstring = Usual.mEmpty;
		try
		{
			outstring = instring.trim();
		}
		catch (Exception e)
		{
			return Usual.mEmpty;
		}
		return outstring;
	}
	/**
	 * Object对象转成String
	 * 
	 * @param obj
	 * @return 为null时返回""
	 */
	public static String f_getString(Object obj)
	{
		return f_getString(obj, Usual.mEmpty);
	}
	/**
	 * Object对象转成String
	 * 
	 * @param obj
	 * @param defaultValue 为null时返回的默认值
	 * @return
	 */
	public static String f_getString(Object obj, String defaultValue)
	{
		try
		{
			return obj == null ? defaultValue : obj.toString();
		}
		catch (Exception exp)
		{
			logger.error("[" + obj + "]转成字符串失败", exp);
			return Usual.mEmpty;
		}
	}
	/**
	 * Object对象转成Integer
	 * 
	 * @param obj
	 * @return 为null时返回0
	 */
	public static int f_getInteger(Object obj)
	{
		return f_getInteger(obj, 0);
	}
	/**
	 * Object对象转成Integer
	 * 
	 * @param obj
	 * @param defaultValue 为null时返回的默认值
	 * @return
	 */
	public static int f_getInteger(Object obj, int defaultValue)
	{
		try
		{
			return obj == null || f_isNullOrEmpty(obj.toString()) ? defaultValue : Integer.valueOf(obj.toString());
		}
		catch (NumberFormatException exp)
		{
			logger.error("[" + obj + "]转成Integer失败", exp);
			return 0;
		}
	}
	/**
	 * Object对象转成Long
	 * 
	 * @param obj
	 * @return 为null时返回0
	 */
	public static long f_getLong(Object obj)
	{
		return f_getLong(obj, 0);
	}
	/**
	 * Object对象转成Long
	 * 
	 * @param obj
	 * @param defaultValue 为null时返回的默认值
	 * @return
	 */
	public static long f_getLong(Object obj, long defaultValue)
	{
		try
		{
			return obj == null || f_isNullOrEmpty(obj.toString()) ? 0 : Long.valueOf(obj.toString());
		}
		catch (NumberFormatException exp)
		{
			logger.error("[" + obj + "]转成Long失败", exp);
			return 0;
		}
	}
	/**
	 * Object对象转成Float
	 * 
	 * @param obj
	 * @return 为null时返回0
	 */
	public static float f_getFloat(Object obj)
	{
		return f_getFloat(obj, 0);
	}
	/**
	 * Object对象转成Float
	 * 
	 * @param obj
	 * @param defaultValue 为null时返回的默认值
	 * @return
	 */
	public static float f_getFloat(Object obj, float defaultValue)
	{
		try
		{
			return obj == null || f_isNullOrEmpty(obj.toString()) ? defaultValue : Float.valueOf(obj.toString());
		}
		catch (NumberFormatException exp)
		{
			logger.error("[" + obj + "]转成Float失败", exp);
			return 0;
		}
	}
	/**
	 * Object对象转成Double
	 * 
	 * @param obj
	 * @return 为null时返回0
	 */
	public static double f_getDouble(Object obj)
	{
		return f_getDouble(obj, 0);
	}
	/**
	 * Object对象转成Double
	 * 
	 * @param obj
	 * @param defaultValue 为null时返回的默认值
	 * @return
	 */
	public static double f_getDouble(Object obj, double defaultValue)
	{
		try
		{
			return obj == null || f_isNullOrEmpty(obj.toString()) ? defaultValue : Double.valueOf(obj.toString());
		}
		catch (NumberFormatException exp)
		{
			logger.error("[" + obj + "]转成Double失败", exp);
			return 0;
		}
	}
	/**
	 * ��ʽ��SQL�ַ�
	 * 
	 * @param instring
	 * @return
	 */
	public static String f_formatStringSQL(String instring)
	{
		String obj = instring.replace('\n', mBlankSpaceChar).replace('\r', mBlankSpaceChar)
		// .replace('\"', mBlankSpaceChar)
				.replace('\t', mBlankSpaceChar).trim();
		return obj;
	}
	/**
	 * ��ʽ���ַ�ΪJSON���Խ��ܵĸ�ʽ �滻������ \n \r \t ""��
	 * 
	 * @param instring
	 * @return
	 */
	public static String f_formatStringJson(String instring)
	{
		String obj = instring.replace("\n", "\\n").replace("\r", "\\r").replace("\t", "\\t").replace("\"", "\\\"").trim();
		return obj;
	}
	/**
	 * ��Ϣ��ʾ��ʽ����
	 * 
	 * @param instr
	 * @return
	 */
	public static String f_formatMessage(String msg)
	{
		String obj = msg.replace("\"", "\'");
		return obj;
	}
	/**
	 * Hex Decode
	 * 
	 * @param str
	 * @return
	 */
	public static byte[] f_decodeHex(String str)
	{
		byte[] bts = null;
		try
		{
			bts = Hex.decode(str);
		}
		catch (Exception e)
		{
			bts = new byte[0];
		}
		return bts;
	}
	/**
	 * Hex Decode
	 * 
	 * @param inbts
	 * @return
	 */
	public static byte[] f_decodeHex(byte[] inbts)
	{
		byte[] bts = null;
		try
		{
			bts = Hex.decode(inbts);
		}
		catch (Exception e)
		{
			bts = new byte[0];
		}
		return bts;
	}
	/**
	 * Hex Encode
	 * 
	 * @param bts
	 * @return
	 */
	public static byte[] f_encodeHex(byte[] bts)
	{
		byte[] rebts = null;
		try
		{
			rebts = Hex.decode(bts);
		}
		catch (Exception e)
		{
			rebts = new byte[0];
		}
		return rebts;
	}
	/**
	 * ��ӡ�ڴ�ʹ�����
	 * 
	 * @param title ����
	 */
	public static void f_printMemoryUse(String title)
	{
		Runtime rtime = Runtime.getRuntime();
		long mem_total0 = rtime.totalMemory();
		long mem_free0 = rtime.freeMemory();
		long mem_used0 = mem_total0 - mem_free0;
		Date date = new Date();
		String now = Usual.mfAll.format(date);
		StringBuilder sb = new StringBuilder();
		sb.append(now);
		sb.append(" MemoryUsage(MB){Total|Free|Used}: ");
		if (!Usual.f_isNullOrWhiteSpace(title))
		{
			sb.append("[");
			sb.append(title);
			sb.append("]");
		}
		sb.append("{");
		sb.append(mem_total0 / 0x100000 + "|");
		sb.append(mem_free0 / 0x100000 + "|");
		sb.append(mem_used0 / 0x100000);
		sb.append("}");
		System.out.println(sb.toString());
	}
	/**
	 * ��ӡ�ڴ�ʹ�����
	 */
	public static void f_printMemoryUse()
	{
		f_printMemoryUse(mEmpty);
	}
	/**
	 * �ֶ��ڴ����
	 */
	public static void f_collectGarbage()
	{
		f_printMemoryUse("�ڴ��ֶ����տ�ʼ");
		long t0 = System.currentTimeMillis();
		// �������
		System.gc();
		long t1 = System.currentTimeMillis();
		Date date = new Date();
		String now = Usual.mfAll.format(date);
		String printString = now.toString() + " Garbage Collected: " + (t1 - t0) + "ms";
		System.out.println(printString);
		f_printMemoryUse("�ڴ��ֶ����ս���");
	}
	/**
	 * ����UUID ��ͬ.Net Guid.NewGuid()
	 * 
	 * @return
	 */
	public static String f_getUUID()
	{
		UUID uuid = UUID.randomUUID();
		String str = uuid.toString();
		return str;
	}
	/**
	 * ���ش�дUUID ��ͬ.Net Guid.NewGuid() ��Oracle��ݿ�洢��Ӹ�Ч���
	 * 
	 * @return
	 */
	public static String f_getUUIDUpper()
	{
		UUID uuid = UUID.randomUUID();
		String str = uuid.toString().toUpperCase();
		return str;
	}
	/**
	 * ��ȡ����IP��ַ
	 * 
	 * @return
	 */
	public static String f_getLocalIpAddress()
	{
		try
		{
			for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();)
			{
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();)
				{
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress())
					{
						return inetAddress.getHostAddress().toString();
					}
				}
			}
		}
		catch (SocketException ex)
		{
			ex.printStackTrace();
		}
		return null;
	}
	/**
	 * 兼容Linux下WebLogic读取配置文件,补充/
	 * 
	 * @param RootPath
	 * @return
	 */
	public static String getRealRoot(String RootPath)
	{
		if (!Usual.f_isNullOrWhiteSpace(RootPath))
		{
			if (!RootPath.endsWith("/") && !RootPath.endsWith("\\"))
			{
				return RootPath + "/";
			}
		}
		return RootPath;
	}
	/**
	 * 有小数则根据index去保存几位，传入整数则去小数点返回整数
	 * @param d 
	 * @param index 位数
	 * @return
	 */
	public static Object doubleTrans(Double d, int index){
		if(d == null){
			return 0;
		}
		if (Math.round(d) - d == 0) {
			return d.longValue();
		} else {
			DecimalFormat df = null;
			if(index <= 0 ){
				df = new DecimalFormat("0"); // 设置double类型小数点后位数格式
				return d.longValue();
			}
			String srt = "0.";
			for (int x = 0; x < index; x++) {
				srt += "0";
			}
			df = new DecimalFormat(srt);
			return  Double.valueOf(df.format(d));
		}
	}
	
	/**
	 * 获取异常的堆栈信息
	 * 
	 * @param t
	 * @return
	 */
	public static String getStackTrace(Throwable t) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);

		try {
			t.printStackTrace(pw);
			return sw.toString();
		} finally {
			pw.close();
		}
	}
	
	/**
	 * 使用java正则表达式去掉多余的.与0 
	 * @param s
	 * @return
	 */
    public static String subZeroAndDot(String s){
        if(s.indexOf(".") > 0){  
            s = s.replaceAll("0+?$", "");//去掉多余的0  
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉  
        }  
        return s;  
    }

	/***
	 * 获取客户端ip地址(可以穿透代理)
	 * @param request
	 * @return
	 */
	public static String getClientIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_CLUSTER_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_FORWARDED");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_VIA");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("REMOTE_ADDR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * 将数字转成ip地址
	 *
	 * @param IpNum 数字
	 * @return 转换后的ip地址
	 */
	public static String f_IntToIp(long IpNum) {
		long mask[] = { 0x000000FF, 0x0000FF00, 0x00FF0000, 0xFF000000 };
		long num = 0;
		StringBuffer ipInfo = new StringBuffer();
		for (int i = 0; i < 4; i++) {
			num = (IpNum & mask[i]) >> (i * 8);
			if (i > 0) {
				ipInfo.insert(0, ".");
			} else {
				ipInfo.insert(0, Long.toString(num, 10));
			}
		}
		return ipInfo.toString();
	}

	/**
	 * 将ip 地址转换成数字
	 *
	 * @param ipAddress 传入的ip地址
	 * @return 转换成数字类型的ip地址
	 */
	public static long f_IpToInt(String ipAddress) {
		String[] ip = ipAddress.split("\\.");
		long a = Integer.parseInt(ip[0]);
		long b = Integer.parseInt(ip[1]);
		long c = Integer.parseInt(ip[2]);
		long d = Integer.parseInt(ip[3]);

		long ipNum = a * 256 * 256 * 256 + b * 256 * 256 + c * 256 + d;
		return ipNum;
	}

	/**
	 * 过滤ip地址
	 * @param ipAddr ip地址
	 * @param filterIpAddrList 过滤的ip地址/ip地址段列表
	 * @return true 过滤  false 不过滤
	 */
	public static Boolean ipAddrFilter(String ipAddr, List<String> filterIpAddrList) {
		Boolean isFilter = false;
		for (String filterIpAddr : filterIpAddrList) {
			//判断是否有"-",有的话是ip地址段，没有的话是ip地址
			if (filterIpAddr.indexOf("-") == -1) {
				// 判断是否过滤
				isFilter = f_IpToInt(ipAddr) == f_IpToInt(filterIpAddr);
				// 需要过滤则直接返回
				if (isFilter) {
					return isFilter;
				}
			} else {
				//分割ip地址段
				String[] fIpAddrArr = filterIpAddr.split("\\-", 2);
				//获取最小、最大两个ip地址
				String fIpAddr1 = fIpAddrArr[0].trim();
				String fIpAddr2 = fIpAddrArr[1].trim();
				// 判断是否在过滤列表ip内
				isFilter = f_IpToInt(ipAddr) >= f_IpToInt(fIpAddr1) && f_IpToInt(ipAddr) <= f_IpToInt(fIpAddr2);
				// 需要过滤则直接返回
				if (isFilter) {
					return isFilter;
				}
			}
		}
		return isFilter;
	}
}
