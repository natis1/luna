package io.luna.game.model.chunk;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import io.luna.game.model.Entity;
import io.luna.game.model.EntityType;
import io.luna.game.model.Position;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

/**
 * A model containing entities and updates for those entities within a chunk.
 *
 * @author lare96 <http://github.com/lare96>
 */
public final class Chunk {

    /**
     * This chunk's position.
     */
    private final ChunkPosition position;

    /**
     * A map of entities.
     */
    // TODO Map<Position, Entity> ??? if you're gonna use a set you might as well
    private final ImmutableMap<EntityType, Set<Entity>> entities;

    {
        Map<EntityType, Set<Entity>> map = new EnumMap<>(EntityType.class);
        for (EntityType type : EntityType.ALL) {
            // Create sets for each entity type.
            map.put(type, new HashSet<>(4));
        }
        entities = Maps.immutableEnumMap(map);
    }

    /**
     * Creates a new {@link ChunkPosition}.
     *
     * @param position The chunk position.
     */
    Chunk(ChunkPosition position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("position", position).
                add("entities", entities).toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Chunk) {
            Chunk other = (Chunk) obj;
            return position.equals(other.position);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return position.hashCode();
    }

    /**
     * Adds an entity to this chunk.
     *
     * @param entity The entity to add.
     * @return {@code true} if successful, {@code false} otherwise.
     */
    public boolean add(Entity entity) {
        return entities.get(entity.getType()).add(entity);
    }

    /**
     * Removes an entity from this chunk.
     *
     * @param entity The entity to remove.
     * @return {@code true} if successful, {@code false} otherwise.
     */
    public boolean remove(Entity entity) {
        return entities.get(entity.getType()).remove(entity);
    }

    /**
     * Determines if this chunk contains an entity.
     *
     * @param entity The entity to determine for.
     * @return {@code true} if this chunk contains the entity, {@code false} otherwise.
     */
    public boolean contains(Entity entity) {
        return entities.get(entity.getType()).contains(entity);
    }

    /**
     * Returns a {@link Set} containing all entities of the specified type in this chunk. The cast type
     * must match the argued type or a {@link ClassCastException} will be thrown.
     *
     * @param type The type of entities to get.
     * @param <E> The type to cast to. Must be a subclass of Entity.
     * @return A set of entities casted to {@code <E>}. As long as {@code <E>} matches {@code type}, no errors will
     * be thrown.
     */
    @SuppressWarnings("unchecked")
    public <E extends Entity> Set<E> getAll(EntityType type) {
        //noinspection unchecked
        return (Set<E>) entities.get(type);
    }

    /**
     * Returns a stream over {@code type} entities in this chunk.
     *
     * @param type The entity type.
     * @param <E> The type.
     * @return The stream.
     */
    @SuppressWarnings("unchecked")
    public <E extends Entity> Stream<E> stream(EntityType type) {
        //noinspection unchecked
        return (Stream<E>) entities.get(type).stream();
    }

    /**
     * @return The position.
     */
    public ChunkPosition getPosition() {
        return position;
    }

    /**
     * @return This chunk's absolute position.
     */
    public Position getAbsolutePosition() {
        return new Position(position.getAbsX(), position.getAbsY());
    }
}