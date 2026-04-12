package com.narxoz.rpg.combatant;

import com.narxoz.rpg.observer.EventManager;
import com.narxoz.rpg.observer.GameEvent;
import com.narxoz.rpg.observer.GameEventType;
import com.narxoz.rpg.observer.GameObserver;
import com.narxoz.rpg.strategy.BossPhase1Strategy;
import com.narxoz.rpg.strategy.BossPhase2Strategy;
import com.narxoz.rpg.strategy.BossPhase3Strategy;
import com.narxoz.rpg.strategy.CombatStrategy;

public class DungeonBoss implements GameObserver {
    private final String name;
    private int hp;
    private final int maxHp;
    private final int baseAttack;
    private final int baseDefense;

    private CombatStrategy strategy;
    private final EventManager eventManager;
    private int currentPhase = 1;

    public DungeonBoss(String name, int hp, int baseAttack, int baseDefense, EventManager eventManager) {
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
        this.baseAttack = baseAttack;
        this.baseDefense = baseDefense;
        this.eventManager = eventManager;
        this.strategy = new BossPhase1Strategy();
    }

    public String getName() { return name; }
    public int getHp() { return hp; }
    public CombatStrategy getStrategy() { return strategy; }
    public int getBaseAttack() { return baseAttack; }
    public int getBaseDefense() { return baseDefense; }
    public boolean isAlive() { return hp > 0; }

    public void takeDamage(int amount) {
        if (!isAlive()) return;
        hp = Math.max(0, hp - amount);

        if (hp == 0) {
            eventManager.fireEvent(new GameEvent(GameEventType.BOSS_DEFEATED, name, 0));
        } else {
            checkPhaseTransition();
        }
    }

    private void checkPhaseTransition() {
        double hpPercent = (double) hp / maxHp;
        int newPhase = currentPhase;

        if (hpPercent <= 0.3) {
            newPhase = 3;
        } else if (hpPercent <= 0.6) {
            newPhase = 2;
        }

        if (newPhase > currentPhase) {
            currentPhase = newPhase;
            eventManager.fireEvent(new GameEvent(GameEventType.BOSS_PHASE_CHANGED, name, currentPhase));
        }
    }


    @Override
    public void onEvent(GameEvent event) {
        if (event.getType() == GameEventType.BOSS_PHASE_CHANGED && event.getSourceName().equals(this.name)) {
            int phase = event.getValue();
            if (phase == 2) {
                this.strategy = new BossPhase2Strategy();
            } else if (phase == 3) {
                this.strategy = new BossPhase3Strategy();
            }
            System.out.println(">> Boss automatically switched strategy to: " + this.strategy.getName());
        }
    }
}