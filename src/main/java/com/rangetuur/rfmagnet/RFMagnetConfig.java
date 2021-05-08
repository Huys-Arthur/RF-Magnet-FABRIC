package com.rangetuur.rfmagnet;

import reborncore.common.config.Config;

public class RFMagnetConfig {

    //magnets
    @Config(config = "magnets", category = "general", key = "magnet always works", comment = "If false the magnet only work when holding in your hands else if true the magnets works when in inventory")
    public static boolean magnet_always_works = false;

    @Config(config = "magnets", category = "capacity", key = "basic magnet", comment = "This value determines how big the capacity of the basic magnet is")
    public static int capacity_basic_magnet = 10_000;

    @Config(config = "magnets", category = "capacity", key = "advanced magnet", comment = "This value determines how big the capacity of the advanced magnet is")
    public static int capacity_advanced_magnet = 60_000;

    @Config(config = "magnets", category = "range", key = "basic magnet", comment = "This value determines how big the range of the basic magnet is")
    public static int range_basic_magnet = 6;

    @Config(config = "magnets", category = "range", key = "advanced magnet", comment = "This value determines how big the range of the advanced magnet is")
    public static int range_advanced_magnet = 8;

    //blocks
    @Config(config = "blocks", category = "disable", key = "magnet jar", comment = "If false the magnet jar is not disabled else if true the magnet jar is disabled")
    public static boolean disable_magnet_jar = false;

    //@Config(config = "blocks", category = "magnet jar", key = "disable with redstone", comment = "If false the magnet jar can not be disabled with redstone else if true the magnet jar can be disabled with redstone (when the magnet jar is disabled it can not attract items anymore but it can still charge)")
    //public static boolean disable_with_redstone_magnet_jar = true;

    @Config(config = "blocks", category = "magnet jar", key = "generate energy from lava", comment = "If false the magnet jar can not generate energy else if true the magnet jar can generate energy from lava: place lava on top of magnet jar and it will generate 8 EU/t when magnet is inside (this can be useful when you do not want to use another mod to generate energy)")
    public static boolean generate_energy_magnet_jar = false;

    @Config(config = "blocks", category = "magnet jar", key = "generate EU/t", comment = "This value determines how match energy the magnet jar generates when there is lava above")
    public static int generate_amount_magnet_jar = 8;

}
