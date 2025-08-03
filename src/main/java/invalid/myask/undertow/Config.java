package invalid.myask.undertow;

import java.io.File;

import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.common.config.Configuration;

public class Config {
    private static final int ENCHANT_ID_MAX = Enchantment.enchantmentsList.length - 1; //255 normally
    public static boolean enable_curse_breaking = true; //TODO: nonvanilla false
    public static boolean enable_depth_strider = true;
    public static boolean enable_magma_strider = true; //TODO: nonvanilla false
    public static boolean enable_swift_swim = true; //TODO: nonvanilla false
    public static boolean enable_curses_damage = true; //TODO: nonvanilla false
    public static boolean enable_enchants_trident = true;
    public static boolean enable_undertow_trident = true; //TODO: nonvanilla false
    public static boolean enable_cleaving_axe = true; //TODO: nonvanilla false

    public static int enchant_curse_breaking_id = 133;
    public static int enchant_curse_weakness_id = 134;
    public static int enchant_curse_undeadsboon_id = 135;
    public static int enchant_curse_bugsboon_id = 136;
    public static int enchant_curse_unimpale_id = 137;

    public static int enchant_depthstrider_id = 107;
    public static int enchant_magmastrider_id = 138;
    public static int enchant_swiftswim_id = 141;

    public static int enchant_impaling_id = 29;
    public static int enchant_loyalty_id = 31;
    public static int enchant_riptide_id = 30;
    public static int enchant_channeling_id = 132;
    public static int enchant_undertow_id = 139;

    public static int enchant_cleaving_id = 140;

    public static boolean channeling_trident_applicable = true;
    public static boolean channeling_sword_applicable = false; //TODO: tag system or registry instead.
    public static int channeling_weather_minimum = 2; //clear, rain, thunder
    public static int channeling_weather_maximum = 2;
    public static boolean channeling_weather_scaled = false;
    public static boolean channeling_skyvis_required = true;
    public static boolean channeling_sky_path_required = true;
    public static boolean channeling_melee_triggers = false;
    public static boolean channeling_owner_safe = false;
    public static boolean channeling_triggers_on_allblocks = false; //TODO: tags
    public static boolean channeling_aggro = false;

    public static boolean depth_strider_horses = true;
    public static boolean depth_strider_nonboots = true;

    public static boolean swiftswim_horses = true;
    public static boolean swiftswim_nonpants = true;
    public static float swift_swim_multiplier = 0.1F;

    public static boolean loyal_cannot_void = false; //like Bedrock!

    public static int bane_kind_aquatic = 3;
    public static boolean impale_by_creaturetype = true; // like Java!
    public static boolean impale_in_rain = false; // like Bedrock!
    public static boolean impale_in_water = false; // like Bedrock!
    public static boolean impale_drowned = false; // in particular, who are Undead normally.
    public static boolean impale_stack_triggers = false; // like nowhere else!
    public static boolean impale_trident_only = true; //like vanilla

    public static boolean riptide_impulse_instant = true;
    public static float riptide_y_mult = 0.39F;
    public static boolean riptide_super_enabled = false; //allow fourth riptide level, which always works

    public static boolean drowned_throw_riptide_tridents = true;
    public static boolean drowned_fly_riptide_tridents = false;
    public static boolean drowned_make_channeling_strikes = false;
    public static boolean drowned_throw_away_tridents = true;

    public static boolean drowned_equipment_odds_bedrock = false;

    public static boolean trident_allow_bedrock_multihits = false; //like Bedrock
    public static boolean trident_allow_entity_multihits = false;
    public static boolean trident_destroys_chorus_flower = true; //TODO: tags
    public static boolean trident_destroys_stala__ites = true;
    public static boolean trident_disable_creative_block_break = true;

    public static boolean infinity_applicable_trident = false;

    public static boolean shield_pitch_matters = false;
    public static boolean dispenser_throws_tridents = false;
    public static boolean shield_knockback_enabled = true;

    public static boolean map_mark_free = true;
    public static boolean map_mark_mansion = true; //TODO: impl
    public static boolean map_mark_monument = true; //TODO: false all
    public static boolean map_mark_treasure = true;
    public static boolean map_mark_banner = true;

    public static int explorer_map_search_radius = 1500;
    public static byte explorer_map_zoom = 2;
    public static boolean explorer_map_always_unfound = true;

