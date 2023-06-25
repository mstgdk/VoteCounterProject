package com.votecounter.dto.response;

import com.votecounter.domain.Alliance;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PartyResponse {
    private String partyName;
    private List<String> image;
    private Alliance alliance;
    private String allianceMessage;

}
