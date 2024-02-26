package com.cr.playerpool;

import com.cr.playerpool.core.TreeSetCollector;
import com.cr.playerpool.model.Player;
import com.cr.playerpool.model.Position;
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

@Slf4j
public class PositionPopulator implements TeamsPopulator {
    @Override
    public TeamGenerationResult populate(final List<Team> teams, final List<Player> players) {
        Map<Position, TreeSet<Player>> positionMap = groupByPosition(players);

        for (Position position : Position.values()) {
            while (nonNull(positionMap.get(position)) && positionMap.get(position).size() >= teams.size()) {
                // weakest team gets top skill
                teams.sort(comparing(Team::totalSkill));
                for (Team team : teams) {
                    team.addPlayer(positionMap.get(position).pollLast());
                }
            }
        }

        final List<Player> remaining = positionMap.values().stream()
                .flatMap(Collection::stream)
                .sorted(comparing(Player::position))
                .toList();

        TeamGenerationResult teamGenerationResult = new TeamGenerationResult(teams, remaining);
        log.debug("Populated by position: {}", teamGenerationResult);
        return teamGenerationResult;
    }

    @VisibleForTesting
    protected static Map<Position, TreeSet<Player>> groupByPosition(final List<Player> players) {
        return players.stream()
                .collect(
                        groupingBy(
                                Player::position,
                                mapping(identity(), new TreeSetCollector(PLAYER_COMPARATOR))
                        )
                );
    }
}
