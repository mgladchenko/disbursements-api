package step;

import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.steps.ScenarioSteps;

import static org.apache.http.HttpHeaders.AUTHORIZATION;

public class BaseStep extends ScenarioSteps {

    private final static String BASE_URI = "https://api.xendit.co";
    private final static String API_SECRET_KEY = "xnd_development_pjy0KRKL89NQVRylOxZ4jEZ0Q66oHx2vBxRKDHwFV36F7antz4Qaz3aul4YkvDf";


    static {
        SerenityRest.filters(new RequestLoggingFilter(LogDetail.ALL));
        SerenityRest.filters(new ResponseLoggingFilter(LogDetail.ALL));
    }

    public static RequestSpecification given() {
        return SerenityRest
                .given()
                .auth()
                .preemptive()
                .basic(API_SECRET_KEY, "")
                .baseUri(BASE_URI)
                //.header(AUTHORIZATION, token)
                .contentType(ContentType.JSON);
    }

}
