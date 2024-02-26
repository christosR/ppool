package com.cr.playerpool.model;

import org.junit.jupiter.api.Test;

import static com.cr.playerpool.model.Position.GOALKEEPER;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("DataFlowIssue")
class PlayerTest {

    @Test
    public void throwsForNullId() {
        assertThrows(NullPointerException.class, () -> new Player(null, 1, GOALKEEPER));
    }

    @Test
    public void throwsForNullSkill() {
        assertThrows(NullPointerException.class, () -> new Player("P1", null, GOALKEEPER));
    }

    @Test
    public void throwsForNullPosition() {
        assertThrows(NullPointerException.class, () -> new Player("P1", 1, null));
    }

    @Test
    public void throwsForNegativeSkill() {
        assertThrows(IllegalArgumentException.class, () -> new Player("P1", -1, GOALKEEPER));
    }

}
