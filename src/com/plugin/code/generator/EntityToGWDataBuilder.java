package com.plugin.code.generator;

import com.plugin.constants.APIConstants;
import com.plugin.constants.EntityBuilderConstants;
import com.plugin.exception.handling.EntityException;
import com.plugin.util.ExceptionHandlingUtils;
import com.plugin.util.FileUtil;
import com.plugin.vo.Entity;
import com.plugin.vo.ExceptionInfoVO;
import com.plugin.vo.Field;
import com.plugin.vo.OutputDataVO;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: zacky
 * Date: 30/8/21
 * Time: 9:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class EntityToGWDataBuilder {

    //Logger definitions
    static {
        String _logPropzpath = EntityToGWDataBuilder.class.getClassLoader().getResource("resources/logging.properties").getFile();
        System.setProperty("java.util.logging.config.file", _logPropzpath);
    }

    private static Logger _logger = Logger.getLogger(EntityToGWDataBuilder.class.getName());
    //Local objects
    private static ArrayList<ExceptionInfoVO> _exceptionsList = new ArrayList<ExceptionInfoVO>();
    private static String $CURRENT_DIR = "";
    private static String $OUTPUT_DIR = "src/gtest/entity/databuilder/";
    private static String $PACKAGE_NAME = "entity.databuilder";
    public boolean $DATE_PRESENT, $INTEGER_PRESENT = false;
    ArrayList<String> _typekeyzList = new ArrayList<String>();
    FileUtil _fileUtil = new FileUtil();
    private ExceptionHandlingUtils _exceptionHandlingUtil = new ExceptionHandlingUtils();
    private StringBuilder _outputFileData = null;

    /**
     * Main method used for local testing to validate the results
     *
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("Print here");
        String filePath = "D:/.MyProjectz/intellij-workspace/gw-custom-plugins/src/test/mocks/";
        String fileName = "MOJDetail_Ins.eti";
        EntityToGWDataBuilder _currentObject = new EntityToGWDataBuilder();
        _currentObject.processEntityFile(filePath, fileName);
        //System.out.println(30/200);
    }

    /**
     * Method that processes the input file, extracts the properties
     * converts into Guidewire databuilder clsses and finally returns
     * whether the file was successfully generated
     *
     * @param _filePath
     * @param _fileName
     * @return
     */
    public boolean processEntityFile(String _filePath, String _fileName) {

        _logger.log(Level.INFO, "Guidewire Entity Data Builder Generator ::: Starts");

        ConfigLoader _configObject = new ConfigLoader();
        boolean _processSuccessful = true;
        $CURRENT_DIR = _filePath;
        String _entityFile = _filePath + "/" + _fileName;

        //Load config.json file and extract the necessary information to be used in generated class
        boolean isConfigLoaded = _configObject.loadConfigurationFile($CURRENT_DIR);
        if (!isConfigLoaded) {
            OutputDataVO _outputObject = new OutputDataVO(_exceptionsList);
            _fileUtil.writeOutputFile($CURRENT_DIR, _outputObject);
            return false;
        }
        _logger.log(Level.INFO, ">>> Config file loaded properly");

        // Instantiate the Factory
        DocumentBuilderFactory _documentBuilderFactory = DocumentBuilderFactory.newInstance();
        Entity _entity = null;
        Element _rootElement;

        try {
            // process XML securely
            _documentBuilderFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

            // parse XML file
            DocumentBuilder db = _documentBuilderFactory.newDocumentBuilder();
            Document _document = db.parse(_fileUtil.loadFile(_entityFile));
            _document.getDocumentElement().normalize();
            _rootElement = _document.getDocumentElement();

            try {
                if (isEntityFile(_document) ||
                        isExtensionFile(_document)) {
                    _entity = createRootNodeObject(_rootElement);
                }
            } catch (EntityException ee) {
                _logger.log(Level.SEVERE, ee.getMessage());
                return false;
            }

            _logger.log(Level.INFO, ">>> Root Entity parsing complete", this);

            StringBuilder _finalOutputBuilder = new StringBuilder();
            ArrayList _fieldzList = createEntityChildList(_rootElement);


            if (APIConstants.$GENERATOR_TYPE_BUILDER.equalsIgnoreCase(ConfigLoader.$GENERATOR_TYPE)) {
                generateGosuDataBuilderClazz(_fieldzList, _entity);
            } else if (APIConstants.$GENERATOR_TYPE_UTIL.equalsIgnoreCase(ConfigLoader.$GENERATOR_TYPE)) {
                generateGosuUtilClazz(_fieldzList, _entity);
            }



            /*for (Object _currentObject : _fieldzList) {
                StringBuilder _autoGeneratedMethods ;
                Field _field = (Field) _currentObject;

                //Builder properties are generated ONLY for Column/Typekeys and the rest are ignored
                if (EntityBuilderConstants.$NODE_COLUMN.equalsIgnoreCase(_field.getNodeType()) ||
                        EntityBuilderConstants.$NODE_TYPEKEY.equalsIgnoreCase(_field.getNodeType())) {
                    configureNecessaryDataTypeImports(_field);
                    _autoGeneratedMethods = generateMethods(_entity, _field);
                    if (_autoGeneratedMethods != null) {
                        _finalOutputBuilder.append(_autoGeneratedMethods);
                    }
                }
            }
            _logger.log(Level.INFO, ">>> Entity Properties parsing complete!");

            _finalOutputBuilder = wrapClazzStructure(_entity, _finalOutputBuilder, generateUsesStatements());
            _logger.log(Level.INFO, ">>> Clazz structure complete!");

            createDirectoryIfNotExists();

            String _gosuClassName = _entity.getBuilder() + ".gs";
            _fileUtil.generateGosuDataBuilderClass($OUTPUT_DIR, _gosuClassName, _finalOutputBuilder);
            _logger.log(Level.INFO, ">>> Data Builder generated successfully!");*/

        } catch (ParserConfigurationException pe) {
            pe.printStackTrace();
            return false;
        } catch (SAXException se) {
            se.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            OutputDataVO _outputObject = new OutputDataVO(_exceptionsList);
            _fileUtil.writeOutputFile($CURRENT_DIR, _outputObject);
        }

        _logger.log(Level.INFO, "Guidewire Entity Data Builder Generator ::: Ends");
        return _processSuccessful;
    }

    public void generateGosuUtilClazz(ArrayList _fieldzList, Entity _entity) {

        String _builderName = _entity.getBuilder();
        StringBuilder _finalOutputBuilder = new StringBuilder();

        InputStream _inputStream = null;
        Scanner _scannerInstance = null;
        String _propzTemplate = null;
        StringBuilder _propzBuilder = new StringBuilder();
        StringBuilder _paramzBuilder = new StringBuilder();
        int _lineBreakLimit = 125;

        for (Object _currentObject : _fieldzList) {
            Field _field = (Field) _currentObject;
            _inputStream = _fileUtil.loadTemplateFileAsStream(APIConstants.$ENTITY_UTIL_PROPERTY_TEMPLATE);
            _scannerInstance = new Scanner(_inputStream).useDelimiter("\\A");
            _propzTemplate = _scannerInstance.hasNext() ? _scannerInstance.next() : "";

/*
            String _fieldType = null;
            String _fieldName = null;

            if (EntityBuilderConstants.$NODE_COLUMN.equalsIgnoreCase(_field.getNodeType())){
                _fieldName = _field.getVarName();
                _fieldType = _field.get
            }else if (EntityBuilderConstants.$NODE_TYPEKEY.equalsIgnoreCase(_field.getNodeType())){

            }
*/


            if (EntityBuilderConstants.$NODE_COLUMN.equalsIgnoreCase(_field.getNodeType()) ||
                    EntityBuilderConstants.$NODE_TYPEKEY.equalsIgnoreCase(_field.getNodeType())) {

                if(_paramzBuilder.length()>0){
                    _paramzBuilder.append(", ");
                    if(_paramzBuilder.length()/_lineBreakLimit > 0){
                        _paramzBuilder.append("\n\t\t\t\t\t\t\t\t\t\t\t");
                        _lineBreakLimit+=125;
                    }
                    _paramzBuilder.append(_field.getVarName() + ":" + _field.getFieldType());
                    _propzTemplate = _propzTemplate.replaceAll("<propertyName>", _field.getMethodName());
                    _propzTemplate = _propzTemplate.replaceAll("<varName>", _field.getVarName());
                }else{
                    System.out.print("Name: " +_field.getFieldName() + " Type: " +_field.getFieldType() + " Mt: " +_field.getMethodName() + " Nd: " +_field.getNodeType() + " Var: " +_field.getVarName());
                    _paramzBuilder.append(_field.getVarName() + ":" + _field.getMethodName());
                    _propzTemplate = _propzTemplate.replaceAll("<propertyName>", _field.getMethodName());
                    _propzTemplate = _propzTemplate.replaceAll("<varName>", _field.getVarName());
                }

                _propzBuilder.append(_propzTemplate);
                _propzBuilder.append("\n");
            }
        }
       // _propzBuilder.append("\n");


        //System.out.print(_propzBuilder.toString());

        _inputStream = _fileUtil.loadTemplateFileAsStream(APIConstants.$ENTITY_UTIL_CLASS_TEMPLATE);
        _scannerInstance = new Scanner(_inputStream).useDelimiter("\\A");
        String _classTemplate = _scannerInstance.hasNext() ? _scannerInstance.next() : "";

        try {
            _classTemplate = _classTemplate.replaceAll("<entityName>", _entity.getEntity());
            _classTemplate = _classTemplate.replaceAll("<builderName>", _entity.getBuilder());
            _classTemplate = _classTemplate.replaceAll("<authorName>", ConfigLoader.$AUTHOR_NAME);
            _classTemplate = _classTemplate.replaceAll("<jiraReference>", ConfigLoader.$JIRA_REFERENCE);
            _classTemplate = _classTemplate.replaceAll("<jiraDescription>", ConfigLoader.$JIRA_DESCRIPTION);


            Date date = Calendar.getInstance().getTime();
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String _currentDate = formatter.format(date);
            formatter = new SimpleDateFormat("hh:mm:ss.SSS a");
            String _currentTime = formatter.format(date);


            _classTemplate = _classTemplate.replaceAll("<today>", _currentDate);
            _classTemplate = _classTemplate.replaceAll("<time>", _currentTime);

            _classTemplate = _classTemplate.replaceAll("<<paramsList>>", _paramzBuilder.toString());
            _classTemplate = _classTemplate.replaceAll("<<fieldsModel>>", _propzBuilder.toString());
            _classTemplate = _classTemplate.replaceAll("<<usesStatement>>", generateUsesStatements().toString());
            _classTemplate = _classTemplate.replaceAll("<<packageName>>", $PACKAGE_NAME);
            _finalOutputBuilder.append(_classTemplate);
            _finalOutputBuilder.append("\n\n");
            //System.out.println(_classTemplate);

        } catch (Exception e) {
            e.printStackTrace();
        }

        createDirectoryIfNotExists();

        String _gosuClassName = _entity.getBuilder() + "Util.gs";
        _fileUtil.generateGosuDataBuilderClass($OUTPUT_DIR, _gosuClassName, _finalOutputBuilder);
        _logger.log(Level.INFO, ">>> Data Builder generated successfully!");
    }

    public void generateGosuDataBuilderClazz(ArrayList _fieldzList, Entity _entity) throws Exception {
        StringBuilder _finalOutputBuilder = new StringBuilder();

        for (Object _currentObject : _fieldzList) {
            StringBuilder _autoGeneratedMethods;
            Field _field = (Field) _currentObject;

            //Builder properties are generated ONLY for Column/Typekeys and the rest are ignored
            if (EntityBuilderConstants.$NODE_COLUMN.equalsIgnoreCase(_field.getNodeType()) ||
                    EntityBuilderConstants.$NODE_TYPEKEY.equalsIgnoreCase(_field.getNodeType())) {
                configureNecessaryDataTypeImports(_field);
                _autoGeneratedMethods = generateMethods(_entity, _field);
                if (_autoGeneratedMethods != null) {
                    _finalOutputBuilder.append(_autoGeneratedMethods);
                }
            }
        }
        _logger.log(Level.INFO, ">>> Entity Properties parsing complete!");

        _finalOutputBuilder = wrapClazzStructure(_entity, _finalOutputBuilder, generateUsesStatements());
        _logger.log(Level.INFO, ">>> Clazz structure complete!");

        createDirectoryIfNotExists();

        String _gosuClassName = _entity.getBuilder() + ".gs";
        _fileUtil.generateGosuDataBuilderClass($OUTPUT_DIR, _gosuClassName, _finalOutputBuilder);
        _logger.log(Level.INFO, ">>> Data Builder generated successfully!");
    }

    /**
     * Method that builds the necessary import statements for the auto-
     * generated classes. Returns the concatenated string
     *
     * @return
     */
    public StringBuilder generateUsesStatements() {
        _logger.log(Level.INFO, ">>> Generating Uses statements... ");
        StringBuilder _usesBuilder = new StringBuilder();
        _usesBuilder.append(EntityBuilderConstants.$USES_DATA_BUILDER);
        _usesBuilder.append("\n");
        if ($INTEGER_PRESENT) {
            _usesBuilder.append(EntityBuilderConstants.$USES_INTEGER);
            _usesBuilder.append("\n");
        }
        if ($DATE_PRESENT) {
            _usesBuilder.append(EntityBuilderConstants.$USES_DATE);
            _usesBuilder.append("\n");
        }

        for (String _typekeyName : _typekeyzList) {
            _usesBuilder.append(EntityBuilderConstants.$USES_TYPEKEY + _typekeyName);
            _usesBuilder.append("\n");
        }

        _logger.log(Level.INFO, ">>> Uses statements complete!");
        return _usesBuilder;
    }

    /**
     * Method that validates whether the entity field is one among the data type
     * for which import statements are needed and sets the flag accordingly.
     *
     * @param _field
     */
    public void configureNecessaryDataTypeImports(Field _field) {
        if (!$DATE_PRESENT &&
                _field.getFieldType().equalsIgnoreCase(EntityBuilderConstants.$DATE_FIELD)) {
            $DATE_PRESENT = true;
        } else if (!$INTEGER_PRESENT &&
                _field.getFieldType().equalsIgnoreCase(EntityBuilderConstants.$INTEGER_FIELD)) {
            $INTEGER_PRESENT = true;
        }
    }

    /**
     * Validates whether the file is an entity (eti) file
     *
     * @param _document
     * @return boolean
     * @since Automated Data Builder v1.0 Implementation
     */
    public boolean isEntityFile(Document _document) {
        return (_document != null && _document.getElementsByTagName(EntityBuilderConstants.$NODE_ENTITY).getLength() > 0) ? true : false;
    }

    /**
     * Validates whether the file is an extension (etx) file     *
     *
     * @param _document
     * @return boolean
     * @since Automated Data Builder v1.0 Implementation
     */
    public boolean isExtensionFile(Document _document) {
        return (_document != null && _document.getElementsByTagName(EntityBuilderConstants.$NODE_EXTENSION).getLength() > 0) ? true : false;
    }

    /**
     * Create Entity object for the given Element
     *
     * @param _rootElement
     * @return Entity
     * @throws EntityException
     * @since Automated Data Builder v1.0 Implementation
     */
    public Entity createRootNodeObject(Element _rootElement) throws EntityException {
        String _entityName, _tableName, _description, _type, _builderName = null;

        _entityName = _rootElement.getAttribute(EntityBuilderConstants.$NODE_ENTITY);
        _tableName = _rootElement.getAttribute(EntityBuilderConstants.$ATTR_TABLE);
        _description = _rootElement.getAttribute(EntityBuilderConstants.$ATTR_DESCRIPTION);
        _type = _rootElement.getAttribute(EntityBuilderConstants.$ATTR_TYPE);

        System.out.println("**********************Entity Name: " + _entityName);
        _builderName = createBuilderName(_entityName);
        if (_entityName != null) {
            return new Entity(_builderName, _description, _entityName, true, false, _tableName, _type);
        } else {
            throw new EntityException("Attribute " + EntityBuilderConstants.$NODE_ENTITY + " not found in eti file.");
        }
    }

    /**
     * Converts Guidewire ETI (Entity) file to POJO file for further
     * processing to auto-generate the Guidewire classes.
     *
     * @param _rootElement
     */
    public ArrayList<Field> createEntityChildList(Element _rootElement) {
        ArrayList _propzList = new ArrayList<Field>();
        NodeList _childNodes = _rootElement.getChildNodes();
        Node _currentNode;
        String _nodeName, _description, _fieldName, _typeListName, _fieldType, _dataType, _typeListWithType = null;
        Boolean _isNullOk;
        HashMap<String, String> _dataTypeMap = loadDataTypes();

        for (int _nodeRef = 0; _nodeRef < _childNodes.getLength(); _nodeRef++) {
            _currentNode = _childNodes.item(_nodeRef);
            if (_currentNode != null) {
                _nodeName = _currentNode.getNodeName();
                if (_currentNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element _element = (Element) _currentNode;
                    _description = _element.getAttribute(EntityBuilderConstants.$ATTR_DESCRIPTION);
                    _fieldName = _element.getAttribute(EntityBuilderConstants.$ATTR_NAME);
                    _fieldType = _element.getAttribute(EntityBuilderConstants.$ATTR_TYPE);
                    _isNullOk = Boolean.getBoolean(_element.getAttribute(EntityBuilderConstants.$ATTR_NULLOK));
                    _typeListName = _element.getAttribute(EntityBuilderConstants.$ATTR_TYPELIST_NAME);
                    if (!EntityBuilderConstants.$NODE_INDEX.equalsIgnoreCase(_nodeName)
                            && !EntityBuilderConstants.$NODE_ARRAY.equalsIgnoreCase(_nodeName)
                            && !EntityBuilderConstants.$NODE_IMPLEMENTS_ENTITY.equalsIgnoreCase(_nodeName)) {

                        if ("column".equalsIgnoreCase(_nodeName)) {
                            _logger.log(Level.INFO, ">>> Node: " + _nodeName + ". Field Name:" + _fieldName + ". Field Type: " + _fieldType + " Data Type: " + _dataTypeMap.get(_fieldType));
                            _propzList.add(new Field(_nodeName, _description, _fieldName, _isNullOk, _dataTypeMap.get(_fieldType), _fieldName.toLowerCase(), _fieldName));
                        } else if ("typekey".equalsIgnoreCase(_nodeName)) {
                            _dataType = "typekey." + _typeListName;
                            _typeListWithType = "typekey." + _typeListName;
                            _typekeyzList.add(_typeListWithType);

                            System.out.println("************************************");
                            System.out.println("Node: " +_nodeName);
                            System.out.println("TPType " +_typeListWithType);
                            System.out.println("Data Type: " +_dataType);
                            System.out.println("TP NAME: "+_typeListName);
                            System.out.println("VAR NAME: " +_fieldName.toLowerCase());
                            System.out.println("************************************");

                            _propzList.add(new Field(_nodeName, _description, _typeListWithType, _isNullOk, _dataType, _fieldName.toLowerCase(), _typeListName));
                        }

                    }
                }
            }
        }
        return _propzList;
    }

    /**
     * To auto-generate method codes for the given Entity and its
     * attributes
     *
     * @param _entity
     * @param _field
     * @return
     */
    public StringBuilder generateMethods(Entity _entity, Field _field) {
        _outputFileData = _outputFileData == null ? new StringBuilder() : _outputFileData;

        StringBuilder _localBuilder = new StringBuilder();
        InputStream _inputStream = _fileUtil.loadTemplateFileAsStream(APIConstants.$ENTITY_BUILDER_WITH_METHOD_TEMPLATE);
        Scanner _scannerInstance = new Scanner(_inputStream).useDelimiter("\\A");
        String _withMethodTemplate = _scannerInstance.hasNext() ? _scannerInstance.next() : "";

        try {
            _withMethodTemplate = _withMethodTemplate.replaceAll("<varName>", _field.getVarName());
            _withMethodTemplate = _withMethodTemplate.replaceAll("<fieldName>", _field.getFieldName());
            _withMethodTemplate = _withMethodTemplate.replaceAll("<methodName>", _field.getMethodName());

            _withMethodTemplate = _withMethodTemplate.replaceAll("<entityName>", _entity.getEntity());
            _withMethodTemplate = _withMethodTemplate.replaceAll("<builderName>", _entity.getBuilder());
            _withMethodTemplate = _withMethodTemplate.replaceAll("<dataType>", _field.getFieldType());
            _localBuilder.append(_withMethodTemplate);
            _localBuilder.append("\n\n");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return _localBuilder;

    }

    /**
     * Wraps the auto-generated method code with Class structure
     *
     * @param _entity
     * @param _methodBuilder
     * @return StringBuilder
     */
    public StringBuilder wrapClazzStructure(Entity _entity, StringBuilder _methodBuilder, StringBuilder _usesBuilder) {

        StringBuilder _localBuilder = new StringBuilder();
        InputStream _inputStream = _fileUtil.loadTemplateFileAsStream(APIConstants.$ENTITY_BUILDER_CLASS_TEMPLATE);
        Scanner _scannerInstance = new Scanner(_inputStream).useDelimiter("\\A");
        String _classTemplate = _scannerInstance.hasNext() ? _scannerInstance.next() : "";

        try {
            _classTemplate = _classTemplate.replaceAll("<entityName>", _entity.getEntity());
            _classTemplate = _classTemplate.replaceAll("<builderName>", _entity.getBuilder());
            _classTemplate = _classTemplate.replaceAll("<authorName>", ConfigLoader.$AUTHOR_NAME);
            _classTemplate = _classTemplate.replaceAll("<jiraReference>", ConfigLoader.$JIRA_REFERENCE);
            _classTemplate = _classTemplate.replaceAll("<jiraDescription>", ConfigLoader.$JIRA_DESCRIPTION);


            Date date = Calendar.getInstance().getTime();
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String _currentDate = formatter.format(date);
            formatter = new SimpleDateFormat("hh:mm:ss.SSS a");
            String _currentTime = formatter.format(date);


            _classTemplate = _classTemplate.replaceAll("<today>", _currentDate);
            _classTemplate = _classTemplate.replaceAll("<time>", _currentTime);

            _classTemplate = _classTemplate.replaceAll("<<withModel>>", _methodBuilder.toString());
            _classTemplate = _classTemplate.replaceAll("<<usesStatement>>", _usesBuilder.toString());
            _classTemplate = _classTemplate.replaceAll("<<packageName>>", $PACKAGE_NAME);
            _localBuilder.append(_classTemplate);
            _localBuilder.append("\n\n");
            System.out.println(_classTemplate);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return _localBuilder;

    }

    /**
     * Returning the name for auto-generated class
     *
     * @param _entityName
     * @return String
     * @since Automated Data Builder v1.0 Implementation
     */
    public String createBuilderName(String _entityName) {
        if (_entityName.endsWith(EntityBuilderConstants.$ENTITY_ENDS_WITH_INS)) {
            return _entityName.replaceAll(EntityBuilderConstants.$ENTITY_ENDS_WITH_INS, "Builder_Ins");
        } else if (_entityName.endsWith(EntityBuilderConstants.$ENTITY_ENDS_WITH_EXT)) {
            return _entityName.replaceAll(EntityBuilderConstants.$ENTITY_ENDS_WITH_EXT, "Builder_Ext");
        } else if (_entityName.endsWith(EntityBuilderConstants.$ENTITY_ENDS_WITH_DLG)) {
            return _entityName.replaceAll(EntityBuilderConstants.$ENTITY_ENDS_WITH_DLG, "Builder_Dlg");
        } else {
            _entityName = _entityName.concat("Builder");
            System.out.println("In else for entity name :::: " + _entityName);
            return _entityName;
        }
    }

    /**
     * List of GW Entity <-> Java data type associations
     *
     * @return
     */
    public HashMap<String, String> loadDataTypes() {

        HashMap<String, String> _dataTypeMap = new HashMap<String, String>();
        _dataTypeMap.put("varchar", "String");
        _dataTypeMap.put("shorttext", "String");
        _dataTypeMap.put("text", "String");
        _dataTypeMap.put("mediumtext", "String");

        _dataTypeMap.put("integer", "int");
        _dataTypeMap.put("percentage", "int");
        _dataTypeMap.put("currencyamount", "int");

        _dataTypeMap.put("dateonly", "Date");
        _dataTypeMap.put("datetime", "Date");
        _dataTypeMap.put("bit", "boolean");

        return _dataTypeMap;

    }

    /**
     * Method to validate and create the Directory structure for
     * the output files
     */
    public void createDirectoryIfNotExists() {
        try {
            Files.createDirectories(Paths.get($OUTPUT_DIR));
        } catch (Exception e) {
            _logger.log(Level.SEVERE, e.getMessage());
            e.printStackTrace();
        }
    }


}
