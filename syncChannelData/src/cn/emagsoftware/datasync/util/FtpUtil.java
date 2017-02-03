package cn.emagsoftware.datasync.util;

import cn.emagsoftware.datasync.util.CommonUtils;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class FtpUtil extends FTPClient {
   private static Logger logger = Logger.getLogger("errorlog");
   private String controlEncoding = "UTF-8";
   private float maximumSize;
   private String fileSavePath;
   private ChannelSftp sftp = null;
   private Session sshSession = null;

   public boolean openConnection(String ip, int port, String user, String pwd) throws Exception {
      boolean blnResult = false;
      this.setControlEncoding(this.controlEncoding);
      this.setConnectTimeout(30000);
      this.connect(ip, port);
      if(!FTPReply.isPositiveCompletion(this.getReplyCode())) {
         logger.error("服务器拒绝建立连接！");
      } else {
         logger.info("打开FTP链接");
         blnResult = this.login(user, pwd);
         if(blnResult) {
            this.enterLocalPassiveMode();

            try {
               this.listFiles();
               logger.info("以被动模式测试服务器连接成功");
            } catch (Exception var7) {
               logger.info("以被动模式测试服务器连接失败", var7);
               this.closeFtp();
               logger.info("准备休眠3分钟，等待二期资源释放");
               Thread.sleep(180000L);
               logger.info("休眠结束");
               throw var7;
            }
         } else {
            logger.error("FTP登录失败，用户名/密码错误");
         }
      }

      return blnResult;
   }

   public boolean downloadFile(String strRemoteFile, String strLocalFile, String encodeing) throws Exception {
      boolean booResult = false;
      File fileOut = new File(strLocalFile);
      FileOutputStream outputStream = null;

      try {
         outputStream = new FileOutputStream(fileOut);
         this.setControlEncoding(StringUtils.isEmpty(encodeing)?"GBK":encodeing);
         this.setFileType(2);
         FTPClientConfig e = new FTPClientConfig("WINDOWS");
         e.setServerLanguageCode("zh");
         String path = strRemoteFile.substring(0, strRemoteFile.lastIndexOf("/"));
         logger.info("FTP切换文件路径：" + path);
         if(path != null && !"".equals(path.trim())) {
            String[] fileName = path.split("/");
            String[] var13 = fileName;
            int var12 = fileName.length;

            for(int var11 = 0; var11 < var12; ++var11) {
               String onepath = var13[var11];
               if(onepath != null && !"".equals(onepath.trim())) {
                  logger.info("切换文件路径到：" + onepath);
                  boolean changeFlag = this.changeWorkingDirectory(onepath);
                  if(!changeFlag) {
                     logger.info("切换路径失败，路径：" + onepath);
                  }
               }
            }
         } else {
            logger.info("FTP文件路径不存在");
         }

         logger.info("完成切换文件路径.");
         String var20 = strRemoteFile.substring(strRemoteFile.lastIndexOf("/") + 1, strRemoteFile.length());
         if(StringUtils.isEmpty(encodeing)) {
            booResult = this.retrieveFile("/" + strRemoteFile, outputStream);
         } else {
            booResult = this.retrieveFile(var20, outputStream);
         }

         logger.info("下载文件到临时目录成功.");
      } catch (Exception var18) {
         booResult = false;
         logger.error(var18);
         throw var18;
      } finally {
         if(outputStream != null) {
            outputStream.flush();
            outputStream.close();
         }

      }

      return booResult;
   }

   public boolean getFileUrl() {
      boolean res = true;

      try {
         Properties e = PropertiesLoaderUtils.loadProperties(new ClassPathResource("util.properties"));
         this.maximumSize = Float.valueOf(e.getProperty("struts.multipart.gamePackageSize")).floatValue();
         if(System.getProperty("os.name").equals("Linux")) {
            this.fileSavePath = e.getProperty("struts.linux.gamePackagePath");
            logger.debug("---Linux环境上传！---");
         } else {
            this.fileSavePath = e.getProperty("struts.gamePackagePath");
            logger.debug("---windows环境上传！---");
         }

         File fileTemp = new File(this.fileSavePath);
         if(!fileTemp.exists() && !fileTemp.isDirectory()) {
            fileTemp.mkdirs();
         }
      } catch (IOException var4) {
         res = false;
         logger.error("---读取文件上传路径、大小失败！---", var4);
      }

      return res;
   }

   public Map ftpDownloadFile(String ftpUrl, String prefixFileName, String encodeing) {
      String res = "1";
      HashMap resMap = new HashMap();
      String path = "";
      String filename = "";
      String ip = "";
      String prot = "";
      String user = "";
      String pwd = "";
      if(ftpUrl != null && !"".equals(ftpUrl.trim())) {
         try {
            int e = ftpUrl.indexOf("/");
            int dt = ftpUrl.indexOf(":", e);
            user = ftpUrl.substring(e + 2, dt);
            System.out.println(user);
            int muLu = ftpUrl.indexOf("@");
            pwd = ftpUrl.substring(dt + 1, muLu);
            System.out.println(pwd);
            String fileMuLu = ftpUrl.substring(muLu + 1);
            ip = fileMuLu.substring(0, fileMuLu.indexOf(":"));
            System.out.println(ip);
            prot = fileMuLu.substring(fileMuLu.indexOf(":") + 1, fileMuLu.indexOf("/"));
            System.out.println(prot);
            path = fileMuLu.substring(fileMuLu.indexOf("/") + 1);
            System.out.println(path);
            if(StringUtils.isNotEmpty(prefixFileName)) {
               filename = prefixFileName + "_" + fileMuLu.substring(fileMuLu.lastIndexOf("/") + 1);
            } else {
               filename = fileMuLu.substring(fileMuLu.lastIndexOf("/") + 1);
            }

            logger.info("下载的缓存文件名：" + filename);
         } catch (Exception var18) {
            res = "3";
            logger.error("-->解析ftp下载地址失败，ftp地址不合法", var18);
         }
      } else {
         res = "2";
      }

      if(!"1".equals(res)) {
         resMap.put("res", res);
         return resMap;
      } else {
         try {
            if(!this.openConnection(ip, Integer.parseInt(prot), user, pwd)) {
               logger.error("FTP登录失败，用户名/密码错误");
               res = "4";
            }
         } catch (Exception var17) {
            res = "4";
            logger.error("-->ftp登录失败!", var17);
         }

         if(!"1".equals(res)) {
            resMap.put("res", res);
            return resMap;
         } else {
            try {
               if(!this.getFileUrl()) {
                  logger.error("读取FTP存储地址错误！");
                  res = "6";
                  resMap.put("res", res);
                  return resMap;
               }

               SimpleDateFormat e1 = new SimpleDateFormat("yyyyMMdd");
               Date dt1 = new Date();
               String muLu1 = e1.format(dt1);
               this.fileSavePath = this.fileSavePath + muLu1 + File.separator;
               File fileMuLu1 = new File(this.fileSavePath);
               if(!fileMuLu1.exists()) {
                  fileMuLu1.mkdirs();
               }

               if(!this.downloadFile(path, this.fileSavePath + filename, encodeing)) {
                  logger.error("FTP下载失败，用户名/密码错误");
                  res = "5";
               } else {
                  filename = muLu1 + File.separator + filename;
                  resMap.put("url", filename);
                  resMap.put("fileSavePathApk", muLu1 + File.separator);
               }
            } catch (Exception var16) {
               logger.error("-->ftp下载失败!", var16);
               res = "5";
            }

            resMap.put("res", res);
            this.closeFtp();
            return resMap;
         }
      }
   }

   public void handleFileUpload(File file) throws IOException {
      this.handleFileUpload(file, (String)null);
   }

   public void handleMultipleFileUpload(String localPath, List localFileNameList, List ftpFileNameList) throws IOException {
      try {
         for(int e = 0; e < localFileNameList.size(); ++e) {
            String localFileName = (String)localFileNameList.get(e);
            this.handleFileUpload(new File(localPath + File.separator + localFileName), CollectionUtils.isNotEmpty(ftpFileNameList)?(String)ftpFileNameList.get(e):null);
         }

         logger.info("附件上传至FTP完成");
      } catch (Exception var6) {
         logger.error("附件保存失败，请稍后再试！", var6);
      }

   }

   public void handleFileUpload(File file, String ftpFileName) throws IOException {
      logger.info("上传文件：" + (StringUtils.isEmpty(ftpFileName)?file.getName():ftpFileName));
      FileInputStream io = null;

      try {
         io = new FileInputStream(file);
         this.storeFile(StringUtils.isEmpty(ftpFileName)?file.getName():ftpFileName, io);
         logger.info("上传完成");
      } catch (IOException var12) {
         logger.error("上传文件至FTP失败：" + file.getAbsolutePath(), var12);
         throw var12;
      } finally {
         if(io != null) {
            try {
               io.close();
            } catch (IOException var11) {
               logger.error("关闭输入流失败：" + file.getAbsolutePath(), var11);
               throw var11;
            }
         }

      }

   }

   public void closeFtp() {
      if(this != null && this.isConnected()) {
         try {
            this.logout();
            this.disconnect();
            logger.info("关闭FTP连接完成");
         } catch (IOException var2) {
            logger.error("关闭FTP连接异常", var2);
         }
      }

   }

   public static boolean doChannelFtpLogin(FtpUtil ftpUtil) {
      try {
         String[] e = CommonUtils.getPropertyValue("reportFrom.properties", new String[]{"channel_ftp_ip", "channel_ftp_port", "channel_ftp_username", "channel_ftp_password"});
         return ftpUtil.openConnection(e[0], Integer.valueOf(e[1]).intValue(), e[2], e[3]);
      } catch (Exception var2) {
         logger.error("登录渠道系统FTP异常", var2);
         return false;
      }
   }

   public static boolean initChannelFtp(FtpUtil ftpUtil, boolean isTryAgain) {
      boolean isLogin = false;
      if(isTryAgain) {
         int e = 0;

         while(e < 3 && !isLogin) {
            isLogin = doChannelFtpLogin(ftpUtil);
            ++e;
            if(!isLogin) {
               try {
                  Thread.sleep(300000L);
               } catch (InterruptedException var6) {
                  logger.error("等待五分钟重试登录FTP异常", var6);
               }
            }
         }
      } else {
         isLogin = doChannelFtpLogin(ftpUtil);
      }

      if(isLogin) {
         try {
            ftpUtil.setFileType(2);
            ftpUtil.setBufferSize(1024);
            ftpUtil.setControlEncoding("UTF-8");
         } catch (IOException var5) {
            logger.error("设置FTP配置异常" + var5);
            return false;
         }
      }

      return isLogin;
   }

   public ChannelSftp connect(String host, int port, String username, String password) throws Exception {
      try {
         JSch e = new JSch();
         e.getSession(username, host, port);
         this.sshSession = e.getSession(username, host, port);
         logger.info("SFTP Session created.");
         this.sshSession.setPassword(password);
         Properties sshConfig = new Properties();
         sshConfig.put("StrictHostKeyChecking", "no");
         this.sshSession.setConfig(sshConfig);
         this.sshSession.connect(30000);
         logger.info("Session connected.======Opening Channel.");
         System.out.println("Session connected.");
         Channel channel = this.sshSession.openChannel("sftp");
         channel.connect();
         this.sftp = (ChannelSftp)channel;
         logger.info("Connected to " + host + ".");
      } catch (Exception var8) {
         throw var8;
      }

      return this.sftp;
   }

   public void closeChannel() throws Exception {
      try {
         if(this.sftp != null) {
            this.sftp.disconnect();
         }

         if(this.sshSession != null) {
            this.sshSession.disconnect();
         }

      } catch (Exception var2) {
         logger.error("close sftp error", var2);
         throw new Exception("close ftp error.");
      }
   }

   public void handleSFTPFileUpload(String directory, File file, ChannelSftp sftp) throws Exception {
      try {
         sftp.cd(directory);
         sftp.put(new FileInputStream(file), file.getName());
      } catch (Exception var5) {
         logger.error("SFTP PUT FILE ERROR", var5);
         throw var5;
      }
   }

   public float getMaximumSize() {
      return this.maximumSize;
   }

   public void setMaximumSize(float maximumSize) {
      this.maximumSize = maximumSize;
   }

   public String getFileSavePath() {
      return this.fileSavePath;
   }

   public void setFileSavePath(String fileSavePath) {
      this.fileSavePath = fileSavePath;
   }

   public static void main(String[] args) {
      FtpUtil ftpUtil = new FtpUtil();
      String url = "ftp://dpftp:dpftp@112.4.3.136:21/warehouse/201404/98bdb32b-2a1d-4028-b5eb-286816fe349e/1398228518103.apk";
      System.out.println(ftpUtil.ftpDownloadFile(url, (String)null, (String)null));
   }
}
