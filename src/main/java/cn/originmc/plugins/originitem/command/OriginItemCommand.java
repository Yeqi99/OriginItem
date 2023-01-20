package cn.originmc.plugins.originitem.command;

import cn.originmc.plugins.origincore.util.command.CommandUtil;
import cn.originmc.plugins.originitem.OriginItem;
import cn.originmc.plugins.originitem.data.object.item.OItem;
import cn.originmc.plugins.originitem.function.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class OriginItemCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        CommandUtil c=new CommandUtil(sender,command,label,args);
        if (!c.isAdmin()){
            if (!c.hasPerm("OriginItem.admin")){
                c.getSender().sendMessage("§c权限不足");
                return true;
            }
        }
        if (c.getParameterAmount()==0){
            c.getSender().sendMessage("§c参数不足");
            return true;
        }
        if (c.is(0,"item")){
            if (!c.isPlayer()){
                return true;
            }
            if (c.getParameterAmount()==1){
                c.getSender().sendMessage("§c参数不足");
                return true;
            }
            if (c.getParameterAmount()==2){
                OItem oItem= ItemManager.getItem(c.getParameter(1));
                c.getPlayer().getInventory().addItem(oItem.randomItem(0));
                return true;
            }
            if (c.getParameterAmount()==3){
                OItem oItem= ItemManager.getItem(c.getParameter(1));
                c.getPlayer().getInventory().addItem(oItem.randomItem(Integer.parseInt(c.getParameter(2))));
                return true;
            }
            if (c.getParameterAmount()==4){
                OItem oItem= ItemManager.getItem(c.getParameter(1));
                Player player= Bukkit.getPlayer(c.getParameter(3));
                if (player==null){
                    c.getSender().sendMessage("§c没有找到玩家");
                    return true;
                }
                player.getInventory().addItem(oItem.randomItem(Integer.parseInt(c.getParameter(2))));
                return true;
            }
        }
        return true;
    }
}
