package invalid.myask.undertow.util;

import com.mojang.realmsclient.util.Pair;
import invalid.myask.undertow.UndertowItems;
import invalid.myask.undertow.entities.fish.EntityGiantClownfish;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class BucketableMobsDict {
    private static final Map<Pair<Item, Class<? extends Entity>>, ItemStack> mobsAndTheirBuckets = new HashMap<>();

    public static void addBucketing(Item emptyBucket, Class<? extends Entity> mobClazz, ItemStack mobbedBucket) {
        mobsAndTheirBuckets.put(Pair.of(emptyBucket, mobClazz), mobbedBucket);
    }

    public static ItemStack getBucketed(Item emptyBucket, Class<? extends Entity> mobClazz) {
        return mobsAndTheirBuckets.get(Pair.of(emptyBucket, mobClazz));
    }

    public static void initialize() {
        addBucketing(Items.water_bucket, EntityGiantClownfish.class, new ItemStack (UndertowItems.BUCKET_OF_GIANT_CLOWNFISH));
    }
}
