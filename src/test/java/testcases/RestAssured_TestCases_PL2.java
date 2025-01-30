package testcases;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.testng.Assert;
import org.testng.annotations.Test;

import coreUtilities.utils.FileOperations;
import rest.ApiUtil;
import rest.CustomResponse;

public class RestAssured_TestCases_PL2 {

	FileOperations fileOperations = new FileOperations();

	private final String JSON_FILE_PATH = "src/main/resources/testData/addCurrency.json"; // Path to the JSON file
	private final String EXCEL_FILE_PATH = "src/main/resources/config.xlsx"; // Path to the Excel file
	private final String SHEET_NAME = "PostData"; // Sheet name in the Excel file
	private final String FILEPATH = "src/main/java/rest/ApiUtil.java";
	ApiUtil apiUtil;

	public static int appointmentId;

	@Test(priority = 1, groups = { "PL2" }, description = "1. Send a GET request to Get All Departments\n"
			+ "2. Validate that all the counter IDs are unique.\n" + "3. Verify the response status code is 200.")
	public void getAllDepartmentsTest() throws Exception {
		apiUtil = new ApiUtil();

		// Send the GET request and get the custom response
		CustomResponse customResponse = apiUtil.getAllDepartments("/AssetReports/GetAllDepartments", null);

		// Validate the method's source code
		boolean isValidationSuccessful = TestCodeValidator.validateTestMethodFromFile(FILEPATH, "getAllDepartments",
				List.of("given", "then", "extract", "response"));
		Assert.assertTrue(isValidationSuccessful,
				"getAllDepartments must be implemented using Rest Assured methods only.");

		// Validate response structure
		Assert.assertTrue(TestCodeValidator.validateResponseFields("getAllDepartments", customResponse),
				"Must have all required fields in the response.");

		// 1. Verify Status code is 200
		Assert.assertEquals(customResponse.getStatusCode(), 200, "Status code should be 200 OK.");

		// 2. Validate that Status field is "OK"
		String status = customResponse.getStatus();
		Assert.assertEquals(status, "OK", "Status should be OK.");

		// 3. Extract and validate the Results array
		List<Map<String, Object>> results = customResponse.getListResults();
		Assert.assertNotNull(results, "Results should not be null.");
		Assert.assertFalse(results.isEmpty(), "Results should not be empty.");

		// 4. Validate each department (without using forEach loop)
		for (int i = 0; i < results.size(); i++) {
			Map<String, Object> result = results.get(i);

			// Extract and validate fields
			Integer departmentId = (Integer) result.get("DepartmentId");
			String departmentName = (String) result.get("DepartmentName");

			Assert.assertNotNull(departmentId, "DepartmentId should not be null for object at index " + i);
			Assert.assertNotNull(departmentName, "DepartmentName should not be null for object at index " + i);
		}

		// Print the response for debugging
		System.out.println("All Departments Response:");
		customResponse.getResponse().prettyPrint();
	}

	@Test(priority = 2, groups = { "PL2" }, description = "1. Send a GET request to Get All Items\n"
			+ "2. Validate that the Item Id and Item name are not null.\n"
			+ "3. Verify the response status code is 200.")
	public void getAllItemsTest() throws Exception {
		apiUtil = new ApiUtil();

		// Send the GET request and get the custom response
		CustomResponse customResponse = apiUtil.getAllItems("/AssetReports/GetAllItems", null);

		// Validate the method's source code
		boolean isValidationSuccessful = TestCodeValidator.validateTestMethodFromFile(FILEPATH, "getAllItems",
				List.of("given", "then", "extract", "response"));
		Assert.assertTrue(isValidationSuccessful, "getAllItems must be implemented using Rest Assured methods only.");

		// Validate response structure
		Assert.assertTrue(TestCodeValidator.validateResponseFields("getAllItems", customResponse),
				"Must have all required fields in the response.");

		// 1. Verify Status code is 200
		Assert.assertEquals(customResponse.getStatusCode(), 200, "Status code should be 200 OK.");

		// 2. Validate that Status field is "OK"
		String status = customResponse.getStatus();
		Assert.assertEquals(status, "OK", "Status should be OK.");

		// 3. Extract and validate the Results array
		List<Map<String, Object>> results = customResponse.getListResults();
		Assert.assertNotNull(results, "Results should not be null.");
		Assert.assertFalse(results.isEmpty(), "Results should not be empty.");

		// 4. Validate each item's information (without using forEach loop)
		for (int i = 0; i < results.size(); i++) {
			Map<String, Object> result = results.get(i);

			// Extract and validate fields
			Integer itemId = (Integer) result.get("ItemId");
			String itemName = (String) result.get("ItemName");

			Assert.assertNotNull(itemId, "ItemId should not be null for object at index " + i);
			Assert.assertNotNull(itemName, "ItemName should not be null for object at index " + i);
		}

		// Print the response for debugging
		System.out.println("All Items Response:");
		customResponse.getResponse().prettyPrint();
	}

