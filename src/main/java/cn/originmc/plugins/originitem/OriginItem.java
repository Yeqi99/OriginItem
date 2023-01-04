package cn.originmc.plugins.originitem;

import cn.originmc.plugins.origincore.util.text.Sender;
import org.bukkit.plugin.java.JavaPlugin;

public final class OriginItem extends JavaPlugin {
    private static JavaPlugin instance;
    private static Sender sender;
    private static final String VERSION = "1.0.0";
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
        getSender().sendOnEnableMsgToLogger("OriginItem","Yeqi",VERSION,"Development");
    }

    @Override
    public void onDisable() {
        getSender().sendOnDisableMsgToLogger("OriginItem","Yeqi",VERSION,"Development");
    }
    public static String getPath(String type){
        return getInstance().getConfig().getString("file."+type+".path");
    }
    public static String getDirName(String type){
        return getInstance().getConfig().getString("file."+type+".dir-name");
    }
}
