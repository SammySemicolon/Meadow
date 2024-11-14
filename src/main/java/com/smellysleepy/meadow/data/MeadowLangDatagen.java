package com.smellysleepy.meadow.data;

import com.smellysleepy.meadow.MeadowMod;
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
import java.util.Set;
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

        DataHelper.takeAll(blocks, i -> i.get() instanceof WallSignBlock);


        blocks.forEach(b -> {
            String name = b.get().getDescriptionId().replaceFirst("block\\.meadow\\.", "");
            name = DataHelper.toTitleCase(correctItemName(name), "_");
            add(b.get().getDescriptionId(), name);
        });

        DataHelper.takeAll(items, i -> i.get() instanceof BlockItem && !(i.get() instanceof ItemNameBlockItem));
        items.forEach(i -> {
            String name = i.get().getDescriptionId().replaceFirst("item\\.meadow\\.", "");
            name = DataHelper.toTitleCase(correctItemName(name), "_");
            add(i.get().getDescriptionId(), name);
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
        addJEEDEffectDescription(MeadowMobEffectRegistry.GOLD_FRUIT_EFFECT, "The melty sensation of gold makes you hastier, increasing attack and dig speed.");
        addJEEDEffectDescription(MeadowMobEffectRegistry.DIAMOND_FRUIT_EFFECT, "The luxurious sensation of diamond makes you richer, increasing your mining fortune.");
        addJEEDEffectDescription(MeadowMobEffectRegistry.EMERALD_FRUIT_EFFECT, "The tempting sensation of emerald makes you luckier, increasing your luck.");
        addJEEDEffectDescription(MeadowMobEffectRegistry.NETHERITE_FRUIT_EFFECT, "The prestigious sensation of netherite makes you tougher, increasing your armor, armor toughness, and providing resistance to fire and knockback. You could really rock the world with this candy.");

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