	@Test(priority = 3, groups = { "PL2" }, description = "1. Send a GET request to Incentive Summary Report\n"
			+ "2. Validate that the Prescriber Id and Prescriber name are not null.\n"
			+ "3. Verify the response status code is 200.")
	public void getIncentiveSummaryTest() throws Exception {
		// Initialize the ApiUtil object
		Map<String, String> searchResult = fileOperations.readExcelPOI(EXCEL_FILE_PATH, SHEET_NAME);

		apiUtil = new ApiUtil();

		String fromDate = searchResult.get("IncSummFromDate");
		String toDate = searchResult.get("IncSummToDate");
		String isRefferal = searchResult.get("IsRefferalOnly");

		// Fetch the response from the API
		CustomResponse customResponse = apiUtil
				.getIncentiveSummaryReport("https://healthapp.yaksha.com/BillingReports/INCTV_DocterSummary?FromDate="
						+ fromDate + "&ToDate=" + toDate + "&IsRefferalOnly=" + isRefferal, null);

		// Validate the method's source code
		boolean isValidationSuccessful = TestCodeValidator.validateTestMethodFromFile(FILEPATH,
				"getIncentiveSummaryReport", List.of("given", "then", "extract", "response"));
		Assert.assertTrue(isValidationSuccessful,
				"getIncentiveSummaryReport must be implemented using Rest Assured methods only.");

		// Validate response structure
		Assert.assertTrue(TestCodeValidator.validateResponseFields("getIncentiveSummaryReport", customResponse),
				"Must have all required fields in the response.");

		// 1. Verify Status code is 200
		Assert.assertEquals(customResponse.getStatusCode(), 200, "Status code should be 200 OK.");

		// 2. Validate that Status field is "OK"
		String status = customResponse.getStatus();
		Assert.assertEquals(status, "OK", "Status should be OK.");

		// 3. Extract and validate the JsonData field (stringified JSON)
		String jsonDataString = customResponse.getResponse().jsonPath().getString("Results.JsonData");
		Assert.assertNotNull(jsonDataString, "JsonData field should not be null.");

		// 4. Deserialize JsonData into a List of Maps
		List<Map<String, Object>> results = customResponse.getListResults();
		Assert.assertNotNull(results, "Results should not be null.");
		Assert.assertFalse(results.isEmpty(), "Results should not be empty.");

		// 5. Validate each record's fields (without using forEach loop)
		for (int i = 0; i < results.size(); i++) {
			Map<String, Object> result = results.get(i);

			// Extract and validate each field
			String prescriberName = (String) result.get("PrescriberName");
			Integer prescriberId = (Integer) result.get("PrescriberId");
			Float docTotalAmount = (Float) result.get("DocTotalAmount");
			Float tdsAmount = (Float) result.get("TDSAmount");
			Float netPayableAmount = (Float) result.get("NetPayableAmount");

			// Print extracted fields
			System.out.println("PrescriberName: " + prescriberName);
			System.out.println("PrescriberId: " + prescriberId);
			System.out.println("DocTotalAmount: " + docTotalAmount);
			System.out.println("TDSAmount: " + tdsAmount);
			System.out.println("NetPayableAmount: " + netPayableAmount);
			System.out.println();

			// Assert fields are not null
			Assert.assertNotNull(prescriberName, "The Prescriber Name is null.");
			Assert.assertNotNull(prescriberId, "The Prescriber ID is null.");
			Assert.assertNotNull(docTotalAmount, "The DocTotal Amount is null.");
			Assert.assertNotNull(tdsAmount, "The TDS Amount is null.");
			Assert.assertNotNull(netPayableAmount, "The Net Payable Amount is null.");
		}

		// Print the entire API response
		System.out.println("Full API Response:");
		customResponse.getResponse().prettyPrint();
	}

	@Test(priority = 4, groups = { "PL2" }, description = "1. Send a GET request to Incentive Referral Summary Report\n"
			+ "2. Validate that the Prescriber name, PrescriberId, DocTotalAmount, TDSAmount and NetPayableAmount are not null.\n"
			+ "3. Verify the response status code is 200.")
	public void getIncentiveReffSummaryTest() throws Exception {
		// Initialize the ApiUtil object
		Map<String, String> searchResult = fileOperations.readExcelPOI(EXCEL_FILE_PATH, SHEET_NAME);

		apiUtil = new ApiUtil();

		String incFromDate = searchResult.get("IncFromDate");
		String incToDate = searchResult.get("IncToDate");
		String isReferral = searchResult.get("DocSumIsRefferalOnly");

		// Fetch the response from the API
		CustomResponse customResponse = apiUtil
				.getIncReffSummReport("https://healthapp.yaksha.com/BillingReports/INCTV_DocterSummary?FromDate="
						+ incFromDate + "&ToDate=" + incToDate + "&IsRefferalOnly=" + isReferral, null);

		// Validate the method's source code
		boolean isValidationSuccessful = TestCodeValidator.validateTestMethodFromFile(FILEPATH, "getIncReffSummReport",
				List.of("given", "then", "extract", "response"));
		Assert.assertTrue(isValidationSuccessful,
				"getIncReffSummReport must be implemented using Rest Assured methods only.");

		// Validate response structure
		Assert.assertTrue(TestCodeValidator.validateResponseFields("getIncReffSummReport", customResponse),
				"Must have all required fields in the response.");

		// 1. Verify Status code is 200
		Assert.assertEquals(customResponse.getStatusCode(), 200, "Status code should be 200 OK.");

		// 2. Validate that Status field is "OK"
		String status = customResponse.getStatus();
		Assert.assertEquals(status, "OK", "Status should be OK.");

		// 3. Extract and validate the JsonData field (stringified JSON)
		String jsonDataString = customResponse.getResponse().jsonPath().getString("Results.JsonData");
		Assert.assertNotNull(jsonDataString, "JsonData field should not be null.");

		// 4. Deserialize JsonData into a List of Maps
		List<Map<String, Object>> results = customResponse.getListResults();
		Assert.assertNotNull(results, "Results should not be null.");
		Assert.assertFalse(results.isEmpty(), "Results should not be empty.");

		// 5. Validate each record's fields (without using forEach loop)
		for (int i = 0; i < results.size(); i++) {
			Map<String, Object> result = results.get(i);

			// Extract and validate each field
			String prescriberName = (String) result.get("PrescriberName");
			Integer prescriberId = (Integer) result.get("PrescriberId");
			Float docTotalAmount = (Float) result.get("DocTotalAmount");
			Float tdsAmount = (Float) result.get("TDSAmount");
			Float netPayableAmount = (Float) result.get("NetPayableAmount");

			// Print extracted fields
			System.out.println("PrescriberName: " + prescriberName);
			System.out.println("PrescriberId: " + prescriberId);
			System.out.println("DocTotalAmount: " + docTotalAmount);
			System.out.println("TDSAmount: " + tdsAmount);
			System.out.println("NetPayableAmount: " + netPayableAmount);
			System.out.println();

			// Assert fields are not null
			Assert.assertNotNull(prescriberName, "The Prescriber Name is null.");
			Assert.assertNotNull(prescriberId, "The Prescriber ID is null.");
			Assert.assertNotNull(docTotalAmount, "The DocTotal Amount is null.");
			Assert.assertNotNull(tdsAmount, "The TDS Amount is null.");
			Assert.assertNotNull(netPayableAmount, "The Net Payable Amount is null.");
		}

		// Print the entire API response
		System.out.println("Full API Response:");
		customResponse.getResponse().prettyPrint();
	}

