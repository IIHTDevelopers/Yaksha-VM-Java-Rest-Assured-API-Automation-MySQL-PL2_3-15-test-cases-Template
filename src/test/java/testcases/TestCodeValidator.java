package testcases;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rest.CustomResponse;

public class TestCodeValidator {

	// Method to validate if specific keywords are used in the method's source code
	public static boolean validateTestMethodFromFile(String filePath, String methodName, List<String> keywords)
			throws IOException {
		// Read the content of the test class file
		String fileContent = new String(Files.readAllBytes(Paths.get(filePath)));

		// Extract the method body for the specified method using regex
		String methodRegex = "(public\\s+CustomResponse\\s+" + methodName + "\\s*\\(.*?\\)\\s*\\{)([\\s\\S]*?)}";
		Pattern methodPattern = Pattern.compile(methodRegex);
		Matcher methodMatcher = methodPattern.matcher(fileContent);

		if (methodMatcher.find()) {

			String methodBody = fetchBody(filePath, methodName);

			// Now we validate the method body for the required keywords
			boolean allKeywordsPresent = true;

			// Loop over the provided keywords and check if each one is present in the
			// method body
			for (String keyword : keywords) {
				Pattern keywordPattern = Pattern.compile("\\b" + keyword + "\\s*\\(");
				if (!keywordPattern.matcher(methodBody).find()) {
					System.out.println("'" + keyword + "()' is missing in the method.");
					allKeywordsPresent = false;
				}
			}

			return allKeywordsPresent;

		} else {
			System.out.println("Method " + methodName + " not found in the file.");
			return false;
		}
	}

