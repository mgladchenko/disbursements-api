Cash Disbursements Flows
Meta: @cash-disb

Narrative:
  In order to verify Cash Disbursements API
  As a registered Xandit API user
  I want to be able to execute basic flows with Cash Disbursements

Lifecycle: Before: Scope: SCENARIO
Given I set valid API_SECRET_KEY

Scenario: Create Cash Disbursement
Meta: @cash-disb-create
When I execute GET disbursement channel request
Then I expect response status code 200
Then I validate response body and disbursement channel with <channelCategory>, <currency>
Given I prepare new CASH disbursement object with: <amount>, <currency>, <beneficiary_id_name>
When I execute POST disbursement request
Then I expect response status code 200
Then I validate response body and disbursement status <initialStatus>
When I execute GET disbursement request
Then I expect response status code 200
Then I validate response body and disbursement status <finalStatus>

Narrative:
Note: Assuming that finalStatus will be triggered via specific amount+beneficiary_id_name mock combination
Note: EWALLET/BANK channelCategory should be covered in separate scenarios
Examples:
| channelCategory | amount | currency | beneficiary_id_name | initialStatus | finalStatus |
| CASH            | 1000   | PHP      | Joe                 | ACCEPTED      | ACCEPTED    |
| CASH            | 1000   | PHP      | Joe                 | ACCEPTED      | SENT        |
| CASH            | 1000   | PHP      | Joe                 | ACCEPTED      | LOCKED      |
| CASH            | 1000   | PHP      | Joe                 | ACCEPTED      | FAILED      |
| CASH            | 1000   | PHP      | Joe                 | ACCEPTED      | COMPLETED   |
| CASH            | 1000   | PHP      | Joe                 | ACCEPTED      | EXPIRED     |
| CASH            | 1000   | PHP      | Joe                 | ACCEPTED      | CANCELLED   |
| CASH            | 1000   | IDR      | Joe                 | ACCEPTED      | ACCEPTED    |
| CASH            | 1000   | IDR      | Joe                 | ACCEPTED      | SENT        |
| CASH            | 1000   | IDR      | Joe                 | ACCEPTED      | LOCKED      |
| CASH            | 1000   | IDR      | Joe                 | ACCEPTED      | FAILED      |
| CASH            | 1000   | IDR      | Joe                 | ACCEPTED      | COMPLETED   |
| CASH            | 1000   | IDR      | Joe                 | ACCEPTED      | EXPIRED     |
| CASH            | 1000   | IDR      | Joe                 | ACCEPTED      | CANCELLED   |

Scenario: Cancel Cash Disbursement
Meta: @cash-disb-cancel
When I execute GET disbursement channel request
Then I expect response status code 200
Then I validate response body and disbursement channel with <channelCategory>, <currency>
Given I prepare new CASH disbursement object with: <amount>, <currency>, <beneficiary_id_name>
When I execute POST disbursement request
Then I expect response status code 200
Then I validate response body and disbursement status ACCEPTED
When I execute GET disbursement request
Then I expect response status code 200
Then I validate response body and disbursement status <initialStatus>
When I execute POST disbursement cancel request
Then I expect response status code 200
When I execute GET disbursement request
Then I expect response status code 200
Then I validate response body and disbursement status <finalStatus>

Narrative:
Note: Assuming that initialStatus will be triggered via specific amount+beneficiary_id_name mock combination
Note: EWALLET/BANK channelCategory should be covered in separate scenarios
Examples:
| channelCategory | amount | currency | beneficiary_id_name | initialStatus | finalStatus |
| CASH            | 1000   | PHP      | Joe                 | ACCEPTED      | CANCELLED   |
| CASH            | 1000   | PHP      | Joe                 | SENT          | CANCELLED   |
| CASH            | 1000   | PHP      | Joe                 | LOCKED        | LOCKED      |
| CASH            | 1000   | PHP      | Joe                 | FAILED        | FAILED      |
| CASH            | 1000   | PHP      | Joe                 | COMPLETED     | COMPLETED   |
| CASH            | 1000   | PHP      | Joe                 | EXPIRED       | EXPIRED     |
| CASH            | 1000   | PHP      | Joe                 | CANCELLED     | CANCELLED   |

Scenario: Disbursement API validations
Meta: @disb-validations
Given I set <apiSecretKey> API_SECRET_KEY
Given I prepare new CASH disbursement object with: <amount>, <currency>, <beneficiary_id_name>
When I execute POST disbursement request
Then I expect response status code <statusCode>

Examples:
| apiSecretKey | amount | currency | beneficiary_id_name | errorCode                    | statusCode |
| invalid      | 1000   | PHP      | Joe                 | INVALID_API_KEY              | 401        |
| empty        | 1000   | PHP      | Joe                 | INVALID_API_KEY              | 401        |
| valid        | 1000   | PHP      | Joe                 | API_VALIDATION_ERROR         | 400        |
| valid        | 1000   | PHP      | Joe                 | EXPIRATION_DATE_INVALID      | 400        |
| valid        | 1000   | PHP      | Joe                 | IDEMPOTENCY_ERROR            | 400        |
| valid        | 1000   | PHP      | Joe                 | CHANNEL_CODE_NOT_SUPPORTED   | 409        |
| valid        | 1000   | PHP      | Joe                 | MINIMUM_TRANSFER_LIMIT_ERROR | 400        |
| valid        | 1000   | PHP      | Joe                 | MAXIMUM_TRANSFER_LIMIT_ERROR | 400        |
| valid        | 1000   | PHP      | Joe                 | REQUEST_FORBIDDEN_ERROR      | 403        |