	@Test(priority = 5, groups = { "PL2" }, description = "1. Send a GET request to Hospital Income Incentive Report\n"
			+ "2. Validate that the ServiceDepartmentName, ServiceDepartmentId, NetSales, ReferralCommission, GrossIncome, OtherIncentive and HospitalNetIncome are not null.\n"
			+ "3. Verify the response status code is 200.")
	public void getHospitalIncomeIncReportTest() throws Exception {
		// Initialize the ApiUtil object
		Map<String, String> searchResult = fileOperations.readExcelPOI(EXCEL_FILE_PATH, SHEET_NAME);

		apiUtil = new ApiUtil();

		String incFromDate = searchResult.get("IncFromDate");
		String incToDate = searchResult.get("IncToDate");
		String serviceDepartments = searchResult.get("ServiceDepartments");

		// Fetch the response from the API
		CustomResponse customResponse = apiUtil
				.getHospIncIncReport("https://healthapp.yaksha.com/Reporting/HospitalIncomeIncentiveReport?FromDate="
						+ incFromDate + "&ToDate=" + incToDate + "&ServiceDepartments=" + serviceDepartments, null);

		// Validate the method's source code
		boolean isValidationSuccessful = TestCodeValidator.validateTestMethodFromFile(FILEPATH, "getHospIncIncReport",
				List.of("given", "then", "extract", "response"));
		Assert.assertTrue(isValidationSuccessful,
				"getHospIncIncReport must be implemented using Rest Assured methods only.");

		// Validate response structure
		Assert.assertTrue(TestCodeValidator.validateResponseFields("getHospIncIncReport", customResponse),
				"Must have all required fields in the response.");

		// 1. Verify Status code is 200
		Assert.assertEquals(customResponse.getStatusCode(), 200, "Status code should be 200 OK.");

		// 2. Validate that Status field is "OK"
		String status = customResponse.getStatus();
		Assert.assertEquals(status, "OK", "Status should be OK.");

		// 3. Extract and validate the Results array
		List<Map<String, Object>> results = customResponse.getListResults();
		Assert.assertNotNull(results, "Results should not be null.");
		Assert.assertFalse(results.isEmpty(), "Results should not be empty.");

		// 4. Validate each record's fields (without using forEach loop)
		for (int i = 0; i < results.size(); i++) {
			Map<String, Object> result = results.get(i);

			// Extract and validate each field
			Integer serviceDepartmentId = (Integer) result.get("ServiceDepartmentId");
			String serviceDepartmentName = (String) result.get("ServiceDepartmentName");
			Float netSales = (Float) result.get("NetSales");
			Float referralCommission = (Float) result.get("ReferralCommission");
			Float grossIncome = (Float) result.get("GrossIncome");
			Float otherIncentive = (Float) result.get("OtherIncentive");
			Float hospitalNetIncome = (Float) result.get("HospitalNetIncome");

			// Print extracted fields
			System.out.println("ServiceDepartmentId: " + serviceDepartmentId);
			System.out.println("ServiceDepartmentName: " + serviceDepartmentName);
			System.out.println("NetSales: " + netSales);
			System.out.println("ReferralCommission: " + referralCommission);
			System.out.println("GrossIncome: " + grossIncome);
			System.out.println("OtherIncentive: " + otherIncentive);
			System.out.println("HospitalNetIncome: " + hospitalNetIncome);
			System.out.println();

			// Assert fields are not null
			Assert.assertNotNull(serviceDepartmentId, "ServiceDepartmentId should not be null.");
			Assert.assertNotNull(serviceDepartmentName, "ServiceDepartmentName should not be null.");
			Assert.assertNotNull(netSales, "NetSales should not be null.");
			Assert.assertNotNull(referralCommission, "ReferralCommission should not be null.");
			Assert.assertNotNull(grossIncome, "GrossIncome should not be null.");
			Assert.assertNotNull(otherIncentive, "OtherIncentive should not be null.");
			Assert.assertNotNull(hospitalNetIncome, "HospitalNetIncome should not be null.");
		}

		// Print the entire API response
		System.out.println("Full API Response:");
		customResponse.getResponse().prettyPrint();
	}

