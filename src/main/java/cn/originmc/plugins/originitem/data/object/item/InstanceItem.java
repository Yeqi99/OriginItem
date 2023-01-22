package cn.originmc.plugins.originitem.data.object.item;

import cn.originmc.plugins.origincore.util.item.DataType;
import cn.originmc.plugins.origincore.util.item.Item;
import cn.originmc.plugins.origincore.util.text.FormatText;
import cn.originmc.plugins.originitem.OriginItem;
import cn.originmc.plugins.originitem.data.ItemData;
import cn.originmc.plugins.originitem.data.object.field.Field;
import cn.originmc.plugins.originitem.data.object.info.Info;
import cn.originmc.plugins.originitem.data.object.info.Pages;
import cn.originmc.plugins.originitem.data.object.inherent.Tier;
import cn.originmc.plugins.originitem.util.VariableUtil;
import org.bukkit.inventory.ItemStack;

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
                return (double)object+"";
            }
            case INT:{
                return (int)object+"";
            }
            case FLOAT:{
                return (float)object+"";
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
        refreshLore();
        setItemStack(VariableUtil.getVarItem(getItemStack()));
    }
    public UUID getUUID(){
        return UUID.fromString((String) get("UUID", DataType.STRING,"ITEM_FORMAT"));
    }
}
