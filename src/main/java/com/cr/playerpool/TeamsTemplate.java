package com.cr.playerpool;

import com.cr.playerpool.model.Player;
import com.cr.playerpool.model.Team;
import com.cr.playerpool.model.TeamGenerationResult;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

@Slf4j
public class TeamsTemplate implements TeamsOperations {

    private final List<TeamsPopulator> populators;

    public TeamsTemplate(List<TeamsPopulator> populators) {
        this.populators = requireNonNull(populators);
    }

    @Override
    public TeamGenerationResult generateTeams(List<Player> players, int teamsNo) {
        log.info("Preparing initial teams list");
        List<Team> teams = IntStream.range(0, teamsNo)
                .mapToObj(i -> new Team())
                .collect(toList());

        List<Player> remaining = new ArrayList<>(players);
        for (TeamsPopulator teamsPopulator : populators) {
            TeamGenerationResult result = teamsPopulator.populate(teams, remaining);
            remaining = result.remaining();
            teams = result.teams();
        }

        return new TeamGenerationResult(teams, remaining);
    }

}
