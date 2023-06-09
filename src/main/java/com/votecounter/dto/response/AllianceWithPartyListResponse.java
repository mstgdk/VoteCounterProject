package com.votecounter.dto.response;

import com.votecounter.domain.Party;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AllianceWithPartyListResponse {
    private Long id;
    private String AllianceName;

    private List<Party> partyList;
}
