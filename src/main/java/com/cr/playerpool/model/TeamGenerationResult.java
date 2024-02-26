package com.cr.playerpool.model;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.StringJoiner;

/**
 * @param teams the generated teams.
 * @param remaining players remained without team.
 */
@Slf4j
public record TeamGenerationResult(List<Team> teams, List<Player> remaining) {

    public void report() {
        log.info("----------------------------------------------");
        log.info("Generated {} teams.", teams.size());

        log.info("----------------------------------------------");
        for (Team team : teams) {
            log.info("                totalSkill: {}", team.totalSkill());
            log.info("     Position Distribution: {}", team.positionDistribution());
            log.info("PositionGroup Distribution: {}", team.positionGroupDistribution());

            StringJoiner stringJoiner = new StringJoiner("\n    ", "[", "]");
            team.getPlayers().forEach(p -> stringJoiner.add(p.toString()));
            log.info("                   Players: {}\n   {}", team.getPlayers().size(), stringJoiner);

            log.info("");
        }

        log.info("----------------------------------------------");
        log.info("Remaining players");
        for (Player player : remaining) {
            log.info(player.toString());
        }
    }
}
