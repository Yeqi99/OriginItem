package cn.originmc.plugins.originitem.data.object.inherent;

import cn.originmc.plugins.origincore.util.item.Item;
import cn.originmc.plugins.origincore.util.random.Randomizer;
import cn.originmc.plugins.originitem.OriginItem;
import com.sun.management.VMOption;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Inherent {
    private String id;
    private int tierAmount;
    private int maxLevel;
    private int minLevel;
    private Attributes attributes;
    private List<Tier> tiers=new ArrayList<>();

    public ItemStack randomGive(int level,ItemStack inItem){
        inItem= attributes.randomGive(inItem);
        Tier tier=randomTier();
        inItem= tier.randomGive(inItem,level-minLevel);
        Item item=new Item(inItem);
        if (!item.hasTag("ITEM_FORMAT")){
            item.addSpace("ITEM_FORMAT");
        }
        item.set("inherent",getId(),"ITEM_FORMAT");
        item.set("level",level,"ITEM_FORMAT");
        return item.getItemStack();
    }
    public Tier randomTier(){
        Map<Tier,Integer> choiceMap=new HashMap<>();
        int allWeight=0;
        for (Tier tier : tiers) {
            allWeight+=tier.getWeight();
            choiceMap.put(tier,allWeight);
        }
        int randomNum= Randomizer.getRandom(0,allWeight);
        for (Tier tier : tiers) {
            if (choiceMap.get(tier)>=randomNum){
                return tier;
            }
        }
        return tiers.get(0);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getTierAmount() {
        return tierAmount;
    }

    public void setTierAmount(int tierAmount) {
        this.tierAmount = tierAmount;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    public int getMinLevel() {
        return minLevel;
    }

    public void setMinLevel(int minLevel) {
        this.minLevel = minLevel;
    }


    public List<Tier> getTiers() {
        return tiers;
    }

    public void setTiers(List<Tier> tiers) {
        this.tiers = tiers;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }
}