package com.cr.playerpool.model;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;

import static com.cr.playerpool.model.Player.PLAYER_COMPARATOR;
import static java.util.Comparator.comparing;

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

    @Override
    public String toString() {
        return new StringJoiner(", ", Team.class.getSimpleName() + "[", "]")
                .add("players=" + players.stream().sorted(POSITION_COMPARATOR).toList())
                .toString();
    }
}
