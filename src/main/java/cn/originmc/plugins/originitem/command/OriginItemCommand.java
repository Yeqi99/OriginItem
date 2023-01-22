package cn.originmc.plugins.originitem.command;

import cn.originmc.plugins.origincore.util.command.CommandUtil;
import cn.originmc.plugins.originitem.OriginItem;
import cn.originmc.plugins.originitem.data.*;
import cn.originmc.plugins.originitem.data.object.external.External;
import cn.originmc.plugins.originitem.data.object.item.OItem;
import cn.originmc.plugins.originitem.function.ItemManager;
import cn.originmc.plugins.originitem.util.VariableUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class OriginItemCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        CommandUtil c=new CommandUtil(sender,command,label,args);
        if (!c.isAdmin()){
            if (!c.hasPerm("OriginItem.admin")){
                OriginItem.getSender().sendToSender(sender,(String) LangData.get(OriginItem.getLangName(),"Insufficient-permissions","&c权限不足"));
                return true;
            }
        }
        if (c.getParameterAmount()==0){
            OriginItem.getSender().sendToSender(sender,(String) LangData.get(OriginItem.getLangName(),"insufficient-parameters","&c参数不足"));
            return true;
        }
        if (c.is(0,"item")){
            if (!c.isPlayer()){
                return true;
            }
            if (c.getParameterAmount()==1){
                OriginItem.getSender().sendToSender(sender,(String) LangData.get(OriginItem.getLangName(),"insufficient-parameters","&c参数不足"));
                return true;
            }
            if (c.getParameterAmount()==2){
                OItem oItem= ItemManager.getItem(c.getParameter(1));
                ItemStack itemStack=oItem.randomItem(0);
                c.getPlayer().getInventory().addItem(VariableUtil.getVarItem(itemStack));
                return true;
            }
            if (c.getParameterAmount()==3){
                OItem oItem= ItemManager.getItem(c.getParameter(1));
                ItemStack itemStack=oItem.randomItem(Integer.parseInt(c.getParameter(2)));
                c.getPlayer().getInventory().addItem(VariableUtil.getVarItem(itemStack));
                return true;
            }
            if (c.getParameterAmount()==4){
                OItem oItem= ItemManager.getItem(c.getParameter(1));
                Player player= Bukkit.getPlayer(c.getParameter(3));
                if (player==null){
                    OriginItem.getSender().sendToSender(sender,(String) LangData.get(OriginItem.getLangName(),"no-player-found","&c没有找到玩家"));
                    return true;
                }
                ItemStack itemStack=oItem.randomItem(Integer.parseInt(c.getParameter(2)));
                player.getInventory().addItem(VariableUtil.getVarItem(itemStack));
                return true;
            }
        }else if (c.is(0,"reload")){
            OriginItem.getInstance().reloadConfig();
            ExternalData.load();
            FieldData.load();
            InfoData.load();
            InherentData.load();
            ItemData.load();
            LangData.load();
            OriginItem.getSender().sendToSender(sender,(String) LangData.get(OriginItem.getLangName(),"reload-succeeded","&a重载成功"));
        }
        return true;
    }
}