	// This method takes the method name as an argument and returns its body as a
	// String.
	public static String fetchBody(String filePath, String methodName) {
		StringBuilder methodBody = new StringBuilder();
		boolean methodFound = false;
		boolean inMethodBody = false;
		int openBracesCount = 0;

		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = br.readLine()) != null) {
				// Check if the method is found by matching method signature
				if (line.contains("public CustomResponse " + methodName + "(")
						|| line.contains("public String " + methodName + "(")
						|| line.contains("public Response " + methodName + "(")) {
					methodFound = true;
				}

				// Once the method is found, start capturing lines
				if (methodFound) {
					if (line.contains("{")) {
						inMethodBody = true;
						openBracesCount++;
					}

					// Capture the method body
					if (inMethodBody) {
						methodBody.append(line).append("\n");
					}

					// Check for closing braces to identify the end of the method
					if (line.contains("}")) {
						openBracesCount--;
						if (openBracesCount == 0) {
							break; // End of method body
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return methodBody.toString();
	}

	public static boolean validateResponseFields(String methodName, CustomResponse customResponse) {
		boolean isValid = true;

		switch (methodName) {

		case "getAllDepartments":
			// List of fields to check at the top level
			List<String> expectedTopLevelFields = List.of("Status");

			// Print the response for debugging
			System.out.println(customResponse.getResponse().prettyPrint());

			// Validate the response structure for required top-level fields
			for (String field : expectedTopLevelFields) {
				if (customResponse.getResponse().jsonPath().get(field) == null) {
					isValid = false;
					System.out.println("Missing field in response: " + field);
				}
			}

			// Validate the Status field at the top level
			String statusField = customResponse.getResponse().jsonPath().getString("Status");
			if (statusField == null || !statusField.equals("OK")) {
				isValid = false;
				System.out.println("Status field is missing or invalid in the response.");
			}

			// Validate the Results array inside the response
			List<Map<String, Object>> results = customResponse.getListResults();
			if (results == null || results.isEmpty()) {
				isValid = false;
				System.out.println("Results array is missing or empty.");
			}

			// Validate each department in the Results (without using forEach)
			for (int i = 0; i < results.size(); i++) {
				Map<String, Object> result = results.get(i);

				// Extract and validate fields
				Integer departmentId = (Integer) result.get("DepartmentId");
				String departmentName = (String) result.get("DepartmentName");

				if (departmentId == null) {
					isValid = false;
					System.out.println("DepartmentId is missing for object at index " + i);
				}

				if (departmentName == null) {
					isValid = false;
					System.out.println("DepartmentName is missing for object at index " + i);
				}
			}

			break;

		case "getAllItems":
			// List of fields to check at the top level
			List<String> expectedTopLevelFields2 = List.of("Status");

			// Print the response for debugging
			System.out.println(customResponse.getResponse().prettyPrint());

			// Validate the response structure for required top-level fields
			for (String field : expectedTopLevelFields2) {
				if (customResponse.getResponse().jsonPath().get(field) == null) {
					isValid = false;
					System.out.println("Missing field in response: " + field);
				}
			}

			// Validate the Status field at the top level
			String statusField2 = customResponse.getResponse().jsonPath().getString("Status");
			if (statusField2 == null || !statusField2.equals("OK")) {
				isValid = false;
				System.out.println("Status field is missing or invalid in the response.");
			}

			// Validate the Results array inside the response
			List<Map<String, Object>> results2 = customResponse.getListResults();
			if (results2 == null || results2.isEmpty()) {
				isValid = false;
				System.out.println("Results array is missing or empty.");
			}

			// Validate each item in the Results (without using forEach)
			for (int i = 0; i < results2.size(); i++) {
				Map<String, Object> result = results2.get(i);

				// Extract and validate fields
				Integer itemId = (Integer) result.get("ItemId");
				String itemName = (String) result.get("ItemName");

				if (itemId == null) {
					isValid = false;
					System.out.println("ItemId is missing for object at index " + i);
				}

				if (itemName == null) {
					isValid = false;
					System.out.println("ItemName is missing for object at index " + i);
				}
			}

			break;

		case "getIncentiveSummaryReport":
			// List of fields to check at the top level
			List<String> expectedTopLevelFields3 = List.of("Status");

			// Print the response for debugging
			System.out.println(customResponse.getResponse().prettyPrint());

			// Validate the response structure for required top-level fields
			for (String field : expectedTopLevelFields3) {
				if (customResponse.getResponse().jsonPath().get(field) == null) {
					isValid = false;
					System.out.println("Missing field in response: " + field);
				}
			}

			// Validate the Status field at the top level
			String statusField3 = customResponse.getResponse().jsonPath().getString("Status");
			if (statusField3 == null || !statusField3.equals("OK")) {
				isValid = false;
				System.out.println("Status field is missing or invalid in the response.");
			}

			// Validate the Results array inside the response
			List<Map<String, Object>> results3 = customResponse.getListResults();
			if (results3 == null || results3.isEmpty()) {
				isValid = false;
				System.out.println("Results array is missing or empty.");
			}

			// Validate each record inside Results (without using forEach)
			for (int i = 0; i < results3.size(); i++) {
				Map<String, Object> result = results3.get(i);

				// Extract and validate each field
				String prescriberName = (String) result.get("PrescriberName");
				Integer prescriberId = (Integer) result.get("PrescriberId");
				Float docTotalAmount = (Float) result.get("DocTotalAmount");
				Float tdsAmount = (Float) result.get("TDSAmount");
				Float netPayableAmount = (Float) result.get("NetPayableAmount");

				if (prescriberName == null) {
					isValid = false;
					System.out.println("PrescriberName is missing for object at index " + i);
				}

				if (prescriberId == null) {
					isValid = false;
					System.out.println("PrescriberId is missing for object at index " + i);
				}

				if (docTotalAmount == null) {
					isValid = false;
					System.out.println("DocTotalAmount is missing for object at index " + i);
				}

				if (tdsAmount == null) {
					isValid = false;
					System.out.println("TDSAmount is missing for object at index " + i);
				}

				if (netPayableAmount == null) {
					isValid = false;
					System.out.println("NetPayableAmount is missing for object at index " + i);
				}
			}

			break;

		case "getIncReffSummReport":
			// List of fields to check at the top level
			List<String> expectedTopLevelFields4 = List.of("Status");

			// Print the response for debugging
			System.out.println(customResponse.getResponse().prettyPrint());

			// Validate the response structure for required top-level fields
			for (String field : expectedTopLevelFields4) {
				if (customResponse.getResponse().jsonPath().get(field) == null) {
					isValid = false;
					System.out.println("Missing field in response: " + field);
				}
			}

			// Validate the Status field at the top level
			String statusField4 = customResponse.getResponse().jsonPath().getString("Status");
			if (statusField4 == null || !statusField4.equals("OK")) {
				isValid = false;
				System.out.println("Status field is missing or invalid in the response.");
			}

			// Validate the Results array inside the response
			List<Map<String, Object>> results4 = customResponse.getListResults();
			if (results4 == null || results4.isEmpty()) {
				isValid = false;
				System.out.println("Results array is missing or empty.");
			}

			// Validate each record inside Results (without using forEach)
			for (int i = 0; i < results4.size(); i++) {
				Map<String, Object> result = results4.get(i);

				// Extract and validate each field
				String prescriberName = (String) result.get("PrescriberName");
				Integer prescriberId = (Integer) result.get("PrescriberId");
				Float docTotalAmount = (Float) result.get("DocTotalAmount");
				Float tdsAmount = (Float) result.get("TDSAmount");
				Float netPayableAmount = (Float) result.get("NetPayableAmount");

				if (prescriberName == null) {
					isValid = false;
					System.out.println("PrescriberName is missing for object at index " + i);
				}

				if (prescriberId == null) {
					isValid = false;
					System.out.println("PrescriberId is missing for object at index " + i);
				}

				if (docTotalAmount == null) {
					isValid = false;
					System.out.println("DocTotalAmount is missing for object at index " + i);
				}

				if (tdsAmount == null) {
					isValid = false;
					System.out.println("TDSAmount is missing for object at index " + i);
				}

				if (netPayableAmount == null) {
					isValid = false;
					System.out.println("NetPayableAmount is missing for object at index " + i);
				}
			}

			break;

		case "getHospIncIncReport":
			// List of fields to check at the top level
			List<String> expectedTopLevelFields5 = List.of("Status");

			// Print the response for debugging
			System.out.println(customResponse.getResponse().prettyPrint());

			// Validate the response structure for required top-level fields
			for (String field : expectedTopLevelFields5) {
				if (customResponse.getResponse().jsonPath().get(field) == null) {
					isValid = false;
					System.out.println("Missing field in response: " + field);
				}
			}

			// Validate the Status field at the top level
			String statusField5 = customResponse.getResponse().jsonPath().getString("Status");
			if (statusField5 == null || !statusField5.equals("OK")) {
				isValid = false;
				System.out.println("Status field is missing or invalid in the response.");
			}

			// Validate the Results array inside the response
			List<Map<String, Object>> results5 = customResponse.getListResults();
			if (results5 == null || results5.isEmpty()) {
				isValid = false;
				System.out.println("Results array is missing or empty.");
			}

			// Validate each record inside Results (without using forEach)
			for (int i = 0; i < results5.size(); i++) {
				Map<String, Object> result = results5.get(i);

				// Extract and validate each field
				Integer serviceDepartmentId = (Integer) result.get("ServiceDepartmentId");
				String serviceDepartmentName = (String) result.get("ServiceDepartmentName");
				Float netSales = (Float) result.get("NetSales");
				Float referralCommission = (Float) result.get("ReferralCommission");
				Float grossIncome = (Float) result.get("GrossIncome");
				Float otherIncentive = (Float) result.get("OtherIncentive");
				Float hospitalNetIncome = (Float) result.get("HospitalNetIncome");

				if (serviceDepartmentId == null) {
					isValid = false;
					System.out.println("ServiceDepartmentId is missing for object at index " + i);
				}

				if (serviceDepartmentName == null) {
					isValid = false;
					System.out.println("ServiceDepartmentName is missing for object at index " + i);
				}

				if (netSales == null) {
					isValid = false;
					System.out.println("NetSales is missing for object at index " + i);
				}

				if (referralCommission == null) {
					isValid = false;
					System.out.println("ReferralCommission is missing for object at index " + i);
				}

				if (grossIncome == null) {
					isValid = false;
					System.out.println("GrossIncome is missing for object at index " + i);
				}

				if (otherIncentive == null) {
					isValid = false;
					System.out.println("OtherIncentive is missing for object at index " + i);
				}

				if (hospitalNetIncome == null) {
					isValid = false;
					System.out.println("HospitalNetIncome is missing for object at index " + i);
				}
			}

			break;

		case "getEmpBillItem":
			// List of fields to check at the top level
			List<String> expectedTopLevelFields6 = List.of("Status");

			// Print the response for debugging
			System.out.println(customResponse.getResponse().prettyPrint());

			// Validate the response structure for required top-level fields
			for (String field : expectedTopLevelFields6) {
				if (customResponse.getResponse().jsonPath().get(field) == null) {
					isValid = false;
					System.out.println("Missing field in response: " + field);
				}
			}

			// Validate the Status field at the top level
			String statusField6 = customResponse.getResponse().jsonPath().getString("Status");
			if (statusField6 == null || !statusField6.equals("OK")) {
				isValid = false;
				System.out.println("Status field is missing or invalid in the response.");
			}

			// Validate the Results map inside the response
			Map<String, Object> result = customResponse.getMapResults();
			if (result == null) {
				isValid = false;
				System.out.println("Results map is missing.");
			}

			// Validate each field in Results (without using forEach)
			Integer employeeIncentiveInfoId = (Integer) result.get("EmployeeIncentiveInfoId");
			Integer employeeIdVal = (Integer) result.get("EmployeeId");
			String fullName = (String) result.get("FullName");
			Float tdsPercent = (Float) result.get("TDSPercent");
			Float empTdsPercent = (Float) result.get("EmpTDSPercent");
			Boolean isActive = (Boolean) result.get("IsActive");
			List<Map<String, Object>> employeeBillItemsMap = (List<Map<String, Object>>) result
					.get("EmployeeBillItemsMap");

			if (employeeIncentiveInfoId == null) {
				isValid = false;
				System.out.println("EmployeeIncentiveInfoId is missing.");
			}

			if (employeeIdVal == null) {
				isValid = false;
				System.out.println("EmployeeId is missing.");
			}

			if (fullName == null) {
				isValid = false;
				System.out.println("FullName is missing.");
			}

			if (tdsPercent == null) {
				isValid = false;
				System.out.println("TDSPercent is missing.");
			}

			if (empTdsPercent == null) {
				isValid = false;
				System.out.println("EmpTDSPercent is missing.");
			}

			if (isActive == null) {
				isValid = false;
				System.out.println("IsActive is missing.");
			}

			if (employeeBillItemsMap == null) {
				isValid = false;
				System.out.println("EmployeeBillItemsMap is missing.");
			}

			break;

		case "getInvntryFiscalYrs":
			// List of fields to check at the top level
			List<String> expectedTopLevelFields7 = List.of("Status");

			// Print the response for debugging
			System.out.println(customResponse.getResponse().prettyPrint());

			// Validate the response structure for required top-level fields
			for (String field : expectedTopLevelFields7) {
				if (customResponse.getResponse().jsonPath().get(field) == null) {
					isValid = false;
					System.out.println("Missing field in response: " + field);
				}
			}

			// Validate the Status field at the top level
			String statusField7 = customResponse.getResponse().jsonPath().getString("Status");
			if (statusField7 == null || !statusField7.equals("OK")) {
				isValid = false;
				System.out.println("Status field is missing or invalid in the response.");
			}

			// Validate the Results list inside the response
			List<Map<String, Object>> results7 = customResponse.getListResults();
			if (results7 == null || results7.isEmpty()) {
				isValid = false;
				System.out.println("Results list is missing or empty.");
			}

			// Validate each record inside Results (without using forEach)
			for (int i = 0; i < results7.size(); i++) {
				Map<String, Object> fiscalYear = results7.get(i);

				// Extract and validate each field
				Integer fiscalYearId = (Integer) fiscalYear.get("FiscalYearId");
				String fiscalYearName = (String) fiscalYear.get("FiscalYearName");
				String startDate = (String) fiscalYear.get("StartDate");
				String endDate = (String) fiscalYear.get("EndDate");
				Boolean isActive7 = (Boolean) fiscalYear.get("IsActive");

				if (fiscalYearId == null) {
					isValid = false;
					System.out.println("FiscalYearId is missing for object at index " + i);
				}

				if (fiscalYearName == null) {
					isValid = false;
					System.out.println("FiscalYearName is missing for object at index " + i);
				}

				if (startDate == null) {
					isValid = false;
					System.out.println("StartDate is missing for object at index " + i);
				}

				if (endDate == null) {
					isValid = false;
					System.out.println("EndDate is missing for object at index " + i);
				}

				if (isActive7 == null) {
					isValid = false;
					System.out.println("IsActive is missing for object at index " + i);
				}
			}

			break;

		case "getActInventory":
			// List of fields to check at the top level
			List<String> expectedTopLevelFields8 = List.of("Status");

			// Print the response for debugging
			System.out.println(customResponse.getResponse().prettyPrint());

			// Validate the response structure for required top-level fields
			for (String field : expectedTopLevelFields8) {
				if (customResponse.getResponse().jsonPath().get(field) == null) {
					isValid = false;
					System.out.println("Missing field in response: " + field);
				}
			}

			// Validate the Status field at the top level
			String statusField8 = customResponse.getResponse().jsonPath().getString("Status");
			if (statusField8 == null || !statusField8.equals("OK")) {
				isValid = false;
				System.out.println("Status field is missing or invalid in the response.");
			}

			// Validate the Results list inside the response
			List<Map<String, Object>> results8 = customResponse.getListResults();
			if (results8 == null || results8.isEmpty()) {
				isValid = false;
				System.out.println("Results list is missing or empty.");
			}

			// Validate each record inside Results (without using forEach)
			for (int i = 0; i < results8.size(); i++) {
				Map<String, Object> store = results8.get(i);

				// Extract and validate each field
				Integer storeId = (Integer) store.get("StoreId");
				String name = (String) store.get("Name");
				String storeDescription = (String) store.get("StoreDescription");

				if (storeId == null) {
					isValid = false;
					System.out.println("StoreId is missing for object at index " + i);
				}

				if (name == null) {
					isValid = false;
					System.out.println("Name is missing for object at index " + i);
				}

				if (storeDescription == null) {
					isValid = false;
					System.out.println("StoreDescription is missing for object at index " + i);
				}
			}

			break;

		case "getInvSubCat":
			// List of fields to check at the top level
			List<String> expectedTopLevelFields9 = List.of("Status");

			// Print the response for debugging
			System.out.println(customResponse.getResponse().prettyPrint());

			// Validate the response structure for required top-level fields
			for (String field : expectedTopLevelFields9) {
				if (customResponse.getResponse().jsonPath().get(field) == null) {
					isValid = false;
					System.out.println("Missing field in response: " + field);
				}
			}

			// Validate the Status field at the top level
			String statusField9 = customResponse.getResponse().jsonPath().getString("Status");
			if (statusField9 == null || !statusField9.equals("OK")) {
				isValid = false;
				System.out.println("Status field is missing or invalid in the response.");
			}

			// Validate the Results list inside the response
			List<Map<String, Object>> results9 = customResponse.getListResults();
			if (results9 == null || results9.isEmpty()) {
				isValid = false;
				System.out.println("Results list is missing or empty.");
			}

			// Validate each record inside Results (without using forEach)
			for (int i = 0; i < results9.size(); i++) {
				Map<String, Object> subCategory = results9.get(i);

				// Extract and validate each field
				Integer subCategoryId = (Integer) subCategory.get("SubCategoryId");
				String subCategoryName = (String) subCategory.get("SubCategoryName");

				if (subCategoryId == null) {
					isValid = false;
					System.out.println("SubCategoryId is missing for object at index " + i);
				}

				if (subCategoryName == null) {
					isValid = false;
					System.out.println("SubCategoryName is missing for object at index " + i);
				}
			}

			break;

		case "getAvlQtyByStoreId":
			// List of fields to check at the top level
			List<String> expectedTopLevelFields10 = List.of("Status");

			// Print the response for debugging
			System.out.println(customResponse.getResponse().prettyPrint());

			// Validate the response structure for required top-level fields
			for (String field : expectedTopLevelFields10) {
				if (customResponse.getResponse().jsonPath().get(field) == null) {
					isValid = false;
					System.out.println("Missing field in response: " + field);
				}
			}

			// Validate the Status field at the top level
			String statusField10 = customResponse.getResponse().jsonPath().getString("Status");
			if (statusField10 == null || !statusField10.equals("OK")) {
				isValid = false;
				System.out.println("Status field is missing or invalid in the response.");
			}

			// Validate the Results map inside the response
			Map<String, Object> results10 = customResponse.getMapResults();
			if (results10 == null) {
				isValid = false;
				System.out.println("Results map is missing.");
			}

			// Validate fields inside the Results map
			Integer itemId = (Integer) results10.get("ItemId");
			Float availableQuantity = (Float) results10.get("AvailableQuantity");
			Integer storeId = (Integer) results10.get("StoreId");

			if (itemId == null) {
				isValid = false;
				System.out.println("ItemId is missing.");
			}

			if (availableQuantity == null) {
				isValid = false;
				System.out.println("AvailableQuantity is missing.");
			}

			if (storeId == null) {
				isValid = false;
				System.out.println("StoreId is missing.");
			}

			break;

		case "getReferrersFromEmployeeSettings":
			// List of fields to check at the top level
			List<String> expectedTopLevelFields11 = List.of("Status");

			// Print the response for debugging
			System.out.println(customResponse.getResponse().prettyPrint());

			// Validate the response structure for required top-level fields
			for (String field : expectedTopLevelFields11) {
				if (customResponse.getResponse().jsonPath().get(field) == null) {
					isValid = false;
					System.out.println("Missing field in response: " + field);
				}
			}

			// Validate the Status field at the top level
			String statusField11 = customResponse.getResponse().jsonPath().getString("Status");
			if (statusField11 == null || !statusField11.equals("OK")) {
				isValid = false;
				System.out.println("Status field is missing or invalid in the response.");
			}

			// Validate the Results list inside the response
			List<Map<String, Object>> results11 = customResponse.getListResults();
			if (results11 == null || results11.isEmpty()) {
				isValid = false;
				System.out.println("Results list is missing or empty.");
			}

			// Validate each record inside Results (without using forEach)
			for (int i = 0; i < results11.size(); i++) {
				Map<String, Object> referrer = results11.get(i);

				// Extract and validate each field
				Integer employeeId = (Integer) referrer.get("EmployeeId");
				String fullName11 = (String) referrer.get("FullName");

				if (employeeId == null) {
					isValid = false;
					System.out.println("EmployeeId is missing for object at index " + i);
				}

				if (fullName11 == null) {
					isValid = false;
					System.out.println("FullName is missing for object at index " + i);
				}
			}

			break;

		case "getLabBillCfgItemsByDepartmentName":
			// List of fields to check at the top level
			List<String> expectedTopLevelFields12 = List.of("Status");

			// Print the response for debugging
			System.out.println(customResponse.getResponse().prettyPrint());

			// Validate the response structure for required top-level fields
			for (String field : expectedTopLevelFields12) {
				if (customResponse.getResponse().jsonPath().get(field) == null) {
					isValid = false;
					System.out.println("Missing field in response: " + field);
				}
			}

			// Validate the Status field at the top level
			String statusField12 = customResponse.getResponse().jsonPath().getString("Status");
			if (statusField12 == null || !statusField12.equals("OK")) {
				isValid = false;
				System.out.println("Status field is missing or invalid in the response.");
			}

			// Validate the Results list inside the response
			List<Map<String, Object>> results12 = customResponse.getListResults();
			if (results12 == null || results12.isEmpty()) {
				isValid = false;
				System.out.println("Results list is missing or empty.");
			}

			// Validate each record inside Results (without using forEach)
			Set<Integer> billItemPriceIds = new HashSet<>();
			Set<Integer> serviceDepartmentIds = new HashSet<>();
			for (int i = 0; i < results12.size(); i++) {
				Map<String, Object> serviceItem = results12.get(i);

				// Extract and validate BillItemPriceId
				Integer billItemPriceId = (Integer) serviceItem.get("BillItemPriceId");
				if (billItemPriceId == null) {
					isValid = false;
					System.out.println("BillItemPriceId is missing for service item at index " + i);
				} else if (!billItemPriceIds.add(billItemPriceId)) {
					isValid = false;
					System.out.println("Duplicate BillItemPriceId found: " + billItemPriceId);
				}

				// Extract and validate ServiceDepartmentId
				Integer serviceDepartmentId = (Integer) serviceItem.get("ServiceDepartmentId");
				if (serviceDepartmentId == null) {
					isValid = false;
					System.out.println("ServiceDepartmentId is missing for service item at index " + i);
				} else if (!serviceDepartmentIds.add(serviceDepartmentId)) {
					isValid = false;
					System.out.println("Duplicate ServiceDepartmentId found: " + serviceDepartmentId);
				}
			}

			break;

		case "getPatientCurrentVisitContextByPatientIdAndVisitId":
			// List of fields to check at the top level
			List<String> expectedTopLevelFields13 = List.of("Status");

			// Print the response for debugging
			System.out.println(customResponse.getResponse().prettyPrint());

			// Validate the response structure for required top-level fields
			for (String field : expectedTopLevelFields13) {
				if (customResponse.getResponse().jsonPath().get(field) == null) {
					isValid = false;
					System.out.println("Missing field in response: " + field);
				}
			}

			// Validate the Status field at the top level
			String statusField13 = customResponse.getResponse().jsonPath().getString("Status");
			if (statusField13 == null || !statusField13.equals("OK")) {
				isValid = false;
				System.out.println("Status field is missing or invalid in the response.");
			}

			// Validate the Results map inside the response
			Map<String, Object> results13 = customResponse.getMapResults();
			if (results13 == null || results13.isEmpty()) {
				isValid = false;
				System.out.println("Results map is missing or empty.");
			}

			// Validate required fields inside Results
			Integer patientId = (Integer) results13.get("PatientId");
			Integer patientVisitId = (Integer) results13.get("PatientVisitId");
			Integer performerId = (Integer) results13.get("PerformerId");
			String visitDate = (String) results13.get("VisitDate");

			if (patientId == null) {
				isValid = false;
				System.out.println("PatientId is missing.");
			}

			if (patientVisitId == null) {
				isValid = false;
				System.out.println("PatientVisitId is missing.");
			}

			if (performerId == null) {
				isValid = false;
				System.out.println("PerformerId is missing.");
			}

			if (visitDate == null) {
				isValid = false;
				System.out.println("VisitDate is missing.");
			}

			break;

		case "getPatientBillingContextByPatientId":
			// Set the expectedPatientId here
			Integer expectedPatientId = 176; // Example patient ID to check

			// List of fields to check at the top level
			List<String> expectedTopLevelFields14 = List.of("Status");

			// Print the response for debugging
			System.out.println(customResponse.getResponse().prettyPrint());

			// Validate the response structure for required top-level fields
			for (String field : expectedTopLevelFields14) {
				if (customResponse.getResponse().jsonPath().get(field) == null) {
					isValid = false;
					System.out.println("Missing field in response: " + field);
				}
			}

			// Validate the Status field at the top level
			String statusField14 = customResponse.getResponse().jsonPath().getString("Status");
			if (statusField14 == null || !statusField14.equals("OK")) {
				isValid = false;
				System.out.println("Status field is missing or invalid in the response.");
			}

			// Validate the Results map inside the response
			Map<String, Object> results14 = customResponse.getMapResults();
			if (results14 == null || results14.isEmpty()) {
				isValid = false;
				System.out.println("Results map is missing or empty.");
			}

			// Validate required fields inside Results
			Integer patientId14 = (Integer) results14.get("PatientId");
			if (patientId14 == null) {
				isValid = false;
				System.out.println("PatientId is missing.");
			}

			// Ensure PatientId matches the expected patientId
			if (patientId14 != null && !patientId14.equals(expectedPatientId)) {
				isValid = false;
				System.out.println("PatientId does not match the expected value.");
			}

			break;

		case "validatePatientNotes":
			// List of fields to check at the top level
			List<String> expectedTopLevelFields15 = List.of("Status");

			// Print the response for debugging
			System.out.println(customResponse.getResponse().prettyPrint());

			// Validate the response structure for required top-level fields
			for (String field : expectedTopLevelFields15) {
				if (customResponse.getResponse().jsonPath().get(field) == null) {
					isValid = false;
					System.out.println("Missing field in response: " + field);
				}
			}

			// Validate the Status field at the top level
			String statusField15 = customResponse.getResponse().jsonPath().getString("Status");
			if (statusField15 == null || !statusField15.equals("OK")) {
				isValid = false;
				System.out.println("Status field is missing or invalid in the response.");
			}

			// Validate the Results list inside the response
			List<Map<String, Object>> results15 = customResponse.getListResults();
			if (results15 == null || results15.isEmpty()) {
				isValid = false;
				System.out.println("Results list is missing or empty.");
			}

			break;

		case "validateAdmittedPatientsData":
			// List of fields to check at the top level
			List<String> expectedTopLevelFields16 = List.of("Status");

			// Print the response for debugging
			System.out.println(customResponse.getResponse().prettyPrint());

			// Validate the response structure for required top-level fields
			for (String field : expectedTopLevelFields16) {
				if (customResponse.getResponse().jsonPath().get(field) == null) {
					isValid = false;
					System.out.println("Missing field in response: " + field);
				}
			}

			// Validate the Status field at the top level
			String statusField16 = customResponse.getResponse().jsonPath().getString("Status");
			if (statusField16 == null || !statusField16.equals("OK")) {
				isValid = false;
				System.out.println("Status field is missing or invalid in the response.");
			}

			// Validate the Results list inside the response
			List<Map<String, Object>> results16 = customResponse.getListResults();
			if (results16 == null || results16.isEmpty()) {
				isValid = false;
				System.out.println("Results list is missing or empty.");
			}

			// Validate the fields inside each result
			for (int i = 0; i < results16.size(); i++) {
				Map<String, Object> patient = results16.get(i);

				// Validate required fields in patient data
				if (patient.get("PatientId") == null) {
					isValid = false;
					System.out.println("PatientId should not be null in result at index " + i);
				}
				if (patient.get("PatientAdmissionId") == null) {
					isValid = false;
					System.out.println("PatientAdmissionId should not be null in result at index " + i);
				}
				if (patient.get("AdmittedDate") == null) {
					isValid = false;
					System.out.println("AdmittedDate should not be null in result at index " + i);
				}
				if (patient.get("Name") == null) {
					isValid = false;
					System.out.println("Name should not be null in result at index " + i);
				}
				if (patient.get("Gender") == null) {
					isValid = false;
					System.out.println("Gender should not be null in result at index " + i);
				}
				if (patient.get("Department") == null) {
					isValid = false;
					System.out.println("Department should not be null in result at index " + i);
				}
				if (patient.get("AdmittingDoctorName") == null) {
					isValid = false;
					System.out.println("AdmittingDoctorName should not be null in result at index " + i);
				}

				// Validate nested BedInformation object
				Map<String, Object> bedInfo = (Map<String, Object>) patient.get("BedInformation");
				if (bedInfo == null) {
					isValid = false;
					System.out.println("BedInformation should not be null in result at index " + i);
				}
				if (bedInfo != null) {
					if (bedInfo.get("BedId") == null) {
						isValid = false;
						System.out.println("BedId should not be null in BedInformation in result at index " + i);
					}
					if (bedInfo.get("Ward") == null) {
						isValid = false;
						System.out.println("Ward should not be null in BedInformation in result at index " + i);
					}
					if (bedInfo.get("BedCode") == null) {
						isValid = false;
						System.out.println("BedCode should not be null in BedInformation in result at index " + i);
					}

					// Validate the Action field in BedInformation
					String action = (String) bedInfo.get("Action");
					if (action == null || !(action.equals("Admission") || action.equals("Transfer"))) {
						isValid = false;
						System.out.println(
								"Action in BedInformation should be either 'Admission' or 'Transfer' in result at index "
										+ i);
					}
				}
			}

			break;

		default:
			System.out.println("Method " + methodName + " is not recognized for validation.");
			isValid = false;
		}
		return isValid;
	}

}