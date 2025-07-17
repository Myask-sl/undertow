package invalid.myask.undertow.recipes.anvil;

import invalid.myask.undertow.Config;

public interface IAnvilCosts {
    default int getMaterialCost() {return 1;}
    default int getLevelCost() {return Config.map_anvil_recipe_levelcost;}
}
