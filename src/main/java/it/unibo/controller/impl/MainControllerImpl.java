package it.unibo.controller.impl;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.apache.commons.lang3.tuple.Pair;

import it.unibo.common.api.card.CardType;
import it.unibo.common.api.property.PropertyPosition;
import it.unibo.common.api.road.RoadPosition;
import it.unibo.common.api.tile.ResourceType;
import it.unibo.common.api.tile.TilePosition;
import it.unibo.controller.api.BoardController;
import it.unibo.controller.api.MainController;
import it.unibo.controller.api.ResourceController;
import it.unibo.controller.api.TurnController;
import it.unibo.model.api.GameManager;
import it.unibo.model.api.Player;
import it.unibo.model.api.ResourceOwner;
import it.unibo.model.impl.GameManagerImpl;
import it.unibo.view.AppView;

/**
 * Main controller implementation.
 */
public final class MainControllerImpl implements MainController {
    private final GameManager gameManager;
    private final BoardController boardController;
    private final ResourceController resourceController;
    private final TurnController turnController;
    private final AppView appView;
    private boolean mustPlaceRobber;

    /**
     * Constructor of the controller.
     * 
     * @param appView the main view
     * @param players list of players' names
     */
    public MainControllerImpl(final AppView appView, final List<String> players) {
        this.appView = appView;
        this.gameManager = new GameManagerImpl(players);

        final Function<String, Player> getPlayerByName = name -> gameManager.getPlayers().stream()
                .filter(p -> p.getName().equals(name)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Player with name " + name + " does not exist."));

        final Function<String, ResourceOwner> getResourceOwnerByName = name -> {
            if (name.equals(getBank())) {
                return gameManager.getResourceManager().getBank();
            }
            return gameManager.getPlayers().stream()
                    .filter(p -> p.getName().equals(name)).findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Player with name " + name + " does not exist."));
        };
        this.boardController = new BoardControllerImpl(getPlayerByName, this.gameManager.getBoard(),
                this.gameManager.getPropertyManager(), this.gameManager.getRoadManager());
        this.resourceController = new ResourceControllerImpl(getResourceOwnerByName, gameManager.getResourceManager());
        this.turnController = new TurnControllerImpl(gameManager.getTurnManager());
    }

    @Override
    public BoardController getBoardController() {
        return this.boardController;
    }

    @Override
    public ResourceController getResourceController() {
        return this.resourceController;
    }

    @Override
    public TurnController getTurnController() {
        return this.turnController;
    }

    @Override
    public List<String> getPlayerNames() {
        return gameManager.getPlayers().stream().map(p -> p.getName()).toList();
    }

    @Override
    public Map<ResourceType, Integer> getPlayerResources(final String playerName) {
        return resourceController.getOwnerResources(playerName);
    }

    @Override
    public int getVictoryPoints(final String playerName) {
        return getPlayerByName(playerName).getVictoryPoints();
    }

    @Override
    public String getCurrentPlayer() {
        return this.turnController.getCurrentPlayerTurn().getName();
    }

    @Override
    public void buildSettlement(final PropertyPosition position) {
        this.gameManager.buildSettlement(position, turnController.getCurrentPlayerTurn());
        redrawResourcesView();
        this.appView.redrawPlayers();
    }

    @Override
    public void buildCity(final PropertyPosition position) {
        gameManager.buildCity(position, turnController.getCurrentPlayerTurn());
        redrawResourcesView();
        this.appView.redrawPlayers();
    }

    @Override
    public void buildRoad(final RoadPosition position) {
        gameManager.buildRoad(position, turnController.getCurrentPlayerTurn());
        redrawResourcesView();
        this.appView.redrawPlayers();
    }

    @Override
    public void buyCard() {
        CardType card = gameManager.buyCard(turnController.getCurrentPlayerTurn());
        if (card.equals(CardType.KNIGHT)) {
            mustPlaceRobber = true;
        }
        redrawResourcesView();
        this.appView.redrawPlayers();
    }

    @Override
    public boolean canBuildSettlement(final PropertyPosition position) {
        return !mustPlaceRobber() && gameManager.canBuildSettlement(position, turnController.getCurrentPlayerTurn());
    }

    @Override
    public boolean canBuildCity(final PropertyPosition position) {
        return !mustPlaceRobber() && gameManager.canBuildCity(position, turnController.getCurrentPlayerTurn());
    }

    @Override
    public boolean canBuildRoad(final RoadPosition position) {
        return !mustPlaceRobber() && gameManager.canBuildRoad(position, turnController.getCurrentPlayerTurn());
    }

    @Override
    public int getPlayerPoints(final String player) {
        return getPlayerByName(player).getVictoryPoints();
    }

    @Override
    public boolean hasResources(final String playerName, final Map<ResourceType, Integer> resources) {
        return resourceController.hasResources(playerName, resources);
    }

    @Override
    public boolean canEndTurn() {
        return !mustPlaceRobber() && gameManager.canEndTurn();
    }

    @Override
    public boolean canBuyCard() {
        return turnController.hasRolled() && !mustPlaceRobber()
                && this.gameManager.canBuyCard(turnController.getCurrentPlayerTurn());
    }

    private Player getPlayerByName(final String name) {
        return this.gameManager.getPlayers().stream().filter(p -> p.getName().equals(name)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Player with name " + name + " does not exist."));
    }

    @Override
    public boolean canRollDie() {
        return !turnController.hasRolled() && turnController.getCycle() > 2;
    }

    @Override
    public void produceResources(final int number) {
        gameManager.produceResources(number);
        redrawResourcesView();
    }

    @Override
    public void acceptTrade(final String proposer, final String accepter,
            final Map<ResourceType, Integer> proposedResources,
            final Map<ResourceType, Integer> wantedResources) {
        resourceController.acceptTrade(proposer, accepter, proposedResources,
                wantedResources);
        redrawResourcesView();
    }

    @Override
    public boolean canStartTrade() {
        return !mustPlaceRobber() && turnController.getCycle() > 2 && turnController.hasRolled();
    }

    private void redrawResourcesView() {
        this.appView.redrawCurrentPlayer();
        this.appView.redrawBank();
    }

    @Override
    public String getBank() {
        return "bank";
    }

    @Override
    public void setRobberPosition(final TilePosition coordinates) {
        this.boardController.setRobberPosition(coordinates);
        this.mustPlaceRobber = false;
        this.appView.redrawCurrentPlayer();
    }

    @Override
    public boolean mustPlaceRobber() {
        return mustPlaceRobber;
    }

    @Override
    public Pair<Integer, Integer> rollDie() {
        final Pair<Integer, Integer> rolledDies = turnController.rollDie();
        produceResources(rolledDies.getLeft() + rolledDies.getRight());
        mustPlaceRobber = (rolledDies.getLeft() + rolledDies.getRight()) == 7;
        return rolledDies;
    }
}
