package cn.emagsoftware.datasync.util;

public class Constants {
   public static final String SQL_DBCONFIG_GET_SYNC_INFO = "get_sync_info";
   public static final String COLUMN_SYNC_INFO_ID = "ID";
   public static final String COLUMN_SYNC_INFO_TABLE_NAME = "TABLE_NAME";
   public static final String COLUMN_SYNC_INFO_COLUMN_NAME = "COLUMN_NAME";
   public static final String COLUMN_SYNC_INFO_FROM_TABLE_NAME = "FROM_TABLE_NAME";
   public static final String COLUMN_SYNC_INFO_FROM_COLUMN_NAME = "FROM_COLUMN_NAME";
   public static final String COLUMN_SYNC_INFO_SYNC_REMARK = "SYNC_REMARK";
   public static final String COLUMN_SYNC_INFO_CREATE_TIME = "CREATE_TIME";
   public static final String COLUMN_SYNC_INFO_CONDITION = "CONDITION";
   public static final String COLUMN_SYNC_INFO_SYNC_TYPE = "SYNC_TYPE";
   public static final String SAVE_SYNC_LOG = "save_sync_log";
   public static final String SQL_GET_SYNC_CHANNEL_INFO = "get_sync_channelinfo";
   public static final String SQL_GET_SYNC_CHANNEL_PRODUCT = "get_sync_channel_product";
   public static final int SQL_FETCH_SIZE = 10000;
   public static final String RESULT_FAIL = "-1";
   public static final String RESULT_SUCCESS = "1";
   public static final String SYNC_TYPE_ALL_1 = "1";
   public static final String SYNC_TYPE_INCREMENT_2 = "2";
   public static final String COLUMN_CHANNEL_ID = "ID";
   public static final String COLUMN_CHANNEL_PARENT_ID = "PARENT_CHANNEL_ID";
   public static final String COLUMN_CHANNEL_NAME = "CHANNEL_NAME";
   public static final String COLUMN_CHANNEL_STATE = "CHANNEL_STATE0";
   public static final String COLUMN_CHANNEL_CORP_ID = "CORP_ID";
   public static final String COLUMN_CHANNEL_CORP_NAME = "company_name_cn";
   public static final String COLUMN_CHANNEL_CORP_STATUS = "company_status0";
   public static final String COLUMN_CHANNEL_FLAG = "CHANNEL_CORP_TYPE";
   public static final String COLUMN_CHANNEL_CLASSCODE = "classCode";
   public static final String COLUMN_CHANNEL_SUB_CLASSCODE = "subClassCode";
   public static final String COLUMN_CHANNEL_FIRST_PROP = "firstProp";
   public static final String COLUMN_CHANNEL_SECOND_PROP = "secondProp";
   public static final String COLUMN_CHANNEL_THIRD_PROP = "thirdProp";
   public static final String COLUMN_CHANNEL_FOUR_PROP = "fourProp";
   public static final String COLUMN_CHANNEL_CHILD_SERVICEID = "childServiceId";

   public static enum PROPERTY {
	   ;
      public static final String REPORT_FROM = "reportFrom.properties";
   }

   public static enum PROPERTY_KEY {
	   ;
      public static final String CHANNEL_FTP_IP = "channel_ftp_ip";
      public static final String CHANNEL_FTP_PORT = "channel_ftp_port";
      public static final String CHANNEL_FTP_USERNAME = "channel_ftp_username";
      public static final String CHANNEL_FTP_PASSWORD = "channel_ftp_password";
      public static final String CAHNNEL_FTP_CHANNEL_PRODUCT_PATH = "channel_ftp_channel_product_path";
   }
}
