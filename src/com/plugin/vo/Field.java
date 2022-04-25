package com.plugin.vo;

/**
 * Created with IntelliJ IDEA.
 * User: zacky
 * Date: 31/8/21
 * Time: 11:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class Field {


    private String nodeType;
    private String fieldDesc;
    private String fieldName;
    private Boolean isNullOk;
    private String fieldType;
    private String varName;
    public String methodName;

    public Field(String nodeType, String fieldDesc, String fieldName, Boolean nullOk, String fieldType, String varName, String methodName) {
        this.nodeType = nodeType;
        this.fieldDesc = fieldDesc;
        this.fieldName = fieldName;
        isNullOk = nullOk;
        this.fieldType = fieldType;
        this.varName = varName;
        this.methodName = methodName;
    }

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName = varName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public String getFieldDesc() {
        return fieldDesc;
    }

    public void setFieldDesc(String fieldDesc) {
        this.fieldDesc = fieldDesc;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Boolean getNullOk() {
        return isNullOk;
    }

    public void setNullOk(Boolean nullOk) {
        isNullOk = nullOk;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }
}
