package com.cr.playerpool.core;

import com.cr.playerpool.model.Player;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import static java.util.Collections.emptySet;
import static java.util.Objects.requireNonNull;

public class TreeSetCollector implements Collector<Player, TreeSet<Player>, TreeSet<Player>> {

    private final Comparator<Player> comparator;

    public TreeSetCollector(final Comparator<Player> comparator) {
        this.comparator = requireNonNull(comparator);
    }

    @Override
    public Supplier<TreeSet<Player>> supplier() {
        return () -> new TreeSet<>(comparator);
    }

    @Override
    public BiConsumer<TreeSet<Player>, Player> accumulator() {
        return TreeSet::add;
    }

    @Override
    public BinaryOperator<TreeSet<Player>> combiner() {
        return (left, right) -> {
            left.addAll(right); return left;
        };
    }

    @Override
    public Function<TreeSet<Player>, TreeSet<Player>> finisher() {
        return i -> (TreeSet<Player>) i;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return emptySet();
    }
}
