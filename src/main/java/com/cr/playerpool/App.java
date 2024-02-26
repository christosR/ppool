package com.cr.playerpool;

import com.cr.playerpool.model.Player;
import com.cr.playerpool.model.Position;
import com.cr.playerpool.model.TeamGenerationResult;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Integer.parseInt;
import static java.util.Objects.requireNonNull;

@Slf4j
public class App {

    public static void main(String[] args) throws IOException {
        final String path = args[0];
        log.info("Loading players csv file: {}", path);

        List<Player> players;
        try (Stream<String> stream = Files.lines(Paths.get(path))) {
            players = stream
                    .filter(l -> !l.isBlank())
                    .map(App::fromCsvRow)
                    .collect(Collectors.toList());
        }

        log.info("Loaded {} players from file", players.size());

        final int teamNo = Integer.parseInt(args[1]);
        log.info("Requested number of teams: {}", teamNo);

        List<TeamsPopulator> populators = List.of(
                new PositionPopulator(),
                new PositionGroupPopulator(),
                new SkillPopulator()
        );

        TeamsOperations teamsOperations = new TeamsTemplate(populators);
        TeamGenerationResult teamGenerationResult = teamsOperations.generateTeams(players, teamNo);
        teamGenerationResult.report();
    }

    public static Player fromCsvRow(final String row) {
        requireNonNull(row);
        final String[] tokens = row.split(",");
        return new Player(
                tokens[0],
                parseInt(tokens[1]),
                Position.from(tokens[2])
        );
    }

}
