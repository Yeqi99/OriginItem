package cn.originmc.plugins.originitem.function.event;

import cn.originmc.plugins.origincore.hook.PlaceholderAPIHook;
import cn.originmc.plugins.origincore.listener.cooldown.CoolDownListener;
import cn.originmc.plugins.origincore.util.action.object.Actions;
import cn.originmc.plugins.origincore.util.item.DataType;
import cn.originmc.plugins.origincore.util.text.FormatText;
import cn.originmc.plugins.originitem.data.object.item.InstanceItem;
import cn.originmc.plugins.originitem.function.ActionsManager;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;


public class ItemActionListener implements Listener {
    @EventHandler
    public static void whenLeftClick(PlayerInteractEvent e){
        if (e.getPlayer().isSneaking()){
            return;
        }
        if (e.getItem()==null){
            return;
        }
        if (e.getItem().getType()== Material.AIR){
            return;
        }
        if (e.getAction()!= Action.LEFT_CLICK_AIR & e.getAction()!= Action.LEFT_CLICK_BLOCK){
            return;
        }
        InstanceItem iItem=new InstanceItem(e.getItem());
        String type="whenLeftClick";
        if (!iItem.hasTag(type)){
            return;
        }
        String perm= (String) iItem.get("limit",DataType.STRING,type);
        if (!e.getPlayer().hasPermission(perm)){
            if (!e.getPlayer().isOp()){
                return;
            }
        }
        Actions actions= ActionsManager.getActions((String) iItem.get("actions", DataType.STRING,type));
        if (actions==null){
            return;
        }
        long nowCd= CoolDownListener.getTime(iItem.getUUID(),type);
        if (nowCd>0){
            if ((boolean)iItem.get("cd-silent", DataType.BOOLEAN,type)){
                return;
            }
            e.getPlayer().sendTitle("§d"+((double)nowCd/20)+"s","§8冷却中...",0,10,0);
            return;
        }
        long cd= (long) iItem.get("cd",DataType.LONG,type);
        String parameter= (String) iItem.get("parameter",DataType.STRING,type);
        parameter=parameter.replace("=","^");
        parameter=parameter.replace(",","`");
        if (PlaceholderAPIHook.isLoad()){
            PlaceholderAPIHook.getPlaceholder(e.getPlayer(),parameter);
        }
        actions.putObject("self",e.getPlayer());
        actions.execute(new FormatText(parameter));
        CoolDownListener.register(cd,iItem.getUUID(),type);
    }
    @EventHandler
    public static void whenRightClick(PlayerInteractEvent e){
        if (e.getPlayer().isSneaking()){
            return;
        }
        if (e.getItem()==null){
            return;
        }
        if (e.getItem().getType()== Material.AIR){
            return;
        }
        if (e.getAction()!= Action.RIGHT_CLICK_AIR & e.getAction()!= Action.RIGHT_CLICK_BLOCK){
            return;
        }
        InstanceItem iItem=new InstanceItem(e.getItem());
        String type="whenRightClick";
        if (!iItem.hasTag(type)){
            return;
        }
        String perm= (String) iItem.get("limit",DataType.STRING,type);
        if (!e.getPlayer().hasPermission(perm)){
            if (!e.getPlayer().isOp()){
                return;
            }
        }
        Actions actions= ActionsManager.getActions((String) iItem.get("actions", DataType.STRING,type));
        if (actions==null){
            return;
        }
        long nowCd= CoolDownListener.getTime(iItem.getUUID(),type);
        if (nowCd>0){
            if ((boolean)iItem.get("cd-silent", DataType.BOOLEAN,type)){
                return;
            }
            e.getPlayer().sendTitle("§d"+((double)nowCd/20)+"s","§8冷却中...",0,10,0);
            return;
        }
        long cd= (long) iItem.get("cd",DataType.LONG,type);
        String parameter= (String) iItem.get("parameter",DataType.STRING,type);
        parameter=parameter.replace("=","^");
        parameter=parameter.replace(",","`");
        if (PlaceholderAPIHook.isLoad()){
            PlaceholderAPIHook.getPlaceholder(e.getPlayer(),parameter);
        }
        actions.putObject("self",e.getPlayer());
        actions.execute(new FormatText(parameter));
        CoolDownListener.register(cd,iItem.getUUID(),type);
    }
    @EventHandler
    public static void whenShiftClick(PlayerToggleSneakEvent e){
        if (e.getPlayer().isSneaking()){
            return;
        }
        if (e.getPlayer().getInventory().getItemInMainHand()==null){
            return;
        }
        if (e.getPlayer().getInventory().getItemInMainHand().getType()== Material.AIR){
            return;
        }
        InstanceItem iItem=new InstanceItem(e.getPlayer().getInventory().getItemInMainHand());
        String type="whenShiftClick";
        if (!iItem.hasTag(type)){
            return;
        }
        String perm= (String) iItem.get("limit",DataType.STRING,type);
        if (!e.getPlayer().hasPermission(perm)){
            if (!e.getPlayer().isOp()){
                return;
            }
        }
        Actions actions= ActionsManager.getActions((String) iItem.get("actions", DataType.STRING,type));
        if (actions==null){
            return;
        }
        long nowCd= CoolDownListener.getTime(iItem.getUUID(),type);
        if (nowCd>0){
            if ((boolean)iItem.get("cd-silent", DataType.BOOLEAN,type)){
                return;
            }
            e.getPlayer().sendTitle("§d"+((double)nowCd/20)+"s","§8冷却中...",0,10,0);
            return;
        }
        long cd= (long) iItem.get("cd",DataType.LONG,type);
        String parameter= (String) iItem.get("parameter",DataType.STRING,type);
        parameter=parameter.replace("=","^");
        parameter=parameter.replace(",","`");
        if (PlaceholderAPIHook.isLoad()){
            PlaceholderAPIHook.getPlaceholder(e.getPlayer(),parameter);
        }
        actions.putObject("self",e.getPlayer());
        actions.execute(new FormatText(parameter));
        CoolDownListener.register(cd,iItem.getUUID(),type);
    }
    @EventHandler
    public static void whenUnShiftClick(PlayerToggleSneakEvent e){
        if (!e.getPlayer().isSneaking()){
            return;
        }
        if (e.getPlayer().getInventory().getItemInMainHand()==null){
            return;
        }
        if (e.getPlayer().getInventory().getItemInMainHand().getType()== Material.AIR){
            return;
        }
        InstanceItem iItem=new InstanceItem(e.getPlayer().getInventory().getItemInMainHand());
        String type="whenUnShiftClick";
        if (!iItem.hasTag(type)){
            return;
        }
        String perm= (String) iItem.get("limit",DataType.STRING,type);
        if (!e.getPlayer().hasPermission(perm)){
            if (!e.getPlayer().isOp()){
                return;
            }
        }
        Actions actions= ActionsManager.getActions((String) iItem.get("actions", DataType.STRING,type));
        if (actions==null){
            return;
        }
        long nowCd= CoolDownListener.getTime(iItem.getUUID(),type);
        if (nowCd>0){
            if ((boolean)iItem.get("cd-silent", DataType.BOOLEAN,type)){
                return;
            }
            e.getPlayer().sendTitle("§d"+((double)nowCd/20)+"s","§8冷却中...",0,10,0);
            return;
        }
        long cd= (long) iItem.get("cd",DataType.LONG,type);
        String parameter= (String) iItem.get("parameter",DataType.STRING,type);
        parameter=parameter.replace("=","^");
        parameter=parameter.replace(",","`");
        if (PlaceholderAPIHook.isLoad()){
            PlaceholderAPIHook.getPlaceholder(e.getPlayer(),parameter);
        }
        actions.putObject("self",e.getPlayer());
        actions.execute(new FormatText(parameter));
        CoolDownListener.register(cd,iItem.getUUID(),type);
    }
    @EventHandler
    public static void whenDrop(PlayerDropItemEvent e){
        if (e.getPlayer().isSneaking()){
            return;
        }
        if (e.getPlayer().getInventory().getItemInMainHand()==null){
            return;
        }
        if (e.getPlayer().getInventory().getItemInMainHand().getType()== Material.AIR){
            return;
        }
        InstanceItem iItem=new InstanceItem(e.getPlayer().getInventory().getItemInMainHand());
        String type="whenDrop";
        if (!iItem.hasTag(type)){
            return;
        }
        String perm= (String) iItem.get("limit",DataType.STRING,type);
        if (!e.getPlayer().hasPermission(perm)){
            if (!e.getPlayer().isOp()){
                return;
            }
        }
        Actions actions= ActionsManager.getActions((String) iItem.get("actions", DataType.STRING,type));
        if (actions==null){
            return;
        }
        long nowCd= CoolDownListener.getTime(iItem.getUUID(),type);
        if (nowCd>0){
            if ((boolean)iItem.get("cd-silent", DataType.BOOLEAN,type)){
                return;
            }
            e.getPlayer().sendTitle("§d"+((double)nowCd/20)+"s","§8冷却中...",0,10,0);
            return;
        }
        long cd= (long) iItem.get("cd",DataType.LONG,type);
        String parameter= (String) iItem.get("parameter",DataType.STRING,type);
        parameter=parameter.replace("=","^");
        parameter=parameter.replace(",","`");
        if (PlaceholderAPIHook.isLoad()){
            PlaceholderAPIHook.getPlaceholder(e.getPlayer(),parameter);
        }
        actions.putObject("self",e.getPlayer());
        actions.execute(new FormatText(parameter));
        CoolDownListener.register(cd,iItem.getUUID(),type);
    }
}
