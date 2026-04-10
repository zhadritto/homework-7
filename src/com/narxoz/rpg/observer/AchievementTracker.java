package com.narxoz.rpg.observer;

import java.util.HashSet;
import java.util.Set;

public class AchievementTracker implements GameObserver {
    private final Set<String> unlockedAchievements = new HashSet<>();

    @Override
    public void onEvent(GameEvent event) {
        switch (event.getType()) {
            case ATTACK_LANDED:
                unlock("First Blood");
                break;
            case HERO_DIED:
                unlock("Casualty of War");
                break;
            case BOSS_DEFEATED:
                unlock("Dungeon Master");
                break;
            default:
                break;
        }
    }

    private void unlock(String achievement) {
        if (unlockedAchievements.add(achievement)) {
            System.out.println(">>> ACHIEVEMENT UNLOCKED: [" + achievement + "] <<<");
        }
    }
}