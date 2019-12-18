package com.tinder.data.response;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class RefreshDataResponse {
    private JsonArray recommendationList;
    private JsonArray teaserList;
    private JsonObject profile;
}
