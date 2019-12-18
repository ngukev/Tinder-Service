package com.tinder.data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter @NoArgsConstructor
public class SwipeDataResponse {
    private List<String> responseList = new ArrayList<>();
}
