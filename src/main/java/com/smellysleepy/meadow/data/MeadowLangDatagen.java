package com.smellysleepy.meadow.data;

import com.smellysleepy.meadow.MeadowMod;
import com.smellysleepy.meadow.common.block.mineral.MineralFloraRegistryBundle;
import com.smellysleepy.meadow.registry.common.*;
import com.smellysleepy.meadow.registry.common.block.MeadowBlockRegistry;
import com.smellysleepy.meadow.registry.common.item.MeadowItemRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.world.effect.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.level.block.*;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.*;
import team.lodestar.lodestone.helpers.DataHelper;

import java.util.HashSet;
import java.util.function.*;

public class MeadowLangDatagen extends LanguageProvider {
    public MeadowLangDatagen(PackOutput gen) {
        super(gen, MeadowMod.MEADOW, "en_us");
    }

    @Override
    protected void addTranslations() {
        var blocks = new HashSet<>(MeadowBlockRegistry.BLOCKS.getEntries());
        var items = new HashSet<>(MeadowItemRegistry.ITEMS.getEntries());
        var effects = new HashSet<>(MeadowMobEffectRegistry.EFFECTS.getEntries());

        DataHelper.takeAll(items, i -> i.get() instanceof BlockItem && !(i.get() instanceof ItemNameBlockItem));
        DataHelper.takeAll(blocks, i -> i.get() instanceof WallSignBlock);

        addPastryNamings(items, MineralFloraRegistry.AMETHYST_FLORA, "Amethyst Crunch", "Amethyst Flakes");
        addPastryNamings(items, MineralFloraRegistry.COAL_FLORA, "Carbon Drip", "Coal Doughnut");
        addPastryNamings(items, MineralFloraRegistry.LAPIS_FLORA, "Lazuli Chewy", "Lazuli Cake");
        addPastryNamings(items, MineralFloraRegistry.REDSTONE_FLORA, "Mumbo Jawbreaker", "Jumbo Cake");
        addPastryNamings(items, MineralFloraRegistry.COPPER_FLORA, "Cuprum Caramel", "Cuprum Cookie");
        addPastryNamings(items, MineralFloraRegistry.IRON_FLORA, "Ferric Hard-Pop", "Ferric Waffle");
        addPastryNamings(items, MineralFloraRegistry.GOLD_FLORA, "Sacchra Melty", "Sacchra Regia");
        addPastryNamings(items, MineralFloraRegistry.DIAMOND_FLORA, "Fortune Taffy", "Fortune Cookie");
        addPastryNamings(items, MineralFloraRegistry.EMERALD_FLORA, "Beryl Sour-Pop", "Choco-Beryl");
        addPastryNamings(items, MineralFloraRegistry.NETHERITE_FLORA, "Ancient Nut", "Ancient Pretzel");

        blocks.forEach(b -> {
            String name = b.get().getDescriptionId().replaceFirst("block\\.meadow\\.", "");
            name = DataHelper.toTitleCase(correctItemName(name), "_");
            addBlock(b, name);
        });

        items.forEach(i -> {
            String name = i.get().getDescriptionId().replaceFirst("item\\.meadow\\.", "");
            name = DataHelper.toTitleCase(correctItemName(name), "_");
            addItem(i, name);
        });

        effects.forEach(e -> {
            String name = DataHelper.toTitleCase(makeProperEnglish(e.getId().getPath()), "_");
            add("effect.meadow." + ForgeRegistries.MOB_EFFECTS.getKey(e.get()).getPath(), name);
        });

        add("itemGroup.meadow.meadow_grove", "Meadow: Hidden Grove");
        add("itemGroup.meadow.mineral_flora", "Meadow: Mineralized Flora");

        addJEEDEffectDescription(MeadowMobEffectRegistry.AMETHYST_FRUIT_EFFECT, "The tasty sensation of amethyst makes you more sneaky, potentially preventing the activation of nearny sculk sensors.");
        addJEEDEffectDescription(MeadowMobEffectRegistry.COPPER_FRUIT_EFFECT, "The unusual sensation of copper makes you a conduit of oxidization, right click blocks to oxidize them.");
        addJEEDEffectDescription(MeadowMobEffectRegistry.IRON_FRUIT_EFFECT, "The sour sensation of iron makes you tougher, increasing armor and armor toughness.");
        addJEEDEffectDescription(MeadowMobEffectRegistry.GOLD_FRUIT_EFFECT, "The melty sensation of gold brings you plunder, increasing your combat looting.");
        addJEEDEffectDescription(MeadowMobEffectRegistry.DIAMOND_FRUIT_EFFECT, "The luxurious sensation of diamond brings you riches, increasing your mining fortune.");
        addJEEDEffectDescription(MeadowMobEffectRegistry.EMERALD_FRUIT_EFFECT, "The tempting sensation of emerald is known throughout the entire world, especially when it comes to wandering traders.");
        addJEEDEffectDescription(MeadowMobEffectRegistry.NETHERITE_FRUIT_EFFECT, "The prestigious sensation of netherite makes you tougher, increasing your armor, armor toughness, and providing resistance to fire and knockback. You could really rock the world with this candy.");
    }

    public void addPastryNamings(HashSet<RegistryObject<Item>> items, MineralFloraRegistryBundle mineralFlora, String candyName, String pastryName) {
        DataHelper.takeAll(items, mineralFlora.candyItem, mineralFlora.pastryItem);
        addItem(mineralFlora.candyItem, candyName);
        addItem(mineralFlora.pastryItem, pastryName);
    }
    public void addJEEDEffectDescription(Supplier<MobEffect> mobEffectSupplier, String description) {
        add(mobEffectSupplier.get().getDescriptionId() + ".description", description);
    }

    public String correctItemName(String name) {
        if (name.contains("music_disc")) {
            return "music_disc";
        }
        if ((!name.endsWith("_bricks"))) {
            if (name.contains("bricks")) {
                name = name.replaceFirst("bricks", "brick");
            }
        }
        if ((!name.endsWith("_boards"))) {
            if (name.contains("boards")) {
                name = name.replaceFirst("boards", "board");
            }
        }
        if (name.contains("_fence") || name.contains("_button")) {
            if (name.contains("planks")) {
                name = name.replaceFirst("_planks", "");
            }
        }
        return makeProperEnglish(name);
    }
    public String makeProperEnglish(String name) {
        String[] replacements = new String[]{"ns_", "rs_", "ts_"};
        String properName = name;
        for (String replacement : replacements) {
            int index = properName.indexOf(replacement);
            if (index != -1) {
                properName = properName.replaceFirst("s_", "'s_");
                break;
            }
        }
        return properName;
    }
}