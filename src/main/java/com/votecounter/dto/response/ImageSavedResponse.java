package com.votecounter.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ImageSavedResponse extends VtResponse{
    private String imageId;

    public ImageSavedResponse(String imageId,String message, boolean success) {
        super(message, success);
        this.imageId = imageId;
    }


}
