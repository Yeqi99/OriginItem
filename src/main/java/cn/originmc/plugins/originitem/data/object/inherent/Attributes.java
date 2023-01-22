package cn.originmc.plugins.originitem.data.object.inherent;

import cn.originmc.plugins.origincore.util.text.FormatText;
import cn.originmc.plugins.originitem.OriginItem;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Attributes {
    private List<FieldSet> fieldSetList=new ArrayList<>();
    public Attributes(List<String> formatList){
        for (String s : formatList) {
            fieldSetList.add(new FieldSet(new FormatText(s)));
        }
    }
    public ItemStack randomGive(ItemStack inItem){
        for (FieldSet fieldSet : fieldSetList) {
            inItem= fieldSet.randomGive(inItem);
        }
        return inItem;
    }
    public ItemStack give(ItemStack inItem){
        for (FieldSet fieldSet : fieldSetList) {
            inItem= fieldSet.give(inItem);
        }
        return inItem;
    }
    public ItemStack add(ItemStack inItem,int amount){
        for (FieldSet fieldSet : fieldSetList) {
            inItem=fieldSet.add(inItem,amount);
        }
        return inItem;
    }
    public List<FieldSet> getFieldSetList() {
        return fieldSetList;
    }

    public void setFieldSetList(List<FieldSet> fieldSetList) {
        this.fieldSetList = fieldSetList;
    }
}
