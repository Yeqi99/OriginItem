package cn.originmc.plugins.originitem;

import cn.originmc.plugins.origincore.util.register.CommandRegister;
import cn.originmc.plugins.origincore.util.register.ListenerRegister;
import cn.originmc.plugins.origincore.util.text.Sender;
import cn.originmc.plugins.originitem.command.OriginItemCommand;
import cn.originmc.plugins.originitem.data.*;
import cn.originmc.plugins.originitem.function.page.PageListener;
import org.bukkit.plugin.java.JavaPlugin;


public final class OriginItem extends JavaPlugin {
    private static JavaPlugin instance;
    private static Sender sender;
    private static final String VERSION = "1.1.2";
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
        CommandRegister.register(this,new OriginItemCommand(),"OriginItem");
        if (getConfig().getBoolean("multi-page.enable")){
            ListenerRegister.register(this,new PageListener());
        }
        getSender().sendOnEnableMsgToLogger("OriginItem","Yeqi",VERSION,"Development");
    }

    @Override
    public void onDisable() {
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
