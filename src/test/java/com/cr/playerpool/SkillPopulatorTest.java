package com.cr.playerpool;

import com.cr.playerpool.model.Player;
import com.cr.playerpool.model.Team;
import com.cr.playerpool.model.TeamGenerationResult;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.cr.playerpool.model.Position.CENTER_BACK;
import static com.cr.playerpool.model.Position.FULL_BACK;
import static com.cr.playerpool.model.Position.GOALKEEPER;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

class SkillPopulatorTest {

    private final TeamsPopulator populator = new SkillPopulator();

    private final List<Player> players = List.of(
            new Player("P1", 95, GOALKEEPER),
            new Player("P2", 96, GOALKEEPER),
            new Player("P3", 97, CENTER_BACK),
            new Player("P4", 98, CENTER_BACK),
            new Player("P5", 99, FULL_BACK),
            new Player("P6", 100, FULL_BACK)
    );

    @Test
    public void testPopulate() {
        List<Team> teams = Lists.newArrayList(new Team(), new Team(), new Team());
        TeamGenerationResult result = populator.populate(teams, players);

        assertThat(result, notNullValue());
        assertThat(result.remaining().isEmpty(), is(true));

        assertThat(result.teams(), notNullValue());
        assertThat(result.teams().size(), equalTo(teams.size()));

        for (Team team : result.teams()) {
            assertThat(team, notNullValue());
            assertThat(team.size(), equalTo(2));
        }

        System.out.println(result);

    }
}
