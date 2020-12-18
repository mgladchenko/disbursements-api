Disbursements Flows
Meta: @disb

Narrative:
  In order to verify Disbursements API
  As a registered Xandit API user
  I want to be able to execute basic flows with Disbursements

Scenario: Create Disbursement in various states
Meta: @disb-create
Given I set valid API_SECRET_KEY
Given I prepare new disbursement object with: <bankCode>, <accountHolderName>, <accountNumber>, <description>, <amount>
When I execute POST disbursement request
Then I expect response status code 200
Then I validate response body and disbursement status <initialStatus>
When I execute GET disbursement request
Then I expect response status code 200
Then I validate response body and disbursement status <finalStatus>

Examples:
| bankCode| accountHolderName| accountNumber | description                        | amount    |initialStatus | finalStatus |
| BCA     | Joe              | 1234567890    | TA PENDING                         | 150000    |PENDING       | PENDING     |
| BCA     | Joe              | 1234567890    | TA COMPLETED                       | 90000     |PENDING       | COMPLETED   |
| BCA     | Jack             | 86868677      | TA Not enough balance error        | 999999999 |PENDING       | FAILED      |
| MANDIRI | Siti             | 12121212      | TA Switching network error         | 150000    |PENDING       | FAILED      |
| MANDIRI | Budi             | 8787878       | TA Rejected by bank error          | 150000    |PENDING       | FAILED      |
| MANDIRI | Sutiono          | 868686        | TA Temporary unknown error         | 150000    |PENDING       | FAILED      |
| MANDIRI | Adnin            | 1351357       | TA Fatal error                     | 150000    |PENDING       | FAILED      |
| MANDIRI | Andri            | 987654321     | TA Unknown bank network error      | 150000    |PENDING       | FAILED      |
| MANDIRI | Yono             | 321321321     | TA Bank maintenance downtime error | 150000    |PENDING       | FAILED      |
| MANDIRI | Rizky            | 7654321       | TA Bank account does not exist     | 150000    |PENDING       | FAILED      |

Scenario: Disbursement API validations
Meta: @disb-validations
Given I set <apiSecretKey> API_SECRET_KEY
Given I prepare new disbursement object with: <bankCode>, Mykola, 777, TA-desc, 150000
When I execute POST disbursement request
Then I expect response status code <statusCode>

Examples:
| apiSecretKey | bankCode | errorMessage                                   | statusCode |
| invalid      | BCA      | API key is not authorized for this API service | 401        |
| empty        | BCA      | API key is not authorized for this API service | 401        |
| valid        | Mono     | Bank code is not supported                     | 400        |


