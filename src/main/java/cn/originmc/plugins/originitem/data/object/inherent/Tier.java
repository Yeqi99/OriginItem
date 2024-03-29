package cn.originmc.plugins.originitem.data.object.inherent;

import cn.originmc.plugins.origincore.util.random.Randomizer;
import cn.originmc.plugins.originitem.data.object.item.InstanceItem;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Tier {
    private int index;
    private String name;
    private String color;
    private List<String> prefixList=new ArrayList<>();
    private int weight;
    private Attributes attributes;
    private TierLevelSetting tierLevelSetting;
    public ItemStack randomGive(ItemStack itemStack,int lvl){
        InstanceItem instanceItem=new InstanceItem(itemStack);
        instanceItem.addSpace("ITEM_TIER");
        instanceItem.set("index",getIndex(),"ITEM_TIER");
        instanceItem.set("prefix",getPrefixList().get(Randomizer.getRandom(0,getPrefixList().size()-1)),"ITEM_TIER");
        instanceItem.setItemStack(attributes.randomGive(instanceItem.getItemStack()));
        instanceItem.upLevel(lvl);
        return instanceItem.getItemStack();
    }
    public ItemStack randomGive(ItemStack itemStack,int lvl,Attributes publicAttributes){
        InstanceItem instanceItem=new InstanceItem(itemStack);
        instanceItem.addSpace("ITEM_TIER");
        instanceItem.set("index",getIndex(),"ITEM_TIER");
        instanceItem.set("prefix",getPrefixList().get(Randomizer.getRandom(0,getPrefixList().size()-1)),"ITEM_TIER");
        instanceItem.setItemStack(publicAttributes.randomGive(instanceItem.getItemStack()));
        instanceItem.set("level",0,"ITEM_FORMAT");
        instanceItem.setItemStack(attributes.randomGive(instanceItem.getItemStack()));
        instanceItem.upLevel(lvl);
        return instanceItem.getItemStack();
    }
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<String> getPrefixList() {
        return prefixList;
    }

    public void setPrefixList(List<String> prefixList) {
        this.prefixList = prefixList;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }


    public Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

    public TierLevelSetting getTierLevelSetting() {
        return tierLevelSetting;
    }

    public void setTierLevelSetting(TierLevelSetting tierLevelSetting) {
        this.tierLevelSetting = tierLevelSetting;
    }
}
