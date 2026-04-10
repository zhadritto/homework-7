package com.narxoz.rpg.strategy;

public class BossPhase1Strategy implements CombatStrategy {
    @Override
    public int calculateDamage(int basePower) { return basePower; }
    @Override
    public int calculateDefense(int baseDefense) { return (int) (baseDefense * 1.2); }
    @Override
    public String getName() { return "Measured and Calculated (Phase 1)"; }
}