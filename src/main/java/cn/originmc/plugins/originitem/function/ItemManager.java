package cn.originmc.plugins.originitem.function;

import cn.originmc.plugins.origincore.util.item.DataType;
import cn.originmc.plugins.origincore.util.item.Item;
import cn.originmc.plugins.originitem.OriginItem;
import cn.originmc.plugins.originitem.data.ItemData;
import cn.originmc.plugins.originitem.data.object.info.Info;
import cn.originmc.plugins.originitem.data.object.inherent.Tier;
import cn.originmc.plugins.originitem.data.object.item.OItem;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import javax.print.DocFlavor;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ItemManager {
    public static OItem getItem(String id){
        for (OItem oItem : ItemData.getoItems()) {
            if (oItem.getId().equalsIgnoreCase(id)){
                return oItem;
            }
        }
        return null;
    }
    public static boolean isOItem(ItemStack itemStack){
        Item item =new Item(itemStack);
        return item.hasTag("id", "ITEM_FORMAT");
    }
    public static String getTypeFromItemStack(ItemStack itemStack){
        Item item =new Item(itemStack);
        return (String) item.get("type", DataType.STRING, "ITEM_FORMAT");
    }
    public static OItem getItemFromItemStack(ItemStack itemStack){
        Item item =new Item(itemStack);
        return getItem((String) item.get("id",DataType.STRING,"ITEM_FORMAT"));
    }
    public static Tier getTierFromItemStack(ItemStack itemStack){
        Item item =new Item(itemStack);
        int tierIndex= (int) item.get("index",DataType.INT,"ITEM_TIER");
        return getItemFromItemStack(itemStack).getInherent().getTiers().get(tierIndex);
    }
    public static String getTierPrefixFromItemStack(ItemStack itemStack){
        Item item =new Item(itemStack);
        return (String) item.get("prefix",DataType.STRING,"ITEM_TIER");
    }
    public static int getInfoPage(ItemStack itemStack){
        Item item =new Item(itemStack);
        return (int) item.get("nowPage",DataType.INT,"ITEM_TIER");
    }
}
