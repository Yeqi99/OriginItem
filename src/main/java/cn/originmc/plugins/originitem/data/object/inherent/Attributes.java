package cn.originmc.plugins.originitem.data.object.inherent;

import cn.originmc.plugins.origincore.util.item.Item;
import cn.originmc.plugins.origincore.util.random.Randomizer;
import cn.originmc.plugins.origincore.util.text.FormatText;
import cn.originmc.plugins.originitem.OriginItem;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Attributes {
    private List<FieldSet> fieldSetList=new ArrayList<>();
    private Map<String,FormatText> enchantmentMap=new HashMap<>();
    public Attributes(List<String> formatList){
        for (String s : formatList) {
            FormatText formatText=new FormatText(s);
            if(formatText.hasKey("enchantment")){
                enchantmentMap.put(formatText.getValue("enchantment"), formatText);
                continue;
            }
            fieldSetList.add(new FieldSet(formatText));
        }
    }
    public ItemStack randomGive(ItemStack inItem){
        for (FieldSet fieldSet : fieldSetList) {
            inItem= fieldSet.randomGive(inItem);
        }
        Item item=new Item(inItem);
        for (Map.Entry<String, FormatText> entry : enchantmentMap.entrySet()) {
            double chance= Double.parseDouble(entry.getValue().getValue("chance"));
            if (Randomizer.randomBoolean(chance,2)){
                String value=entry.getValue().getValue("level");
                if (value.contains("|")){
                    value = Randomizer.getRandomFromStr(value);
                }
                if (value.contains("-")){
                    value = Randomizer.getRandomFromSection(value,0);
                }
                item.setEnchantment(entry.getKey(),Integer.parseInt(value),Boolean.parseBoolean(entry.getValue().getValue("flag")));
            }
        }
        return item.getItemStack();
    }
    public ItemStack randomAdd(ItemStack inItem,int amount){
        for (FieldSet fieldSet : fieldSetList) {
            inItem= fieldSet.randomAdd(inItem,amount);
        }
        Item item=new Item(inItem);
        for (Map.Entry<String, FormatText> entry : enchantmentMap.entrySet()) {
            double chance= Double.parseDouble(entry.getValue().getValue("chance"));
            if (Randomizer.randomBoolean(chance,2)){
                String value=entry.getValue().getValue("level");
                if (value.contains("|")){
                    value = Randomizer.getRandomFromStr(value);
                }
                if (value.contains("-")){
                    value = Randomizer.getRandomFromSection(value,0);
                }
                item.setEnchantment(entry.getKey(),item.getEnchantmentsLevel(entry.getKey())+Integer.parseInt(value),Boolean.parseBoolean(entry.getValue().getValue("flag")));
            }
        }
        return item.getItemStack();
    }
    public ItemStack give(ItemStack inItem){
        for (FieldSet fieldSet : fieldSetList) {
            inItem= fieldSet.give(inItem);
        }
        Item item=new Item(inItem);
        for (Map.Entry<String, FormatText> entry : enchantmentMap.entrySet()) {
            String value=entry.getValue().getValue("level");
            if (value.contains("|")){
                value = Randomizer.getRandomFromStr(value);
            }
            if (value.contains("-")){
                value = Randomizer.getRandomFromSection(value,0);
            }
            item.setEnchantment(entry.getKey(),Integer.parseInt(value),Boolean.parseBoolean(entry.getValue().getValue("flag")));
        }
        return item.getItemStack();
    }
    public ItemStack add(ItemStack inItem,int amount){
        for (FieldSet fieldSet : fieldSetList) {
            inItem=fieldSet.add(inItem,amount);
        }
        Item item=new Item(inItem);
        for (Map.Entry<String, FormatText> entry : enchantmentMap.entrySet()) {
            String value=entry.getValue().getValue("level");
            if (value.contains("|")){
                value = Randomizer.getRandomFromStr(value);
            }
            if (value.contains("-")){
                value = Randomizer.getRandomFromSection(value,0);
            }
            item.setEnchantment(entry.getKey(),item.getEnchantmentsLevel(entry.getKey())+Integer.parseInt(value),Boolean.parseBoolean(entry.getValue().getValue("flag")));
        }
        return item.getItemStack();
    }
    public List<FieldSet> getFieldSetList() {
        return fieldSetList;
    }

    public void setFieldSetList(List<FieldSet> fieldSetList) {
        this.fieldSetList = fieldSetList;
    }

    public Map<String, FormatText> getEnchantmentMap() {
        return enchantmentMap;
    }

    public void setEnchantmentMap(Map<String, FormatText> enchantmentMap) {
        this.enchantmentMap = enchantmentMap;
    }
}
