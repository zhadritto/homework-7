package com.narxoz.rpg.engine;

import com.narxoz.rpg.combatant.DungeonBoss;
import com.narxoz.rpg.combatant.PlayerHero;
import com.narxoz.rpg.observer.EventManager;
import com.narxoz.rpg.observer.GameEvent;
import com.narxoz.rpg.observer.GameEventType;
import com.narxoz.rpg.strategy.DefensiveStrategy;

import java.util.List;

public class DungeonEngine {
    private final List<PlayerHero> heroes;
    private final DungeonBoss boss;
    private final EventManager eventManager;
    private static final int MAX_ROUNDS = 50;

    public DungeonEngine(List<PlayerHero> heroes, DungeonBoss boss, EventManager eventManager) {
        this.heroes = heroes;
        this.boss = boss;
        this.eventManager = eventManager;
    }

    public EncounterResult runEncounter() {
        int round = 1;
        System.out.println("\n=== THE ENCOUNTER BEGINS ===");

        while (round <= MAX_ROUNDS && boss.isAlive() && getSurvivingCount() > 0) {
            System.out.println("\n--- Round " + round + " ---");
            if (round == 3 && heroes.get(0).isAlive()) {
                System.out.println(heroes.get(0).getName() + " decides to play it safe and switches to Defensive Strategy!");
                heroes.get(0).setStrategy(new DefensiveStrategy());
            }
            for (PlayerHero hero : heroes) {
                if (hero.isAlive() && boss.isAlive()) {
                    int heroAtk = hero.getStrategy().calculateDamage(hero.getAttackPower());
                    int bossDef = boss.getStrategy().calculateDefense(boss.getBaseDefense());
                    int damage = Math.max(0, heroAtk - bossDef);

                    boss.takeDamage(damage);
                    eventManager.fireEvent(new GameEvent(GameEventType.ATTACK_LANDED, hero.getName(), damage));
                }
            }

            if (boss.isAlive()) {
                System.out.println("Boss attacks the party using: " + boss.getStrategy().getName());
                for (PlayerHero hero : heroes) {
                    if (hero.isAlive()) {
                        int bossAtk = boss.getStrategy().calculateDamage(boss.getBaseAttack());
                        int heroDef = hero.getStrategy().calculateDefense(hero.getDefense());
                        int damage = Math.max(0, bossAtk - heroDef);

                        hero.takeDamage(damage);
                        eventManager.fireEvent(new GameEvent(GameEventType.ATTACK_LANDED, boss.getName(), damage));
                    }
                }
            }
            round++;
        }

        System.out.println("\n=== ENCOUNTER ENDED ===");
        boolean heroesWon = !boss.isAlive();
        return new EncounterResult(heroesWon, round - 1, getSurvivingCount());
    }

    private int getSurvivingCount() {
        int count = 0;
        for (PlayerHero h : heroes) if (h.isAlive()) count++;
        return count;
    }
}