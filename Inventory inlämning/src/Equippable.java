interface Equippable {
    void equipItem(Inventory inventory);
    void unEquipItem(Inventory inventory);
    void repairDurability(Inventory inventory);
    void durabilityDamage(Inventory inventory);
}