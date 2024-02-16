package it.unibo.controller.impl;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.apache.commons.lang3.tuple.Pair;

import it.unibo.common.card.CardType;
import it.unibo.common.property.PropertyPosition;
import it.unibo.common.road.RoadPosition;
import it.unibo.common.tile.ResourceType;
import it.unibo.common.tile.TilePosition;
import it.unibo.controller.api.BoardController;
import it.unibo.controller.api.MainController;
import it.unibo.controller.api.ResourceController;
import it.unibo.controller.api.TurnController;
import it.unibo.model.api.GameManager;
import it.unibo.model.api.Player;
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
    private static final int ROBBER_NUMBER = 7;

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

        this.boardController = new BoardControllerImpl(getPlayerByName, this.gameManager.getBoard(),
                this.gameManager.getPropertyManager(), this.gameManager.getRoadManager());
        this.resourceController = new ResourceControllerImpl(getPlayerByName, gameManager.getResourceManager());
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

    // @Override
    // public TurnController getTurnController() {
    // return this.turnController;
    // }

    @Override
    public List<String> getPlayerNames() {
        return gameManager.getPlayers().stream().map(p -> p.getName()).toList();
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
        final CardType card = gameManager.buyCard(turnController.getCurrentPlayerTurn());
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
    public boolean canEndTurn() {
        return !mustPlaceRobber() && gameManager.canEndTurn();
    }

    @Override
    public boolean canBuyCard() {
        return turnController.hasRolled() && !mustPlaceRobber()
                && this.gameManager.canBuyCard(turnController.getCurrentPlayerTurn());
    }

    @Override
    public boolean canRollDie() {
        return !turnController.hasRolled() && turnController.getCycle() > 2; //
    }

    @Override
    public void produceResources(final int number) {
        gameManager.produceResources(number);
        redrawResourcesView();
    }

    @Override
    public void tradeWithPlayer(final String proposer, final String accepter,
            final Map<ResourceType, Integer> proposedResources,
            final Map<ResourceType, Integer> wantedResources) {
        resourceController.tradeWithPlayer(proposer, accepter, proposedResources,
                wantedResources);
        redrawResourcesView();
    }

    @Override
    public boolean canStartTrade() {
        return !mustPlaceRobber() && turnController.getCycle() > 2 && turnController.hasRolled();
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
        mustPlaceRobber = rolledDies.getLeft() + rolledDies.getRight() == ROBBER_NUMBER;
        return rolledDies;
    }

    @Override
    public void tradeWithBank(final String proposer, final Map<ResourceType, Integer> proposedResources,
            final Map<ResourceType, Integer> wantedResources) {
        this.resourceController.tradeWithBank(proposer, proposedResources, wantedResources);
        redrawResourcesView();

    }

    @Override
    public void endTurn() {
        turnController.endTurn();
    }

    private void redrawResourcesView() {
        this.appView.redrawCurrentPlayer();
        this.appView.redrawBank();
    }

    private Player getPlayerByName(final String name) {
        return this.gameManager.getPlayers().stream().filter(p -> p.getName().equals(name)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Player with name " + name + " does not exist."));
    }

}
