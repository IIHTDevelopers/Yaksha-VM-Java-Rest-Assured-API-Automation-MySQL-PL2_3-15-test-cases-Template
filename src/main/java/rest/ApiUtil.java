package rest;

import java.util.List;
import java.util.Map;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ApiUtil {

	private static final String BASE_URL = "https://healthapp.yaksha.com/api";

	/**
	 * @Test1 This method fetches all departments from the API using the
	 *        "/AssetReports/GetAllDepartments" endpoint. It validates the response
	 *        status code, checks the "Status" field, and iterates through the
	 *        "Results" field to extract and validate DepartmentId and
	 *        DepartmentName for each record. It ensures that neither the
	 *        DepartmentId nor DepartmentName is null. After validation, it prints
	 *        the response for further inspection.
	 *
	 * @param endpoint - The API endpoint for fetching all departments.
	 * @param body     - Optional request body (null in this case).
	 *
	 * @return CustomResponse - The API response containing all the departments.
	 */
	public CustomResponse getAllDepartments(String endpoint, Object body) {
		RequestSpecification request = RestAssured.given().header("Authorization", AuthUtil.getAuthHeader())
				.header("Content-Type", "application/json");

		// Only add the body if it's not null
		if (body != null) {
			request.body(body);
		}

		// Execute the GET request and extract the response
		Response response = request.get(BASE_URL + endpoint).then().extract().response();

		// Extract required data from the response
		int statusCode = response.statusCode();
		String status = response.jsonPath().getString("Status");
		List<Map<String, Object>> results = response.jsonPath().getList("Results");

		// Return a CustomResponse object
		return new CustomResponse(response, statusCode, status, results);
	}

	/**
	 * @Test2 This method fetches all items from the API using the
	 *        "/AssetReports/GetAllItems" endpoint. It validates the response status
	 *        code, checks the "Status" field, and iterates through the "Results"
	 *        field to extract and validate ItemId and ItemName for each record. It
	 *        ensures that neither the ItemId nor ItemName is null. After
	 *        validation, it prints the response for further inspection.
	 *
	 * @param endpoint - The API endpoint for fetching all items.
	 * @param body     - Optional request body (null in this case).
	 *
	 * @return CustomResponse - The API response containing all the items.
	 */
	public CustomResponse getAllItems(String endpoint, Object body) {
		RequestSpecification request = RestAssured.given().header("Authorization", AuthUtil.getAuthHeader())
				.header("Content-Type", "application/json");

		// Only add the body if it's not null
		if (body != null) {
			request.body(body);
		}

		// Execute the GET request and extract the response
		Response response = request.get(BASE_URL + endpoint).then().extract().response();

		// Extract required data from the response
		int statusCode = response.statusCode();
		String status = response.jsonPath().getString("Status");
		List<Map<String, Object>> results = response.jsonPath().getList("Results");

		// Return a CustomResponse object
		return new CustomResponse(response, statusCode, status, results);
	}

	/**
	 * @Test3 This method fetches the incentive summary report for a specified date
	 *        range and referral condition from the API. It validates the response
	 *        status code, checks the "Status" field, and extracts fields such as
	 *        PrescriberName, PrescriberId, DocTotalAmount, TDSAmount, and
	 *        NetPayableAmount from the "JsonData" field (which contains stringified
	 *        JSON). It deserializes the JsonData into a List of Maps and iterates
	 *        through the records to validate and print the extracted fields.
	 *
	 * @param URL  - The API endpoint for fetching the incentive summary report.
	 * @param body - Optional request body (null in this case).
	 *
	 * @return CustomResponse - The API response containing the incentive summary
	 *         report.
	 */
	public CustomResponse getIncentiveSummaryReport(String URL, Object body) {
		RequestSpecification request = RestAssured.given().header("Authorization", AuthUtil.getAuthHeader())
				.header("Content-Type", "application/json");

		// Only add the body if it's not null
		if (body != null) {
			request.body(body);
		}

		// Execute the GET request and extract the response
		Response response = request.get(URL).then().extract().response();

		// Extract required data from the response
		int statusCode = response.statusCode();
		String status = response.jsonPath().getString("Status");
		String jsonDataString = response.jsonPath().getString("Results.JsonData");

		// Deserialize JsonData into a List of Maps
		List<Map<String, Object>> results = JsonPath.from(jsonDataString).getList("$");

		// Return a CustomResponse object
		return new CustomResponse(response, statusCode, status, results);
	}

	/**
	 * @Test4 This method fetches the incentive referral summary report for a
	 *        specified date range and referral condition from the API. It validates
	 *        the response status code, checks the "Status" field, and extracts
	 *        fields such as PrescriberName, PrescriberId, DocTotalAmount,
	 *        TDSAmount, and NetPayableAmount from the "JsonData" field (which
	 *        contains stringified JSON). It deserializes the JsonData into a List
	 *        of Maps and iterates through the records to validate and print the
	 *        extracted fields.
	 *
	 * @param URL  - The API endpoint for fetching the incentive referral summary
	 *             report.
	 * @param body - Optional request body (null in this case).
	 *
	 * @return CustomResponse - The API response containing the incentive referral
	 *         summary report.
	 */
	public CustomResponse getIncReffSummReport(String URL, Object body) {
		RequestSpecification request = RestAssured.given().header("Authorization", AuthUtil.getAuthHeader())
				.header("Content-Type", "application/json");

		// Only add the body if it's not null
		if (body != null) {
			request.body(body);
		}

		// Execute the GET request and extract the response
		Response response = request.get(URL).then().extract().response();

		// Extract required data from the response
		int statusCode = response.statusCode();
		String status = response.jsonPath().getString("Status");
		String jsonDataString = response.jsonPath().getString("Results.JsonData");

		// Deserialize JsonData into a List of Maps
		List<Map<String, Object>> results = JsonPath.from(jsonDataString).getList("$");

		// Return a CustomResponse object
		return new CustomResponse(response, statusCode, status, results);
	}

	/**
	 * @Test5 This method fetches the hospital income incentive report for a
	 *        specified date range and service department from the API. It validates
	 *        the response status code, checks the "Status" field, and extracts
	 *        fields such as ServiceDepartmentId, ServiceDepartmentName, NetSales,
	 *        ReferralCommission, GrossIncome, OtherIncentive, and HospitalNetIncome
	 *        from the "Results" array.
	 *
	 * @param URL  - The API endpoint for fetching the hospital income incentive
	 *             report.
	 * @param body - Optional request body (null in this case).
	 *
	 * @return CustomResponse - The API response containing the hospital income
	 *         incentive report.
	 */
	public CustomResponse getHospIncIncReport(String URL, Object body) {
		RequestSpecification request = RestAssured.given().header("Authorization", AuthUtil.getAuthHeader())
				.header("Content-Type", "application/json");

		// Only add the body if it's not null
		if (body != null) {
			request.body(body);
		}

		// Execute the GET request and extract the response
		Response response = request.get(URL).then().extract().response();

		// Extract required data from the response
		int statusCode = response.statusCode();
		String status = response.jsonPath().getString("Status");
		List<Map<String, Object>> results = response.jsonPath().getList("Results");

		// Return a CustomResponse object
		return new CustomResponse(response, statusCode, status, results);
	}

	/**
	 * @Test6 This method fetches employee bill items for a specific employee from
	 *        the API. It validates the response status, checks the "Status" field,
	 *        and extracts fields like EmployeeIncentiveInfoId, EmployeeId,
	 *        FullName, TDSPercent, EmpTDSPercent, IsActive, and
	 *        EmployeeBillItemsMap for validation.
	 *
	 * @param URL  - The API endpoint for fetching employee bill items.
	 * @param body - Optional request body (null in this case).
	 *
	 * @return CustomResponse - The API response containing employee bill items and
	 *         related data.
	 */
	public CustomResponse getEmpBillItem(String URL, Object body) {
		RequestSpecification request = RestAssured.given().header("Authorization", AuthUtil.getAuthHeader())
				.header("Content-Type", "application/json");

		// Only add the body if it's not null
		if (body != null) {
			request.body(body);
		}

		// Execute the GET request and extract the response
		Response response = request.get(BASE_URL + URL).then().extract().response();

		// Extract required data from the response
		int statusCode = response.statusCode();
		String status = response.jsonPath().getString("Status");
		Map<String, Object> results = response.jsonPath().getMap("Results");

		// Return a CustomResponse object
		return new CustomResponse(response, statusCode, status, results);
	}

	/**
	 * @Test7 This method fetches inventory fiscal years from the API and validates
	 *        various fields. It checks the fiscal year ID, name, start date, end
	 *        date, and active status for each fiscal year.
	 *
	 * @param URL  - The API endpoint for retrieving inventory fiscal years.
	 * @param body - Optional request body (null in this case).
	 *
	 * @return CustomResponse - The API response containing the fiscal years and
	 *         their details.
	 */
	public CustomResponse getInvntryFiscalYrs(String URL, Object body) {
		RequestSpecification request = RestAssured.given().header("Authorization", AuthUtil.getAuthHeader())
				.header("Content-Type", "application/json");

		// Only add the body if it's not null
		if (body != null) {
			request.body(body);
		}

		// Execute the GET request and extract the response
		Response response = request.get(BASE_URL + URL).then().extract().response();

		// Extract required data from the response
		int statusCode = response.statusCode();
		String status = response.jsonPath().getString("Status");
		List<Map<String, Object>> results = response.jsonPath().getList("Results");

		// Return a CustomResponse object
		return new CustomResponse(response, statusCode, status, results);
	}

	/**
	 * @Test8 This method activates inventory by retrieving store information from
	 *        the API. It fetches a list of stores and validates each store's ID,
	 *        name, and description.
	 *
	 * @param URL  - The API endpoint for activating inventory.
	 * @param body - Optional request body (null in this case).
	 *
	 * @return CustomResponse - The API response includes the HTTP status code and a
	 *         list of stores.
	 */
	public CustomResponse getActInventory(String URL, Object body) {
		RequestSpecification request = RestAssured.given().header("Authorization", AuthUtil.getAuthHeader())
				.header("Content-Type", "application/json");

		// Only add the body if it's not null
		if (body != null) {
			request.body(body);
		}

		// Execute the GET request and extract the response
		Response response = request.get(BASE_URL + URL).then().extract().response();

		// Extract required data from the response
		int statusCode = response.statusCode();
		String status = response.jsonPath().getString("Status");
		List<Map<String, Object>> results = response.jsonPath().getList("Results");

		// Return a CustomResponse object
		return new CustomResponse(response, statusCode, status, results);
	}

	/**
	 * @Test9 This method retrieves and validates the subcategories of inventory
	 *        from the API. It fetches the list of subcategories and validates each
	 *        subcategory's ID and name.
	 *
	 * @param URL  - The API endpoint that provides the subcategories information.
	 * @param body - Optional request body (null in this case).
	 *
	 * @return CustomResponse - The API response includes the HTTP status code and a
	 *         list of subcategories.
	 */
	public CustomResponse getInvSubCat(String URL, Object body) {
		RequestSpecification request = RestAssured.given().header("Authorization", AuthUtil.getAuthHeader())
				.header("Content-Type", "application/json");

		// Only add the body if it's not null
		if (body != null) {
			request.body(body);
		}

		// Execute the GET request and extract the response
		Response response = request.get(BASE_URL + URL).then().extract().response();

		// Extract required data from the response
		int statusCode = response.statusCode();
		String status = response.jsonPath().getString("Status");
		List<Map<String, Object>> results = response.jsonPath().getList("Results");

		// Return a CustomResponse object
		return new CustomResponse(response, statusCode, status, results);
	}

	/**
	 * @Test10 This method retrieves and validates the available quantity of an item
	 *         in a specific store based on the provided item ID and store ID.
	 *
	 * @param URL  - The API endpoint that provides the available quantity of an
	 *             item by item ID and store ID.
	 * @param body - Optional request body (null in this case).
	 *
	 * @return CustomResponse - The API response includes the HTTP status code, item
	 *         details, and available quantity information.
	 */
	public CustomResponse getAvlQtyByStoreId(String URL, Object body) {
		RequestSpecification request = RestAssured.given().header("Authorization", AuthUtil.getAuthHeader())
				.header("Content-Type", "application/json");

		// Only add the body if it's not null
		if (body != null) {
			request.body(body);
		}

		// Execute the GET request and extract the response
		Response response = request.get(BASE_URL + URL).then().extract().response();

		// Extract required data from the response
		int statusCode = response.statusCode();
		String status = response.jsonPath().getString("Status");
		Map<String, Object> results = response.jsonPath().getMap("Results");

		// Return a CustomResponse object
		return new CustomResponse(response, statusCode, status, results);
	}

	/**
	 * @Test11 This method retrieves a list of referrers from employee settings
	 *         based on the provided criteria.
	 *
	 * @param endpoint - The API endpoint to fetch the referrers list.
	 * @param body     - Optional map containing parameters for filtering the
	 *                 referrers list. If null, no body will be included in the
	 *                 request.
	 * @description This method constructs a GET request with the necessary
	 *              authorization and content-type headers. If a body is provided,
	 *              it is sent as a JSON payload; otherwise, the request is executed
	 *              without a body. The method then extracts and returns the API
	 *              response containing the list of referrers.
	 * @return CustomResponse - The API response with the list of referrers.
	 */
	public CustomResponse getReferrersFromEmployeeSettings(String endpoint, Map<String, String> body) {
		RequestSpecification request = RestAssured.given().header("Authorization", AuthUtil.getAuthHeader())
				.header("Content-Type", "application/json");

		// Only add the body if it's not null
		if (body != null) {
			request.body(body);
		}

		// Execute the GET request and extract the response
		Response response = request.get(BASE_URL + endpoint).then().extract().response();

		// Extract required data from the response
		int statusCode = response.statusCode();
		String status = response.jsonPath().getString("Status");
		List<Map<String, Object>> results = response.jsonPath().getList("Results");

		// Return a CustomResponse object
		return new CustomResponse(response, statusCode, status, results);
	}

	/**
	 * @Test12 This method retrieves laboratory billing configuration items based on
	 *         the specified department name.
	 *
	 * @param endpoint - The API endpoint to fetch the lab billing configuration
	 *                 items.
	 * @param body     - Optional map containing parameters for filtering the lab
	 *                 billing items. If null, no body will be included in the
	 *                 request.
	 * @description This method constructs a GET request with the required
	 *              authorization and content-type headers. If a body is provided,
	 *              it is sent as a JSON payload; otherwise, the request is executed
	 *              without a body. The method then extracts and returns the API
	 *              response containing the lab billing configuration items.
	 * @return CustomResponse - The API response with the lab billing configuration
	 *         items.
	 */
	public CustomResponse getLabBillCfgItemsByDepartmentName(String endpoint, Map<String, String> body) {
		RequestSpecification request = RestAssured.given().header("Authorization", AuthUtil.getAuthHeader())
				.header("Content-Type", "application/json");

		// Only add the body if it's not null
		if (body != null) {
			request.body(body);
		}

		// Execute the GET request and extract the response
		Response response = request.get(BASE_URL + endpoint).then().extract().response();

		// Extract required data from the response
		int statusCode = response.statusCode();
		String status = response.jsonPath().getString("Status");
		List<Map<String, Object>> results = response.jsonPath().getList("Results");

		// Return a CustomResponse object
		return new CustomResponse(response, statusCode, status, results);
	}

	/**
	 * @Test13 This method retrieves the current visit context of a patient based on
	 *         the provided patient ID and visit ID.
	 *
	 * @param endpoint - The API endpoint to fetch the patient's current visit
	 *                 context.
	 * @param body     - Optional map containing parameters for filtering the visit
	 *                 context. If null, no body will be included in the request.
	 * @description This method constructs a GET request with the required
	 *              authorization and content-type headers. If a body is provided,
	 *              it is sent as a JSON payload; otherwise, the request is executed
	 *              without a body. The method then extracts and returns the API
	 *              response containing the patient's current visit context.
	 * @return CustomResponse - The API response with the current visit context of
	 *         the patient.
	 */
	public CustomResponse getPatientCurrentVisitContextByPatientIdAndVisitId(String endpoint,
			Map<String, String> body) {
		RequestSpecification request = RestAssured.given().header("Authorization", AuthUtil.getAuthHeader())
				.header("Content-Type", "application/json");

		// Only add the body if it's not null
		if (body != null) {
			request.body(body);
		}

		// Execute the GET request and extract the response
		Response response = request.get(BASE_URL + endpoint).then().extract().response();
		System.out.println(BASE_URL + endpoint);
		// Extract required data from the response
		int statusCode = response.statusCode();
		String status = response.jsonPath().getString("Status");
		Map<String, Object> results = response.jsonPath().getMap("Results");

		// Return a CustomResponse object
		return new CustomResponse(response, statusCode, status, results);
	}

	/**
	 * @Test14 This method retrieves the billing context for a patient based on the
	 *         provided patient ID.
	 *
	 * @param endpoint - The API endpoint to fetch the patient's billing context.
	 * @param body     - Optional map containing parameters for filtering the
	 *                 billing context. If null, no body will be included in the
	 *                 request.
	 * @description This method constructs a GET request with the necessary
	 *              authorization and content-type headers. If a body is provided,
	 *              it is sent as a JSON payload; otherwise, the request is executed
	 *              without a body. The method extracts and returns the API response
	 *              containing the patient's billing context.
	 * @return CustomResponse - The API response with the billing context of the
	 *         patient.
	 */
	public CustomResponse getPatientBillingContextByPatientId(String endpoint, Map<String, String> body) {
		RequestSpecification request = RestAssured.given().header("Authorization", AuthUtil.getAuthHeader())
				.header("Content-Type", "application/json");

		// Only add the body if it's not null
		if (body != null) {
			request.body(body);
		}

		// Execute the GET request and extract the response
		Response response = request.get(BASE_URL + endpoint).then().extract().response();

		// Extract required data from the response
		int statusCode = response.statusCode();
		String status = response.jsonPath().getString("Status");
		Map<String, Object> results = response.jsonPath().getMap("Results");

		// Return a CustomResponse object
		return new CustomResponse(response, statusCode, status, results);
	}

	/**
	 * @Test15.1 This method searches for a registered patient by their name or
	 * other identifiers.
	 *
	 * @param endpoint - The API endpoint to which the GET request is sent.
	 * @param body     - Optional
	 * @description This method sends a GET request to search for a registered
	 *              patient and returns the response containing the patient details,
	 *              including the PatientId.
	 * @return CustomResponse - The API response includes details such as PatientId,
	 *         PatientCode, ShortName, etc.
	 */
	public CustomResponse searchPatient(String endpoint, Object body) {
		RequestSpecification request = RestAssured.given().header("Authorization", AuthUtil.getAuthHeader())
				.header("Content-Type", "application/json");

		if (body != null) {
			request.body(body);
		}

		// Execute the GET request and extract the response
		Response response = request.get(BASE_URL + endpoint).then().extract().response();

		// Extract required data from the response
		int statusCode = response.statusCode();
		String status = response.jsonPath().getString("Status");
		List<Map<String, Object>> results = response.jsonPath().getList("Results");

		// Return a CustomResponse object
		return new CustomResponse(response, statusCode, status, results);
	}

	/**
	 * @Test15.2 This method retrieves clinical notes for a given patient using
	 * their PatientId.
	 *
	 * @param endpoint - The API endpoint to which the GET request is sent.
	 * @param body     - Optional
	 * @description This method sends a GET request to fetch clinical notes for a
	 *              specific patient and returns the response containing the details
	 *              of the notes.
	 * @return CustomResponse - The API response includes details such as VisitCode,
	 *         PatientId, NotesId, etc.
	 */
	public CustomResponse getPatientNotes(String endpoint, Object body) {
		RequestSpecification request = RestAssured.given().header("Authorization", AuthUtil.getAuthHeader())
				.header("Content-Type", "application/json");

		if (body != null) {
			request.body(body);
		}

		// Execute the GET request and extract the response
		Response response = request.get(BASE_URL + endpoint).then().extract().response();

		// Extract required data from the response
		int statusCode = response.statusCode();
		String status = response.jsonPath().getString("Status");
		List<Map<String, Object>> results = response.jsonPath().getList("Results");

		// Return a CustomResponse object
		return new CustomResponse(response, statusCode, status, results);
	}

	/**
	 * @Test16 This method retrieves the data of admitted patients.
	 * 
	 * @param endpoint - The API endpoint to which the GET request is sent.
	 * @description This method sends a GET request to fetch the list of admitted
	 *              patients, including their admission details, patient
	 *              information, and bed information.
	 * @return CustomResponse - The API response contains the list of admitted
	 *         patients in the 'Results' array.
	 */
	public CustomResponse getAdmittedPatientsData(String endpoint) {
		RequestSpecification request = RestAssured.given().header("Authorization", AuthUtil.getAuthHeader())
				.header("Content-Type", "application/json");

		// Execute the GET request and extract the response
		Response response = request.get(BASE_URL + endpoint).then().extract().response();

		// Extract required data from the response
		int statusCode = response.statusCode();
		String status = response.jsonPath().getString("Status");
		List<Map<String, Object>> results = response.jsonPath().getList("Results");

		// Return a CustomResponse object
		return new CustomResponse(response, statusCode, status, results);
	}

}