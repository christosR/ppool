package com.cr.playerpool.model;

import lombok.Getter;

import static com.cr.playerpool.model.PositionGroup.DEFENDERS;
import static com.cr.playerpool.model.PositionGroup.FORWARDS;
import static com.cr.playerpool.model.PositionGroup.GOALKEEPERS;
import static com.cr.playerpool.model.PositionGroup.MIDFIELDERS;
import static java.util.Objects.requireNonNull;

@Getter
public enum Position {

    GOALKEEPER("Goalkeeper", GOALKEEPERS),
    CENTER_BACK("Center Back", DEFENDERS),
    FULL_BACK("Full Back", DEFENDERS),
    DEFENSIVE_MIDFIELDER("Defensive Midfielder", MIDFIELDERS),
    CENTRAL_MIDFIELDER("Central Midfielder", MIDFIELDERS),
    ATTACKING_MIDFIELDER("Attacking Midfielder", MIDFIELDERS),
    WINGER("Winger", FORWARDS),
    FORWARD("Forward", FORWARDS);

    private final String text;
    private final PositionGroup positionGroup;

    Position(String s, PositionGroup positionGroup) {
        this.text = s;
        this.positionGroup = requireNonNull(positionGroup);
    }

    public static Position from(String value) {
        requireNonNull(value);

        for (Position position : Position.values()) {
            if (position.getText().equalsIgnoreCase(value)) {
                return position;
            }
        }

        throw new IllegalArgumentException(String.format("Invalid Position value: %s", value));
    }

}
