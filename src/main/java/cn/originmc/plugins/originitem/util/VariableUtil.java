package cn.originmc.plugins.originitem.util;

import cn.originmc.plugins.origincore.hook.PlaceholderAPIHook;
import cn.originmc.plugins.origincore.util.item.Item;
import cn.originmc.plugins.origincore.util.text.Color;
import cn.originmc.plugins.origincore.util.text.VariableString;
import cn.originmc.plugins.originitem.data.object.external.External;
import cn.originmc.plugins.originitem.data.object.field.Field;
import cn.originmc.plugins.originitem.data.object.info.Pages;
import cn.originmc.plugins.originitem.data.object.item.InstanceItem;
import cn.originmc.plugins.originitem.data.object.item.OItem;
import cn.originmc.plugins.originitem.function.FieldManager;
import cn.originmc.plugins.originitem.function.ItemManager;
import org.bukkit.entity.Player;
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
                continue;
            }
            if (s.equalsIgnoreCase("tier-prefix")){
                vs.setVariable(s,instanceItem.getTierPrefix());
                continue;
            }
            if (s.equalsIgnoreCase("tier-name")){
                vs.setVariable(s,instanceItem.getTier().getName());
                continue;
            }
            if (s.equalsIgnoreCase("item-type")){
                vs.setVariable(s,instanceItem.getOItemType());
                continue;
            }
            if (s.equalsIgnoreCase("item-lvl")){
                vs.setVariable(s,instanceItem.getLevel()+"");
                continue;
            }
            if (s.equalsIgnoreCase("page-now")){
                int page=instanceItem.getNowInfoPage();
                vs.setVariable(s,(page+2)+"");
                continue;
            }
            if (s.equalsIgnoreCase("page-max")){
                vs.setVariable(s,(instanceItem.getMaxInfoPage()+1)+"");
                continue;
            }
            if (s.contains("&")){
                String str=s.replace("&","");
                if (instanceItem.getEnchantmentsLevel(str)<=0){
                    return null;
                }else {
                    vs.setVariable(s,"");
                }
                continue;
            }
            if (s.contains("$")){
                String str=s.replace("$","");
                vs.setVariable(s,instanceItem.getEnchantmentsLevel(str)+"");
            }
            if (s.contains("~")){
                String fieldId=s.replace("~","");
                Field field= FieldManager.getField(fieldId);
                if (field==null){
                    return null;
                }else {
                    if (!instanceItem.hasField(field)){
                        return null;
                    }else {
                        vs.setVariable(s,"");
                    }
                }
                continue;
            }
            if (s.contains("!")){
                String fieldId=s.replace("!","");
                Field field= FieldManager.getField(fieldId);
                if (field!=null){
                    vs.setVariable(s,instanceItem.getFieldValue(field,"0"));
                }
                continue;
            }
            if (s.contains("@")){
                String fieldId=s.replace("@","");
                Field field= FieldManager.getField(fieldId);
                if (field!=null){
                    vs.setVariable(s,field.getName());
                }
                continue;
            }
            if (s.contains("#")){
                String fieldId=s.replace("#","");
                Field field= FieldManager.getField(fieldId);
                if (field!=null){
                    vs.setVariable(s,field.getInfo());
                }
                continue;
            }
        }
        return vs.getResultString();
    }
    public static List<String> getVarStringList(List<String> inList,ItemStack itemStack){
        List<String> clone=new ArrayList<>();
        for (String s : inList) {
            if (s.equalsIgnoreCase("*tier-lore*")){
                InstanceItem instanceItem=new InstanceItem(itemStack);
                OItem oItem=instanceItem.toOItem();
                External external=oItem.getExternal();
                int tierIndex=instanceItem.getTier().getIndex();
                clone.addAll(external.getTierLore(tierIndex));
                continue;
            }
            String string=getVarString(s,itemStack);
            if (string!=null){
                clone.add(string);
            }
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
    public static ItemStack getPAPIVarItem(Player player,ItemStack itemStack){
        if (!PlaceholderAPIHook.isLoad()){
            return itemStack;
        }
        Item item=new Item(itemStack);
        item.setDisplay(PlaceholderAPIHook.getPlaceholder(player,item.getDisplay()));
        item.setLore(PlaceholderAPIHook.getPlaceholder(player,item.getLore()));
        return item.getItemStack();
    }
}
