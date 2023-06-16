package com.votecounter.dto.response;

import com.votecounter.domain.Candidate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AllCandidatesResponse {
    List<Candidate> allCandidates;
}
