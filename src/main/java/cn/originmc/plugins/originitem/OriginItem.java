package cn.originmc.plugins.originitem;

import cn.originmc.plugins.origincore.util.register.CommandRegister;
import cn.originmc.plugins.origincore.util.register.CompleterRegister;
import cn.originmc.plugins.origincore.util.register.ListenerRegister;
import cn.originmc.plugins.origincore.util.text.Sender;
import cn.originmc.plugins.originitem.command.OriginItemCommand;
import cn.originmc.plugins.originitem.command.OriginItemTabCompleter;
import cn.originmc.plugins.originitem.data.*;
import cn.originmc.plugins.originitem.function.event.ItemActionListener;
import cn.originmc.plugins.originitem.function.page.PageListener;
import cn.originmc.plugins.originitem.papi.FieldExpansion;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;


public final class OriginItem extends JavaPlugin {
    private static JavaPlugin instance;
    private static Sender sender;
    private static final String VERSION = "1.2.0";
    public static JavaPlugin getInstance() {
        return instance;
    }

    public static Sender getSender() {
        return sender;
    }

    @Override
    public void onEnable() {
        instance=this;
        sender=new Sender(this);
        saveDefaultConfig();
        FieldData.load();
        ExternalData.load();
        InfoData.load();
        InherentData.load();
        ItemData.load();
        LangData.load();
        ActionsData.load();
        CommandRegister.register(this,new OriginItemCommand(),"OriginItem");
        CompleterRegister.register(this,new OriginItemTabCompleter(),"OriginItem");
        ListenerRegister.register(this,new ItemActionListener());
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new FieldExpansion().register();
        }
        if (getConfig().getBoolean("multi-page.enable")){
            ListenerRegister.register(this,new PageListener());
        }
        ItemActionListener.lastShiftToggleTimeMap=new HashMap<>();
        getSender().sendOnEnableMsgToLogger("OriginItem","Yeqi",VERSION,"Development");
    }

    @Override
    public void onDisable() {
        ItemActionListener.lastShiftToggleTimeMap.clear();
        getSender().sendOnDisableMsgToLogger("OriginItem","Yeqi",VERSION,"Development");
    }
    public static String getPath(String type){
        return getInstance().getConfig().getString("file."+type+".path",getInstance().getDataFolder().getPath()+"/"+type);
    }
    public static String getDirName(String type){
        return getInstance().getConfig().getString("file."+type+".dir-name",getInstance().getDataFolder().getPath()+"/"+type);
    }
    public static String getLangName(){
        return getInstance().getConfig().getString("lang");
    }
}
