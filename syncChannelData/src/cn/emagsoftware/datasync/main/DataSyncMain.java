package cn.emagsoftware.datasync.main;

import cn.emagsoftware.datasync.base.BaseSyncMain;
import cn.emagsoftware.datasync.base.SyncChannelInfoData;
import cn.emagsoftware.datasync.base.SyncChannelProductData;
import cn.emagsoftware.datasync.dao.DataSyncDao;
import cn.emagsoftware.datasync.util.CommonUtils;
import cn.emagsoftware.datasync.util.Constants;
import cn.emagsoftware.datasync.util.FtpUtil;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;

public class DataSyncMain extends BaseSyncMain {
   private static final Logger logger = Logger.getLogger(DataSyncMain.class);
   private static FtpUtil ftpUtil = new FtpUtil();

   public static void main(String[] args) {
      long logId = CommonUtils.getLogId();
      logger.info(logId + "-->" + "<<<<<<<<<<<<<<<<<<<<<<<<<<数据库表同步开始>>>>>>>>>>>>>>>>>>>>>>>>>>");
      logger.info(logId + "-->" + "开始加载SQL信息到缓存");
      loadSqlCache();
      logger.info(logId + "-->" + "加载SQL信息到缓存完成");
      List<SyncChannelInfoData> infoList = null;
      List<SyncChannelProductData> proList = null;

      try {
         DataSyncDao e = new DataSyncDao();
         logger.info(logId + "-->" + "-------------开始查询同步数据信息-------------");
         infoList = e.getSyncChannelInfo(logId);
         proList = e.getSyncChannelProduct(logId);
         if(infoList != null && infoList.size() > 0 || proList != null && proList.size() > 0) {
            boolean flag = uploadFileToFtp(infoList, proList, logId);
            if(flag) {
               logger.info("渠道信息同步到渠道FTP成功");
            } else {
               logger.info("渠道信息同步到渠道FTP失败");
            }
         } else {
            if(infoList == null || infoList.size() == 0) {
               logger.info(logId + "-->" + "该批次没有需要同步的渠道数据");
            }

            if(proList == null || proList.size() == 0) {
               logger.info(logId + "-->" + "该批次没有需要同步的渠道内容数据");
            }
         }
      } catch (Exception e) {
         logger.error(logId + "-->" + "同步表数据失败，错误信息:", e);
      }

      logger.info(logId + "-->" + "<<<<<<<<<<<<<<<<<<<<<<<<<数据库表同步结束>>>>>>>>>>>>>>>>>>>>>>>>>>");
   }

