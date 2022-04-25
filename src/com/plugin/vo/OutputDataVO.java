package com.plugin.vo;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: zacky
 * Date: 3/1/22
 * Time: 5:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class OutputDataVO {

    private ArrayList<ExceptionInfoVO> exceptionInfo;

    public OutputDataVO(ArrayList<ExceptionInfoVO> exceptionInfo) {
        this.exceptionInfo = exceptionInfo;
    }

    public ArrayList<ExceptionInfoVO> getExceptionInfo() {
        return exceptionInfo;
    }

    public void setExceptionInfo(ArrayList<ExceptionInfoVO> exceptionInfo) {
        this.exceptionInfo = exceptionInfo;
    }
}
