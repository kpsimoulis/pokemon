package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import card.Energy;


public class Attack {


    private String name;
    private Target target;
    private int damagePoints;
    private HashMap<Energy, Integer> energyRequired;
    private boolean flip;
    private int additionalDamagePoints;
    private ApplyStatus applyStatus;
    private Target additionalTarget;
    private Destination destination;

    private enum Target {OPPONENTACTIVE, OPPONENTHAND, UNDEFINED}

    private enum ApplyStatus {PARALYZED, NONE}

    private enum Destination {BOTTOMOFDECK, NONE}

    public Attack() {
        this.name = "";
        this.target = Target.UNDEFINED;
        this.damagePoints = 0;
        this.energyRequired = new HashMap<Energy, Integer>();
        this.flip = false;
        this.additionalDamagePoints = 0;
        this.ApplyStatus = ApplyStatus.NONE;
        this.additionalTarget = Target.UNDEFINED;
        this.destination = Destination.NONE;
    }

    public String getDescription() {
        String description = name + "<br/>" + "Target : " + target + "<br/>" + "Damage points: " + damagePoints + "<br/>" + "Energy required: " + "<br/>";
        for (Map.Entry<Energy, Integer> entry : energyRequired.entrySet()) {
            description += "&nbsp;&nbsp;&nbsp;" + entry.getKey().getType() + ": " + entry.getValue() + "<br/>";
        }
        description += "Flip required: " + flip;
        if (applyStatus == ApplyStatus.PARALYZED) {
            description += "<br/>Status to apply: Paralyzed";
        }
        if (additionalDamagePoints > 0) {
            description += "<br/>" + "Potential additional damage: " + additionalDamagePoints;
        }
        if (destination != Destination.NONE) {
            description += "<br/>" + "Destination: " + destination;
        }
        return description;
    }

    public String getTarget() {
        return this.target.toString();
    }

    public int getDamagePoints() {
        return this.damagePoints;
    }

    public HashMap<Energy, Integer> getEnergyRequired() {
        return this.energyRequired;
    }

    public boolean getFlipRequired() {
        return this.flip;
    }

    public String getStatusToApply() {
        return this.applyStatus.toString();
    }

    public String getAdditionalTarget() {
        return this.additionalTarget.toString();
    }

    public int getAdditionalDamagePoints() {
        return this.additionalDamagePoints;
    }

    public String getDestination() {
        return this.destination.toString();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFlip(boolean flip) {
        if (flip) {
            this.flip = true;
        } else {
            this.flip = false;
        }
    }

    public void setTarget(String target) {
        if (target.equals("opponent-active")) {
            this.target = Target.OPPONENTACTIVE;
        } else if (target.equals("opponent-hand")) {
            this.target = Target.OPPONENTHAND;
        } else {
            this.target = Target.UNDEFINED;
        }
    }

    public void setApplyStatus(String status) {
        if (status.equals("paralyzed")) {
            applyStatus = ApplyStatus.PARALYZED;
        } else {
            applyStatus = ApplyStatus.NONE;
        }
    }

    public void setDamagePoints(int points) {
        this.damagePoints = points;
    }

    public void setAdditionalDamagePoints(int points) {
        this.additionalDamagePoints = points;
    }

    public void setAdditionalTarget(String target) {
        if (target.equals("opponent-active")) {
            this.additionalTarget = Target.OPPONENTACTIVE;
        } else {
            this.additionalTarget = Target.UNDEFINED;
        }
    }

    public void addEnergyRequirement(Energy energy, int amount) {
        this.energyRequired.put(energy, amount);
    }

    public void setDestination(String dest) {
        if (dest.equals("deck-bottom")) {
            this.destination = Destination.BOTTOMOFDECK;
        } else {
            this.destination = Destination.NONE;
        }
    }
}