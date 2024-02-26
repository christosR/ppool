package com.cr.playerpool;

import com.cr.playerpool.model.Player;
import com.cr.playerpool.model.Position;
import com.cr.playerpool.model.Team;
import com.cr.playerpool.model.TeamGenerationResult;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import static com.cr.playerpool.PositionPopulator.groupByPosition;
import static com.cr.playerpool.model.Position.CENTER_BACK;
import static com.cr.playerpool.model.Position.CENTRAL_MIDFIELDER;
import static com.cr.playerpool.model.Position.DEFENSIVE_MIDFIELDER;
import static com.cr.playerpool.model.Position.FORWARD;
import static com.cr.playerpool.model.Position.FULL_BACK;
import static com.cr.playerpool.model.Position.GOALKEEPER;
import static com.cr.playerpool.model.Position.WINGER;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

class PositionPopulatorTest {

    private final TeamsPopulator positionPopulator = new PositionPopulator();

    private final List<Player> players = List.of(
            new Player("P1", 100, GOALKEEPER),
            new Player("P2", 100, GOALKEEPER),
            new Player("P3", 100, CENTER_BACK),
            new Player("P4", 100, CENTER_BACK),
            new Player("P5", 100, FULL_BACK),
            new Player("P6", 100, FULL_BACK),
            new Player("P7", 100, CENTRAL_MIDFIELDER),
            new Player("P8", 100, CENTRAL_MIDFIELDER),
            new Player("P9", 100, FORWARD),
            new Player("P10", 100, FORWARD)
    );

    @Test
    public void testPopulate() {
        List<Team> teams = Lists.newArrayList(new Team(), new Team());

        TeamGenerationResult result = positionPopulator.populate(teams, players);
        assertThat(result, notNullValue());
        assertThat(result.remaining().isEmpty(), is(true));

        for (Team team : result.teams()) {
            assertThat(team, notNullValue());
            assertThat(team.size(), is(5));
        }
    }

    @Test
    public void testGroupByPosition() {
        Map<Position, TreeSet<Player>> positionTreeSetMap = groupByPosition(players);
        assertThat(positionTreeSetMap, notNullValue());

        assertThat(positionTreeSetMap.get(GOALKEEPER), notNullValue());
        assertThat(positionTreeSetMap.get(GOALKEEPER).size(), equalTo(2));

        assertThat(positionTreeSetMap.get(CENTER_BACK), notNullValue());
        assertThat(positionTreeSetMap.get(CENTER_BACK).size(), equalTo(2));

        assertThat(positionTreeSetMap.get(FULL_BACK), notNullValue());
        assertThat(positionTreeSetMap.get(FULL_BACK).size(), equalTo(2));

        assertThat(positionTreeSetMap.get(CENTRAL_MIDFIELDER), notNullValue());
        assertThat(positionTreeSetMap.get(CENTRAL_MIDFIELDER).size(), equalTo(2));

        assertThat(positionTreeSetMap.get(FORWARD), notNullValue());
        assertThat(positionTreeSetMap.get(FORWARD).size(), equalTo(2));

        assertThat(positionTreeSetMap.get(WINGER), nullValue());
        assertThat(positionTreeSetMap.get(DEFENSIVE_MIDFIELDER), nullValue());
    }
}
