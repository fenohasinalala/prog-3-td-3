package app.foot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@EqualsAndHashCode
@ToString
public class Match {
    private Integer id;
    private TeamMatch teamA;
    private TeamMatch teamB;
    private String stadium;
    private Instant datetime;
}