	@Test(priority = 6, groups = { "PL2" }, description = "1. Send a GET request to Incentive Employee Bill Items\n"
			+ "2. Validate that the EmployeeIncentiveInfoId, EmployeeId, FullName, TDSPercent, EmpTDSPercent, IsActive are not null.\n"
			+ "3. Verify the response status code is 200.")
	public void getIncenEmpBillItemsTest() throws Exception {
		// Initialize the ApiUtil object
		Map<String, String> searchResult = fileOperations.readExcelPOI(EXCEL_FILE_PATH, SHEET_NAME);

		apiUtil = new ApiUtil();

		String employeeId = searchResult.get("employeeId");

		// Fetch the response from the API
		CustomResponse customResponse = apiUtil.getEmpBillItem("/Incentive/EmployeeBillItems?employeeId=" + employeeId,
				null);

		// Validate the method's source code
		boolean isValidationSuccessful = TestCodeValidator.validateTestMethodFromFile(FILEPATH, "getEmpBillItem",
				List.of("given", "then", "extract", "response"));
		Assert.assertTrue(isValidationSuccessful,
				"getEmpBillItem must be implemented using Rest Assured methods only.");

		// Validate response structure
		Assert.assertTrue(TestCodeValidator.validateResponseFields("getEmpBillItem", customResponse),
				"Must have all required fields in the response.");

		// 1. Verify Status code is 200
		Assert.assertEquals(customResponse.getStatusCode(), 200, "Status code should be 200 OK.");

		// 2. Validate that Status field is "OK"
		String status = customResponse.getStatus();
		Assert.assertEquals(status, "OK", "Status should be OK.");

		// 3. Extract and validate the Results field (map)
		Map<String, Object> result = customResponse.getMapResults();
		Assert.assertNotNull(result, "Results field should not be null.");

		// 4. Extract and validate each field
		Integer employeeIncentiveInfoId = (Integer) result.get("EmployeeIncentiveInfoId");
		Integer employeeIdVal = (Integer) result.get("EmployeeId");
		String fullName = String.valueOf(result.get("FullName"));
		Float tdsPercent = (Float) result.get("TDSPercent");
		Float empTdsPercent = (Float) result.get("EmpTDSPercent");
		Boolean isActive = (Boolean) result.get("IsActive");
		List<Map<String, Object>> employeeBillItemsMap = (List<Map<String, Object>>) result.get("EmployeeBillItemsMap");

		// Print extracted fields
		System.out.println("EmployeeIncentiveInfoId: " + employeeIncentiveInfoId);
		System.out.println("EmployeeId: " + employeeIdVal);
		System.out.println("FullName: " + fullName);
		System.out.println("TDSPercent: " + tdsPercent);
		System.out.println("EmpTDSPercent: " + empTdsPercent);
		System.out.println("IsActive: " + isActive);
		System.out.println("EmployeeBillItemsMap: " + employeeBillItemsMap);
		System.out.println();

		// Assert fields are not null
		Assert.assertNotNull(employeeIncentiveInfoId, "EmployeeIncentiveInfoId should not be null.");
		Assert.assertNotNull(employeeIdVal, "EmployeeId should not be null.");
		Assert.assertNotNull(fullName, "FullName should not be null.");
		Assert.assertNotNull(tdsPercent, "TDSPercent should not be null.");
		Assert.assertNotNull(empTdsPercent, "EmpTDSPercent should not be null.");
		Assert.assertNotNull(isActive, "IsActive should not be null.");
		Assert.assertNotNull(employeeBillItemsMap, "EmployeeBillItemsMap should not be null.");

		// Print the entire API response
		System.out.println("Full API Response:");
		customResponse.getResponse().prettyPrint();
	}

	@Test(priority = 7, groups = { "PL2" }, description = "1. Send a GET request to Inventory Fiscal Years\n"
			+ "2. Validate that the FiscalYearId, FiscalYearName are not null.\n"
			+ "3. Verify the response status code is 200.")
	public void getInventoryFiscalYearsTest() throws IOException {
		// Initialize the ApiUtil object
		apiUtil = new ApiUtil();

		// Fetch the response from the API
		CustomResponse customResponse = apiUtil.getInvntryFiscalYrs("/Inventory/InventoryFiscalYears", null);

		// Validate the method's source code
		boolean isValidationSuccessful = TestCodeValidator.validateTestMethodFromFile(FILEPATH, "getInvntryFiscalYrs",
				List.of("given", "then", "extract", "response"));
		Assert.assertTrue(isValidationSuccessful,
				"getInvntryFiscalYrs must be implemented using Rest Assured methods only.");

		// Validate response structure
		Assert.assertTrue(TestCodeValidator.validateResponseFields("getInvntryFiscalYrs", customResponse),
				"Must have all required fields in the response.");

		// 1. Verify Status code is 200
		Assert.assertEquals(customResponse.getStatusCode(), 200, "Status code should be 200 OK.");

		// 2. Validate that Status field is "OK"
		String status = customResponse.getStatus();
		Assert.assertEquals(status, "OK", "Status should be OK.");

		// 3. Extract and validate the Results field (list)
		List<Map<String, Object>> results = customResponse.getListResults();
		Assert.assertNotNull(results, "Results field should not be null.");
		Assert.assertFalse(results.isEmpty(), "Results list should not be empty.");

		// 4. Validate each record's fields (without using forEach loop)
		for (int i = 0; i < results.size(); i++) {
			Map<String, Object> fiscalYear = results.get(i);

			// Extract and validate each field
			Integer fiscalYearId = (Integer) fiscalYear.get("FiscalYearId");
			String fiscalYearName = (String) fiscalYear.get("FiscalYearName");
			String startDate = (String) fiscalYear.get("StartDate");
			String endDate = (String) fiscalYear.get("EndDate");
			Boolean isActive = (Boolean) fiscalYear.get("IsActive");

			// Print extracted fields
			System.out.println("FiscalYearId: " + fiscalYearId);
			System.out.println("FiscalYearName: " + fiscalYearName);
			System.out.println("StartDate: " + startDate);
			System.out.println("EndDate: " + endDate);
			System.out.println("IsActive: " + isActive);
			System.out.println();

			// Assert fields are not null
			Assert.assertNotNull(fiscalYearId, "FiscalYearId should not be null.");
			Assert.assertNotNull(fiscalYearName, "FiscalYearName should not be null.");
			Assert.assertNotNull(startDate, "StartDate should not be null.");
			Assert.assertNotNull(endDate, "EndDate should not be null.");
			Assert.assertNotNull(isActive, "IsActive should not be null.");
		}

		// Print the entire API response
		System.out.println("Full API Response:");
		customResponse.getResponse().prettyPrint();
	}

