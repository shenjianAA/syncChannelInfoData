package cn.emagsoftware.datasync.util;

import cn.emagsoftware.datasync.base.DataSyncInfo;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class CommonUtils {
   private static Logger logger = Logger.getLogger("errorlog");
   private static SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");

   public static final String getThrowableStackTrace(Throwable e) {
      StringBuffer sb = new StringBuffer();
      sb.append(e.toString());
      StackTraceElement[] ses = e.getStackTrace();
      StackTraceElement[] var6 = ses;
      int var5 = ses.length;

      for(int var4 = 0; var4 < var5; ++var4) {
         StackTraceElement msg = var6[var4];
         sb.append(getLineSeparator());
         sb.append(msg.toString());
      }

      String var7 = sb.toString();
      if(var7.length() > 4000) {
         var7 = var7.substring(0, 4000);
      }

      return var7;
   }

   public static final String getLineSeparator() {
      return System.getProperty("line.separator");
   }

   public static long getLogId() {
      long time = System.currentTimeMillis();
      Random rdm = new Random();
      return time + (long)rdm.nextInt(100);
   }

   public static boolean isNull(Object[] objs) {
      if(objs == null) {
         return true;
      } else {
         for(int i = 0; i < objs.length; ++i) {
            if(objs[i] != null && !"".equals(objs[i].toString().trim())) {
               return false;
            }
         }

         return true;
      }
   }

   public static boolean isNull(String str) {
      return str == null || "".equals(str.trim());
   }

   public static String checkDataSyncInfo(DataSyncInfo info) {
      return isNull(info.getTableName())?"table_name is null ":(isNull(info.getColumnName())?"column_name is null":(info.getColumnName().trim().indexOf(" ") != -1?"column_name has space":(isNull(info.getFromTableName())?"from_table_name is null":(info.getFromColumnName().trim().indexOf(" ") != -1?"from_column_name has space":(isNull(info.getFromColumnName())?"from_column_name is null":(isNull(info.getSyncType())?"sync_type is null":(!"1".equals(info.getSyncType()) && !"2".equals(info.getSyncType())?"sync_type auth fail":(!isNull(info.getCondition()) && !info.getCondition().trim().substring(0, 5).equalsIgnoreCase("where")?"condition not starts with \'where\'":""))))))));
   }

   public static String[] getPropertyValue(String fileName, String... keys) {
      String[] values = new String[keys.length];

      try {
         Properties e = PropertiesLoaderUtils.loadProperties(new ClassPathResource(fileName));

         for(int i = 0; i < keys.length; ++i) {
            values[i] = e.getProperty(keys[i]);
         }
      } catch (IOException var5) {
         logger.error("从配置文件中读取属性值异常", var5);
      }

      return values;
   }

   public static String getStringyyyyMMdd(Date date) {
      return yyyyMMdd.format(date);
   }
}
