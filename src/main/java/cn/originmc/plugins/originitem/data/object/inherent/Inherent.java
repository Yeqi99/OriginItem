package cn.originmc.plugins.originitem.data.object.inherent;

import cn.originmc.plugins.origincore.util.item.Item;
import cn.originmc.plugins.origincore.util.random.Randomizer;
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

    /**
     * 将内在模板赋予物品ItemStack
     * @param level 物品的等级
     * @param inItem 赋予前的物品
     * @param tierIndex 稀有度序号
     * @return 赋予内在模板后的物品
     */
    public ItemStack give(int level,ItemStack inItem,int tierIndex){
        if (tierIndex<0){
            tierIndex=0;
        }
        if (tierIndex>=tiers.size()){
            tierIndex=tiers.size()-1;
        }
        Tier tier= getTiers().get(tierIndex);
        Item item=new Item(inItem);
        if (!item.hasTag("ITEM_FORMAT")){
            item.addSpace("ITEM_FORMAT");
        }
        item.set("inherent",getId(),"ITEM_FORMAT");
        item.set("level",level,"ITEM_FORMAT");
        item.setItemStack(tier.randomGive(item.getItemStack(),level,attributes));
        return item.getItemStack();
    }
    public ItemStack give(int level,ItemStack inItem,int tierMinIndex,int tierMaxIndex){
        Item item=new Item(inItem);
        if (!item.hasTag("ITEM_FORMAT")){
            item.addSpace("ITEM_FORMAT");
        }
        Tier tier= randomTier(tierMinIndex,tierMaxIndex);
        item.set("inherent",getId(),"ITEM_FORMAT");
        item.set("level",level,"ITEM_FORMAT");
        item.setItemStack(tier.randomGive(item.getItemStack(),level,attributes));
        return item.getItemStack();
    }
    public ItemStack randomGive(int level,ItemStack inItem){
        Item item=new Item(inItem);
        if (!item.hasTag("ITEM_FORMAT")){
            item.addSpace("ITEM_FORMAT");
        }
        Tier tier=randomTier();
        item.set("inherent",getId(),"ITEM_FORMAT");
        item.set("level",level,"ITEM_FORMAT");
        item.setItemStack(tier.randomGive(item.getItemStack(),level,attributes));
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
    public Tier randomTier(int min,int max){
        Map<Tier,Integer> choiceMap=new HashMap<>();
        int allWeight=0;
        if (max<min){
            int temp=min;
            min=max;
            max=temp;
        }
        if (min<0){
            min=0;
        }
        if (max>=tiers.size()){
            max=tiers.size()-1;
        }
        for (Tier tier : tiers.subList(min, max - 1)) {
            allWeight+=tier.getWeight();
            choiceMap.put(tier,allWeight);
        }
        int randomNum= Randomizer.getRandom(0,allWeight);
        for (Tier tier : tiers.subList(min, max - 1)) {
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
