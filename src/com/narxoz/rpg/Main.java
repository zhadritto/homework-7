package com.narxoz.rpg;

import com.narxoz.rpg.combatant.DungeonBoss;
import com.narxoz.rpg.combatant.PlayerHero;
import com.narxoz.rpg.engine.DungeonEngine;
import com.narxoz.rpg.engine.EncounterResult;
import com.narxoz.rpg.observer.*;
import com.narxoz.rpg.strategy.AggressiveStrategy;
import com.narxoz.rpg.strategy.BalancedStrategy;
import com.narxoz.rpg.strategy.DefensiveStrategy;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EventManager eventManager = new EventManager();
        PlayerHero warrior = new PlayerHero("Warrior", 100, 30, 15, new AggressiveStrategy(), eventManager);
        PlayerHero tank = new PlayerHero("Paladin", 120, 15, 25, new DefensiveStrategy(), eventManager);
        PlayerHero rogue = new PlayerHero("Rogue", 80, 25, 10, new BalancedStrategy(), eventManager);
        List<PlayerHero> party = Arrays.asList(warrior, tank, rogue);
        DungeonBoss boss = new DungeonBoss("Cursed Lich King", 250, 40, 10, eventManager);
        eventManager.registerObserver(new BattleLogger());
        eventManager.registerObserver(new AchievementTracker());
        eventManager.registerObserver(new PartySupport(party));
        eventManager.registerObserver(new HeroStatusMonitor(party));
        eventManager.registerObserver(new LootDropper());
        eventManager.registerObserver(boss);
        DungeonEngine engine = new DungeonEngine(party, boss, eventManager);
        EncounterResult result = engine.runEncounter();
        System.out.println("\n=== ENCOUNTER RESULT ===");
        System.out.println("Heroes Won: " + result.isHeroesWon());
        System.out.println("Rounds Played: " + result.getRoundsPlayed());
        System.out.println("Surviving Heroes: " + result.getSurvivingHeroes());
    }
}