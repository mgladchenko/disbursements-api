package step;

import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.steps.ScenarioSteps;

import static config.ConfigProperties.*;

public class BaseStep extends ScenarioSteps {

    static {
        SerenityRest.filters(new RequestLoggingFilter(LogDetail.ALL));
        SerenityRest.filters(new ResponseLoggingFilter(LogDetail.ALL));
    }

    protected static RequestSpecification given(String apiSecretKey) {
        return SerenityRest
                .given()
                .auth()
                .preemptive()
                .basic(apiSecretKey, "")
                .baseUri(BASE_URI)
                .contentType(ContentType.JSON);
    }

}
