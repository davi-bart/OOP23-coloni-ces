
package it.unibo.model.resource;

import java.util.Map;

import it.unibo.common.tile.ResourceType;
/**
 * Resource owner.
 */
public interface ResourceOwner {
    /**
     * Get the default amount of resources of the owner.
     * @return a map containing, for each resource, the dafault amount.
     */
    Map<ResourceType, Integer> getDefaultResources();
}