	@Test(priority = 8, groups = { "PL2" }, description = "1. Send a GET request to Activate Inventory\n"
			+ "2. Validate that the StoreId, Name, and StoreDescription are not null.\n"
			+ "3. Verify the response status code is 200.")
	public void activateInventoryTest() throws IOException {
		// Initialize the ApiUtil object
		apiUtil = new ApiUtil();

		// Fetch the response from the API
		CustomResponse customResponse = apiUtil.getActInventory("/ActivateInventory/", null);

		// Validate the method's source code
		boolean isValidationSuccessful = TestCodeValidator.validateTestMethodFromFile(FILEPATH, "getActInventory",
				List.of("given", "then", "extract", "response"));
		Assert.assertTrue(isValidationSuccessful,
				"getActInventory must be implemented using Rest Assured methods only.");

		// Validate response structure
		Assert.assertTrue(TestCodeValidator.validateResponseFields("getActInventory", customResponse),
				"Must have all required fields in the response.");

		// 1. Verify Status code is 200
		Assert.assertEquals(customResponse.getStatusCode(), 200, "Status code should be 200 OK.");

		// 2. Validate that Status field is "OK"
		String status = customResponse.getStatus();
		Assert.assertEquals(status, "OK", "Status should be OK.");

		// 3. Extract and validate the Results field (list)
		List<Map<String, Object>> results = customResponse.getListResults();
		Assert.assertNotNull(results, "Results field should not be null.");
		Assert.assertFalse(results.isEmpty(), "Results list should not be empty.");

		// 4. Validate each record's fields (without using forEach loop)
		for (int i = 0; i < results.size(); i++) {
			Map<String, Object> store = results.get(i);

			// Extract and validate each field
			Integer storeId = (Integer) store.get("StoreId");
			String name = (String) store.get("Name");
			String storeDescription = (String) store.get("StoreDescription");

			// Print extracted fields
			System.out.println("StoreId: " + storeId);
			System.out.println("Name: " + name);
			System.out.println("StoreDescription: " + storeDescription);
			System.out.println();

			// Assert fields are not null
			Assert.assertNotNull(storeId, "StoreId should not be null.");
			Assert.assertNotNull(name, "Name should not be null.");
			Assert.assertNotNull(storeDescription, "StoreDescription should not be null.");
		}

		// Print the entire API response
		System.out.println("Full API Response:");
		customResponse.getResponse().prettyPrint();
	}

	@Test(priority = 9, groups = { "PL2" }, description = "1. Send a GET request to Inventory Subcategory\n"
			+ "2. Validate that the SubCategoryName and SubCategoryId are not null.\n"
			+ "3. Verify the response status code is 200.")
	public void inventorySubCategoryTest() throws IOException {
		// Initialize the ApiUtil object
		apiUtil = new ApiUtil();

		// Fetch the response from the API
		CustomResponse customResponse = apiUtil.getInvSubCat("/Inventory/SubCategories", null);

		// Validate the method's source code
		boolean isValidationSuccessful = TestCodeValidator.validateTestMethodFromFile(FILEPATH, "getInvSubCat",
				List.of("given", "then", "extract", "response"));
		Assert.assertTrue(isValidationSuccessful, "getInvSubCat must be implemented using Rest Assured methods only.");

		// Validate response structure
		Assert.assertTrue(TestCodeValidator.validateResponseFields("getInvSubCat", customResponse),
				"Must have all required fields in the response.");

		// 1. Verify Status code is 200
		Assert.assertEquals(customResponse.getStatusCode(), 200, "Status code should be 200 OK.");

		// 2. Validate that Status field is "OK"
		String status = customResponse.getStatus();
		Assert.assertEquals(status, "OK", "Status should be OK.");

		// 3. Extract and validate the Results field (list)
		List<Map<String, Object>> results = customResponse.getListResults();
		Assert.assertNotNull(results, "Results field should not be null.");
		Assert.assertFalse(results.isEmpty(), "Results list should not be empty.");

		// 4. Validate each record's fields (without using forEach loop)
		for (int i = 0; i < results.size(); i++) {
			Map<String, Object> subCategory = results.get(i);

			// Extract and validate each field
			Integer subCategoryId = (Integer) subCategory.get("SubCategoryId");
			String subCategoryName = (String) subCategory.get("SubCategoryName");

			// Print extracted fields
			System.out.println("SubCategoryId: " + subCategoryId);
			System.out.println("SubCategoryName: " + subCategoryName);
			System.out.println();

			// Assert fields are not null
			Assert.assertNotNull(subCategoryId, "SubCategoryId should not be null.");
			Assert.assertNotNull(subCategoryName, "SubCategoryName should not be null.");
		}

		// Print the entire API response
		System.out.println("Full API Response:");
		customResponse.getResponse().prettyPrint();
	}

