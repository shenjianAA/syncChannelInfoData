package cn.emagsoftware.datasync.base;

public class SyncChannelProductData {
   private String channelCode;
   private Integer type;
   private String cooperateCode;
   private String contentCode;
   private String productCode;
   private String status;

   public String getChannelCode() {
      return this.channelCode;
   }

   public void setChannelCode(String channelCode) {
      this.channelCode = channelCode;
   }

   public Integer getType() {
      return this.type;
   }

   public void setType(Integer type) {
      this.type = type;
   }

   public String getCooperateCode() {
      return this.cooperateCode;
   }

   public void setCooperateCode(String cooperateCode) {
      this.cooperateCode = cooperateCode;
   }

   public String getContentCode() {
      return this.contentCode;
   }

   public void setContentCode(String contentCode) {
      this.contentCode = contentCode;
   }

   public String getProductCode() {
      return this.productCode;
   }

   public void setProductCode(String productCode) {
      this.productCode = productCode;
   }

   public String getStatus() {
      return this.status;
   }

   public void setStatus(String status) {
      this.status = status;
   }
}
