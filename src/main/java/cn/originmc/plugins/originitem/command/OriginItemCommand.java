package cn.originmc.plugins.originitem.command;

import cn.originmc.plugins.origincore.util.command.CommandUtil;
import cn.originmc.plugins.origincore.util.item.Item;
import cn.originmc.plugins.origincore.util.random.Randomizer;
import cn.originmc.plugins.origincore.util.text.InteractiveKey;
import cn.originmc.plugins.origincore.util.text.TextProcessing;
import cn.originmc.plugins.origincore.util.text.interactivekey.objcet.ClickAction;
import cn.originmc.plugins.originitem.OriginItem;
import cn.originmc.plugins.originitem.data.*;
import cn.originmc.plugins.originitem.data.object.item.OItem;
import cn.originmc.plugins.originitem.function.ItemManager;
import cn.originmc.plugins.originitem.util.VariableUtil;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            if (c.getParameterAmount()<2){
                OriginItem.getSender().sendToSender(sender,(String) LangData.get(OriginItem.getLangName(),"insufficient-parameters","&c参数不足"));
                return true;
            }
            if (c.is(1,"get")){
                OItem oItem= ItemManager.getItem(c.getParameter(2));
                if (oItem==null){
                    OriginItem.getSender().sendToSender(sender,(String) LangData.get(OriginItem.getLangName(),"no-item-found","&c没有找到物品"));
                    return true;
                }
                if (c.getParameterAmount()==3){
                    ItemStack itemStack=oItem.randomItem(Randomizer.getRandom(oItem.getInherent().getMinLevel(),oItem.getInherent().getMaxLevel()));
                    c.getPlayer().getInventory().addItem(VariableUtil.getVarItem(itemStack));
                    return true;
                }else if (c.getParameterAmount()==4){
                    String level=c.getParameter(3);
                    if (level.contains("|")){
                        level=Randomizer.getRandomFromStr(level);
                    }
                    if (level.contains("-")){
                        level=Randomizer.getRandomFromSection(level,0);
                    }
                    ItemStack itemStack=oItem.randomItem(Integer.parseInt(level));
                    c.getPlayer().getInventory().addItem(VariableUtil.getVarItem(itemStack));
                    return true;
                }else if (c.getParameterAmount()==5){
                    String level=c.getParameter(3);
                    if (level.contains("|")){
                        level=Randomizer.getRandomFromStr(level);
                    }
                    if (level.contains("-")){
                        level=Randomizer.getRandomFromSection(level,0);
                    }
                    ItemStack itemStack=oItem.getItem(Integer.parseInt(level),Integer.parseInt(c.getParameter(4)));
                    c.getPlayer().getInventory().addItem(VariableUtil.getVarItem(itemStack));
                    return true;
                }else if (c.getParameterAmount()==6){
                    String level=c.getParameter(3);
                    if (level.contains("|")){
                        level=Randomizer.getRandomFromStr(level);
                    }
                    if (level.contains("-")){
                        level=Randomizer.getRandomFromSection(level,0);
                    }
                    ItemStack itemStack=oItem.getItem(Integer.parseInt(level)
                            ,Integer.parseInt(c.getParameter(4)),Integer.parseInt(c.getParameter(5)));
                    c.getPlayer().getInventory().addItem(VariableUtil.getVarItem(itemStack));
                    return true;
                }
            }else if (c.is(1,"give")){
                Player player=Bukkit.getPlayer(c.getParameter(2));
                if (player==null){
                    OriginItem.getSender().sendToSender(sender,(String) LangData.get(OriginItem.getLangName(),"no-player-found","&c没有找到玩家"));
                    return true;
                }
                OItem oItem= ItemManager.getItem(c.getParameter(3));
                if (oItem==null){
                    OriginItem.getSender().sendToSender(sender,(String) LangData.get(OriginItem.getLangName(),"no-item-found","&c没有找到物品"));
                    return true;
                }
                if (c.getParameterAmount()==4){
                    ItemStack itemStack=oItem.randomItem(Randomizer.getRandom(oItem.getInherent().getMinLevel(),oItem.getInherent().getMaxLevel()));
                    c.getPlayer().getInventory().addItem(VariableUtil.getVarItem(itemStack));
                    return true;
                }else if (c.getParameterAmount()==5){
                    String level=c.getParameter(4);
                    if (level.contains("|")){
                        level=Randomizer.getRandomFromStr(level);
                    }
                    if (level.contains("-")){
                        level=Randomizer.getRandomFromSection(level,0);
                    }
                    ItemStack itemStack=oItem.randomItem(Integer.parseInt(level));
                    c.getPlayer().getInventory().addItem(VariableUtil.getVarItem(itemStack));
                    return true;
                }else if (c.getParameterAmount()==6){
                    String level=c.getParameter(4);
                    if (level.contains("|")){
                        level=Randomizer.getRandomFromStr(level);
                    }
                    if (level.contains("-")){
                        level=Randomizer.getRandomFromSection(level,0);
                    }
                    ItemStack itemStack=oItem.getItem(Integer.parseInt(level),Integer.parseInt(c.getParameter(5)));
                    c.getPlayer().getInventory().addItem(VariableUtil.getVarItem(itemStack));
                    return true;
                }else if (c.getParameterAmount()==7){
                    String level=c.getParameter(4);
                    if (level.contains("|")){
                        level=Randomizer.getRandomFromStr(level);
                    }
                    if (level.contains("-")){
                        level=Randomizer.getRandomFromSection(level,0);
                    }
                    ItemStack itemStack=oItem.getItem(Integer.parseInt(level)
                            ,Integer.parseInt(c.getParameter(5)),Integer.parseInt(c.getParameter(6)));
                    c.getPlayer().getInventory().addItem(VariableUtil.getVarItem(itemStack));
                    return true;
                }
            }else if (c.is(1,"list")){
                for (OItem oItem : ItemData.getoItems()) {
                    List<BaseComponent[]> bcList=new ArrayList<>();
                    InteractiveKey ik=new InteractiveKey("&b"+oItem.getId());
                    ik.setClickAction(new ClickAction(ClickEvent.Action.RUN_COMMAND,"/oitem item get "+oItem.getId()));
                    InteractiveKey ik1=new InteractiveKey("&c+");
                    ik1.setClickAction(new ClickAction(ClickEvent.Action.SUGGEST_COMMAND,"/oitem item get "+oItem.getId()+" [level] [tier]"));
                    InteractiveKey ik2=new InteractiveKey("&c+");
                    ik2.setClickAction(new ClickAction(ClickEvent.Action.SUGGEST_COMMAND,"/oitem item get "+oItem.getId()+" [level] [minTier] [maxTier]"));
                    InteractiveKey ik3=new InteractiveKey("&c+");
                    ik3.setClickAction(new ClickAction(ClickEvent.Action.SUGGEST_COMMAND,"/oitem item give <player> "+oItem.getId()+" [level] [tier]"));
                    InteractiveKey ik4=new InteractiveKey("&c+");
                    ik4.setClickAction(new ClickAction(ClickEvent.Action.SUGGEST_COMMAND,"/oitem item give <player> "+oItem.getId()+" [level] [minTier] [maxTier]"));
                    bcList.add(ik.get());
                    bcList.add(ik1.get());
                    bcList.add(ik2.get());
                    bcList.add(ik3.get());
                    bcList.add(ik4.get());
                    c.getPlayer().spigot().sendMessage(TextProcessing.MergeList(bcList));
                }
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
