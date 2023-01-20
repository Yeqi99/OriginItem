package cn.originmc.plugins.originitem.function;

import cn.originmc.plugins.originitem.data.ItemData;
import cn.originmc.plugins.originitem.data.object.item.OItem;

public class ItemManager {
    public static OItem getItem(String id){
        for (OItem oItem : ItemData.getoItems()) {
            if (oItem.getId().equalsIgnoreCase(id)){
                return oItem;
            }
        }
        return null;
    }
}