    public static boolean crawl_player_enable = true;
    public static boolean crawl_player_manual = true; //TODO:false TODO: clientside
    public static boolean crawl_player_autostand = true;
    public static int crawl_player_manual_doublesneak_window = 7; //TODO: clientside //tick range for doublepress. 7 @ 20 t/s = .35s
    public static boolean enable_crawl_keybind = true; //TODO: nonvanilla false
    public static boolean key_toggles_crawl = true; //TODO: clientside. Also, FIXME?
    public static boolean de_yaw_crawl_pose = true;
    public static boolean crawl_biped_mobs_enable = true; //TODO: false
    public static float sneak_player_height = 1.65F;
    public static float sneak_biped_ratio = 0.83F; //TODO: 1.0

    public static boolean enable_swim = true;
    public static boolean silly_swim = false;
    public static float swim_friction_multiplier = 0.75F; //silly_swim around 0.5
    public static float swim_y_multiplier = 0.25F; //silly swim around 1
    public static boolean prevent_swim_riptide_posemove = false;

    public static float ladder_fastfall_pitch = 60F;

///Item/Block Enables
    public static boolean enable_conduit = true;

    public static boolean enable_shields = true;
    public static boolean enable_spears = true; //recipe only; it registers it regardless

    public static boolean enable_cartography = true;
    public static boolean enable_table_mapmaking = true;
    public static boolean enable_anvil_mapwork = true;//TODO: false
    public static boolean enable_dumb_maps = true;
    public static boolean enable_deco_maps = true;
    public static int map_anvil_recipe_levelcost = 0;

    public static boolean guard_anvil_left = true;
    public static boolean allow_free_anvil_recipes_general = false;

    public static int locate_permission_level = 0; //TODO: 2
    public static int locatebiome_permission_level = 0; //TODO: 2
    public static int givemapto_permission_level = 0; //TODO: 2

    public static int locate_default_radius = 100;
    public static int locatebiome_default_radius = 200;
    public static int buried_treasure_x = 9;
    public static int buried_treasure_y_depth_max = 5;
    public static int buried_treasure_z = 9;
    public static int buried_treasure_chance_denominator = 100;
    public static int buried_treasure_chance_numerator = 1;
    public static int world_height_min = 0;
    public static int world_height_max = 255;
    public static boolean generate_buried_treasure = true;
    public static boolean buried_treasure_loot_bedrock = false;

    public static int fish_spawn_y_min;
    public static int fish_spawn_y_max;
    public static float fish_spawn_dist_min;
    public static float fish_spawn_dist_max;
    public static boolean fish_spawn_weirdwater = false;
    public static float fish_despawn_dist_must;
    public static float fish_despawn_dist_may;
    public static float fish_despawn_dist_may_sq;
    public static boolean fishbone_drop_bedrock = false;

    public static boolean can_bottle_lightning = true; //
    public static boolean glass_bottle_lightning = false;

