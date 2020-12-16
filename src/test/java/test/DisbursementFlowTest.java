package test;

import model.Disbursement;
import net.serenitybdd.junit.runners.SerenityRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SerenityRunner.class)
public class DisbursementFlowTest extends BaseRestTest {

    @Test
    public void createDisbursement() {
        Disbursement disbursement = Disbursement.builder().build();
        disbursementSteps.createDisbursement(disbursement);
    }

}
