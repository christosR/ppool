package com.cr.playerpool;

import com.cr.playerpool.model.Player;
import com.cr.playerpool.model.Team;
import com.cr.playerpool.model.TeamGenerationResult;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import static com.cr.playerpool.model.Player.PLAYER_COMPARATOR;
import static java.util.Comparator.comparing;

@Slf4j
public class SkillPopulator implements TeamsPopulator {
    @Override
    public TeamGenerationResult populate(List<Team> teams, List<Player> players) {
        TreeSet<Player> remainingTreeSet = new TreeSet<>(PLAYER_COMPARATOR);
        remainingTreeSet.addAll(players);

        while (remainingTreeSet.size() >= teams.size()) {
            // weakest team gets top skill
            teams.sort(comparing(Team::totalSkill));
            for (Team team : teams) {
                team.addPlayer(remainingTreeSet.pollLast());
            }
        }

        return new TeamGenerationResult(teams, new ArrayList<>(remainingTreeSet));
    }
}
