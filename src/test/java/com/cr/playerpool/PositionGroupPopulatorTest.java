package com.cr.playerpool;

import com.cr.playerpool.model.Player;
import com.cr.playerpool.model.PositionGroup;
import com.cr.playerpool.model.Team;
import com.cr.playerpool.model.TeamGenerationResult;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import static com.cr.playerpool.PositionGroupPopulator.groupByPositionGroup;
import static com.cr.playerpool.model.Position.CENTER_BACK;
import static com.cr.playerpool.model.Position.CENTRAL_MIDFIELDER;
import static com.cr.playerpool.model.Position.FORWARD;
import static com.cr.playerpool.model.Position.FULL_BACK;
import static com.cr.playerpool.model.Position.GOALKEEPER;
import static com.cr.playerpool.model.PositionGroup.DEFENDERS;
import static com.cr.playerpool.model.PositionGroup.FORWARDS;
import static com.cr.playerpool.model.PositionGroup.GOALKEEPERS;
import static com.cr.playerpool.model.PositionGroup.MIDFIELDERS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

class PositionGroupPopulatorTest {

    private final TeamsPopulator populator = new PositionGroupPopulator();
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

        TeamGenerationResult result = populator.populate(teams, players);
        assertThat(result, notNullValue());
        assertThat(result.remaining().isEmpty(), is(true));

        for (Team team : result.teams()) {
            assertThat(team, notNullValue());
            assertThat(team.size(), is(5));
        }
    }

    @Test
    public void testGroupByPositionGroup() {
        Map<PositionGroup, TreeSet<Player>> positionGroupMap = groupByPositionGroup(players);

        assertThat(positionGroupMap, notNullValue());
        assertThat(positionGroupMap.size(), equalTo(4));

        assertThat(positionGroupMap.get(GOALKEEPERS), notNullValue());
        assertThat(positionGroupMap.get(GOALKEEPERS).size(), equalTo(2));

        assertThat(positionGroupMap.get(DEFENDERS), notNullValue());
        assertThat(positionGroupMap.get(DEFENDERS).size(), equalTo(4));

        assertThat(positionGroupMap.get(MIDFIELDERS), notNullValue());
        assertThat(positionGroupMap.get(MIDFIELDERS).size(), equalTo(2));

        assertThat(positionGroupMap.get(FORWARDS), notNullValue());
        assertThat(positionGroupMap.get(FORWARDS).size(), equalTo(2));
    }
}
