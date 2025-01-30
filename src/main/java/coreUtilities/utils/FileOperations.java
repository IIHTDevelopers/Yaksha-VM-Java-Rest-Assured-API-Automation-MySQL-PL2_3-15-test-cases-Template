package coreUtilities.utils;

import java.util.Map;
import java.util.Properties;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;

public class FileOperations {
	public JSONParser jsonParser;
	public JSONObject jsonObject;
	public Fillo fillo;
	public Connection connection;
	public Properties properties;

	/**
	 * This method is useful to read the excel sheet based on the Filename and sheet
	 * name. It'll return the values for the respective sheet in {@link Map} where
	 * the first column name as a key and the value as per the value entered in
	 * second column.
	 * 
	 * @param excelFilePath - {@link String} excel sheet location
	 * @param sheetName     - {@link String} Sheet name to read the excel
	 * @return {@link Map}
	 * @throws Exception
	 */
	public Map<String, String> readExcelPOI(String excelFilePath, String sheetName) throws Exception {
		// write your logic here
		return null;
	}

}
