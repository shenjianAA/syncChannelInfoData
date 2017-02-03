package cn.emagsoftware.datasync.base;

public class SyncChannelInfoData {
	/*渠道代码*/
   private String id;
   
   /*父渠道代码*/
   private String parentChannelId;
   
   /*渠道基本名称*/
   private String channelName;
   
   /*渠道状态 	1:商用
			2:暂停
			3:下线
	*/
   private String channelStatus;
   
   /*渠道商代码*/
   private String channelCorpID;
   
   /*渠道商基本名称*/
   private String channelCorpName;
   
   /*渠道商状态1:商用
		2:暂停
		3:下线
		*/
   private String channelCorpStatus;
   
   /*自有渠道标示1：自有渠道
		0：非自有渠道
		*/
   private String channelFlag;
   
   /*渠道一级分类代码*/
   private String classCode;
   
   /*渠道二级分类代码*/
   private String subClassCode;
   
   /*渠道一级属性ID*/
   private String firstProp;
   
   /*渠道二级属性ID*/
   private String secondProp;
   
   /*渠道三级属性ID*/
   private String thirdProp;
   
   /*渠道四级属性ID*/
   private String fourProp;
   
   /*子业务ID*/
   private String childServiceId;

   public String getId() {
      return this.id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public String getParentChannelId() {
      return this.parentChannelId;
   }

   public void setParentChannelId(String parentChannelId) {
      this.parentChannelId = parentChannelId;
   }

   public String getChannelName() {
      return this.channelName;
   }

   public void setChannelName(String channelName) {
      this.channelName = channelName;
   }

   public String getChannelStatus() {
      return this.channelStatus;
   }

   public void setChannelStatus(String channelStatus) {
      this.channelStatus = channelStatus;
   }

   public String getChannelCorpID() {
      return this.channelCorpID;
   }

   public void setChannelCorpID(String channelCorpID) {
      this.channelCorpID = channelCorpID;
   }

   public String getChannelCorpName() {
      return this.channelCorpName;
   }

   public void setChannelCorpName(String channelCorpName) {
      this.channelCorpName = channelCorpName;
   }

   public String getChannelCorpStatus() {
      return this.channelCorpStatus;
   }

   public void setChannelCorpStatus(String channelCorpStatus) {
      this.channelCorpStatus = channelCorpStatus;
   }

   public String getChannelFlag() {
      return this.channelFlag;
   }

   public void setChannelFlag(String channelFlag) {
      this.channelFlag = channelFlag;
   }

   public String getClassCode() {
      return this.classCode;
   }

   public void setClassCode(String classCode) {
      this.classCode = classCode;
   }

   public String getSubClassCode() {
      return this.subClassCode;
   }

   public void setSubClassCode(String subClassCode) {
      this.subClassCode = subClassCode;
   }

   public String getFirstProp() {
      return this.firstProp;
   }

   public void setFirstProp(String firstProp) {
      this.firstProp = firstProp;
   }

   public String getSecondProp() {
      return this.secondProp;
   }

   public void setSecondProp(String secondProp) {
      this.secondProp = secondProp;
   }

   public String getThirdProp() {
      return this.thirdProp;
   }

   public void setThirdProp(String thirdProp) {
      this.thirdProp = thirdProp;
   }

   public String getFourProp() {
      return this.fourProp;
   }

   public void setFourProp(String fourProp) {
      this.fourProp = fourProp;
   }

   public String getChildServiceId() {
      return this.childServiceId;
   }

   public void setChildServiceId(String childServiceId) {
      this.childServiceId = childServiceId;
   }
}
