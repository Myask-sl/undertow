package invalid.myask.undertow.util;

import com.google.common.collect.ImmutableSet;
import java.util.*;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.structure.MapGenScatteredFeature;
import net.minecraft.world.gen.structure.MapGenStructure;

import invalid.myask.undertow.Config;
import invalid.myask.undertow.ducks.IMapGenStructure_Accessor;

import static net.minecraft.util.MathHelper.abs;

public class UndertowUtils {

    private static Map<String, MapGenStructure> structureTypeMap = new HashMap<>();
    private static List<String> structureNameList = new ArrayList<>();
    private static final Map<String, BiomeGenBase> templeBiomeMap = new HashMap<>();

    public static void noteStructGen(MapGenStructure mGS) {
        String s = mGS.func_143025_a(),
            modId = ((IMapGenStructure_Accessor)mGS).undertow$getModId(),
            withMod = modId + ":" + s;
        structureTypeMap.put(s, mGS);
        structureTypeMap.put(withMod, mGS);
        structureNameList.add(s);
        structureNameList.add(withMod);
        if (modId.equals("minecraft") && mGS instanceof MapGenScatteredFeature && !structureTypeMap.containsKey("templeDesert")) {
            structureTypeMap.put("templeDesert", mGS);
            structureTypeMap.put("templeJungle", mGS);
            structureTypeMap.put("witchHutSwamp", mGS);
            templeBiomeMap.put("templeDesert", BiomeGenBase.desert);
            templeBiomeMap.put("templeJungle", BiomeGenBase.jungle);
            templeBiomeMap.put("witchHutSwamp", BiomeGenBase.swampland);
            //Intentionally no Temple mapping here so it returns null so biomematch will find all three.
            structureNameList.addAll(templeBiomeMap.keySet());
        }
    }

    public static boolean structureNamesContain(String name) {
        return structureNameList.contains(name);
    }
    public static MapGenStructure getStructureByName(String name) {
        return structureTypeMap.get(name);
    }
    public static void structureTabCompletes(ArrayList<String> result, String lastarg) {
        for (String s : UndertowUtils.structureNameList) {
            if (s.startsWith(lastarg) || s.toLowerCase().startsWith(lastarg.toLowerCase())) result.add(s);
        }
    }
    public static BiomeGenBase getTempleBiomeByName(String arg) {
        return templeBiomeMap.get(arg);
    }

    /**
     * Checks if rained on.
     * @param poorSap whom to check
     * @return true if rained on
     */
    public static boolean rainCheck(Entity poorSap) {
        World w = poorSap.worldObj;
        int x = (int)poorSap.posX, y = (int)poorSap.posY, z = (int)poorSap.posZ;
        double top = (poorSap.getBoundingBox() == null) ? poorSap.posY - poorSap.yOffset + poorSap.height :
            poorSap.getBoundingBox().maxY;
        BiomeGenBase b = w.getBiomeGenForCoords(x, z);
        return w.isRaining() && b.canSpawnLightningBolt() // returns "doesn't auto-snow" && "can rain" anded biome flags
            && b.getFloatTemperature(x, y, z) > 0.15 //warm enough to not snow
            && w.getPrecipitationHeight(x,z) - .5 < top;
    }

    /**
     * Checks if rained on.
     * @param w world
     * @param x x
     * @param y y
     * @param z z
     * @return if currently rained on
     */
    public static boolean rainCheck(World w, int x, int y, int z) {
        if (w == null) return false;
        BiomeGenBase b = w.getBiomeGenForCoords(x, z);
        return w.isRaining() && b.canSpawnLightningBolt() // returns "doesn't auto-snow" && "can rain" anded biome flags
            && b.getFloatTemperature(x, y, z) > 0.15 //warm enough to not snow
            && w.getPrecipitationHeight(x,z) <= y;
    }

    public static int topSolidBlock (World w, int x, int z) {
        if (w == null) return Config.world_height_min - 1;
        int y = w.getPrecipitationHeight(x, z);
        while (y >= Config.world_height_min && (w.getBlock(x, y, z).getMaterial() == Material.water ||
            w.getBlock(x, y, z).getMaterial() == Material.lava || w.isAirBlock(x,y,z))) {
            y--;
        }
        return y;
    }

    public static final Set<Class<? extends EntityLivingBase>> bipeds = ImmutableSet.of(EntityWitch.class,
        EntitySkeleton.class, EntityZombie.class, EntityGiantZombie.class, EntityEnderman.class, EntityVillager.class);

    public static boolean isBiped(EntityLivingBase elba) {
        return bipeds.contains(elba.getClass());
    }

    /*
     * Locator function.
     * @param x in meters-coordinate of searched-from location
     * @param z in meters
     * @param radius in chunk * stepsize. Other locate versions call this "attempts" for some reason.
     * @param explored look in generated
     * @param unexplored look in ungenerated
     * @param theTest test bifunction is what you're looking for at chunkcoords(x,z)?
     * @return Nearest (by one axis, not absolute) location of thing.
     */ /*
    private static ChunkCoordIntPair locateAny(int x, int z, int radius, boolean explored, boolean unexplored, BiFunction<Integer, Integer, Boolean> theTest) {

        return null;
    } */

