package step;

import io.restassured.response.ValidatableResponse;
import model.Disbursement;
import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Step;
import org.assertj.core.api.SoftAssertions;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import static config.ConfigProperties.API_SECRET_KEY;
import static endpoint.Endpoint.*;
import static model.DisbursementStatus.PENDING;
import static org.hamcrest.Matchers.is;

public class DisbursementSteps extends BaseStep {

    @Step
    @Given("I set $apiSecretKeyType API_SECRET_KEY")
    public DisbursementSteps setApiSecretKey(String apiSecretKeyType) {
        String apiSecretKey;
        switch (apiSecretKeyType) {
            case "valid":
                apiSecretKey = API_SECRET_KEY;
                break;
            case "invalid":
                apiSecretKey = API_SECRET_KEY + "invalid";
                break;
            case "empty":
                apiSecretKey = "";
                break;
            default:
                throw new Error("Value should be one of: valid, invalid, empty.");
        }
        Serenity.setSessionVariable("apiSecretKey").to(apiSecretKey);
        return this;
    }

    @Step
    @Given("I prepare new disbursement object with: $bankCode, $accountHolderName, $accountNumber, $description, $amount")
    public DisbursementSteps prepareDisbursementObject(String bankCode,
                                                       String accountHolderName,
                                                       String accountNumber,
                                                       String description,
                                                       int amount) {
        Disbursement disbursement = Disbursement.builder()
                .bank_code(bankCode)
                .account_holder_name(accountHolderName)
                .account_number(accountNumber)
                .description(description)
                .amount(amount)
                .build();
        Serenity.setSessionVariable("disbursement").to(disbursement);
        return this;
    }

    @Step
    @When("I execute POST disbursement request")
    public DisbursementSteps postDisbursement() {
        String apiSecretKey = Serenity.sessionVariableCalled("apiSecretKey");
        Disbursement disbursement = Serenity.sessionVariableCalled("disbursement");

        ValidatableResponse validatableResponse = given(apiSecretKey)
                .body(disbursement)
                .post(POST_DISBURSEMENT)
                .then();
        Serenity.setSessionVariable("validatableResponse").to(validatableResponse);
        return this;
    }

    @Step
    @When("I execute GET disbursement request")
    public DisbursementSteps getDisbursement() {
        String apiSecretKey = Serenity.sessionVariableCalled("apiSecretKey");
        Disbursement disbursement = Serenity.sessionVariableCalled("disbursement");

        ValidatableResponse validatableResponse = given(apiSecretKey)
                .get(GET_DISBURSEMENT, disbursement.getId())
                .then();
        Serenity.setSessionVariable("validatableResponse").to(validatableResponse);
        return this;
    }

    @Step
    @Then("I expect response status code $expectedStatusCode")
    public DisbursementSteps validateResponseStatusCode(String expectedStatusCode) {
        ValidatableResponse validatableResponse = Serenity.sessionVariableCalled("validatableResponse");
        validatableResponse.statusCode(Integer.parseInt(expectedStatusCode));
        return this;
    }

    @Step
    @Then("I validate response body and disbursement status $status")
    public DisbursementSteps validateResponseBody(String status) {
        ValidatableResponse validatableResponse = Serenity.sessionVariableCalled("validatableResponse");
        Disbursement createdDisbursement = validatableResponse.extract().as(Disbursement.class);
        Serenity.setSessionVariable("disbursement").to(createdDisbursement);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(createdDisbursement.getStatus().toString()).as("status").isEqualTo(status);
            softly.assertThat(createdDisbursement.getUser_id()).as("user_id").isNotEmpty();
            softly.assertThat(createdDisbursement.getId()).as("id").isNotEmpty();
        });
        return this;
    }

    @Step
    @Then("I validate response body error message $message")
    public DisbursementSteps validateResponseBodyErrorMessage(String message) {
        ValidatableResponse validatableResponse = Serenity.sessionVariableCalled("validatableResponse");
        validatableResponse.body("message", is(message));
        return this;
    }
}
