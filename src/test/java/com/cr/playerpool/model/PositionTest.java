package com.cr.playerpool.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.cr.playerpool.model.PositionGroup.DEFENDERS;
import static com.cr.playerpool.model.PositionGroup.FORWARDS;
import static com.cr.playerpool.model.PositionGroup.GOALKEEPERS;
import static com.cr.playerpool.model.PositionGroup.MIDFIELDERS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;


class PositionTest {

    @Test
    public void testFromNull() {
        assertThrows(
                NullPointerException.class,
                () -> Position.from(null)
        );
    }

    @Test
    public void testFromEmptyString() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Position.from("")
        );
    }

    private static Stream<Arguments> provideForPositionEnum() {
        return Stream.of(
                Arguments.of("Goalkeeper", Position.GOALKEEPER, GOALKEEPERS),
                Arguments.of("Center Back", Position.CENTER_BACK, DEFENDERS),
                Arguments.of("Full Back", Position.FULL_BACK, DEFENDERS),
                Arguments.of("Defensive Midfielder", Position.DEFENSIVE_MIDFIELDER, MIDFIELDERS),
                Arguments.of("Central Midfielder", Position.CENTRAL_MIDFIELDER, MIDFIELDERS),
                Arguments.of("Attacking Midfielder", Position.ATTACKING_MIDFIELDER, MIDFIELDERS),
                Arguments.of("Winger", Position.WINGER, FORWARDS),
                Arguments.of("Forward", Position.FORWARD, FORWARDS)
        );
    }

    @ParameterizedTest
    @MethodSource("provideForPositionEnum")
    public void testFrom(final String value, final Position expected, final PositionGroup expectedGroup) {
        Position position = Position.from(value);
        assertThat(position, equalTo(expected));
        assertThat(position.getPositionGroup(), equalTo(expectedGroup));
    }

}
