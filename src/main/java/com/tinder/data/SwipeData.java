package com.tinder.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class SwipeData implements Serializable {
    private boolean liked;
    private String userID;
    @JsonProperty("sNumber")
    private String sNumber;
}
