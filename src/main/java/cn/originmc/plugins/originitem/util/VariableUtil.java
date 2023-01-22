package cn.originmc.plugins.originitem.util;

import cn.originmc.plugins.origincore.util.item.Item;
import cn.originmc.plugins.origincore.util.text.Color;
import cn.originmc.plugins.origincore.util.text.VariableString;
import cn.originmc.plugins.originitem.data.object.field.Field;
import cn.originmc.plugins.originitem.data.object.info.Pages;
import cn.originmc.plugins.originitem.data.object.item.InstanceItem;
import cn.originmc.plugins.originitem.data.object.item.OItem;
import cn.originmc.plugins.originitem.function.FieldManager;
import cn.originmc.plugins.originitem.function.ItemManager;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class VariableUtil {
    public static String getVarString(String originString, ItemStack itemStack){
        VariableString vs=new VariableString(originString,'*');
        InstanceItem instanceItem=new InstanceItem(itemStack);
        if (vs.getVariableAmount()<=0){
            return originString;
        }
        for (String s : vs.getAllVariable()) {
            if (s.equalsIgnoreCase("tier-color")){
                vs.setVariable(s, instanceItem.getTier().getColor());
            }
            if (s.equalsIgnoreCase("tier-prefix")){
                vs.setVariable(s,instanceItem.getTierPrefix());
            }
            if (s.equalsIgnoreCase("tier-name")){
                vs.setVariable(s,instanceItem.getTier().getName());
            }
            if (s.equalsIgnoreCase("item-type")){
                vs.setVariable(s,instanceItem.getOItemType());
            }
            if (s.contains("!")){
                String fieldId=s.replace("!","");
                Field field= FieldManager.getField(fieldId);
                if (field!=null){
                    vs.setVariable(s,instanceItem.getFieldValue(field,"0"));
                }
            }
        }
        return vs.getResultString();
    }
    public static List<String> getVarStringList(List<String> inList,ItemStack itemStack){
        List<String> clone=new ArrayList<>();
        for (String s : inList) {
            clone.add(getVarString(s,itemStack));
        }
        return clone;
    }
    public static ItemStack getVarItem(ItemStack itemStack){
        OItem oItem= ItemManager.getItemFromItemStack(itemStack);
        String display=getVarString(oItem.getExternal().getDisplay(),itemStack);
        Item item=new Item(itemStack);
        List<String> info=itemStack.getItemMeta().getLore();
        info=getVarStringList(info,itemStack);
        item.setDisplay(Color.toColor(display));
        item.setLore(Color.toColor(info));
        return item.getItemStack();
    }
}
