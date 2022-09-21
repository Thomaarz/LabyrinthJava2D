package fr.thomas.modele.entity;

import fr.thomas.exceptions.MovementException;
import fr.thomas.modele.map.Localizable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Player extends Localizable {

    public static final int MAX_CANCEL = 5;

    public static final int DEFAULT_POWER = 100;
    public static final int POWER_LOSS = 10;
    public static final int POWER_WIN = 10;

    private int power = DEFAULT_POWER;

    private DoubleProperty powerProperty = new SimpleDoubleProperty(power / 100.0);

    private int canceledMovements = 0;
    private List<Movement> movementsHistory = new ArrayList<>();

    public Player(int x, int y) {
        super(x, y);
    }

    public void setPower(int power) {
        this.power = power;
        this.powerProperty.set(this.power / 100.0);
    }

    /**
     * Move the player in the movement
     */
    public void move(Movement movement) {
        super.move(movement.getX(), movement.getY());
    }

    /**
     * Remove power
     */
    public void removePower(int power) throws MovementException {
        if (this.power <= 0) {
            throw new MovementException("Aucune énergie disponible.");
        }

        this.power -= power;

        this.powerProperty.set(this.power / 100.0);
    }

    /**
     * Add Power
     */
    public void addPower(int power) {
        this.power += power;

        this.powerProperty.set(this.power / 100.0);
    }

    /**
     * Add Movement in history
     */
    public void addMovement(Movement movement) {
        movementsHistory.add(movement);
    }

    /**
     * Cancel "amount" movement
     */
    public void cancelMovement(int amount) throws MovementException {
        for (int i = 0; i < amount; i++) {
            cancelMovement();
        }
    }

    /**
     * Cancel one movement, remove from history and give power
     */
    private void cancelMovement() throws MovementException {

        // No Movement in history
        if (movementsHistory.size() <= 0) {
            throw new MovementException("Impossible d'annuler le dernier déplacement.");
        }

        // Cancel max movements
        if (canceledMovements >= MAX_CANCEL) {
            throw new MovementException("Vous avez annuler le maximum de déplacement (" + MAX_CANCEL + ")");
        }

        // Add Cancel
        canceledMovements++;

        // Last Movement
        Movement last = movementsHistory.get(movementsHistory.size() - 1);

        // Move Opossite Direction
        move(last.getOpposite());

        // Remove Last Movement from History
        movementsHistory.remove(movementsHistory.size() - 1);

        // Give Power
        addPower(POWER_LOSS);
    }

    /**
     * Reset the player stats (for replay)
     */
    public void reset() {
        set(1, 4);
        setPower(Player.DEFAULT_POWER);
        movementsHistory = new ArrayList<>();
        canceledMovements = 0;
    }

    @Override
    public String toString() {
        return "Player{" +
                "power=" + power +
                ", canceledMovements=" + canceledMovements +
                ", movementsHistory=" + movementsHistory +
                '}';
    }
}
