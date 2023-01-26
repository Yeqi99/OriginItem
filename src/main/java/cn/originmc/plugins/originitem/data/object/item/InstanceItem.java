package cn.originmc.plugins.originitem.data.object.item;

import cn.originmc.plugins.origincore.util.item.DataType;
import cn.originmc.plugins.origincore.util.item.Item;
import cn.originmc.plugins.origincore.util.text.FormatText;
import cn.originmc.plugins.originitem.OriginItem;
import cn.originmc.plugins.originitem.data.ExternalData;
import cn.originmc.plugins.originitem.data.FieldData;
import cn.originmc.plugins.originitem.data.ItemData;
import cn.originmc.plugins.originitem.data.object.external.External;
import cn.originmc.plugins.originitem.data.object.field.Field;
import cn.originmc.plugins.originitem.data.object.info.Info;
import cn.originmc.plugins.originitem.data.object.info.Pages;
import cn.originmc.plugins.originitem.data.object.inherent.Attributes;
import cn.originmc.plugins.originitem.data.object.inherent.FieldSet;
import cn.originmc.plugins.originitem.data.object.inherent.Inherent;
import cn.originmc.plugins.originitem.data.object.inherent.Tier;
import cn.originmc.plugins.originitem.function.ExternalManager;
import cn.originmc.plugins.originitem.function.InherentManager;
import cn.originmc.plugins.originitem.util.VariableUtil;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.index.qual.PolyUpperBound;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class InstanceItem extends Item {

    public InstanceItem(ItemStack itemStack) {
        super(itemStack);
    }
    public boolean hasField(Field field){
        if (field.getNbt().getSpaceName().equalsIgnoreCase("*")){
            return hasTag(field.getNbt().getKey());
        }else {
            return hasTag(field.getNbt().getKey(),field.getNbt().getSpaceName());
        }
    }
    public List<Field> getFields(){
        List<Field> fields=new ArrayList<>();
        for (Field field : FieldData.getFieldList()) {
            if (hasField(field)){
                fields.add(field);
            }
        }
        return fields;
    }
    public void addFieldSet(String fieldSetFT,int amount){
        FieldSet fieldSet=new FieldSet(new FormatText(fieldSetFT));
        setItemStack(fieldSet.add(getItemStack(),amount));
    }
    public void addRandomFieldSet(String fieldSetFT,int amount){
        FieldSet fieldSet=new FieldSet(new FormatText(fieldSetFT));
        setItemStack(fieldSet.randomAdd(getItemStack(),amount));
    }
    public void giveFieldSet(String fieldSetFT){
        FieldSet fieldSet=new FieldSet(new FormatText(fieldSetFT));
        setItemStack(fieldSet.give(getItemStack()));
    }
    public void giveRandomFieldSet(String fieldSetFT){
        FieldSet fieldSet=new FieldSet(new FormatText(fieldSetFT));
        setItemStack(fieldSet.randomGive(getItemStack()));
    }
    public void addAttributes(Attributes attributes,int amount){
        setItemStack(attributes.add(getItemStack(),amount));
    }
    public void addRandomAttributes(Attributes attributes,int amount){
        setItemStack(attributes.randomAdd(getItemStack(),amount));
    }
    public void giveAttributes(Attributes attributes){
        setItemStack(attributes.give(getItemStack()));
    }
    public void giveRandomAttributes(Attributes attributes){
        setItemStack(attributes.randomGive(getItemStack()));
    }
    public External getExternal(){
        return ExternalManager.getExternal((String) get("external",DataType.STRING,"ITEM_FORMAT"));
    }
    public Inherent getInherent(){
        return InherentManager.getInherent((String) get("inherent",DataType.STRING,"ITEM_FORMAT"));
    }
    public String getFieldValue(Field field,String defaultValue){
        if (!hasField(field)){
            return  defaultValue;
        }
        Object object;
        if (field.getNbt().getSpaceName().equalsIgnoreCase("*")){
            object = get(field.getNbt().getKey(), field.getNbt().getDataType());
        }else {
            object = get(field.getNbt().getKey(), field.getNbt().getDataType(), field.getNbt().getSpaceName());
        }
        switch (field.getNbt().getDataType()){
            case DOUBLE:{

                return String.format("%."+field.getNbt().getSign()+"f",object);
            }
            case INT:{
                return (int)object+"";
            }
            case FLOAT:{
                return String.format("%."+field.getNbt().getSign()+"f",object)+"";
            }
            case STRING:{
                return (String) object;
            }
            case LONG:{
                return (long)object+"";
            }
            case FORMATTEXT:{
                return ((FormatText)object).getFormatString();
            }
            default:{
                return defaultValue;
            }
        }
    }
    public int getNowInfoPage(){
        if (!hasTag("nowPage","ITEM_TIER")){
            return -1;
        }
        return (int)get("nowPage",DataType.INT,"ITEM_TIER");
    }
    public int getLevel(){
        return (int) get("level",DataType.INT,"ITEM_FORMAT");
    }
    public void setPage(int index){
        set("nowPage",index,"ITEM_TIER");
    }
    public int getMaxInfoPage(){
        Pages pages=new Pages(toOItem().getExternal().getInfoIdList());
        return pages.getSize();
    }
    public int getNextPage(){
        int size= getMaxInfoPage();
        int nowPage= getNowInfoPage()+1;
        if (nowPage>=size){
            return -1;
        }
        if (nowPage<0){
            return 0;
        }
        return nowPage;
    }
    public boolean isOItem(){
        return hasTag("id", "ITEM_FORMAT");
    }
    public String getOItemID() {
        return (String) get("id", DataType.STRING, "ITEM_FORMAT");
    }
    public OItem toOItem(){
        for (OItem oItem : ItemData.getoItems()) {
            if (oItem.getId().equalsIgnoreCase(getOItemID())){
                return oItem;
            }
        }
        return null;
    }
    public String getOItemType(){
        return (String) get("type", DataType.STRING, "ITEM_FORMAT");
    }
    public Tier getTier(){
        int tierIndex= (int) get("index",DataType.INT,"ITEM_TIER");
        return toOItem().getInherent().getTiers().get(tierIndex);
    }
    public String getTierPrefix(){
        return (String) get("prefix",DataType.STRING,"ITEM_TIER");
    }
    public void refreshLore(){
        OItem oItem=toOItem();
        int nowPage = getNowInfoPage();
        if (nowPage==-1){
            setLore(oItem.getExternal().getLore());
        }
        Pages pages=new Pages(oItem.getExternal().getInfoIdList());
        setLore(pages.getPage(nowPage,oItem.getExternal().getLore()));
    }
    public void refreshVar(){
        if (hasDisplay()){
            setDisplay(VariableUtil.getVarString(getExternal().getDisplay(),getItemStack()));
        }
        setItemStack(VariableUtil.getVarItem(getItemStack()));
    }
    public void refreshPAPIVar(Player player){
        setItemStack(VariableUtil.getPAPIVarItem(player,getItemStack()));
    }
    public UUID getUUID(){
        return UUID.fromString((String) get("UUID", DataType.STRING,"ITEM_FORMAT"));
    }

    public void addInstanceItem(InstanceItem instanceItem){

    }

}
