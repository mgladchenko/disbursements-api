package step;

import model.Disbursement;
import net.thucydides.core.annotations.Step;


import static org.apache.http.HttpStatus.SC_OK;

public class DisbursementSteps extends BaseStep {

    @Step
    public void createDisbursement(Disbursement disbursement) {
        given()
                .body(disbursement)
                .post(endpoint.Disbursement.CREATE_DISBURSEMENT)
                .then()
                .statusCode(SC_OK);
    }
}