	@Test(priority = 10, groups = { "PL2" }, description = "1. Send a GET request to available Items by Store Id\n"
			+ "2. Validate that the ItemId, AvailableQuantity and StoreId are not null.\n"
			+ "3. Verify the response status code is 200.")
	public void availableItemsTest() throws Exception {
		// Initialize the ApiUtil object
		Map<String, String> searchResult = fileOperations.readExcelPOI(EXCEL_FILE_PATH, SHEET_NAME);

		apiUtil = new ApiUtil();

		String reqItemId = searchResult.get("itemId");
		String reqStoreId = searchResult.get("storeId");

		// Fetch the response from the API
		CustomResponse customResponse = apiUtil.getAvlQtyByStoreId(
				"/Inventory/AvailableQuantityByItemIdAndStoreId?itemId=" + reqItemId + "&storeId=" + reqStoreId, null);

		// Validate the method's source code
		boolean isValidationSuccessful = TestCodeValidator.validateTestMethodFromFile(FILEPATH, "getAvlQtyByStoreId",
				List.of("given", "then", "extract", "response"));
		Assert.assertTrue(isValidationSuccessful,
				"getAvlQtyByStoreId must be implemented using Rest Assured methods only.");

		// Validate response structure
		Assert.assertTrue(TestCodeValidator.validateResponseFields("getAvlQtyByStoreId", customResponse),
				"Must have all required fields in the response.");

		// 1. Verify Status code is 200
		Assert.assertEquals(customResponse.getStatusCode(), 200, "Status code should be 200 OK.");

		// 2. Validate that Status field is "OK"
		String status = customResponse.getStatus();
		Assert.assertEquals(status, "OK", "Status should be OK.");

		// 3. Extract and validate the Results field (map)
		Map<String, Object> results = customResponse.getMapResults();
		Assert.assertNotNull(results, "Results field should not be null.");

		// Extract fields from the "Results" object
		Integer itemId = (Integer) results.get("ItemId");
		Float availableQuantity = (Float) results.get("AvailableQuantity");
		Integer storeId = (Integer) results.get("StoreId");

		// Print extracted fields
		System.out.println("ItemId: " + itemId);
		System.out.println("AvailableQuantity: " + availableQuantity);
		System.out.println("StoreId: " + storeId);

		// Assert fields are not null
		Assert.assertNotNull(itemId, "ItemId should not be null.");
		Assert.assertNotNull(availableQuantity, "AvailableQuantity should not be null.");
		Assert.assertNotNull(storeId, "StoreId should not be null.");

		// Print the entire API response
		System.out.println("Full API Response:");
		customResponse.getResponse().prettyPrint();
	}

	@Test(priority = 11, groups = { "PL2" }, description = "1. Send GET request to fetch the list of doctors.\n"
			+ "2. Verify the response status code is 200.\n" + "3. Validate the response contains 'Status' as 'OK'.\n"
			+ "4. Ensure EmployeeId exists and is unique for each record in the response.")
	public void validateReferrersFromEmployeeSettingsTest() throws Exception {
		apiUtil = new ApiUtil();

		// Fetch the response from the API
		CustomResponse customResponse = apiUtil.getReferrersFromEmployeeSettings("/EmployeeSettings/Referrers", null);

		// Validate the method's source code
		boolean isValidationSuccessful = TestCodeValidator.validateTestMethodFromFile(FILEPATH,
				"getReferrersFromEmployeeSettings", List.of("given", "then", "extract", "response"));
		Assert.assertTrue(isValidationSuccessful,
				"getReferrersFromEmployeeSettings must be implemented using Rest Assured methods only.");

		// Validate response structure
		Assert.assertTrue(TestCodeValidator.validateResponseFields("getReferrersFromEmployeeSettings", customResponse),
				"Must have all required fields in the response.");

		// 1. Verify Status code is 200
		Assert.assertEquals(customResponse.getStatusCode(), 200, "Status code should be 200 OK.");

		// 2. Validate that Status field is "OK"
		String status = customResponse.getStatus();
		Assert.assertEquals(status, "OK", "Status should be OK.");

		// 3. Extract and validate the Results field (list)
		List<Map<String, Object>> results = customResponse.getListResults();
		Assert.assertNotNull(results, "Results field should not be null.");
		Assert.assertFalse(results.isEmpty(), "Results list should not be empty.");

		// 4. Validate EmployeeId existence and uniqueness (without using forEach)
		Set<Integer> employeeIds = new HashSet<>();
		for (int i = 0; i < results.size(); i++) {
			Map<String, Object> referrer = results.get(i);

			// Extract and validate EmployeeId
			Integer employeeId = (Integer) referrer.get("EmployeeId");
			Assert.assertNotNull(employeeId, "EmployeeId should not be null for referrer at index " + i);

			// Ensure EmployeeId is unique
			Assert.assertTrue(employeeIds.add(employeeId), "Duplicate EmployeeId found: " + employeeId);
		}

		System.out.println("API Response:");
		customResponse.getResponse().prettyPrint();
	}

