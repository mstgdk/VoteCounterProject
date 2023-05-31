package com.votecounter.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImageFileResponse {
    private String  name;

    private String url;

    private String  type;

    private long size;
}
