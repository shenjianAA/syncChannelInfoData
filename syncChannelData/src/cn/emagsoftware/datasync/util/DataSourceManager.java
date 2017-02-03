package cn.emagsoftware.datasync.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import javax.sql.DataSource;

public class DataSourceManager {
   public static DataSource getDataSoucre(String dataSourceName) {
      return new ComboPooledDataSource(dataSourceName);
   }

   public static DataSource getChannelDataSoucre() {
      return new ComboPooledDataSource("ChannelDBConfig");
   }
}
