package cn.originmc.plugins.originitem.data.object.item;

import cn.originmc.plugins.origincore.util.item.Item;
import cn.originmc.plugins.originitem.data.object.external.External;
import cn.originmc.plugins.originitem.data.object.inherent.Inherent;
import org.bukkit.inventory.ItemStack;

public class OItem {
    private String id;
    private External external;
    private Inherent inherent;
    private String type;

    public ItemStack randomItem(int level){
        ItemStack itemStack=getExternal().getItem();
        itemStack=getInherent().randomGive(level,itemStack);
        Item item=new Item(itemStack);
        item.set("type",getType(),"ITEM_FORMAT");
        item.set("id",getId(),"ITEM_FORMAT");
        return item.getItemStack();
    }

    public External getExternal() {
        return external;
    }

    public void setExternal(External external) {
        this.external = external;
    }

    public Inherent getInherent() {
        return inherent;
    }

    public void setInherent(Inherent inherent) {
        this.inherent = inherent;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
