package com.plugin.vo;

/**
 * Created with IntelliJ IDEA.
 * User: zacky
 * Date: 31/8/21
 * Time: 8:06 AM
 * To change this template use File | Settings | File Templates.
 */

/**
 * <entity
 * xmlns="http://guidewire.com/datamodel"
 * desc="MOJ details"
 * entity="MOJDetail_Ins"
 * exportable="false"
 * final="false"
 * table="MOJDetail_Ins"
 * type="retirable">
 * <implementsEntity
 * name="Extractable"/>
 */
public class Entity {

    private String desc;
    private String entity;
    private Boolean exportable;
    private Boolean isFinal;
    private String table;
    private String type;
    private String builder;

    public Entity(String builder, String desc, String entity, Boolean exportable, Boolean aFinal, String table, String type) {
        this.builder = builder;
        this.desc = desc;
        this.entity = entity;
        this.exportable = exportable;
        isFinal = aFinal;
        this.table = table;
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public Boolean getExportable() {
        return exportable;
    }

    public void setExportable(Boolean exportable) {
        this.exportable = exportable;
    }

    public Boolean getFinal() {
        return isFinal;
    }

    public void setFinal(Boolean aFinal) {
        isFinal = aFinal;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBuilder() {
        return builder;
    }

    public void setBuilder(String builder) {
        this.builder = builder;
    }


}
