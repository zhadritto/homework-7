package com.narxoz.rpg.observer;

import com.narxoz.rpg.combatant.PlayerHero;
import java.util.List;

public class HeroStatusMonitor implements GameObserver {
    private final List<PlayerHero> party;

    public HeroStatusMonitor(List<PlayerHero> party) {
        this.party = party;
    }

    @Override
    public void onEvent(GameEvent event) {
        if (event.getType() == GameEventType.HERO_LOW_HP || event.getType() == GameEventType.HERO_DIED) {
            System.out.println("--- HERO STATUS UPDATE ---");
            for (PlayerHero hero : party) {
                String status = hero.isAlive() ? hero.getHp() + "/" + hero.getMaxHp() + " HP" : "DEAD";
                System.out.printf(" %s: %s%n", hero.getName(), status);
            }
            System.out.println("--------------------------");
        }
    }
}