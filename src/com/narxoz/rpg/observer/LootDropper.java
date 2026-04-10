package com.narxoz.rpg.observer;

public class LootDropper implements GameObserver {
    @Override
    public void onEvent(GameEvent event) {
        if (event.getType() == GameEventType.BOSS_PHASE_CHANGED) {
            System.out.println("[Loot] The boss drops a glowing shard as it changes forms!");
        } else if (event.getType() == GameEventType.BOSS_DEFEATED) {
            System.out.println("[Loot] EPIC DROP: The Cursed Sword of Narxoz!");
        }
    }
}