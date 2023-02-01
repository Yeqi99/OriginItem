package cn.originmc.plugins.originitem.data.object.item;

import cn.originmc.plugins.origincore.util.item.DataType;
import cn.originmc.plugins.origincore.util.item.Item;
import cn.originmc.plugins.origincore.util.text.FormatText;
import cn.originmc.plugins.originitem.data.FieldData;
import cn.originmc.plugins.originitem.data.ItemData;
import cn.originmc.plugins.originitem.data.object.external.External;
import cn.originmc.plugins.originitem.data.object.field.Field;
import cn.originmc.plugins.originitem.data.object.field.NBT;
import cn.originmc.plugins.originitem.data.object.info.Pages;
import cn.originmc.plugins.originitem.data.object.inherent.*;
import cn.originmc.plugins.originitem.function.ExternalManager;
import cn.originmc.plugins.originitem.function.InherentManager;
import cn.originmc.plugins.originitem.util.VariableUtil;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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
    public Object getFieldValue(Field field){
        if (!hasField(field)){
            return null;
        }
        NBT nbt=field.getNbt();
        Object o;
        if (nbt.getSpaceName().equalsIgnoreCase("*")){
            o=get(nbt.getKey(),nbt.getDataType());
        }else {
            o=get(nbt.getKey(),nbt.getDataType(),nbt.getSpaceName());
        }
        return o;
    }
    public void setFieldValue(Field field,Object object){
        if (!hasField(field)){
            return;
        }
        NBT nbt=field.getNbt();
        if (nbt.getSpaceName().equalsIgnoreCase("*")){
            set(nbt.getKey(),object);
        }else {
            set(nbt.getKey(),object,nbt.getSpaceName());
        }
    }
    public void addNumFieldValue(Field field,double num){
        if (!hasField(field)){
            return;
        }
        if (field.getNbt().getDataType()==DataType.DOUBLE|
                field.getNbt().getDataType()==DataType.FLOAT|
                field.getNbt().getDataType()==DataType.INT){
            double value= (double) getFieldValue(field);
            value=value+num;
            setFieldValue(field,value);
        }
    }
    public void fieldMultiplier(Field field,double num){
        if (!hasField(field)){
            return;
        }
        if (field.getNbt().getDataType()==DataType.DOUBLE|
        field.getNbt().getDataType()==DataType.FLOAT|
        field.getNbt().getDataType()==DataType.INT){
            double value= (double) getFieldValue(field);
            value=value*num;
            setFieldValue(field,value);
        }
    }
    public void reFieldMultiplier(Field field,double num){
        if (!hasField(field)){
            return;
        }
        if (field.getNbt().getDataType()==DataType.DOUBLE|
                field.getNbt().getDataType()==DataType.FLOAT|
                field.getNbt().getDataType()==DataType.INT){
            double value= (double) getFieldValue(field);
            value=value/num;
            setFieldValue(field,value);
        }
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
    public void setLevel(int level){
        set("level",level,"ITEM_FORMAT");
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
        return Math.max(nowPage, 0);
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

    public void addInstanceItem(String spaceName,InstanceItem instanceItem){
        List<InstanceItem> instanceItems=getInstanceItems(spaceName);
        instanceItems.add(instanceItem);
        setInstanceItems(instanceItems,spaceName);
    }

    public List<InstanceItem> getInstanceItems(String spaceName){
        if (!hasTag(spaceName)){
            return null;
        }
        List<InstanceItem> instanceItems=new ArrayList<>();
        ItemStack[] items = (ItemStack[]) get("add-items",DataType.ITEMSTACKARRAY,spaceName);
        for (ItemStack item : items) {
            instanceItems.add(new InstanceItem(item));
        }
        return instanceItems;
    }
    public void setInstanceItems(List<InstanceItem> instanceItems,String spaceName){
        if (!hasTag(spaceName)){
            addSpace(spaceName);
        }
        ItemStack[] itemStacks=new ItemStack[instanceItems.size()];
        for (int i=0;i<instanceItems.size();i++){
            itemStacks[i]=instanceItems.get(i).getItemStack();
        }
        set("add-items",itemStacks,spaceName);
    }
    public void upLevel(){
        if (getLevel()+1>getInherent().getMaxLevel()){
            return;
        }
        TierLevelSetting tierLevelSetting=getTier().getTierLevelSetting();
        int newLevel=getLevel()+1;

        if (getExternal().hasTierLevelSetting(getTier().getIndex())){
            TierLevelSetting externalTierLevelSetting= getExternal().getTierLevelSetting(getTier().getIndex());
            if (externalTierLevelSetting.getSpecialLvlPerAddAttributesMap().get(newLevel)!=null){
                setItemStack(externalTierLevelSetting.getSpecialLvlPerAddAttributesMap().get(newLevel).randomAdd(getItemStack(),1));
            }else {
                setItemStack(externalTierLevelSetting.getLvlPerAddAttributes().randomAdd(getItemStack(),1));
            }
        }

        if (tierLevelSetting.getSpecialLvlPerAddAttributesMap().get(newLevel)!=null){
            setItemStack(tierLevelSetting.getSpecialLvlPerAddAttributesMap().get(newLevel).randomAdd(getItemStack(),1));
        }else {
            setItemStack(tierLevelSetting.getLvlPerAddAttributes().randomAdd(getItemStack(),1));
        }

        setLevel(newLevel);
    }
    public void downLevel(){
        int level=getLevel();
        if (level<=0){
            return;
        }
        TierLevelSetting tierLevelSetting=getTier().getTierLevelSetting();

        if (getExternal().hasTierLevelSetting(getTier().getIndex())){
            TierLevelSetting externalTierLevelSetting= getExternal().getTierLevelSetting(getTier().getIndex());
            if (externalTierLevelSetting.getSpecialLvlPerAddAttributesMap().get(level)!=null){
                setItemStack(externalTierLevelSetting.getSpecialLvlPerAddAttributesMap().get(level).remove(getItemStack(),1));
            }else {
                setItemStack(externalTierLevelSetting.getLvlPerAddAttributes().remove(getItemStack(),1));
            }
        }

        if (tierLevelSetting.getSpecialLvlPerAddAttributesMap().get(level)!=null){
            setItemStack(tierLevelSetting.getSpecialLvlPerAddAttributesMap().get(level).remove(getItemStack(),1));
        }else {
            setItemStack(tierLevelSetting.getLvlPerAddAttributes().remove(getItemStack(),1));
        }
        setLevel(getLevel()-1);
    }
    public void upLevel(int amount){
        for (int i=0;i<amount;i++){
            upLevel();
        }
    }
    public void downLevel(int amount){
        for (int i=0;i<amount;i++){
            downLevel();
        }
    }
}
