package com.cr.playerpool.model;

import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.util.Comparator;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Comparator.comparing;

public record Player(
        @NonNull String id,
        @NonNull Integer skill,
        @NonNull Position position) {

    public Player {
        checkArgument(skill >= 0);
    }

    public static final Comparator<Player> PLAYER_COMPARATOR = comparing(Player::skill)
            .thenComparing(Player::position)
            .thenComparing(Player::id);

}
