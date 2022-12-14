package fr.thomas.controller.tasks;

import fr.thomas.controller.Controller;
import fr.thomas.modele.game.GameState;

public class WinTask extends GameTask {

    public WinTask(Controller controller) {
        super(controller);

        controller.getMenusManager().setGameState(GameState.END);
        controller.getTextInfo().setText("Gagné !");
        controller.getEndInfos().setText("Bien joué !\n" +
                "Déplacements effectués: " + controller.getGame().getPlayer().getMovementsHistory().size() + "\n" +
                "Energies collectées: " + controller.getGame().getPlayer().getPowerUsed() + "\n" +
                "Retours effectués: " + controller.getGame().getPlayer().getCanceledMovements() + "\n" +
                "");

        controller.getGame().setEnd(true);

        new VisitedTasks(controller, true).start();
    }

    @Override
    public void onTick() {
        getGameLoop().stop();
    }
}