	@Test(priority = 12, groups = { "PL2" }, description = "1. Send GET request to fetch the list of service items.\n"
			+ "2. Verify the response status code is 200.\n" + "3. Validate the response contains 'Status' as 'OK'.\n"
			+ "4. Ensure BillItemPriceId and ServiceDepartmentId exist in the response.")
	public void validateLabBillCfgItemsByDepartmentNameTest() throws Exception {
		apiUtil = new ApiUtil();

		// Fetch the response from the API
		CustomResponse customResponse = apiUtil
				.getLabBillCfgItemsByDepartmentName("/Billing/LabBillCfgItems?departmentName=radiology", null);

		// Validate the method's source code
		boolean isValidationSuccessful = TestCodeValidator.validateTestMethodFromFile(FILEPATH,
				"getLabBillCfgItemsByDepartmentName", List.of("given", "then", "extract", "response"));
		Assert.assertTrue(isValidationSuccessful,
				"getLabBillCfgItemsByDepartmentName must be implemented using Rest Assured methods only.");

		// Validate response structure
		Assert.assertTrue(
				TestCodeValidator.validateResponseFields("getLabBillCfgItemsByDepartmentName", customResponse),
				"Must have all required fields in the response.");

		// 1. Verify Status code is 200
		Assert.assertEquals(customResponse.getStatusCode(), 200, "Status code should be 200 OK.");

		// 2. Validate that Status field is "OK"
		String status = customResponse.getStatus();
		Assert.assertEquals(status, "OK", "Status should be OK.");

		// 3. Extract and validate the Results field (list)
		List<Map<String, Object>> results = customResponse.getListResults();
		Assert.assertNotNull(results, "Results field should not be null.");
		Assert.assertFalse(results.isEmpty(), "Results list should not be empty.");

		// 4. Validate BillItemPriceId and ServiceDepartmentId existence (without using
		// forEach)
		Set<Integer> billItemPriceIds = new HashSet<>();
		Set<Integer> serviceDepartmentIds = new HashSet<>();
		for (int i = 0; i < results.size(); i++) {
			Map<String, Object> serviceItem = results.get(i);

			// Extract and validate BillItemPriceId
			Integer billItemPriceId = (Integer) serviceItem.get("BillItemPriceId");
			Assert.assertNotNull(billItemPriceId, "BillItemPriceId should not be null for service item at index " + i);
			Assert.assertTrue(billItemPriceIds.add(billItemPriceId),
					"Duplicate BillItemPriceId found: " + billItemPriceId);

			// Extract and validate ServiceDepartmentId
			Integer serviceDepartmentId = (Integer) serviceItem.get("ServiceDepartmentId");
			Assert.assertNotNull(serviceDepartmentId,
					"ServiceDepartmentId should not be null for service item at index " + i);
			Assert.assertTrue(serviceDepartmentIds.add(serviceDepartmentId),
					"Duplicate ServiceDepartmentId found: " + serviceDepartmentId);
		}

		System.out.println("API Response:");
		customResponse.getResponse().prettyPrint();
	}

	@Test(priority = 13, groups = { "PL2" }, description = "1. Send GET request to fetch patient visit details.\n"
			+ "2. Verify the response status code is 200.\n" + "3. Validate the response contains 'Status' as 'OK'.\n"
			+ "4. Ensure PatientId, PatientVisitId, PerformerId, and VisitDate are not null.")
	public void validatePatientCurrentVisitContextByPatientIdAndVisitIdTest() throws Exception {
		apiUtil = new ApiUtil();
		int patientId = 176;
		int visitId = 147;

		// Fetch the response from the API
		CustomResponse customResponse = apiUtil.getPatientCurrentVisitContextByPatientIdAndVisitId(
				"/Visit/PatientCurrentVisitContext?patientId=" + patientId + "&visitId=" + visitId, null);

		// Validate response structure
		Assert.assertTrue(TestCodeValidator.validateResponseFields("getPatientCurrentVisitContextByPatientIdAndVisitId",
				customResponse), "Must have all required fields in the response.");

		// 1. Verify Status code is 200
		Assert.assertEquals(customResponse.getStatusCode(), 200, "Status code should be 200 OK.");

		// 2. Validate that Status field is "OK"
		String status = customResponse.getStatus();
		Assert.assertEquals(status, "OK", "Status should be OK.");

		// 3. Extract and validate the Results field (map)
		Map<String, Object> results = customResponse.getMapResults();
		Assert.assertNotNull(results, "Results field should not be null.");

		// 4. Validate required fields
		Assert.assertNotNull(results.get("PatientId"), "PatientId should not be null.");
		Assert.assertEquals(results.get("PatientId"), patientId, "Patient ID does not match");
		Assert.assertNotNull(results.get("PatientVisitId"), "PatientVisitId should not be null.");
		Assert.assertEquals(results.get("PatientVisitId"), visitId, "Visit ID does not match");
		Assert.assertNotNull(results.get("PerformerId"), "PerformerId should not be null.");
		Assert.assertNotNull(results.get("VisitDate"), "VisitDate should not be null.");

		System.out.println("API Response:");
		customResponse.getResponse().prettyPrint();
	}

	@Test(priority = 14, groups = { "PL2" }, description = "1. Send GET request to fetch patient billing details.\n"
			+ "2. Verify the response status code is 200.\n" + "3. Validate the response contains 'Status' as 'OK'.\n"
			+ "4. Ensure PatientId matches the provided parameter.")
	public void validatePatientBillingContextByPatientIdTest() throws Exception {
		apiUtil = new ApiUtil();
		int expectedPatientId = 176;

		// Fetch the response from the API
		CustomResponse customResponse = apiUtil.getPatientBillingContextByPatientId(
				"/Billing/PatientBillingContext?patientId=" + expectedPatientId, null);

		// Validate the method's source code
		boolean isValidationSuccessful = TestCodeValidator.validateTestMethodFromFile(FILEPATH,
				"getPatientBillingContextByPatientId", List.of("given", "then", "extract", "response"));
		Assert.assertTrue(isValidationSuccessful,
				"getPatientBillingContextByPatientId must be implemented using Rest Assured methods only.");

		// Validate response structure
		Assert.assertTrue(
				TestCodeValidator.validateResponseFields("getPatientBillingContextByPatientId", customResponse),
				"Must have all required fields in the response.");

		// 1. Verify Status code is 200
		Assert.assertEquals(customResponse.getStatusCode(), 200, "Status code should be 200 OK.");

		// 2. Validate that Status field is "OK"
		String status = customResponse.getStatus();
		Assert.assertEquals(status, "OK", "Status should be OK.");

		// 3. Extract and validate the Results field (map)
		Map<String, Object> results = customResponse.getMapResults();
		Assert.assertNotNull(results, "Results field should not be null.");

		// 4. Validate that PatientId matches the provided parameter
		Integer actualPatientId = (Integer) results.get("PatientId");
		Assert.assertEquals(actualPatientId, expectedPatientId, "PatientId does not match the provided parameter.");

		System.out.println("API Response:");
		customResponse.getResponse().prettyPrint();
	}

