package com.votecounter.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CandidateRequest {
    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private Long partyId;
}
