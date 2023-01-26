package cn.originmc.plugins.originitem.api;

import cn.originmc.plugins.originitem.command.OriginItemCommand;
import cn.originmc.plugins.originitem.data.object.field.Field;
import cn.originmc.plugins.originitem.data.object.item.InstanceItem;
import cn.originmc.plugins.originitem.data.object.item.OItem;
import cn.originmc.plugins.originitem.function.FieldManager;
import cn.originmc.plugins.originitem.function.ItemManager;
import org.bukkit.inventory.ItemStack;

public class OriginItemAPI {
    private static int numberOfApiCalls;
    public OriginItemAPI(){
        numberOfApiCalls++;
    }

    public static int getNumberOfApiCalls() {
        return numberOfApiCalls;
    }

    public void reloadPlugin(){
        OriginItemCommand.reload();
    }
    public InstanceItem getInstanceItem(ItemStack itemStack){
        return new InstanceItem(itemStack);
    }
    public boolean isOItem(ItemStack itemStack){
        return new InstanceItem(itemStack).isOItem();
    }

    public OItem getOItem(String id){
        return ItemManager.getItem(id);
    }

    public boolean hasOItem(String id){
        return ItemManager.hasOItem(id);
    }

    public boolean hasField(String id){
        return FieldManager.hasField(id);
    }

    public Field getField(String id){
        return FieldManager.getField(id);
    }

    public boolean registerField(Field field){
        return FieldManager.register(field);
    }


}
