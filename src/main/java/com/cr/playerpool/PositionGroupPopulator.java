package com.cr.playerpool;

import com.cr.playerpool.core.TreeSetCollector;
import com.cr.playerpool.model.Player;
import com.cr.playerpool.model.PositionGroup;
import com.cr.playerpool.model.Team;
import com.cr.playerpool.model.TeamGenerationResult;
import com.google.common.annotations.VisibleForTesting;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import static com.cr.playerpool.model.Player.PLAYER_COMPARATOR;
import static java.util.Comparator.comparing;
import static java.util.Objects.nonNull;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

@Slf4j
public class PositionGroupPopulator implements TeamsPopulator{
    @Override
    public TeamGenerationResult populate(List<Team> teams, List<Player> players) {
        Map<PositionGroup, TreeSet<Player>> positionGroupMap = groupByPositionGroup(players);

        for (PositionGroup positionGroup : PositionGroup.values()) {
            while (nonNull(positionGroupMap.get(positionGroup)) && positionGroupMap.get(positionGroup).size() >= teams.size()) {
                // weakest team gets top skill
                teams.sort(comparing(Team::totalSkill));
                for (Team team : teams) {
                    Player p = positionGroupMap.get(positionGroup).pollLast();
                    team.addPlayer(p);
                }
            }
        }

        List<Player> remaining = positionGroupMap.values().stream()
                .flatMap(Collection::stream)
                .sorted(comparing(Player::position))
                .collect(toList());

        TeamGenerationResult result = new TeamGenerationResult(teams, remaining);
        log.debug("Populated by position-group: {}", result);
        return result;
    }

    @VisibleForTesting
    protected static Map<PositionGroup, TreeSet<Player>> groupByPositionGroup(final List<Player> players) {
        return players.stream()
                .collect(
                        groupingBy(
                                p -> p.position().getPositionGroup(),
                                mapping(identity(), new TreeSetCollector(PLAYER_COMPARATOR))
                        )
                );
    }
}
