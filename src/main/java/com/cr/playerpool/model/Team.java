package com.cr.playerpool.model;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import static com.cr.playerpool.model.Player.PLAYER_COMPARATOR;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

@Slf4j
@Getter
public class Team {

    private static final Comparator<Player> POSITION_COMPARATOR = comparing(Player::position)
            .thenComparing(Player::skill)
            .thenComparing(Player::id);

    private final Set<Player> players = new HashSet<>();

    public void addPlayer(final Player player) {
        players.add(player);
    }

    public int totalSkill() {
        return players.stream()
                .mapToInt(Player::skill)
                .sum();
    }

    public int size() {
        return players.size();
    }

    public Map<Position, Long> positionDistribution() {
        return players.stream().collect(groupingBy(Player::position, counting()));
    }

    public Map<PositionGroup, Long> positionGroupDistribution() {
        return players.stream().collect(groupingBy(p -> p.position().getPositionGroup(), counting()));
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Team.class.getSimpleName() + "[", "]")
                .add("players=" + players.stream().sorted(POSITION_COMPARATOR).toList())
                .toString();
    }
}
