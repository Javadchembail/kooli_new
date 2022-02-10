package com.kooli.app.kooli.payloads.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressRequest {
    private String line_1;
    private String line_2;
    private String pincode;
    private String city;
    private String state;
    private String type;
    private String buildingName;
}
