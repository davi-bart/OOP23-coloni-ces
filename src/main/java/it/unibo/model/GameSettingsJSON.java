package it.unibo.model;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.unibo.model.mapgenerator.BeginnerGameMapGenerator;
import it.unibo.model.mapgenerator.GameMapGenerator;
import it.unibo.model.mapgenerator.RandomGameMapGenerator;

/**
 * Implementation of Game settings through a JSON file.
 */
public final class GameSettingsJSON implements GameSettings {

    private enum MapType {
        /**
         * @see BeginnerGameMapGenerator
         */
        BEGINNER,
        /**
         * @see RandomGameMapGenerator
         */
        RANDOM
    }

    private static final Map<String, MapType> FIELD_TO_MAP_TYPE = Map.of(
            "random", MapType.RANDOM,
            "beginner", MapType.BEGINNER);
    private static final String DEFAULT_MAP_FIELD = "random";
    private static final MapType DEFAULT_MAP_TYPE = MapType.RANDOM;
    private static final boolean DEFAULT_RANDOM_ORDER = true;
    private static final int DEFAULT_POINTS_TO_WIN = 10;
    private int pointsToWin = DEFAULT_POINTS_TO_WIN;
    private MapType mapType = DEFAULT_MAP_TYPE;
    private boolean randomOrder = DEFAULT_RANDOM_ORDER;

    /**
     * Game settings constructor.
     * 
     * @param filePath path of settings file in CLASSPATH
     */
    @SuppressWarnings({ "PMD.EmptyCatchBlock" }) // default values are already set
    public GameSettingsJSON(final String filePath) {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            final JsonNode settings = objectMapper.readTree(ClassLoader.getSystemResourceAsStream(filePath));
            final String mapFieldName = "map";
            final String pointsFieldName = "points";
            final String shuffleFieldName = "shuffle";
            setMapType(Optional.ofNullable(settings.get(mapFieldName)));
            setPoints(Optional.ofNullable(settings.get(pointsFieldName)));
            setRandomOrder(Optional.ofNullable(settings.get(shuffleFieldName)));
        } catch (final IOException | IllegalArgumentException e) {
        }
    }

    @Override
    public int getPointsToWin() {
        return pointsToWin;
    }

    @Override
    public GameMapGenerator getGameMapGenerator() {
        return switch (mapType) {
            case BEGINNER -> new BeginnerGameMapGenerator();
            default -> new RandomGameMapGenerator();
        };
    }

    @Override
    public boolean isRandomOrder() {
        return randomOrder;
    }

    private void setMapType(final Optional<JsonNode> selectedMap) {
        if (selectedMap.isPresent()) {
            mapType = FIELD_TO_MAP_TYPE.getOrDefault(
                    selectedMap.get().asText(DEFAULT_MAP_FIELD).toLowerCase(Locale.US),
                    DEFAULT_MAP_TYPE);
        }
    }

    private void setPoints(final Optional<JsonNode> selectedPoints) {
        if (selectedPoints.isPresent()) {
            pointsToWin = selectedPoints.get().asInt(DEFAULT_POINTS_TO_WIN);
        }
    }

    private void setRandomOrder(final Optional<JsonNode> selectedRandomOrder) {
        if (selectedRandomOrder.isPresent()) {
            randomOrder = selectedRandomOrder.get().asBoolean(DEFAULT_RANDOM_ORDER);
        }
    }

}
