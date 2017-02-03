package cn.emagsoftware.datasync.dao;

import cn.emagsoftware.datasync.base.BaseSyncDao;
import cn.emagsoftware.datasync.base.DataSyncInfo;
import cn.emagsoftware.datasync.base.SyncChannelInfoData;
import cn.emagsoftware.datasync.base.SyncChannelProductData;
import cn.emagsoftware.datasync.util.CommonUtils;
import cn.emagsoftware.datasync.util.Constants;
import cn.emagsoftware.datasync.util.DataSourceManager;
import cn.emagsoftware.datasync.util.SqlXmlCache;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class DataSyncDao extends BaseSyncDao {
   private static final Logger logger = Logger.getLogger(DataSyncDao.class);
   private static int sum = 0;
   Connection sourceConn = null;
   PreparedStatement sourceStatement = null;
   ResultSet sourceResultSet = null;
   long logId;

   public List<SyncChannelInfoData> getSyncChannelInfo(long logId) {
      ArrayList<SyncChannelInfoData> infoList = new ArrayList<SyncChannelInfoData>();
      Connection dbConn = null;

      try {
         logger.info(logId + "获取渠道库连接");
         dbConn = DataSourceManager.getChannelDataSoucre().getConnection();
         PreparedStatement e = dbConn.prepareStatement(SqlXmlCache.getSql(Constants.SQL_GET_SYNC_CHANNEL_INFO));
         logger.info(logId + "查询此次同步的表、字段信息，取得结果集");
         ResultSet dbResultSet = e.executeQuery();

         while(dbResultSet.next()) {
            SyncChannelInfoData info = new SyncChannelInfoData();
            info.setId(dbResultSet.getString("ID"));
            info.setParentChannelId(dbResultSet.getString("PARENT_CHANNEL_ID"));
            info.setChannelName(dbResultSet.getString("CHANNEL_NAME"));
            info.setChannelStatus(dbResultSet.getString("CHANNEL_STATE0"));
            info.setChannelCorpID(dbResultSet.getString("CORP_ID"));
            info.setChannelCorpName(dbResultSet.getString("company_name_cn"));
            info.setChannelCorpStatus(dbResultSet.getString("company_status0"));
            info.setChannelFlag(dbResultSet.getString("CHANNEL_CORP_TYPE"));
            info.setClassCode(dbResultSet.getString("classCode"));
            info.setSubClassCode(dbResultSet.getString("subClassCode"));
            info.setFirstProp(" ");
            info.setSecondProp(" ");
            info.setThirdProp(" ");
            info.setFourProp(" ");
            info.setChildServiceId(" ");
            infoList.add(info);
         }
      } catch (Exception var16) {
         logger.info("查询渠道基本信息同步数据异常..." + var16);
      } finally {
         if(dbConn != null) {
            try {
               dbConn.close();
            } catch (SQLException var15) {
               logger.error(logId + "关闭连接异常，SQLException！", var15);
            }
         }

      }

      logger.info("渠道基本信息同步查查询结束.....");
      return infoList;
   }

   public List<SyncChannelProductData> getSyncChannelProduct(long logId) {
      ArrayList<SyncChannelProductData> infoList = new ArrayList<SyncChannelProductData>();
      Connection dbConn = null;

      try {
         logger.info(logId + "获取渠道库连接");
         dbConn = DataSourceManager.getChannelDataSoucre().getConnection();
         PreparedStatement e = dbConn.prepareStatement(SqlXmlCache.getSql(Constants.SQL_GET_SYNC_CHANNEL_PRODUCT));
         logger.info(logId + "查询此次渠道产品内容同步的表、字段信息，取得结果集");
         ResultSet dbResultSet = e.executeQuery();

         while(dbResultSet.next()) {
            SyncChannelProductData info = new SyncChannelProductData();
            info.setChannelCode(dbResultSet.getString("CHANNEL_ID"));
            info.setContentCode(dbResultSet.getString("CONTENTID"));
            info.setCooperateCode(dbResultSet.getString("SP_CODE"));
            info.setProductCode(dbResultSet.getString("PRODUCTCODE"));
            info.setStatus(dbResultSet.getString("STATUS"));
            info.setType(Integer.valueOf(Integer.parseInt(dbResultSet.getString("TYPE"))));
            infoList.add(info);
         }
      } catch (Exception var16) {
         logger.info(logId + "查询此次渠道产品内容同步的表、字段信息异常..", var16);
      } finally {
         if(dbConn != null) {
            try {
               dbConn.close();
            } catch (SQLException var15) {
               logger.error(logId + "关闭连接异常，SQLException！", var15);
            }
         }

      }

     logger.info("渠道内容产品信息同步查询结束.....");
      return infoList;
   }

   public int getSyncSum() {
      return sum;
   }

   /**
    * @param info
    * @param type
    *            1:查询语句，2：插入语句，3：删除语句
    * @param params
    *            查询条件中的参数
    * @return
    */
   @SuppressWarnings("unused")
   private String getSql(DataSyncInfo info, int type) {
       String sql = "";
       switch (type) {
           case 1:
               sql = "select " + info.getFromColumnName() + " from " + info.getFromTableName();
               if (!CommonUtils.isNull(info.getCondition()))
                   sql = sql + "  " + info.getCondition();
               break;
           case 2:
               String[] columns = info.getColumnName().split(",");
               sql = "insert into  " + info.getTableName() + "(" + info.getColumnName() + ") values(";
               for (String string : columns) {
                   sql += "?,";
               }
               sql = sql.substring(0, sql.length() - 1) + ")";
               break;
           case 3:
               sql = "DELETE FROM " + info.getTableName();
               break;
           default:
               break;
       }
       logger.info(logId+ "执行sql：" + sql);
       return sql;
   }
}