    public static void synchronizeConfiguration(File configFile) {
        Configuration configuration = new Configuration(configFile);

        enable_curse_breaking = configuration.getBoolean("enchant_enable_curse_breaking", "enchant.enable",
            enable_curse_breaking, "Enable Curse of Breaking (nonvanilla)", "config.enable.enchant.curse.breaking");
        enable_depth_strider =  configuration.getBoolean("enchant_enable_depth_strider", "enchant.enable",
            enable_depth_strider, "Enable Depth Strider (vanilla)", "config.enable.enchant.depth.strider");
        enable_magma_strider =  configuration.getBoolean("enchant_enable_magma_strider", "enchant.enable",
            enable_magma_strider, "Enable Magma Strider (nonvanilla)", "config.enable.enchant.magma.strider");
        enable_swift_swim =  configuration.getBoolean("enchant_enable_swift_swim", "enchant.enable",
            enable_swift_swim, "Enable Swift Swim (nonvanilla)", "config.enable.enchant.swift.swim");
        enable_curses_damage =  configuration.getBoolean("enchant_enable_curses_damage", "enchant.enable",
            enable_curses_damage, "Enable Curses of Weakness (nonvanilla)", "config.enable.enchant.curses.damage");
        enable_enchants_trident =  configuration.getBoolean("enchant_enable_trident", "enchant.enable",
            enable_enchants_trident, "Enable trident enchants (vanilla)", "config.enable.enchant.trident");
        enable_undertow_trident = configuration.getBoolean("enchant_enable_undertow_trident", "enchant.enable",
            enable_undertow_trident, "Enable Undertow enchant (for tridents: nonvanilla)", "config.enable.enchant.undertow");
        enable_cleaving_axe = configuration.getBoolean("enchant_enable_cleaving_axe", "enchant.enable",
            enable_cleaving_axe, "Enable Cleaving enchant on axes (from Combat Tests)", "config.enable.enchant.cleaving");

        enchant_curse_breaking_id = configuration.getInt("enchant_id_curse_breaking_id", "enchant.id",
            enchant_curse_breaking_id, 0, ENCHANT_ID_MAX, "Enchant ID for Curse of Breaking", "config.id.enchant.curse.breaking");
        enchant_curse_weakness_id = configuration.getInt("enchant_id_curse_weakness_id", "enchant.id",
            enchant_curse_weakness_id, 0, ENCHANT_ID_MAX, "Enchant ID for Curse of Weakness", "config.id.enchant.curse.damage.all");
        enchant_curse_undeadsboon_id = configuration.getInt("enchant_id_curse_undeadsboon_id", "enchant.id",
            enchant_curse_undeadsboon_id, 0, ENCHANT_ID_MAX, "Enchant ID for Curse of Undeadboon", "config.id.enchant.curse.damage.undead");
        enchant_curse_bugsboon_id = configuration.getInt("enchant_id_curse_bugsboon_id", "enchant.id",
            enchant_curse_bugsboon_id, 0, ENCHANT_ID_MAX, "Enchant ID for Curse of Bugsboon", "config.id.enchant.curse.damage.arthropods");
        enchant_curse_unimpale_id = configuration.getInt("enchant_id_curse_fishboon_id", "enchant.id",
            enchant_curse_unimpale_id, 0, ENCHANT_ID_MAX, "Enchant ID for Curse of Fishboon", "config.id.enchant.curse.damage.aquatic");

        enchant_depthstrider_id = configuration.getInt("enchant_depthstrider_id", "enchant.id",
            enchant_depthstrider_id, 0, ENCHANT_ID_MAX, "Enchant ID for Depthstrider", "config.id.enchant.depth.strider");
        enchant_magmastrider_id = configuration.getInt("enchant_magmastrider_id", "enchant.id",
            enchant_magmastrider_id, 0, ENCHANT_ID_MAX, "Enchant ID for Magmastrider", "config.id.enchant.magma.strider");
        enchant_swiftswim_id = configuration.getInt("enchant_swiftswim_id", "enchant.id",
            enchant_swiftswim_id, 0, ENCHANT_ID_MAX, "Enchant ID for Swiftswim", "config.id.enchant.swift.swim");

        enchant_channeling_id = configuration.getInt("enchant_id_channeling", "enchant.id",
            enchant_channeling_id, 0, ENCHANT_ID_MAX, "Enchant ID for Channeling", "config.id.enchant.zeustarget");
        enchant_impaling_id = configuration.getInt("enchant_id_impaling", "enchant.id",
            enchant_impaling_id, 0, ENCHANT_ID_MAX, "Enchant ID for Impaling", "config.id.enchant.damage.aquatic");
        enchant_loyalty_id = configuration.getInt("enchant_id_loyalty", "enchant.id",
            enchant_loyalty_id, 0, ENCHANT_ID_MAX, "Enchant ID for Loyalty", "config.id.enchant.lanyard");
        enchant_riptide_id = configuration.getInt("enchant_id_riptide", "enchant.id",
            enchant_riptide_id, 0, ENCHANT_ID_MAX, "Enchant ID for Riptide", "config.id.enchant.riptide");
        enchant_undertow_id = configuration.getInt("enchant_undertow_id", "enchant.id",
            enchant_undertow_id, 0, ENCHANT_ID_MAX, "Enchant ID for Undertow", "config.id.enchant.undertow");

        enchant_cleaving_id = configuration.getInt("enchant_cleaving_id", "enchant.id",
            enchant_cleaving_id, 0, ENCHANT_ID_MAX, "Enchant ID for Cleaving", "config.id.enchant.cleaving");

        channeling_trident_applicable = configuration.getBoolean("channeling_trident_applicable", "enchant.zeustarget",
            channeling_trident_applicable, "Tridents enchantable: Channeling (vanilla)", "config.enchant.zeustarget.appliesto.tridents");
        channeling_sword_applicable = configuration.getBoolean("channeling_sword_applicable", "enchant.zeustarget",
            channeling_sword_applicable, "Swords enchantable: Channeling --enable mêlée triggers for any use. (nonvanilla)", "config.enchant.zeustarget.appliesto.swords");

        channeling_weather_minimum = configuration.getInt("channeling_weather_minimum", "enchant.zeustarget",
            channeling_weather_minimum, 0, 2, "Channeling weather minimum: 0 anytime, 1 rain, 2 (vanilla) thunderstorm",
            "config.enchant.zeustarget.needed.weather.minimum");
        channeling_weather_maximum = configuration.getInt("channeling_weather_maximum", "enchant.zeustarget",
            channeling_weather_maximum, 0, 2, "Channeling weather maximum: 0 anytime, 1 rain, 2 (vanilla) thunderstorm",
            "config.enchant.zeustarget.needed.weather.maximum");
        channeling_weather_scaled = configuration.getBoolean("channeling_weather_scaled", "enchant.zeustarget",
            channeling_weather_scaled, "Higher levels of channeling exist and allow lower weather requirements (overrides min/max, nonvanilla)",
            "config.enchant.zeustarget.needed.weather.scaled");
        channeling_skyvis_required = configuration.getBoolean("channeling_skyvis_required", "enchant.zeustarget",
            channeling_skyvis_required, "Channeling hit must see sky to strike (vanilla)", "config.enchant.zeustarget.needed.skyvis");
        channeling_sky_path_required = configuration.getBoolean("channeling_sky_path_required", "enchant.zeustarget",
            channeling_sky_path_required, "Channeling hit requires clear column to sky (vanilla)", "config.enchant.zeustarget.needed.skypath");
        channeling_melee_triggers = configuration.getBoolean("channeling_melee_triggers", "enchant.zeustarget",
            channeling_melee_triggers, "Channeling hit in mêlée will call lightning bolt. Dangerous! (nonvanilla)", "config.enchant.zeustarget.permit.melee");
        channeling_owner_safe = configuration.getBoolean("channeling_owner_safe", "enchant.zeustarget",
            channeling_owner_safe, "Called lightning won't [directly] hurt owner nonvanilla)", "config.enchant.zeustarget.owner.safe");
        channeling_triggers_on_allblocks = configuration.getBoolean("channeling_triggers_on_allblocks", "enchant.zeustarget",
            channeling_triggers_on_allblocks, "Channeling will trigger on all blocks nonvanilla)", "config.enchant.zeustarget.permit.allblocks");

        channeling_aggro = configuration.getBoolean("channeling_aggro", "enchant.zeustarget",
            channeling_aggro, "Channeling lightning aggros to thrower (nonvanilla)", "config.enchant.zeustarget.channeling.aggro");

        depth_strider_horses = configuration.getBoolean("depth_strider_horses", "enchant.depthstrider",
            depth_strider_horses, "Let horses benefit from Depth/Magma Strider, as in vanilla 1.14-1.20.4", "config.enchant.depthstrider.horses");
        depth_strider_nonboots = configuration.getBoolean("depth_strider_nonboots", "enchant.depthstrider",
            depth_strider_nonboots, "Let non-boots Depth/Magma Strider work, as in vanilla < 1.21. Does not enable applying.", "config.enchant.depthstrider.nonboots");

        swiftswim_horses = configuration.getBoolean("swiftswim_horses", "enchant.swiftswim",
            swiftswim_horses, "Let horses benefit from swiftswim", "config.enchant.swiftswim.horses");
        swiftswim_nonpants = configuration.getBoolean("swiftswim_nonpants", "enchant.swiftswim",
            swiftswim_nonpants, "Let non-pants Swiftswim apply. Does not enable so-enchanting.", "config.enchant.swiftswim.nonpants");
        swift_swim_multiplier = configuration.getFloat("swift_swim_multiplier", "enchant.swiftswim",
            swift_swim_multiplier, 0, Float.MAX_VALUE, "Speed multiplier per swiftswim level (nonvanilla)",
            "config.enchant.swiftswim.multiplier");

        loyal_cannot_void = configuration.getBoolean("loyal_cannot_void", "enchant.loyal", loyal_cannot_void,
            "Loyal-enchanted tridents will start returning rather than dying to the Void (Bedrock)", "config.enchant.lanyard.novoid");

        bane_kind_aquatic = configuration.getInt("enchant_bane_kind_impaling", "enchant.kind",
            bane_kind_aquatic, 0, Integer.MAX_VALUE, "Numeric ID for aquatic-bane sort", "config.kind.bane.aquatic");

        impale_by_creaturetype = configuration.getBoolean("impale_by_creaturetype", "enchant.impaling", impale_by_creaturetype,
            "Impale by type (Java)", "config.impale.by.creaturetype");
        impale_in_rain = configuration.getBoolean("impale_in_rain", "enchant.impaling", impale_in_rain,
            "Impale in rain (Bedrock)", "config.impale.in.rain");
        impale_in_water = configuration.getBoolean("impale_in_water", "enchant.impaling", impale_in_water,
            "Impale underwater (Bedrock)", "config.impale.in.water");
        impale_drowned = configuration.getBoolean("impale_drowned", "enchant.impaling", impale_drowned,
            "Impale drowned, in particular (nonvanilla)", "config.impale.drowned");
        impale_stack_triggers = configuration.getBoolean("impale_stack_triggers", "enchant.impaling", impale_stack_triggers,
            "Stack impaling damage if multiple criteria met (nonvanilla)", "config.impale.stack.triggers"); // like nowhere else!
        impale_trident_only = configuration.getBoolean("impale_trident_only", "enchant.impaling", impale_trident_only,
            "Only allow tridents to ");

        riptide_impulse_instant = configuration.getBoolean("riptide_impulse_instant", "enchant.riptide",
            riptide_impulse_instant, "Riptide instant thrust rather than extended (vanilla)", "config.riptide.impulse.instant");
        riptide_y_mult = configuration.getFloat("riptide_y_mult", "enchant.riptide",
            riptide_y_mult, 0, Float.MAX_VALUE, "Riptide Y multiplier (0.39 ~ vanilla)", "config.riptide.impulse.ymult");
        riptide_super_enabled = configuration.getBoolean("riptide_super_enabled", "enchant.riptide", riptide_super_enabled,
            "Riptide gets fourth level that always works (nonvanilla)", "config.riptide.super.enable");

        drowned_throw_riptide_tridents = configuration.getBoolean("drowned_throw_riptide_tridents", "drowned.mechanics",
            drowned_throw_riptide_tridents, "Drowned throw Riptide tridents, unlike players (vanilla)", "config.drowned.throw.riptide.tridents");
        drowned_fly_riptide_tridents = configuration.getBoolean("drowned_fly_riptide_tridents", "drowned.mechanics",
            drowned_throw_riptide_tridents, "Drowned fly Riptide tridents, when not thrown (nonvanilla)", "config.drowned.fly.riptide.tridents");
        drowned_make_channeling_strikes = configuration.getBoolean("drowned_make_channeling_strikes", "drowned.mechanics",
            drowned_make_channeling_strikes, "Drowned-thrown Channeling tridents can make lightning bolts (1.21.2+)",
            "config.drowned.activate.channeling.tridents");
        drowned_throw_away_tridents = configuration.getBoolean("drowned_throw_away_tridents", "drowned.mechanics",
            drowned_throw_away_tridents,"Drowned lose thrown tridents like players. DAMAGE BUGGED TO NOT GO DOWN (nonvanilla. Bug is vanilla.)", "config.drowned.throw.away.tridents");

        drowned_equipment_odds_bedrock = configuration.getBoolean("drowned_equipment_odds_bedrock", "drowned.mechanics",
            drowned_equipment_odds_bedrock, "Drowned get equipped when converted (as Bedrock) and have more tridents/shells, fewer fishing rods (per Bedrock odds)",
            "config.drowned.equipment.odds.bedrock");

        trident_allow_bedrock_multihits = configuration.getBoolean("trident_allow_bedrock_multihits", "trident.mechanics",
            trident_allow_bedrock_multihits, "Thrown trident will hit again after impacting some block (Bedrock)", "config.trident.allow.block.multihits");
        trident_allow_entity_multihits = configuration.getBoolean("trident_allow_entity_multihits", "trident.mechanics",
            trident_allow_entity_multihits, "Thrown trident will hit again after impacting some entity (nonvanilla)", "config.trident.allow.entity.multihits");
        trident_destroys_chorus_flower = configuration.getBoolean("trident_destroys_chorus_flower", "trident.mechanics",
            trident_destroys_chorus_flower, "Thrown trident destroys chorus flower (vanilla)", "config.trident.destroys.chorusflower");
        trident_destroys_stala__ites = configuration.getBoolean("trident_destroys_stala__ites", "trident.mechanics",
            trident_destroys_stala__ites, "Thrown trident destroys stala??ites (vanilla)", "config.trident.destroys.stala.ites");
        trident_disable_creative_block_break = configuration.getBoolean("trident_disable_creative_block_break", "trident.mechanics",
            trident_disable_creative_block_break, "Trident leftclick can't break blocks in Creative mode, just like sword (vanilla)", "config.trident.creative.no.mining");

        infinity_applicable_trident = configuration.getBoolean("infinity_applicable_trident", "trident",
            infinity_applicable_trident, "Trident may be enchanted with Infinity like bows (nonvanilla)", "config.trident.infinity.enchantable");

        dispenser_throws_tridents = configuration.getBoolean("dispenser_throws_tridents", "trident.mechanics",
            dispenser_throws_tridents, "Dispenser throws tridents (like in Combat Tests)", "config.trident.dispenser.throws");

        shield_pitch_matters = configuration.getBoolean("shield_pitch_matters", "shield",
            shield_pitch_matters, "Make shield pitch matter for block angle calc (nonvanilla)", "config.shield.pitch.matters");
        shield_knockback_enabled = configuration.getBoolean("shield_knockback_enabled", "shield",
            shield_knockback_enabled, "Make shield knock back melee attackers (like in Bedrock or pre-1.14 bugging it out)", "config.shield.knockback");

        map_mark_free = configuration.getBoolean("map_mark_free", "map.manual_mark",
            map_mark_free, "Let right-click map on air mark POI (limit 1 per map, nonvanilla)", "config.map.mark.free");
        map_mark_mansion = configuration.getBoolean("map_mark_mansion", "map.manual_mark",
            map_mark_mansion, "Let right-click map on woodland mansion to mark it on map (nonvanilla)", "config.map.mark.mansion");
        map_mark_monument = configuration.getBoolean("map_mark_monument", "map.manual_mark",
            map_mark_monument, "Let right-click map on ocean monument/Guardian to mark monument on map (nonvanilla)", "config.map.mark.monument");
        map_mark_treasure = configuration.getBoolean("map_mark_treasure", "map.manual_mark",
            map_mark_treasure, "Let right-click map on buried treasure to mark it on map (nonvanilla)", "config.map.mark.treasure");
        map_mark_banner = configuration.getBoolean("map_mark_banner", "map.manual_mark",
            map_mark_banner, "Let right-click map on banner to mark it on map (vanilla in Java)", "config.map.mark.banner");

        explorer_map_search_radius = configuration.getInt("explorer_map_search_radius", "map.explorer",
            explorer_map_search_radius, 1, Integer.MAX_VALUE,
            "How many chunks away to search for explorer map targets","config.map.explorer.radius");
        explorer_map_zoom = (byte) configuration.getInt( "explorer_map_zoom", "map.explorer",
            explorer_map_zoom, 0, 4, "What zoom explorer maps are (0-4, vanilla 2)",
            "config.map.explorer.zoom");
        explorer_map_always_unfound = configuration.getBoolean("explorer_map_always_unfound", "map.explorer",
            explorer_map_always_unfound, "Do explorer maps always return a never-found structure?(vanilla false)",
            "config.map.explorer.unfoundonly");

        crawl_player_enable = configuration.getBoolean("crawl_player_enable", "poses.crawl",
            crawl_player_enable, "Let player crawl at all (as vanilla 1.9+)", "config.poses.crawl.enable");
        crawl_player_manual = configuration.getBoolean("crawl_player_manual", "poses.crawl",
            crawl_player_manual, "Let player manually engage crawl by doubletap sneak (nonvanilla)", "config.poses.crawl.manual");
        crawl_player_autostand = configuration.getBoolean("crawl_player_autostand", "poses.crawl",
            crawl_player_autostand, "Crawling player autostands when able (as vanilla)", "config.poses.crawl.autostand");
        crawl_player_manual_doublesneak_window = configuration.getInt("crawl_player_manual_doublesneak_window", "poses.crawl",
            crawl_player_manual_doublesneak_window, -1, Integer.MAX_VALUE,
            "Manual crawl double-tap window in ticks (20/s) default 7 (nonvanilla. -1 disables doubletap-sneak crawl)", "config.poses.crawl.manual.window");
        enable_crawl_keybind = configuration.getBoolean("enable_crawl_keybind", "poses.crawl",
            enable_crawl_keybind, "Manual crawl (if enabled) has direct keybind (nonvanilla)", "config.poses.crawl.keybind.enable");
        key_toggles_crawl = configuration.getBoolean("key_toggles_crawl", "poses.crawl",
            key_toggles_crawl, "Manual crawl keybind, if enabled, is toggle rather than hold (neither vanilla)", "config.poses.crawl.keybind.toggle");

        de_yaw_crawl_pose = configuration.getBoolean("de_yaw_crawl_pose", "poses.crawl",
            de_yaw_crawl_pose, "Prevent strafe/retreat from turning body during crawl.", "config.poses.crawl.deyaw");
        crawl_biped_mobs_enable = configuration.getBoolean("crawl_biped_mobs_enable", "poses.crawl",
            crawl_biped_mobs_enable, "Let biped mobs crawl (nonvanilla, experimental, broken)", "config.poses.crawl.enable.mobs");
        sneak_player_height = configuration.getFloat("sneak_player_height", "poses.sneak",
            sneak_player_height, 1.01F, 2.0F,
            "Sneaking player's height: 1.8 unchanged, 1.65 (Bedrock -20.x)/(Java 1.9-1.13), 1.5 (modern)",
            "config.poses.sneak.height");
        sneak_biped_ratio = configuration.getFloat("sneak_biped_height", "poses.sneak",
            sneak_biped_ratio, 0.5F, 1.0F,
            "Biped sneak height (nonvanilla, experimental/buggy, 1.0 disables, 0.9 like 1.9-1.13 player, 0.83 like modern player)",
            "config.poses.sneak.height.ratio.mobs");


        enable_swim = configuration.getBoolean("enable_swim", "poses.swim",
            enable_swim, "Enable swimming (vanilla, 1.13+)", "config.poses.swim.enable");
        silly_swim = configuration.getBoolean("silly_swim", "poses.swim",
            silly_swim, "Vertical angle swimming accelerates rather than constant speed (nonvanilla)",
            "config.poses.swim.silly");

        swim_friction_multiplier = configuration.getFloat("swim_friction_multiplier", "poses.swim",
            swim_friction_multiplier,  Float.MIN_VALUE, Float.MAX_VALUE, "Swim friction multiplier (~.75 seems vanilla)",
            "config.poses.swim.friction.multiplier");
        swim_y_multiplier = configuration.getFloat("swim_y_multiplier", "poses.swim",
            swim_y_multiplier, Float.MIN_VALUE, Float.MAX_VALUE, "Swim y-velocity multiplier (~.25 seems like vanilla)",
            "config.poses.swim.y.multiplier");
        prevent_swim_riptide_posemove = configuration.getBoolean("prevent_swim_riptide_posemove", "poses.swim",
            prevent_swim_riptide_posemove, "If true, only crawl will immediately lower hitbox.",
            "config.poses.swimtide.ydrop.disable");

        ladder_fastfall_pitch = configuration.getFloat("ladder_fastfall_pitch", "ladders",
            ladder_fastfall_pitch, -91, 91, "Minimum angle below which to fastfall on ladders, in degrees. Remember, positive is looking down. (nonvanilla)",
            "config.ladder.fastfall.pitch");

        enable_conduit = configuration.getBoolean("enable_conduit", "enable.block",
            enable_conduit, "Enable conduits (vanilla, 1.13+)", "config.enable.blocks.conduit");

        enable_shields = configuration.getBoolean("enable_shields", "enable.item",
            enable_shields, "Enable shields (Java Edition 1.9+)", "config.enable.items.shield");
        enable_spears = configuration.getBoolean("enable_spears", "enable.item",
            enable_spears, "Enable spears (nonvanilla)", "config.enable.items.spears");

        enable_cartography = configuration.getBoolean("enable_cartography", "enable.block",
            enable_cartography, "Enable Cartography Table (Village&Pillage+)", "config.enable.blocks.maptable");
        enable_table_mapmaking = configuration.getBoolean("enable_table_mapmaking", "enable.craft",
            enable_table_mapmaking, "Enable making maps on Cartography Table (Bedrock)", "config.enable.craft.tablemaps");
        enable_anvil_mapwork = configuration.getBoolean("enable_anvil_mapwork", "enable.craft",
            enable_anvil_mapwork, "Enable map recipes on anvil (Bedrock)", "config.enable.craft.anvilmaps");
        enable_dumb_maps = configuration.getBoolean("enable_dumb_maps", "enable.item",
            enable_dumb_maps, "Enable no-compass maps that don't show players (like Bedrock)", "config.enable.items.dumbmaps");
        enable_deco_maps = configuration.getBoolean("enable_deco_maps", "enable.item",
            enable_deco_maps, "Enable marking maps (nonvanilla)", "config.enable.map.marking");

        map_anvil_recipe_levelcost = configuration.getInt("map_anvil_recipe_levelcost", "anvil",
            map_anvil_recipe_levelcost, 0, 39, "Map anvil recipe cost. 0 (Bedrock default) has a mixin.", "config.anvil.maps.levelcost");

        guard_anvil_left = configuration.getBoolean("guard_anvil_left", "anvil",
            guard_anvil_left, "Drop stack of N-1 from left anvil input if it wasn't single (bugfix)", "config.anvil.bugfix.leftstacks");
        allow_free_anvil_recipes_general = configuration.getBoolean("allow_free_anvil_recipes_general", "anvil",
            allow_free_anvil_recipes_general, "Allow taking result on anvil if no level cost for anything (?)", "config.anvil.zerocost_recipes");

        locate_permission_level = configuration.getInt("locate_permission_level", "commands",
            locate_permission_level, 0, 5, "Permission level for /locate (0-4. 2 vanilla. 5 prevents any use)", "config.commands.locate.permissionlevel");
        locatebiome_permission_level = configuration.getInt("locatebiome_permission_level", "commands",
            locatebiome_permission_level, 0, 5, "Permission level for /locatebiome (0-4. 2 vanilla. 5 prevents any use)",
            "config.commands.locatebiome.permissionlevel");
        locate_default_radius = configuration.getInt("locate_default_radius", "commands",
            locate_default_radius, 0, Integer.MAX_VALUE, "Default chunk radius for /locate",
            "config.commands.locate.radius.default");
        locatebiome_default_radius = configuration.getInt("locatebiome_default_radius", "commands",
            locatebiome_default_radius, 0, Integer.MAX_VALUE, "Default chunk radius for /locatebiome",
            "config.commands.locatebiome.radius.default");

        givemapto_permission_level = configuration.getInt("givemapto_permission_level", "commands",
            givemapto_permission_level, 0, 5, "Permission level for /givemapto (0-4. nonvanilla. 5 prevents any use)",
            "config.commands.givemapto.permissionlevel");

        buried_treasure_x = configuration.getInt("buried_treasure_x", "worldgen.structures",
            buried_treasure_x, 0, 15, "X position in chunk of buried treasure (defaults to 9 for Java. 8 is Bedrock)",
            "config.worldgen.treasure.fine_x");
        buried_treasure_y_depth_max = configuration.getInt("buried_treasure_y_depth_max", "worldgen.structures",
            buried_treasure_y_depth_max, 1, 500, "Max (Y) depth from surface at coordinate treasure gets buried",
            "config.worldgen.treasure.depth_y");
        buried_treasure_z = configuration.getInt("buried_treasure_z", "worldgen.structures",
            buried_treasure_z, 0, 15, "Z position in chunk of buried treasure (defaults to 9 for Java. 8 is Bedrock)",
            "config.worldgen.treasure.fine_z");
        buried_treasure_chance_denominator = configuration.getInt("buried_treasure_chance_denominator", "worldgen.structures",
            buried_treasure_chance_denominator, 1, Integer.MAX_VALUE, "Denominator for buried treasure chance.",
            "config.worldgen.treasure.chance.denominator");
        buried_treasure_chance_numerator = configuration.getInt("buried_treasure_chance_numerator", "worldgen.structures",
            buried_treasure_chance_numerator, 0, Integer.MAX_VALUE, "Numerator for buried treasure chance.",
            "config.worldgen.treasure.chance.numerator");
        generate_buried_treasure = configuration.getBoolean("generate_buried_treasure", "worldgen.structures",
            generate_buried_treasure, "Generate buried treasure by default in overworld (dimension 0)? (vanilla true)",
            "config.worldgen.treasure.enable");

        fish_spawn_y_min = configuration.getInt("fish_spawn_y_min", "fish.spawn",
            50, world_height_min, world_height_max, "Minimum height(Y) for natural-spawning fish",
            "config.fish.spawn.y.min");
        fish_spawn_y_max = configuration.getInt("fish_spawn_y_max", "fish.spawn",
            50, world_height_min, world_height_max, "Maximum height(Y) for natural-spawning fish",
            "config.fish.spawn.y.max");
        fish_spawn_dist_min = configuration.getFloat("fish_spawn_dist_min", "fish.spawn",
            20, 0, Float.MAX_VALUE, "Minimum distance from closest player to spawn fish",
            "config.fish.spawn.r.min");
        fish_spawn_dist_max = configuration.getFloat("fish_spawn_dist_max", "fish.spawn",
            64, 0, Float.MAX_VALUE, "Maximum distance from closest player to spawn fish",
            "config.fish.spawn.r.max");
        fish_spawn_weirdwater = configuration.getBoolean("fish_spawn_weirdwater", "fish.spawn",
            fish_spawn_weirdwater, "Fish can spawn in non-water waterblocks (e.g. kelp, bubble columns if added)",
            "config.fish.spawn.weirdwater");
        fish_despawn_dist_must = configuration.getFloat("fish_despawn_dist_must", "fish.despawn",
            64, 0, Float.MAX_VALUE, "Minimum distance from player at which [non-persistent] fish instantly despawn",
            "config.fish.despawn.r.all");
        fish_despawn_dist_may = configuration.getFloat("fish_despawn_dist_must", "fish.despawn",
            40, 0, Float.MAX_VALUE, "Minimum distance from player at which [non-persistent] fish randomly despawn",
            "config.fish.despawn.r.some");
        fish_despawn_dist_may_sq = fish_despawn_dist_may * fish_despawn_dist_may;

        fishbone_drop_bedrock = configuration.getBoolean("fishbone_drop_bedrock", "fish.drops",
            fishbone_drop_bedrock, "Whether fish drop bonemeal more-often and Lootable like Bedrock",
            "config.fish.drops.bedrocklike");

        can_bottle_lightning = configuration.getBoolean("can_bottle_lightning", "magic",
            can_bottle_lightning, "Whether you may bottle lightning (famously difficult. Nonvanilla)",
            "config.magic.bottle.lightning");
        glass_bottle_lightning = configuration.getBoolean("glass_bottle_lightning", "magic",
            glass_bottle_lightning, "Whether glass bottles as well as bottles o' enchanting can bottle lightning",
            "config.magic.bottle.lightning.glass");

        if (configuration.hasChanged()) {
            configuration.save();
        }
    }
}
