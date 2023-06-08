package com.votecounter.dto.response;

import com.votecounter.domain.Party;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Lob;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AllianceResponse {

    private Long id;
    private String AllianceName;

    private List<Party> partyList;
}
