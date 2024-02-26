package com.cr.playerpool;

import com.cr.playerpool.model.Player;
import com.cr.playerpool.model.TeamGenerationResult;

import java.util.List;

public interface TeamsOperations {
    TeamGenerationResult generateTeams(List<Player> players, int teamsNo);
}
