package cn.originmc.plugins.originitem.function.page;

import cn.originmc.plugins.origincore.listener.cooldown.CoolDownListener;
import cn.originmc.plugins.originitem.OriginItem;
import cn.originmc.plugins.originitem.data.LangData;
import cn.originmc.plugins.originitem.data.object.item.InstanceItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;


public class PageListener implements Listener {
    @EventHandler
    public static void playerTurnPages(InventoryClickEvent e){
        if (e.getClick() == ClickType.valueOf(OriginItem.getInstance().getConfig().getString("multi-page.click-type","MIDDLE"))){
            ItemStack itemStack= e.getCurrentItem();
            if (itemStack==null){
                return;
            }
            if (itemStack.getType()!= Material.AIR){
                InstanceItem instanceItem=new InstanceItem(itemStack);
                if (CoolDownListener.isCoolDown(instanceItem.getUUID())){
                    String coolDownMeg=(String) LangData.get(OriginItem.getLangName(),"page-coolDown","&7翻页冷却中");
                    if (!coolDownMeg.equalsIgnoreCase("*")){
                        OriginItem.getSender().sendToPlayer((Player) e.getWhoClicked(),coolDownMeg);
                    }
                    return;
                }
                if (!instanceItem.isOItem()){
                    return;
                }
                instanceItem.setPage(instanceItem.getNextPage());
                instanceItem.refreshVar();
                e.setCurrentItem(instanceItem.getItemStack());
                CoolDownListener.register(OriginItem.getInstance().getConfig().getLong("multi-page.cool-down"),instanceItem.getUUID());
            }
        }
    }
}
