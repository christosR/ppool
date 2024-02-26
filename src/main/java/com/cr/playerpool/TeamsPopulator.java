package com.cr.playerpool;

import com.cr.playerpool.model.Player;
import com.cr.playerpool.model.Team;
import com.cr.playerpool.model.TeamGenerationResult;

import java.util.List;

public interface TeamsPopulator {
    /**
     * @param teams List of teams to be populated
     * @param players available players
     * @return TeamGenerationResult
     */
    TeamGenerationResult populate(List<Team> teams, List<Player> players);
}
