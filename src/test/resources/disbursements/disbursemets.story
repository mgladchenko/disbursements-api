Disbursements Flows
Meta: @disb

Narrative:
  In order to verify Disbursements API
  As a registered Xandit API user
  I want to be able to execute basic flows with c

Lifecycle: Before: Scope: SCENARIO
Given I set valid API_SECRET_KEY

Scenario: Create new Disbursement
Meta: @create-disb
Given I prepare new disbursement object with: <bankCode>, <accountHolderName>, <accountNumber>, <description>, <amount>
When I execute POST disbursement request
Then I expect response status code 200
Then I validate response body

Examples:
| bankCode| accountHolderName| accountNumber | description | amount | status  |
| BCA     | Joe              | 1234567890    | TA disb     | 15000  | PENDING |
