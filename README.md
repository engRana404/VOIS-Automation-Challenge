# VOIS Automation Challenge 🚀

## 📌 Overview
This repository contains a **Hybrid Test Automation Framework** developed in **Java** using **Selenium WebDriver, TestNG, Maven, and Allure**.  
The framework automates the **Bing search scenario for “Vodafone”** and demonstrates:
- **Scalability & Maintainability** through Page Object Model (POM)
- **Reusability** with modular utilities and data-driven design
- **Cross-browser support** for Chrome, Firefox, and Edge
- **Professional reporting** with Allure (including screenshots on failures)
- **CI/CD integration** with GitHub Actions

---
## 🔹 Challenge Scenario

1. Open [Bing](https://www.bing.com)
2. Type and search for **“Vodafone”**
3. Validate that the **first results page** contains **2 "Related searches for Vodafone"** sections, each with items containing the word *Vodafone*
4. Navigate to **page 2** and count the number of normal search results (ignore maps, videos, images → only text results, ~9 expected)
5. Navigate to **page 3** and compare the number of results with page 2
6. Close the browser

---

## 📂 Project Structure
```
VOIS-Automation-Challenge/
├── src/
│   ├── main/java/com/vois/
│   │   ├── drivers/      # WebDriver setup and management
│   │   ├── listeners/    # TestNG and Allure listeners
│   │   ├── pages/        # Page Object Model classes
│   │   ├── reporting/    # Reporting utilities
│   │   └── utils/        # Utility classes
│   └── test/java/com/vois/tests/ # Test classes
├── BingTests.xml         # TestNG suite file
├── BingTestsParallel.xml # TestNG parallel suite file
├── pom.xml               # Maven configuration
├── allure-report/        # Generated Allure reports
├── allure-results/       # Allure results (generated after test run)
└── screenshots/          # Screenshots from test runs
```

---

## ⚡ Prerequisites
- **Java 17+** installed
- **Maven 3.6+** installed
- **Chrome / Firefox / Edge** installed
- **Allure CLI** (for report generation) → [Installation Guide](https://docs.qameta.io/allure/)

---

## ⚙️ Setup
1. Clone the repository:
   ```sh
   git clone <repo-url>
   cd VOIS-Automation-Challenge
   ```
2. Install dependencies:
   ```sh
   mvn clean install
   ```

## ▶️ Running Tests
- To run the default test suite:
  ```sh
  mvn test
  ```
- To run a specific suite (e.g., parallel):
  ```sh
  mvn -DsuiteXmlFile=BingTestsParallel.xml test
  ```

## 📊 Reporting
### Generating Allure Report
1. Run tests to generate results:
   ```sh
   mvn test
   ```
2. Generate and open the Allure report:
   ```sh
   allure serve allure-results
   ```
   (Requires Allure CLI: https://docs.qameta.io/allure/)

---

## 🛠️ Key Technologies
- Selenium WebDriver
- TestNG
- Allure Reporting
- WebDriverManager
- SLF4J & Log4j
- Maven
- GitHub Actions (for CI/CD)
- Page Object Model (POM)

---

## ✅ Challenge Coverage

| Criteria                             | Status |
|--------------------------------------| ------ |
| Script is Modular                    | ✅ Done |
| All Dependencies via Maven           | ✅ Done |
| Test Data in External Files          | ✅ Done |
| Execution Browser managed by user    | ✅ Done |
| Browser Support – Chrome             | ✅ Done |
| Browser Support – Firefox            | ✅ Done |
| Browser Support – Edge               | ✅ Done |
| JUnit/TestNG Tests                   | ✅ Done |
| Execution Report                     | ✅ Done |
| All Test Scenario Steps covered      | ✅ Done |
| Solution Description & Documentation | ✅ Done |
| Extra 1: Parallel Execution Support  | ✅ Done |
| Extra 2: GitHub Actions Integration  | ✅ Done |

---

## 👩‍💻 Author

### **Rana Gamal**  
- 🌐 [LinkedIn](https://www.linkedin.com/in/rana-gamal-daif)
- 💻 [GitHub](https://github.com/engRana404)
- ✉️ [Email](mailto:RanaGamalDaif@gmail.com) 