package model;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class Disbursement {

    @Builder.Default
    private String external_id = "disb-" + System.currentTimeMillis();
    @Builder.Default
    private int amount = 15000;
    @Builder.Default
    private String bank_code = "BCA";
    @Builder.Default
    private String account_holder_name = "Joe";
    @Builder.Default
    private String account_number = "1234567890";
    @Builder.Default
    private String description = "Disbursement TA";
}
