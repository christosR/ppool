package com.cr.playerpool;

import com.cr.playerpool.model.Player;
import com.cr.playerpool.model.Team;
import com.cr.playerpool.model.TeamGenerationResult;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

class TeamsTemplateIntTest {

    private final List<TeamsPopulator> populators = List.of(
            new PositionPopulator(),
            new PositionGroupPopulator(),
            new SkillPopulator()
    );

    private final TeamsOperations teamsOperations = new TeamsTemplate(populators);

    @Test
    public void testGenerateTeams() {
        final int teamsSize = 3;
        List<Player> players = TestUtils.loadPlayerData("test-pool-01.csv");

        TeamGenerationResult teamGenerationResult = teamsOperations.generateTeams(players, teamsSize);
        teamGenerationResult.report();

        assertThat(teamGenerationResult, notNullValue());

        assertThat(teamGenerationResult.teams(), notNullValue());
        assertThat(teamGenerationResult.teams().size(), is(teamsSize));

        for (Team team : teamGenerationResult.teams()) {
            assertThat(team.getPlayers().size(), is(6)); //based on dataset
        }

        assertThat(teamGenerationResult.remaining(), notNullValue());
        assertThat(teamGenerationResult.remaining().size(), is(1));
        assertThat(teamGenerationResult.remaining().get(0).id(), is("P61"));
    }


}
