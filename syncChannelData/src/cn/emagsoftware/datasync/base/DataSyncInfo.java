package cn.emagsoftware.datasync.base;

import java.sql.Date;
import java.sql.Timestamp;

public class DataSyncInfo {

    private String id;

    /** 目标库表名 */
    private String tableName;

    /** 目标库表字段名 */
    private String columnName;

    /** 来源库表名 */
    private String fromTableName;

    /** 来源库表字段名 */
    private String fromColumnName;

    /** 描述 */
    private String syncRemark;

    /** 创建时间 */
    private Date createTime;

    /** 同步查询数据时的完整where条件，带where */
    private String condition;

    /** 同步类型，1：全量（insert之前需要清空目标数据库表），2：增量 */
    private String syncType;

    public DataSyncInfo() {
    }

    public DataSyncInfo(String id, String tableName, String columnName, String fromTableName, String fromColumnName, String syncRemark,
                        Timestamp createTime, String condition, String syncType) {
        super();
        this.id = id;
        this.tableName = tableName;
        this.columnName = columnName;
        this.fromTableName = fromTableName;
        this.fromColumnName = fromColumnName;
        this.syncRemark = syncRemark;
        this.createTime = new Date(createTime.getTime());
        this.condition = condition;
        this.syncType = syncType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getFromTableName() {
        return fromTableName;
    }

    public void setFromTableName(String fromTableName) {
        this.fromTableName = fromTableName;
    }

    public String getFromColumnName() {
        return fromColumnName;
    }

    public void setFromColumnName(String fromColumnName) {
        this.fromColumnName = fromColumnName;
    }

    public String getSyncRemark() {
        return syncRemark;
    }

    public void setSyncRemark(String syncRemark) {
        this.syncRemark = syncRemark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getSyncType() {
        return syncType;
    }

    public void setSyncType(String syncType) {
        this.syncType = syncType;
    }

    @Override
    public String toString() {
        return "DataSyncInfo [id=" + id + ", tableName=" + tableName + ", columnName=" + columnName + ", fromTableName=" + fromTableName
                + ", fromColumnName=" + fromColumnName + ", syncRemark=" + syncRemark + ", createTime=" + createTime + ", condition=" + condition
                + ", syncType=" + syncType + "]";
    }



}
