package com.votecounter.dto.response;

import com.votecounter.domain.Party;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CandidateResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private Party party;
}
