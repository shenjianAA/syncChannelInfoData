package cn.emagsoftware.datasync.base;

import cn.emagsoftware.datasync.util.SqlXmlCache;

public class BaseSyncMain {
   protected static void loadSqlCache() {
	   // ======================================================================================
       // 加载SQL语句配制XML并解析为缓存对象
       SqlXmlCache.readSqlXmlCache();
       // ======================================================================================
   }
}
