package com.cr.playerpool.model;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

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
            log.info("totalSkill: {} {}", team.totalSkill(), team.getPlayers());
        }

        log.info("----------------------------------------------");
        log.info("Remaining players");
        for (Player player : remaining) {
            log.info(player.toString());
        }
    }
}
