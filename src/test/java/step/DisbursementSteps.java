package step;

import io.restassured.response.ValidatableResponse;
import model.Disbursement;
import net.thucydides.core.annotations.Step;
import org.assertj.core.api.SoftAssertions;


import static endpoint.DisbursementStatus.PENDING;
import static org.apache.http.HttpStatus.SC_OK;

public class DisbursementSteps extends BaseStep {

    @Step
    public void createDisbursement(Disbursement disbursement) {
        ValidatableResponse response = given()
                .body(disbursement)
                .post(endpoint.Disbursement.CREATE_DISBURSEMENT)
                .then()
                .statusCode(SC_OK);
        Disbursement createdDisbursement = response.extract().as(Disbursement.class);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(createdDisbursement.getStatus()).as("status").isEqualTo(PENDING);
            softly.assertThat(createdDisbursement.getUser_id()).as("user_id").isNotEmpty();
            softly.assertThat(createdDisbursement.getId()).as("id").isNotEmpty();
        });
    }
}
