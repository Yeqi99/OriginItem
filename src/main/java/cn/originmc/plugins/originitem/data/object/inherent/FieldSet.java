package cn.originmc.plugins.originitem.data.object.inherent;

import cn.originmc.plugins.origincore.util.item.DataType;
import cn.originmc.plugins.origincore.util.item.Item;
import cn.originmc.plugins.origincore.util.random.Randomizer;
import cn.originmc.plugins.origincore.util.text.FormatText;
import cn.originmc.plugins.originitem.OriginItem;
import cn.originmc.plugins.originitem.data.object.field.Field;
import cn.originmc.plugins.originitem.function.FieldManager;
import org.bukkit.inventory.ItemStack;

public class FieldSet {
    private String fieldId;
    private String value;
    private double chance;
    public FieldSet(FormatText formatText){
        setFieldId(formatText.getValue("field"));
        setValue(formatText.getValue("value"));
        String chance= formatText.getValue("chance");
        if (chance!=null){
            setChance(Double.parseDouble(chance));
        }
    }
    public ItemStack give(ItemStack inItem){
        Field field= FieldManager.getField(getFieldId());
        return field.getNbt().give(inItem, value);
    }
    public ItemStack randomGive(ItemStack inItem){
        if (Randomizer.randomBoolean(chance,2)){
            return give(inItem);
        }else {
            return inItem;
        }
    }
    public ItemStack add(ItemStack inItem){
        Field field= FieldManager.getField(getFieldId());
        return field.getNbt().add(inItem,value);
    }

    public String getFieldId() {
        return fieldId;
    }
    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public double getChance() {
        return chance;
    }

    public void setChance(double chance) {
        this.chance = chance;
    }
}
