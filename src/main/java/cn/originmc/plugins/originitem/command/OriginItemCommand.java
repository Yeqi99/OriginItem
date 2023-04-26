package cn.originmc.plugins.originitem.command;

import cn.originmc.plugins.origincore.util.command.CommandUtil;
import cn.originmc.plugins.origincore.util.item.DataType;
import cn.originmc.plugins.origincore.util.item.Item;
import cn.originmc.plugins.origincore.util.random.Randomizer;
import cn.originmc.plugins.origincore.util.text.InteractiveKey;
import cn.originmc.plugins.origincore.util.text.TextProcessing;
import cn.originmc.plugins.origincore.util.text.interactivekey.objcet.ClickAction;
import cn.originmc.plugins.originitem.OriginItem;
import cn.originmc.plugins.originitem.data.*;
import cn.originmc.plugins.originitem.data.object.field.Field;
import cn.originmc.plugins.originitem.data.object.item.InstanceItem;
import cn.originmc.plugins.originitem.data.object.item.OItem;
import cn.originmc.plugins.originitem.function.FieldManager;
import cn.originmc.plugins.originitem.function.ItemManager;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class OriginItemCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        CommandUtil c=new CommandUtil(sender,command,label,args);
        if (c.getParameterAmount()==0){
            if (!c.isAdmin()){
                if (!c.hasPerm("OriginItem.help")){
                    OriginItem.getSender().sendToSender(sender,(String) LangData.get(OriginItem.getLangName(),"Insufficient-permissions","&c权限不足"));
                    return true;
                }
            }
            List<String> help=new ArrayList<>();
            help.add("&c/oitem item list");
            help.add("&8获得所有可获取物品列表");
            help.add("&c/oitem item get <itemID> [level] [tierIndex]");
            help.add("&8获得指定ID的物品，可指定等级和稀有度,不指定部分随机获取");
            help.add("&c/oitem item get <itemID> [level] [minTierIndex] [maxTierIndex]");
            help.add("&8获得指定ID的物品，可以按照物品稀有度区间随机(按原权重)");
            help.add("&c/oitem up [level]");
            help.add("&8提升手中物品等级 可指定等级");
            help.add("&c/oitem down [level]");
            help.add("&8降低手中物品等级 可指定等级");
            help.add("&c/oitem * <value> <fieldId>");
            help.add("&8使手中物品某字段值乘以一个数字(只能为数字类型字段)");
            help.add("&c/oitem / <value> <fieldId>");
            help.add("&8使手中物品某字段值除以一个数字(只能为数字类型字段)");
            help.add("&c/oitem + <value> <fieldId>");
            help.add("&8使手中物品某字段值加一个数字(只能为数字类型字段)");
            help.add("&c/oitem fields");
            help.add("&8查看所有已加载字段");
            help.add("&c/oitem reload");
            help.add("&8重载插件");
            OriginItem.getSender().sendToSender(sender,help);//(String) LangData.get(OriginItem.getLangName(),"insufficient-parameters","&c参数不足")
            return true;
        }
        if (c.is(0,"item")){
            if (!c.isAdmin()){
                if (!c.hasPerm("OriginItem.item")){
                    OriginItem.getSender().sendToSender(sender,(String) LangData.get(OriginItem.getLangName(),"Insufficient-permissions","&c权限不足"));
                    return true;
                }
            }
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
                    InstanceItem instanceItem=new InstanceItem(itemStack);
                    instanceItem.refreshVar();
                    instanceItem.refreshPAPIVar(c.getPlayer());
                    c.getPlayer().getInventory().addItem(instanceItem.getItemStack());
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
                    InstanceItem instanceItem=new InstanceItem(itemStack);
                    instanceItem.refreshVar();
                    instanceItem.refreshPAPIVar(c.getPlayer());
                    c.getPlayer().getInventory().addItem(instanceItem.getItemStack());
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
                    InstanceItem instanceItem=new InstanceItem(itemStack);
                    instanceItem.refreshVar();
                    instanceItem.refreshPAPIVar(c.getPlayer());
                    c.getPlayer().getInventory().addItem(instanceItem.getItemStack());
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
                    InstanceItem instanceItem=new InstanceItem(itemStack);
                    instanceItem.refreshVar();
                    instanceItem.refreshPAPIVar(c.getPlayer());
                    c.getPlayer().getInventory().addItem(instanceItem.getItemStack());
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
                    InstanceItem instanceItem=new InstanceItem(itemStack);
                    instanceItem.refreshVar();
                    instanceItem.refreshPAPIVar(c.getPlayer());
                    player.getInventory().addItem(instanceItem.getItemStack());
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
                    InstanceItem instanceItem=new InstanceItem(itemStack);
                    instanceItem.refreshVar();
                    instanceItem.refreshPAPIVar(c.getPlayer());
                    player.getInventory().addItem(instanceItem.getItemStack());
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
                    InstanceItem instanceItem=new InstanceItem(itemStack);
                    instanceItem.refreshVar();
                    instanceItem.refreshPAPIVar(c.getPlayer());
                    player.getInventory().addItem(instanceItem.getItemStack());
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
                    InstanceItem instanceItem=new InstanceItem(itemStack);
                    instanceItem.refreshVar();
                    instanceItem.refreshPAPIVar(c.getPlayer());
                    player.getInventory().addItem(instanceItem.getItemStack());
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
            if (!c.isAdmin()){
                if (!c.hasPerm("OriginItem.reload")){
                    OriginItem.getSender().sendToSender(sender,(String) LangData.get(OriginItem.getLangName(),"Insufficient-permissions","&c权限不足"));
                    return true;
                }
            }
            reload();
            OriginItem.getSender().sendToSender(sender,(String) LangData.get(OriginItem.getLangName(),"reload-succeeded","&a重载成功"));
        }else if (c.is(0,"up")){
            if (!c.isAdmin()){
                if (!c.hasPerm("OriginItem.up")){
                    OriginItem.getSender().sendToSender(sender,(String) LangData.get(OriginItem.getLangName(),"Insufficient-permissions","&c权限不足"));
                    return true;
                }
            }
            ItemStack itemStack=c.getPlayer().getInventory().getItemInMainHand();
            if (itemStack.getType()== Material.AIR){
                return true;
            }
            InstanceItem instanceItem=new InstanceItem(c.getPlayer().getInventory().getItemInMainHand());
            int amount=1;
            if (c.getParameterAmount()==2){
                amount= Integer.parseInt(c.getParameter(1));
            }
            instanceItem.upLevel(amount);
            instanceItem.refreshLore();
            instanceItem.refreshVar();
            instanceItem.refreshPAPIVar(c.getPlayer());
            c.getPlayer().getInventory().setItemInMainHand(instanceItem.getItemStack());
        }else if (c.is(0,"down")){
            if (!c.isAdmin()){
                if (!c.hasPerm("OriginItem.down")){
                    OriginItem.getSender().sendToSender(sender,(String) LangData.get(OriginItem.getLangName(),"Insufficient-permissions","&c权限不足"));
                    return true;
                }
            }
            ItemStack itemStack=c.getPlayer().getInventory().getItemInMainHand();
            if (itemStack.getType()== Material.AIR){
                return true;
            }
            InstanceItem instanceItem=new InstanceItem(c.getPlayer().getInventory().getItemInMainHand());
            int amount=1;
            if (c.getParameterAmount()==2){
                amount= Integer.parseInt(c.getParameter(1));
            }
            instanceItem.downLevel(amount);
            instanceItem.refreshLore();
            instanceItem.refreshVar();
            instanceItem.refreshPAPIVar(c.getPlayer());
            c.getPlayer().getInventory().setItemInMainHand(instanceItem.getItemStack());
        }else if (c.is(0,"*")){
            if (!c.isAdmin()){
                if (!c.hasPerm("OriginItem.*")){
                    OriginItem.getSender().sendToSender(sender,(String) LangData.get(OriginItem.getLangName(),"Insufficient-permissions","&c权限不足"));
                    return true;
                }
            }
            double m= Double.parseDouble(c.getParameter(1));
            InstanceItem instanceItem=new InstanceItem(c.getPlayer().getInventory().getItemInMainHand());
            instanceItem.fieldMultiplier(FieldManager.getField(c.getParameter(2)),m);
            instanceItem.refreshLore();
            instanceItem.refreshVar();
            instanceItem.refreshPAPIVar(c.getPlayer());
            c.getPlayer().getInventory().setItemInMainHand(instanceItem.getItemStack());
        }else if (c.is(0,"/")){
            if (!c.isAdmin()){
                if (!c.hasPerm("OriginItem./")){
                    OriginItem.getSender().sendToSender(sender,(String) LangData.get(OriginItem.getLangName(),"Insufficient-permissions","&c权限不足"));
                    return true;
                }
            }
            double m= Double.parseDouble(c.getParameter(1));
            InstanceItem instanceItem=new InstanceItem(c.getPlayer().getInventory().getItemInMainHand());
            instanceItem.reFieldMultiplier(FieldManager.getField(c.getParameter(2)),m);
            instanceItem.refreshLore();
            instanceItem.refreshVar();
            instanceItem.refreshPAPIVar(c.getPlayer());
            c.getPlayer().getInventory().setItemInMainHand(instanceItem.getItemStack());
        }else if (c.is(0,"+")){
            if (!c.isAdmin()){
                if (!c.hasPerm("OriginItem.+")){
                    OriginItem.getSender().sendToSender(sender,(String) LangData.get(OriginItem.getLangName(),"Insufficient-permissions","&c权限不足"));
                    return true;
                }
            }
            double m= Double.parseDouble(c.getParameter(1));
            InstanceItem instanceItem=new InstanceItem(c.getPlayer().getInventory().getItemInMainHand());
            instanceItem.addNumFieldValue(FieldManager.getField(c.getParameter(2)),m);
            instanceItem.refreshLore();
            instanceItem.refreshVar();
            instanceItem.refreshPAPIVar(c.getPlayer());
            c.getPlayer().getInventory().setItemInMainHand(instanceItem.getItemStack());
        }else if (c.is(0,"fields")){
            if (!c.isAdmin()){
                if (!c.hasPerm("OriginItem.fields")){
                    OriginItem.getSender().sendToSender(sender,(String) LangData.get(OriginItem.getLangName(),"Insufficient-permissions","&c权限不足"));
                    return true;
                }
            }
            for (Field field : FieldData.getFieldList()) {
                OriginItem.getSender().sendToSender(c.getSender(),field.getName());
            }
        }
        return true;
    }
    public static void reload(){
        OriginItem.getInstance().reloadConfig();
        ExternalData.load();
        FieldData.load();
        InfoData.load();
        InherentData.load();
        ItemData.load();
        LangData.load();
    }
}
