**Project contains automated scenarios for Xendit  'Disbursement' API**


**Setup on OS X:**
1. Install Homebrew package manager if not installed (https://brew.sh/)
2. Install JDK 8 or higher via Homebrew   
`brew cask install java`  
3. Verify that JDK was installed  
`java -version`
4. Install Maven via Homebrew  
`brew install maven`
5. Verify that Maven was installed  
`mvn -version`   

**To Run tests from command line:**  
`mvn clean verify`  
Note: This will run RunJBehaveTest which specifies BDD Story file for execution.

**View Serenity HTML report:**  
`mvn serenity:aggregate`    
Report will be available at: http://localhost:63342/disbursements-api/target/site/serenity/