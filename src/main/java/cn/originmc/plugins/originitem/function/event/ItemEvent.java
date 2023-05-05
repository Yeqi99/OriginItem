package cn.originmc.plugins.originitem.function.event;

import cn.originmc.plugins.origincore.hook.PlaceholderAPIHook;
import cn.originmc.plugins.origincore.listener.cooldown.CoolDownListener;
import cn.originmc.plugins.origincore.util.action.object.Actions;
import cn.originmc.plugins.origincore.util.item.DataType;
import cn.originmc.plugins.origincore.util.item.Item;
import cn.originmc.plugins.origincore.util.text.FormatText;
import cn.originmc.plugins.origincore.util.text.Sender;
import cn.originmc.plugins.originitem.OriginItem;
import cn.originmc.plugins.originitem.data.object.item.InstanceItem;
import cn.originmc.plugins.originitem.function.ActionsManager;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import javax.xml.crypto.Data;

public class ItemEvent implements Listener {
    @EventHandler
    public static void whenLeftClick(PlayerInteractEvent e){
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
        if (!iItem.hasTag("whenLeftClick")){
            return;
        }
        String perm= (String) iItem.get("limit",DataType.STRING,"whenLeftClick");
        if (!e.getPlayer().hasPermission(perm)){
            if (!e.getPlayer().isOp()){
                return;
            }
        }
        Actions actions= ActionsManager.getActions((String) iItem.get("actions", DataType.STRING,"whenLeftClick"));
        if (actions==null){
            return;
        }
        long nowCd= CoolDownListener.getTime(iItem.getUUID(),"whenLeftClick");
        if (nowCd>0){
            if ((boolean)iItem.get("cd-silent", DataType.BOOLEAN,"whenLeftClick")){
                return;
            }
            e.getPlayer().sendTitle("§d"+((double)nowCd/20)+"s","§8冷却中...",0,10,0);
            return;
        }
        long cd= (long) iItem.get("cd",DataType.LONG,"whenLeftClick");
        String parameter= (String) iItem.get("parameter",DataType.STRING,"whenLeftClick");
        parameter=parameter.replace("=","^");
        parameter=parameter.replace(",","`");
        if (PlaceholderAPIHook.isLoad()){
            PlaceholderAPIHook.getPlaceholder(e.getPlayer(),parameter);
        }
        actions.putObject("self",e.getPlayer());
        actions.execute(new FormatText(parameter));
        CoolDownListener.register(cd,iItem.getUUID(),"whenLeftClick");
    }
}
