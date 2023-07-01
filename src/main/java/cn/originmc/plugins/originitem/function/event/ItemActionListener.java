package cn.originmc.plugins.originitem.function.event;

import cn.originmc.plugins.origincore.hook.PlaceholderAPIHook;
import cn.originmc.plugins.origincore.listener.cooldown.CoolDownListener;
import cn.originmc.plugins.origincore.util.action.object.Actions;
import cn.originmc.plugins.origincore.util.item.DataType;
import cn.originmc.plugins.origincore.util.text.FormatText;
import cn.originmc.plugins.originitem.OriginItem;
import cn.originmc.plugins.originitem.data.object.item.InstanceItem;
import cn.originmc.plugins.originitem.function.ActionsManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;
import java.util.UUID;


public class ItemActionListener implements Listener {
    public static Map<UUID, Long> lastShiftToggleTimeMap;

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        boolean isSneaking = event.getPlayer().isSneaking();
        boolean isLeftClick = event.getAction().name().contains("LEFT");
        boolean isRightClick = event.getAction().name().contains("RIGHT");
        if (isSneaking && isLeftClick) {
            executeActions("whenShiftLeftClick",event.getPlayer(),1);
            // 潜行+左键
        } else if (isSneaking && isRightClick) {
            executeActions("whenShiftRightClick",event.getPlayer(),0);
            // 潜行+右键
        } else if (!isSneaking && isLeftClick) {
            executeActions("whenLeftClick",event.getPlayer(),0);
            // 左键
        } else if (!isSneaking && isRightClick) {
            executeActions("whenRightClick",event.getPlayer(),0);
            // 右键
        }
    }
    @EventHandler
    public void onPlayerToggleSneak(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();

        if (event.isSneaking()) {
            long currentTime = System.currentTimeMillis();
            long lastShiftToggleTime = lastShiftToggleTimeMap.getOrDefault(playerUUID, 0L);
            if (currentTime - lastShiftToggleTime <= 500) {
                executeActions("whenDoubleShiftClick",event.getPlayer(),4);
            }
            lastShiftToggleTimeMap.put(playerUUID, currentTime);
        }
    }
    @EventHandler
    public void onPlayerSwapHandItems(PlayerSwapHandItemsEvent event) {
        if (event.getPlayer().isSneaking()) {
            executeActions("whenShiftFClick",event.getPlayer(),3);
            // 同时按下Shift+F
        }else{
            executeActions("whenFClick",event.getPlayer(),2);
        }
        event.setCancelled(true);
    }

    public void executeActions(String clickType,Player player,int index){
        new BukkitRunnable() {
            @Override
            public void run() {
                ItemStack itemStack=null;
                if (index==0){
                    itemStack=player.getInventory().getItemInMainHand();
                }else if (index==1){
                    itemStack=player.getInventory().getHelmet();
                }else if (index==2){
                    itemStack=player.getInventory().getChestplate();
                }else if (index==3){
                    itemStack=player.getInventory().getLeggings();
                }else if (index==4){
                    itemStack=player.getInventory().getBoots();
                }
                if (itemStack == null) {
                    cancel();
                    return;
                } else {
                    if (itemStack.getType() == Material.AIR) {
                        cancel();
                        return;
                    }
                }
                InstanceItem iItem=new InstanceItem(itemStack);
                String type=clickType;
                if (!iItem.hasTag(type)){
                    cancel();
                    return;
                }
                String perm= (String) iItem.get("limit",DataType.STRING,type);
                if (!player.hasPermission(perm)){
                    if (!player.isOp()){
                        cancel();
                        return;
                    }
                }
                Actions actions= ActionsManager.getActions((String) iItem.get("actions", DataType.STRING,type));
                if (actions==null){
                    cancel();
                    return;
                }
                long nowCd= CoolDownListener.getTime(iItem.getUUID(),type);
                if (nowCd>0){
                    if ((boolean)iItem.get("cd-silent", DataType.BOOLEAN,type)){
                        cancel();
                        return;
                    }
                    player.sendTitle("§d"+((double)nowCd/20)+"s","§8冷却中...",0,10,0);
                    cancel();
                    return;
                }
                long cd= (long) iItem.get("cd",DataType.LONG,type);
                String parameter= (String) iItem.get("parameter",DataType.STRING,type);
                parameter=parameter.replace("=","^");
                parameter=parameter.replace(",","`");
                if (PlaceholderAPIHook.isLoad()){
                    PlaceholderAPIHook.getPlaceholder(player,parameter);
                }
                actions.putObject("self",player);
                actions.execute(new FormatText(parameter));
                CoolDownListener.register(cd,iItem.getUUID(),type);
                cancel();
            }
        }.runTaskAsynchronously(OriginItem.getInstance());
    }

}
