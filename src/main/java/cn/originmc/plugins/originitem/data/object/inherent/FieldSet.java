package cn.originmc.plugins.originitem.data.object.inherent;

import cn.originmc.plugins.origincore.hook.PlaceholderAPIHook;
import cn.originmc.plugins.origincore.util.list.ListUtil;
import cn.originmc.plugins.origincore.util.random.Randomizer;
import cn.originmc.plugins.origincore.util.text.FormatText;
import cn.originmc.plugins.originitem.OriginItem;
import cn.originmc.plugins.originitem.data.object.field.Field;
import cn.originmc.plugins.originitem.function.FieldManager;
import cn.originmc.plugins.originitem.util.VariableUtil;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FieldSet {
    private FormatText fieldSetFT;
    public FieldSet(FormatText formatText){
        setFieldSetFT(formatText);
    }
    public ItemStack give(ItemStack inItem){
        FormatText clone=new FormatText(VariableUtil.getVarString(getFieldSetFT().getFormatString(),inItem));
        if (getFieldSetFT().hasKey("fields")){
            int libSize= Integer.parseInt(clone.getValue("fields"));
            int outSize= Integer.parseInt(clone.getValue("output"));
            Map<Object,Double> chanceMap=new HashMap<>();
            for (int i=0;i<libSize;i++){
                List<String> setting= ListUtil.getListFromFormatStr(clone.getValue("field-"+i));
                String id=setting.get(0);
                FieldSet fieldSet=new FieldSet(new FormatText("field^"+id+"`value^"+setting.get(1)));
                double weight= Double.parseDouble(setting.get(2));
                chanceMap.put(fieldSet,weight);
            }
            for (Object object : Randomizer.randomObjects(chanceMap, outSize)) {
                FieldSet fieldSet= (FieldSet) object;
                inItem= fieldSet.give(inItem);
            }
            return inItem;
        }
        String randomFieldId=clone.getValue("field");
        String valueClone=clone.getValue("value");
        if (randomFieldId.contains("|")){
            randomFieldId = Randomizer.getRandomFromStr(randomFieldId);
            valueClone= clone.getValue(randomFieldId+"-value");
        }
        Field field= FieldManager.getField(randomFieldId);
        if (field==null){
            return inItem;
        }
        return field.getNbt().give(inItem, valueClone);
    }
    public ItemStack randomGive(ItemStack inItem){
        FormatText clone=new FormatText(VariableUtil.getVarString(getFieldSetFT().getFormatString(),inItem));
        if (!clone.hasKey("chance")){
            return give(inItem);
        }else {
            if (Randomizer.randomBoolean(Double.parseDouble(clone.getValue("chance")),2)){
                return give(inItem);
            }else {
                return inItem;
            }
        }
    }
    public ItemStack randomAdd(ItemStack inItem,int amount){
        FormatText clone=new FormatText(VariableUtil.getVarString(getFieldSetFT().getFormatString(),inItem));
        if (!clone.hasKey("chance")){
            return add(inItem,amount);
        }else {
            if (Randomizer.randomBoolean(Double.parseDouble(clone.getValue("chance")),2)){
                return add(inItem,amount);
            }else {
                return inItem;
            }
        }
    }
    public ItemStack add(ItemStack inItem,int amount){
        FormatText clone=new FormatText(VariableUtil.getVarString(getFieldSetFT().getFormatString(),inItem));
        if (getFieldSetFT().hasKey("fields")){
            int libSize= Integer.parseInt(clone.getValue("fields"));
            int outSize= Integer.parseInt(clone.getValue("output"));
            Map<Object,Double> chanceMap=new HashMap<>();
            for (int i=0;i<libSize;i++){
                List<String> setting= ListUtil.getListFromFormatStr(clone.getValue("field-"+i));
                String id=setting.get(0);
                FieldSet fieldSet=new FieldSet(new FormatText("field^"+id+"`value^"+setting.get(1)));
                double weight= Double.parseDouble(setting.get(2));
                chanceMap.put(fieldSet,weight);
            }
            for (Object object : Randomizer.randomObjects(chanceMap, outSize)) {
                FieldSet fieldSet= (FieldSet) object;
                inItem= fieldSet.give(inItem);
            }
            return inItem;
        }
        String randomFieldId=clone.getValue("field");
        String valueClone=clone.getValue("value");
        if (randomFieldId.contains("|")){
            randomFieldId = Randomizer.getRandomFromStr(randomFieldId);
            valueClone= clone.getValue(randomFieldId+"-value");
        }
        Field field= FieldManager.getField(randomFieldId);
        if (field==null){
            return inItem;
        }
        return field.getNbt().add(inItem, valueClone,amount);
    }


    public FormatText getFieldSetFT() {
        return fieldSetFT;
    }

    public void setFieldSetFT(FormatText fieldSetFT) {
        this.fieldSetFT = fieldSetFT;
    }
}