    /**
     * Returns nearest struct or null on failure. Basically later versions' /locate.
     *
     * @param struct     to find
     * @param inX        in meters-coordinate of searched-from location
     * @param inZ        in meters
     * @param radius     in chunk * stepsize. Other locate versions call this "attempts" for some reason.
     * @param explored   look in generated
     * @param unexplored look in ungenerated
     * @param biome
     * @return the chunk coordinate pair of where it found it, or null on no match
     */
    public static ChunkCoordIntPair locateStructure(MapGenStructure struct, World inW, int inX, int inZ, int radius,
                                                    boolean explored, boolean unexplored, BiomeGenBase biome) {
        if (!explored && !unexplored) return null; //Can't find anything outside the set of (p & !p)
        int chunX = inX >> 4, chunZ = inZ >> 4;

//        struct.func_151539_a(inW.getChunkProvider(), inW, chunX, chunZ, null); //Something's desynched sometimes between this and gen. FIXME

        for (int sqRad = 0; sqRad <= radius; sqRad++) {
            boolean xEdge = false, zEdge = false;
            for (int x = -sqRad; x <= sqRad; x++) {
                xEdge = abs(x) == sqRad;
                for (int z = -sqRad; z <= sqRad; z++) {
                    zEdge = abs(z) == sqRad;
                    int cX = chunX + x, cZ = chunZ + z;
                    if (!xEdge && !zEdge) continue; //already checked interior on prior radius pass
                    boolean chunkExplored = inW.checkChunksExist(cX << 4, 64, cZ << 4, cX << 4, 64, cZ << 4);
                    if (chunkExplored ? !explored : !unexplored) continue; //doesn't match filter
                    if (((IMapGenStructure_Accessor)struct).undertow$canSpawnStructureAtCoords(cX, cZ)
                        && matchBiome(inW, cX, cZ, biome))
                        return new ChunkCoordIntPair(cX, cZ);
                }
            }
        }
        return null;
        // return locateAny(inX, inZ, radius, explored, unexplored, (Integer cX, Integer cZ) -> ((IMapGenStructure_Accessor)struct).undertow$canSpawnStructureAtCoords(cX, cZ));
    }

    /**
     * A function to check if biome matches temple biome's type. Null for anybiome.
     * @param inW world
     * @param cX chunk X
     * @param cZ chunk Z
     * @param biome target biome, null for wildcard
     * @return if matches
     */
    private static boolean matchBiome(World inW, int cX, int cZ, BiomeGenBase biome) {
        if (biome == null) return true;
        BiomeGenBase worldBiome = inW.getBiomeGenForCoords(cX << 4, cZ << 4);
        if (worldBiome == biome) return true;
        if (biome == BiomeGenBase.jungle && worldBiome == BiomeGenBase.jungleHills) return true;
        if (biome == BiomeGenBase.desert && worldBiome == BiomeGenBase.desertHills) return true;
        return false;
    }

    public static ChunkCoordIntPair locateBiome(BiomeGenBase biome, World inW, int inX, int inZ, int radius, boolean explored, boolean unexplored) {
        if (!explored && !unexplored) return null; //Can't find anything outside the set of (p & !p)
        int chunX = inX >> 4, chunZ = inZ >> 4;
        for (int sqRad = 0; sqRad <= radius; sqRad++) {
            boolean xEdge = false, zEdge = false;
            for (int x = -sqRad; x <= sqRad; x++) {
                xEdge = abs(x) == sqRad;
                for (int z = -sqRad; z <= sqRad; z++) {
                    zEdge = abs(z) == sqRad;
                    int cX = chunX + x, cZ = chunZ + z;
                    if (!xEdge && !zEdge) continue; //already checked interior on prior radius pass
                    boolean chunkExplored = inW.checkChunksExist(cX << 4, 64, cZ << 4, cX << 4, 64, cZ << 4);
                    if (chunkExplored ? !explored : !unexplored) continue; //doesn't match filter
                    if (inW.getBiomeGenForCoordsBody(cX << 4, cZ << 4) == biome)
                        return new ChunkCoordIntPair(cX, cZ);
                }
            }
        }
        return null;
        // return locateAny(inX, inZ, radius, explored, unexplored, (Integer bX, Integer bZ) -> (inW.getBiomeGenForCoordsBody(bX << 4, bZ << 4) == biome));
    }

    public static String broadenBoolString(String allegedlyBoolean) {
        if ("t".equalsIgnoreCase(allegedlyBoolean) || "y".equalsIgnoreCase(allegedlyBoolean)) return "true";
        if ("f".equalsIgnoreCase(allegedlyBoolean) || "n".equalsIgnoreCase(allegedlyBoolean)) return "false";
        return allegedlyBoolean;
    }

    public static Integer firstUnusedBiomeID(boolean skipVanilla) {
        BiomeGenBase[] biomeArr = BiomeGenBase.getBiomeGenArray();
        int i;
        for (i = skipVanilla ? 40 : 0; i < biomeArr.length; i++) {
            if (biomeArr[i] == null)
                break;
        }
        if (i == biomeArr.length) return null;
        else return i;
    }

    public static Vec3 scalarMult(Vec3 LHS, double scalar) {
        return Vec3.createVectorHelper(LHS.xCoord * scalar, LHS.yCoord * scalar, LHS.zCoord * scalar);
    }
}