	@Test(priority = 15, groups = {
			"PL2" }, description = "1. Retrieve the patient ID by searching the patient Devid8 Roy8.\n"
					+ "2. Use the patient ID to fetch the patient's clinical notes.\n"
					+ "3. Validate the response status code, status, and key fields in the results.")
	public void validatePatientNotes() throws IOException {
		apiUtil = new ApiUtil();
		String expectedPatientName = "Devid8 Roy8";

		// Step 1: Get the patient ID by searching for the patient
		String patientSearchEndpoint = "/Patient/SearchRegisteredPatient?search=" + expectedPatientName;
		CustomResponse patientSearchResponse = apiUtil.searchPatient(patientSearchEndpoint, null);
		patientSearchResponse.getResponse().prettyPrint();

		// Validate the response of the patient search
		Assert.assertEquals(patientSearchResponse.getStatusCode(), 200, "Status code should be 200.");
		String searchStatus = patientSearchResponse.getStatus();
		Assert.assertEquals(searchStatus, "OK", "Status should be OK.");

		// Extract the patient ID
		Integer patientId = (Integer) patientSearchResponse.getListResults().get(0).get("PatientId");
		Assert.assertNotNull(patientId, "PatientId should not be null.");

		System.out.println("Patient ID retrieved: " + patientId);

		// Step 2: Use the patient ID to fetch clinical notes
		String patientNotesEndpoint = "/Clinical/PatientNotes?patientId=" + patientId;
		CustomResponse patientNotesResponse = apiUtil.getPatientNotes(patientNotesEndpoint, null);

		// Validate the response of the patient notes API
		Assert.assertEquals(patientNotesResponse.getStatusCode(), 200, "Status code should be 200.");
		String notesStatus = patientNotesResponse.getStatus();
		Assert.assertEquals(notesStatus, "OK", "Status should be OK.");

		// Validate that the results contain valid notes data
		List<Map<String, Object>> notesResults = patientNotesResponse.getListResults();
		for (int i = 0; i < notesResults.size(); i++) {
			Map<String, Object> note = notesResults.get(i);

			// Extract and validate each field
			Assert.assertNotNull(note.get("PatientId"), "PatientId should not be null in the notes.");
			Assert.assertNotNull(note.get("NotesId"), "NotesId should not be null in the notes.");
			Assert.assertNotNull(note.get("CreatedOn"), "CreatedOn should not be null in the notes.");
		}

		// Print the response for debugging purposes
		System.out.println("Patient Notes Response:");
		patientNotesResponse.getResponse().prettyPrint();
	}

	@Test(priority = 16, groups = { "PL2" }, description = "1. Send a GET request to fetch admitted patients data.\n"
			+ "2. Validate that all required fields in the response are not null.\n"
			+ "3. Verify the response status code is 200 and the status is 'OK'.\n"
			+ "4. Validate the 'Action' field in the BedInformation object is 'Admission'.")
	public void validateAdmittedPatientsData() throws IOException {
		apiUtil = new ApiUtil();

		// Step 1: Send a GET request to fetch admitted patients data
		String endpoint = "/Admission/AdmittedPatientsData?admissionStatus=admitted";
		CustomResponse response = apiUtil.getAdmittedPatientsData(endpoint);

		// Validate the response status code and status
		Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200.");
		String status = response.getStatus();
		Assert.assertEquals(status, "OK", "Status should be OK.");

		// Step 2: Validate key fields in the 'Results' array
		List<Map<String, Object>> results = response.getListResults();
		Assert.assertNotNull(results, "Results array should not be null.");
		Assert.assertFalse(results.isEmpty(), "Results array should not be empty.");

		// Step 3: Validate that all required fields are not null
		for (int i = 0; i < results.size(); i++) {
			Map<String, Object> patient = results.get(i);

			// Validate required fields in patient data
			Assert.assertNotNull(patient.get("PatientId"), "PatientId should not be null.");
			Assert.assertNotNull(patient.get("PatientAdmissionId"), "PatientAdmissionId should not be null.");
			Assert.assertNotNull(patient.get("AdmittedDate"), "AdmittedDate should not be null.");
			Assert.assertNotNull(patient.get("Name"), "Name should not be null.");
			Assert.assertNotNull(patient.get("Gender"), "Gender should not be null.");
			Assert.assertNotNull(patient.get("Department"), "Department should not be null.");
			Assert.assertNotNull(patient.get("AdmittingDoctorName"), "AdmittingDoctorName should not be null.");

			// Validate nested BedInformation object
			Map<String, Object> bedInfo = (Map<String, Object>) patient.get("BedInformation");
			Assert.assertNotNull(bedInfo, "BedInformation object should not be null.");
			Assert.assertNotNull(bedInfo.get("BedId"), "BedId should not be null in BedInformation.");
			Assert.assertNotNull(bedInfo.get("Ward"), "Ward should not be null in BedInformation.");
			Assert.assertNotNull(bedInfo.get("BedCode"), "BedCode should not be null in BedInformation.");

			// Validate that the Action field in BedInformation is either "Admission" or
			// "Transfer"
			String action = (String) bedInfo.get("Action");
			Assert.assertNotNull(action, "Action should not be null in BedInformation.");
			Assert.assertTrue(action.equals("Admission") || action.equals("Transfer"),
					"Action in BedInformation should be either 'Admission' or 'Transfer'.");
		}

		// Print the entire API response for debugging
		System.out.println("Response:");
		response.getResponse().prettyPrint();
	}

}
