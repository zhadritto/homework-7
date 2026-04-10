package com.narxoz.rpg.observer;

public class BattleLogger implements GameObserver {
    @Override
    public void onEvent(GameEvent event) {
        System.out.printf("[LOG] Event: %s | Source: %s | Value: %d%n",
                event.getType(), event.getSourceName(), event.getValue());
    }
}