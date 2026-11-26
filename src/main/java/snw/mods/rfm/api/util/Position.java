package snw.mods.rfm.api.util;

/**
 * A point in a level, with the rotation the entity should face.
 * <p>
 * This is a plain value object, so the API does not have to expose Minecraft's own position types.
 *
 * @param level The level the position is in, identified by its dimension key
 * @param pos   The coordinates of the point
 * @param rot   The rotation to apply, in degrees
 */
public record Position(NamespacedPath level, Vector3d pos, Vector2f rot) {
}
