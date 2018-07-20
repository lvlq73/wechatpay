package com.wechat.utils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.Writer;
import java.util.HashMap;
import java.util.List;

/**
 * 转换工具类
 */
public class ConvertUtil {

    private static XStream  xstream = new XStream();
    /**
     * 扩展xstream，使其支持CDATA块
     */
    private static XStream xstreamCDATA = new XStream(new XppDriver() {
        public HierarchicalStreamWriter createWriter(Writer out) {
            return new PrettyPrintWriter(out) {
                // 对全部xml节点的转换都添加CDATA标记
                boolean cdata = true;
                public void startNode(String name, Class clazz) {
                    super.startNode(name, clazz);
                    if(name.equals("CreateTime")){
                        cdata = false;
                    }
                }
                protected void writeText(QuickWriter writer, String text) {
                    if (cdata) {
                        writer.write("<![CDATA[");
                        writer.write(text);
                        writer.write("]]>");
                    } else {
                        writer.write(text);
                    }
                }
            };
        }
    });
    /**
     * map转xml
     * @param map
     * @return
     */
    public static String mapToXml(HashMap<String,Object> map) {
        xstream.alias("xml", map.getClass());
        return xstream.toXML(map);
    }
    /**
     * object 转xml (有CDATA标记)
     * @param obj
     * @return
     */
    public static String objectToXmlCDATA(Object obj) {
        xstreamCDATA.alias("xml", obj.getClass());
        return xstreamCDATA.toXML(obj);
    }
    /**
     * object 转xml
     * @param obj
     * @return
     */
    public static String objectToXml(Object obj) {
        xstream.alias("xml", obj.getClass());
        return xstream.toXML(obj);
    }
    /**
     * xml转map对象
     * @param xml
     * @return
     */
    public static HashMap<String,Object> readStringXmlOut(String xml) {
        HashMap<String,Object> map = new HashMap<String,Object>();
        Document doc = null;
        try {
            doc = DocumentHelper.parseText(xml); // 将字符串转为XML
            Element rootElt = doc.getRootElement(); // 获取根节点
            List<Element> list = rootElt.elements();//获取根节点下所有节点
            for (Element element : list) {  //遍历节点
                map.put(element.getName(), element.getText()); //节点的name为map的key，text为map的value
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
