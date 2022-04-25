package com.plugin.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.plugin.constants.APIConstants;
import com.plugin.vo.OutputDataVO;

import java.io.*;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: zacky
 * Date: 23/4/22
 * Time: 9:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class FileUtil {


    private static Logger _logger = Logger.getLogger(FileUtil.class.getName());

    /**
     * Load the file for the given path in parameter
     *
     * @param _filePath
     * @return File
     */
    public FileReader getFileContent(String _filePath) throws FileNotFoundException {
        _logger.log(Level.INFO, ">>> Read file content starts.", this.getClass());
        FileReader _reader = null;
        System.out.println(_filePath);
        URL _fileURL = this.getClass().getClassLoader().getResource(_filePath);
        if (_fileURL != null) {
            _reader = new FileReader(_fileURL.getFile());
        } else {
            _reader = new FileReader(new File(_filePath));
        }
        _logger.log(Level.INFO, ">>> File content read.", this);
        return _reader;
    }

    /**
     * Load the file for the given path in parameter
     *
     * @param _filePath
     * @return File
     */
    public File loadFile(String _filePath) {
        _logger.log(Level.INFO, ">>> Accessing file - " +_filePath, this);
        URL _fileURL = this.getClass().getClassLoader().getResource(_filePath);
        File _fileObject = null;
        if (_fileURL != null) {
            _fileObject = new File(_fileURL.getFile());
        } else {
            if (_fileObject == null) {
                System.out.println("New File: " + _filePath);
                _fileObject = new File(_filePath);
            }
        }
        _logger.log(Level.INFO, ">>> File accessed!", this);
        return _fileObject;
    }


    /**
     * Load the file for the given path in parameter
     *
     * @param _filePath
     * @return File
     */
    public InputStream loadTemplateFileAsStream(String _filePath) {

        // The class loader that loaded the class
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(_filePath);

        // the stream holding the file content
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + _filePath);
        } else {
            return inputStream;
        }
    }



    public void writeOutputFile(String _path, OutputDataVO _outputVO) {
        File _outputFile;
        try {
            if (_outputVO != null) {
                _outputFile = new File(_path + APIConstants.$EXCEPTION_FILE);
                ObjectMapper mapper = new ObjectMapper();
                mapper.writerWithDefaultPrettyPrinter().writeValue(_outputFile, _outputVO);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }




    public void generateGosuDataBuilderClass(String _path, String _fileName, StringBuilder _content) {
        try {
            File _file = new File(_path);
            _file.mkdir();
            FileWriter fw = new FileWriter(_path + _fileName );
            fw.write(_content.toString());
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    /**
     * @param _sourceFile
     * @return
     * EntityToGWDataBuilder
     */
    /*public String extractPackageName(String _sourceFile) {

        int _srcIndex = _sourceFile.indexOf("src");
        String _packageName = _srcIndex >= 0 ?
                _sourceFile.substring(_srcIndex + 4, _sourceFile.lastIndexOf("/")) :
                _sourceFile.substring(0, _sourceFile.lastIndexOf("/"));
        _packageName = _packageName.replaceAll("/", ".");
        return _packageName;
    }
*/
}
