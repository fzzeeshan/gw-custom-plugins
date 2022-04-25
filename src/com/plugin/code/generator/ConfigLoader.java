package com.plugin.code.generator;


import com.plugin.constants.APIConstants;
import com.plugin.util.ExceptionHandlingUtils;
import com.plugin.util.FileUtil;
import com.plugin.vo.ExceptionInfoVO;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: zacky
 * Date: 6/1/22
 * Time: 8:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class ConfigLoader {


    private static final String $CONFIG_FILE_NAME = "config.json";

    public static String $JIRA_REFERENCE = "";
    public static String $JIRA_DESCRIPTION = "";
    public static String $AUTHOR_NAME = "";
    public static String $GENERATOR_TYPE = "";

    private static ArrayList<ExceptionInfoVO> _exceptionsList = new ArrayList<ExceptionInfoVO>();
    public JSONParser jsonParser = new JSONParser();
    private ExceptionHandlingUtils _exceptionHandlingUtil = new ExceptionHandlingUtils();



    /**
     * @return
     */
    public boolean loadConfigurationFile(String configFileDirectory) {
        FileReader reader = null;

        FileUtil _utilObject = new FileUtil();

        try {

            reader = _utilObject.getFileContent(configFileDirectory + $CONFIG_FILE_NAME);
            if (reader != null) {
                JSONObject _jsonObj = (JSONObject) jsonParser.parse(reader);
                if (_jsonObj != null) {

                    Object _value = _jsonObj.get(APIConstants.$JIRA_REFERENCE_FIELDNAME);
                    if (_value != null) {
                        $JIRA_REFERENCE = _value.toString();
                        _value = null;
                    } else {
                        _exceptionsList.add(
                                new ExceptionInfoVO(APIConstants.$EXCEPTION_CODE_INFO,
                                        APIConstants.$100_EXCEPTION_INFO_VALUE,
                                        _exceptionHandlingUtil.getExceptionShortName(APIConstants.$100_SHORT_NAME, "JIRA Reference"),
                                        _exceptionHandlingUtil.getExceptionDescription(APIConstants.$100_SHORT_NAME, "jiraReference", "JIRA Reference")));
                    }

                    _value = _jsonObj.get(APIConstants.$JIRA_DESCRIPTION_FIELDNAME);
                    if (_value != null) {
                        $JIRA_DESCRIPTION = _value.toString();
                        _value = null;
                    } else {
                        _exceptionsList.add(
                                new ExceptionInfoVO(APIConstants.$EXCEPTION_CODE_INFO,
                                        APIConstants.$100_EXCEPTION_INFO_VALUE,
                                        _exceptionHandlingUtil.getExceptionShortName(APIConstants.$100_SHORT_NAME, "JIRA Description"),
                                        _exceptionHandlingUtil.getExceptionDescription(APIConstants.$100_SHORT_NAME, "jiraDescription", "JIRA Description")));
                    }

                    _value = _jsonObj.get(APIConstants.$AUTHOR_FIELDNAME);
                    if (_value != null) {
                        $AUTHOR_NAME = _value.toString();
                        _value = null;
                    } else {
                        _exceptionsList.add(
                                new ExceptionInfoVO(APIConstants.$EXCEPTION_CODE_INFO,
                                        APIConstants.$100_EXCEPTION_INFO_VALUE,
                                        _exceptionHandlingUtil.getExceptionShortName(APIConstants.$100_SHORT_NAME, "Author"),
                                        _exceptionHandlingUtil.getExceptionDescription(APIConstants.$100_SHORT_NAME, "author", "Author")));
                    }

                    _value = _jsonObj.get(APIConstants.$GENERATOR_TYPE_FIELDNAME);
                    if (_value != null) {
                        if(APIConstants.$GENERATOR_TYPE_BUILDER.equalsIgnoreCase(_value.toString()) ||
                                APIConstants.$GENERATOR_TYPE_UTIL.equalsIgnoreCase(_value.toString())){
                            $GENERATOR_TYPE = _value.toString();
                        }else{
                            $GENERATOR_TYPE = "builder";
                            _exceptionsList.add(
                                    new ExceptionInfoVO(APIConstants.$EXCEPTION_CODE_INFO,
                                            APIConstants.$100_EXCEPTION_INFO_VALUE,
                                            _exceptionHandlingUtil.getExceptionShortName(APIConstants.$100_SHORT_NAME, "Generation Type"),
                                            _exceptionHandlingUtil.getExceptionDescription(APIConstants.$100_SHORT_NAME, "generation-type", "Generation Type")));
                        }

                        _value = null;
                    } else {
                        _exceptionsList.add(
                                new ExceptionInfoVO(APIConstants.$EXCEPTION_CODE_INFO,
                                        APIConstants.$100_EXCEPTION_INFO_VALUE,
                                        _exceptionHandlingUtil.getExceptionShortName(APIConstants.$100_SHORT_NAME, "Generation Type"),
                                        _exceptionHandlingUtil.getExceptionDescription(APIConstants.$100_SHORT_NAME, "generation-type", "Generation Type")));
                    }


                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            _exceptionsList.add(
                    new ExceptionInfoVO(APIConstants.$EXCEPTION_CODE_ERROR,
                            APIConstants.$400_EXCEPTION_ERROR_VALUE,
                            APIConstants.$400_SHORT_NAME,
                            APIConstants.$400_DESCRIPTION));
            return false;
        } catch (ParseException e) {
            _exceptionsList.add(
                    new ExceptionInfoVO(APIConstants.$EXCEPTION_CODE_ERROR,
                            APIConstants.$401_EXCEPTION_ERROR_VALUE,
                            APIConstants.$401_SHORT_NAME,
                            APIConstants.$401_DESCRIPTION));
            return false;
        } catch (IOException e) {
            _exceptionsList.add(
                    new ExceptionInfoVO(APIConstants.$EXCEPTION_CODE_ALERT,
                            APIConstants.$501_EXCEPTION_ALERT_VALUE,
                            APIConstants.$501_SHORT_NAME,
                            APIConstants.$501_DESCRIPTION));
            return false;
        } catch (Exception e) {
            _exceptionsList.add(
                    new ExceptionInfoVO(APIConstants.$EXCEPTION_CODE_ALERT,
                            APIConstants.$502_EXCEPTION_ALERT_VALUE,
                            APIConstants.$502_SHORT_NAME,
                            APIConstants.$502_DESCRIPTION));
            return false;
        }
        return false;
    }







}
