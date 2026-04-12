package com.narxoz.rpg.combatant;

import com.narxoz.rpg.observer.EventManager;
import com.narxoz.rpg.observer.GameEvent;
import com.narxoz.rpg.observer.GameEventType;
import com.narxoz.rpg.strategy.CombatStrategy;

public class PlayerHero extends Hero {
    private CombatStrategy strategy;
    private final EventManager eventManager;
    private boolean lowHpFired = false;

    public PlayerHero(String name, int hp, int attackPower, int defense, CombatStrategy strategy, EventManager eventManager) {
        super(name, hp, attackPower, defense);
        this.strategy = strategy;
        this.eventManager = eventManager;
    }

    public CombatStrategy getStrategy() { return strategy; }
    public void setStrategy(CombatStrategy strategy) { this.strategy = strategy; }

    @Override
    public void takeDamage(int amount) {
        boolean wasAlive = isAlive();
        super.takeDamage(amount);

        if (isAlive() && !lowHpFired && getHp() <= getMaxHp() * 0.3) {
            lowHpFired = true;
            eventManager.fireEvent(new GameEvent(GameEventType.HERO_LOW_HP, getName(), getHp()));
        }

        if (wasAlive && !isAlive()) {
            eventManager.fireEvent(new GameEvent(GameEventType.HERO_DIED, getName(), 0));
        }
    }
}