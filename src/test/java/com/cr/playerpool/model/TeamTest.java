package com.cr.playerpool.model;

import com.cr.playerpool.TestUtils;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static com.cr.playerpool.TestUtils.randomPlayer;
import static java.util.concurrent.ThreadLocalRandom.current;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

class TeamTest {

    @Test
    public void testCreate() {
        Team team = new Team();
        assertThat(team, notNullValue());
        assertThat(team.size(), is(0));
        assertThat(team.totalSkill(), is(0));
    }

    @RepeatedTest(10)
    public void testTeamPlayers() {
        final int teamSize = current().nextInt(1, 50);

        final List<Player> players = new ArrayList<>();
        Team team = new Team();

        IntStream.range(0, teamSize).forEach(i -> {
            Player player = randomPlayer();
            players.add(player);
            team.addPlayer(player);
        });

        assertThat(team.size(), is(players.size()));
        int expectedSkill = players.stream()
                .mapToInt(Player::skill)
                .reduce(0, Integer::sum);
        assertThat(team.totalSkill(), is(expectedSkill));
    }

}
