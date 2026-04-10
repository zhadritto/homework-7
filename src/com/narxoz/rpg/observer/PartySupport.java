package com.narxoz.rpg.observer;

import com.narxoz.rpg.combatant.PlayerHero;
import java.util.List;

public class PartySupport implements GameObserver {
    private final List<PlayerHero> party;

    public PartySupport(List<PlayerHero> party) {
        this.party = party;
    }

    @Override
    public void onEvent(GameEvent event) {
        if (event.getType() == GameEventType.HERO_LOW_HP) {
            System.out.println("[PartySupport] Crisis detected! Healing a random living ally...");
            for (PlayerHero hero : party) {
                if (hero.isAlive()) {
                    hero.heal(30);
                    System.out.println("[PartySupport] " + hero.getName() + " was healed for 30 HP!");
                    break; // Лечим только одного
                }
            }
        }
    }
}