   public static boolean uploadFileToFtp(List<SyncChannelInfoData> infoList, List<SyncChannelProductData> proList, long logId) {
      boolean flag = true;
      String nowDate = CommonUtils.getStringyyyyMMdd(new Date());
      StringBuffer sb = new StringBuffer();
      File infoFile = null;
      File proFile = null;
      String chFilename = sb.append("channelInfo").append("_").append(nowDate).append("_").append("001").toString();
      String proFilename = "channelProduct" + "_" + nowDate + "_" + "002";
      infoFile = new File(chFilename);
      proFile = new File(proFilename);
      FileOutputStream fost = null;
      OutputStreamWriter psw = null;
      FileOutputStream fos = null;
      OutputStreamWriter osw = null;
         try {
            logger.info(logId + "-->" + "-------------开始组装渠道信息同步数据-------------");
            fost = new FileOutputStream(infoFile);
            psw = new OutputStreamWriter(fost, "gbk");
            for(SyncChannelInfoData  e :infoList){
                 psw.write(e.getId());
                 psw.write("|");
                 psw.write(e.getParentChannelId());
                 psw.write("|");
                 psw.write(e.getChannelName());
                 psw.write("|");
                 psw.write(e.getChannelStatus());
                 psw.write("|");
                 psw.write(e.getChannelCorpID());
                 psw.write("|");
                 if(e.getChannelCorpName() != null && !"".equals(e.getChannelCorpName())) {
                    psw.write(e.getChannelCorpName());
                    psw.write("|");
                 } else {
                    psw.write(" ");
                    psw.write("|");
                 }

                 psw.write(e.getChannelCorpStatus());
                 psw.write("|");
                 psw.write(e.getChannelFlag());
                 psw.write("|");
                 psw.write(e.getClassCode());
                 psw.write("|");
                 if(e.getSubClassCode() != null && !"".equals(e.getSubClassCode())) {
                    psw.write(e.getSubClassCode());
                    psw.write("|");
                 } else {
                    psw.write(" ");
                    psw.write("|");
                 }

                 psw.write(e.getFirstProp());
                 psw.write("|");
                 psw.write(e.getSecondProp());
                 psw.write("|");
                 psw.write(e.getThirdProp());
                 psw.write("|");
                 psw.write(e.getFourProp());
                 psw.write("|");
                 psw.write(e.getChildServiceId());
                 psw.write("|");
                 psw.write("\r\n");
            }
            psw.close();
            logger.info(logId + "-->" + "-------------组装渠道信息同步数据结束-------------");
            logger.info(logId + "-->" + "-------------开始组装渠道内容信息同步数据-------------");
            fos = new FileOutputStream(proFile);
            osw = new OutputStreamWriter(fos, "gbk");
            for(SyncChannelProductData e1 : proList){
                osw.write(e1.getChannelCode());
                osw.write("|");
                osw.write(String.valueOf(e1.getType()));
                osw.write("|");
                osw.write(e1.getCooperateCode());
                osw.write("|");
                if(e1.getContentCode() != null && !"".equals(e1.getContentCode())) {
                   osw.write(e1.getContentCode());
                   osw.write("|");
                } else {
                   osw.write(" ");
                   osw.write("|");
                }

                osw.write(e1.getProductCode());
                osw.write("|");
                osw.write(e1.getStatus());
                osw.write("|");
                osw.write("\r\n");
             
            }
            osw.close();
            logger.info(logId + "-->" + "-------------组装渠道内容信息同步数据结束-------------");
            logger.info(logId + "-->" + "初始化FTP工具开启连接");
            if(initFtp()) {
               String e2 = CommonUtils.getPropertyValue(Constants.PROPERTY.REPORT_FROM,
	                    Constants.PROPERTY_KEY.CAHNNEL_FTP_CHANNEL_PRODUCT_PATH)[0];
               logger.info(logId + "-->" + "上传文件ftp路径：" + e2);
               boolean isExist1 = ftpUtil.changeWorkingDirectory(e2);
               if(!isExist1) {
                  flag = false;
                  logger.info(logId + "-->" + "渠道信息同步Ftp路径" + e2 + "不存在");
                  return flag;
               }

               logger.info(logId + "-->" + "切换至上传目录：" + (ftpUtil.changeWorkingDirectory(e2)?"success":"fail"));
               logger.info(logId + "-->" + "渠道信息同步到渠道ftp开始.....");
               ftpUtil.handleFileUpload(infoFile, chFilename);
               logger.info(logId + "-->" + "渠道内容信息同步到渠道ftp开始.....");
               ftpUtil.handleFileUpload(proFile, proFilename);
               ftpUtil.closeFtp();
            }else{
	            flag = false;
	            logger.info(logId + "-->" + "初始化FTP工具开启连接失败");
	            return flag;
            }
         } catch (Exception e) {
            flag = false;
            logger.info(logId + "-->" + "渠道信息同步到渠道ftp失败", e);
            return flag;
         } finally {
            try {
               if(psw != null) {
                  psw.close();
               }

               if(fost != null) {
                  fost.close();
               }

               if(osw != null) {
                  osw.close();
               }

               if(fos != null) {
                  fos.close();
               }
            } catch (IOException e) {
               flag = false;
               logger.info(logId + "-->" + "渠道文件上传异常", e);
               return flag;
            }

         }
         infoFile.delete();
         proFile.delete();
         logger.info(logId + "-->" + "清除缓存文件成功.....");
         return flag;
      }

   private static boolean initFtp() {
      int i = 0;
      boolean isLogin = false;

      while(i < 3 && !isLogin) {
         isLogin = doLogin();
         ++i;
         if(!isLogin) {
            try {
               Thread.sleep(5 * 60 * 1000);
            } catch (InterruptedException e) {
               logger.error("等待五分钟重试登录FTP异常", e);
            }
         }
      }

      if(isLogin) {
         try {
            ftpUtil.setFileType(2);
            ftpUtil.setBufferSize(1024);
            ftpUtil.setControlEncoding("UTF-8");
         } catch (IOException e) {
            logger.error("设置FTP配置异常" + e);
            return false;
         }
      }

      return isLogin;
   }

   private static boolean doLogin() {
      try {
         String[] ftpProperty = CommonUtils.getPropertyValue(Constants.PROPERTY.REPORT_FROM,
                 Constants.PROPERTY_KEY.CHANNEL_FTP_IP, Constants.PROPERTY_KEY.CHANNEL_FTP_PORT,
                 Constants.PROPERTY_KEY.CHANNEL_FTP_USERNAME, Constants.PROPERTY_KEY.CHANNEL_FTP_PASSWORD);
         return ftpUtil.openConnection(ftpProperty[0], Integer.valueOf(ftpProperty[1]).intValue(), ftpProperty[2], ftpProperty[3]);
      } catch (Exception e) {
         logger.error("登录安管FTP异常", e);
         return false;
      }
   }
}
