Cash Disbursements Flows
Meta: @cash-disb

Narrative:
  In order to verify Cash Disbursements API
  As a registered Xandit API user
  I want to be able to execute basic flows with Cash Disbursements

Lifecycle: Before: Scope: SCENARIO
Given I set valid API_SECRET_KEY

Scenario: Create new Cash Disbursement
Meta: @create-cash-disb
When I enter <value1> value
Then I should see <value1> value on screen