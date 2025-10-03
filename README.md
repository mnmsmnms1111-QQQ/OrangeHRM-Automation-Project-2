OrangeHRM Automation Project

Project Name: OrangeHRM Automation
Date: 10/3/2025
Website: OrangeHRM Demo

Project Overview

This project automates testing for the OrangeHRM demo website. The automation covers key HR workflows including:

Employee creation and role assignment

Employee report generation and export

Negative test cases for data validation (e.g., duplicate IDs, invalid names)

The goal is to ensure HR processes function correctly, identify data issues, and verify access rights based on roles.

Features

Employee Management Automation: Create new employees, assign roles, verify login credentials.

Report Generation: Generate employee reports with filters and export to Excel/PDF.

Negative Test Automation: Validate invalid inputs for names, IDs, and login details.

Data-Driven Testing: Supports Excel and JSON datasets for test inputs.

Screenshots on Test Failures: Captures evidence for failed test cases.

Tech Stack

Programming Language: Java 21

Testing Framework: TestNG

Automation Framework: Selenium WebDriver

Build Tool: Maven

Browser Support: Chrome (via ChromeDriver)

Dependencies: Guice, Selenium, Apache POI (for Excel), JSON parser libraries

Project Structure
orangehrm/
│
├─ src/main/java/
│   ├─ adminpage/               # Admin page objects and actions
│   ├─ loginpage/               # Login page classes
│   ├─ pimpage/                 # PIM page objects
│
├─ src/test/java/
│   ├─ dataproviders/       # Data provider classes
│       └─ DataProviders.java
│   ├─ tests/               # Test classes
│       ├─ BaseTest.java
│       ├─ DriveFactory.java
│       ├─ EmployeeCreation.java
│       ├─ EmployeeCreationNegative.java
│       ├─ EmployeeReports.java
│       └─ EmployeeReportsNegative.java
│
├─ utils/
│   ├─ ExcelFileManager.java
│   └─ JsonReader.java
│
├─ resources/
│   ├─ dataset.xlsx
│   └─ employeeData.json
│
├─ target/
│   ├─ pom.xml
│   └─ testng.xml

Test Scenarios
1. Employee Creation and Role Assignment (PIM + Admin)

Goal: HR creates a new employee and assigns correct roles.

Steps:

Login as Admin.

Navigate to PIM → Add Employee.

Enter employee details (first name, last name, employee ID).

Enable Create Login Details, set username, password, and status.

Assign user role (ESS, Admin, Supervisor) in Admin → User Management → Users.

Save the employee record.

Verify login with assigned credentials.

Confirm access rights according to role.

Modules: PIM, Admin, User Management

2. Employee Report Generation and Export

Goal: Admin generates employee reports for HR analysis.

Steps:

Login as Admin.

Navigate to PIM → Reports.

Search and open Employee Contact info report.

Apply filters (optional: Job Title, Department, Employment Status).

Generate report showing employee data: personal info, job info, attendance, leave.

Export report to Excel or PDF.

Bonus: Analyze workforce trends, role distribution, leave patterns.

Use reports to inform promotions, payroll, and staffing decisions (via email).

Modules: Reports, PIM, Time, Leave

3. Negative Test Cases

The automation covers invalid inputs for employee creation:

Test Name	Description	Screenshot
Duplicate Employee ID	Prevent duplicate Employee IDs	duplicateIDError
Blank Login Details	Verify login fields cannot be blank	blankLoginError
Long Names	Names > 255 chars should be invalid	longNamesError
Letters in Employee ID	Employee ID cannot contain letters	lettersInEmpIDError
Spaces in Employee ID	Employee ID cannot contain spaces	spacesInEmpIDError
Numeric Names	Names cannot be numeric	numericNamesError
Very Short Names	Names of 1 character	shortNamesError
Special Characters in Employee ID	Employee ID cannot contain special chars	specialCharsInEmpIDError
Middle Name Special Characters	Middle name cannot contain special chars	middleNameSpecialCharsError
Setup / Installation

Clone the repository:

git clone <repository-url>


Install prerequisites:

JDK 21

Maven 3.x

Chrome browser + ChromeDriver

Build project using Maven:

mvn clean install

Running Tests

Run all tests using Maven:

mvn clean test


Run specific TestNG suite:

mvn test -Dsurefire.suiteXmlFiles=testng.xml


Test reports: Located in target/surefire-reports.

Screenshots: Saved during test failures in the screenshots/ folder.

Data Files

Excel: resources/dataset.xlsx – Test data for employees

JSON: resources/employeeData.json – User credentials and configurations

Contributing

Follow existing page object and test structures.

Add new test cases in tests/ package.

Use ExcelFileManager or JsonReader for test data.

Notes

All test classes follow TestNG conventions.

Automated negative tests capture screenshots for validation.

Maven Surefire is used to run tests; TestNG suite is defined in testng.xml.
