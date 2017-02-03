package cn.emagsoftware.datasync.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public final class SqlXmlCache {
   private static final Logger logger = Logger.getLogger(SqlXmlCache.class);
   private static final String xmlName = "sql/sql.xml";
   private static Map sqlXmlMap = new HashMap();

   public static synchronized void readSqlXmlCache() {
      logger.info("启动，读取记录所有SQL语句的XML信息！");
      InputStream is = null;

      try {
         ClassLoader e = Thread.currentThread().getContextClassLoader();
         is = e.getResourceAsStream("sql/sql.xml");
         DocumentBuilderFactory domfac = DocumentBuilderFactory.newInstance();
         DocumentBuilder dombuilder = domfac.newDocumentBuilder();
         Document doc = dombuilder.parse(is);
         Element root = doc.getDocumentElement();
         NodeList books = root.getElementsByTagName("sql");
         if(books != null) {
            for(int i = 0; i < books.getLength(); ++i) {
               Node nodeItem = books.item(i);
               Node attrNode = nodeItem.getAttributes().getNamedItem("id");
               if(attrNode == null) {
                  throw new RuntimeException("解析XML失败，节点SQL上缺少id属性!");
               }

               String nodeValue = attrNode.getNodeValue();
               String sql = nodeItem.getTextContent().trim();
               sqlXmlMap.put(nodeValue, sql);
            }
         }
      } catch (Exception var20) {
         logger.error("ERROR:读取SQL文件信息出错。", var20);
      } finally {
         if(is != null) {
            try {
               is.close();
            } catch (IOException var19) {
               logger.error("ERROR:关闭SQL文件信息出错。", var19);
            }
         }

      }

   }

   public static Map getSqlXmlCache() {
      return sqlXmlMap;
   }

   public static String getSql(String sqlId) {
      if(sqlXmlMap != null && !sqlXmlMap.isEmpty()) {
         Iterator var2 = sqlXmlMap.entrySet().iterator();

         while(var2.hasNext()) {
            Entry entry = (Entry)var2.next();
            if(((String)entry.getKey()).equals(sqlId.toLowerCase(Locale.FRENCH))) {
               return (String)entry.getValue();
            }
         }
      }

      return null;
   }
}
