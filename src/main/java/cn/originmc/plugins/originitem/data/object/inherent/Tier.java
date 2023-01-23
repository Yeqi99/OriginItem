package cn.originmc.plugins.originitem.data.object.inherent;

import cn.originmc.plugins.origincore.util.item.Item;
import cn.originmc.plugins.origincore.util.random.Randomizer;
import cn.originmc.plugins.originitem.OriginItem;
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
        itemStack= attributes.randomGive(itemStack);
        if (getTierLevelSetting().getSpecialLvlPerAddAttributesMap().containsKey(lvl)){
            itemStack= getTierLevelSetting().getSpecialLvlPerAddAttributesMap().get(lvl).add(itemStack,1);
        }
        itemStack= getTierLevelSetting().getLvlPerAddAttributes().add(itemStack,lvl);
        Item item=new Item(itemStack);
        item.addSpace("ITEM_TIER");
        item.set("index",getIndex(),"ITEM_TIER");
        item.set("prefix",getPrefixList().get(Randomizer.getRandom(0,getPrefixList().size()-1)),"ITEM_TIER");
        return item.getItemStack();
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
