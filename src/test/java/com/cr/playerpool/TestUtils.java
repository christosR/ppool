package com.cr.playerpool;

import com.cr.playerpool.model.Player;
import com.cr.playerpool.model.Position;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static java.lang.Integer.parseInt;
import static java.util.Objects.requireNonNull;
import static java.util.concurrent.ThreadLocalRandom.current;

public class TestUtils {

    public static final String COMMA = ",";

    public static Stream<String> streamLines(final String resource) {
        requireNonNull(resource);
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        try {
            URL url = classloader.getResource(resource);
            Path path = Paths.get(requireNonNull(url).toURI());
            return Files.lines(path);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static Player fromCsvRow(final String row) {
        requireNonNull(row);
        final String[] tokens = row.split(COMMA);
        return new Player(
                tokens[0],
                parseInt(tokens[1]),
                Position.from(tokens[2])
        );
    }

    public static List<Player> loadPlayerData(final String resource) {
        return streamLines(resource)
                .filter(l -> !l.isBlank())
                .map(TestUtils::fromCsvRow)
                .toList();
    }

    public static Player randomPlayer() {
        final String id = "P" + current().nextLong(1, 999_999);
        return new Player(id, current().nextInt(0, 100), randomPosition());
    }

    public static Position randomPosition() {
        return Arrays.asList(Position.values()).get(current().nextInt(0, Position.values().length));
    }
}
