package cn.originmc.plugins.originitem.function;

import cn.originmc.plugins.origincore.util.action.object.Actions;
import cn.originmc.plugins.origincore.util.text.FormatText;
import cn.originmc.plugins.originitem.data.ActionsData;
import org.bukkit.entity.Player;

public class ActionsManager {
    public static Actions getActions(String id){
        for (Actions actions : ActionsData.getActionsList()) {
            String i= (String) actions.getObjectMap().get("id");
            if (i.equalsIgnoreCase(id)){
                return actions;
            }
        }
        return null;
    }
    public static boolean execute(Player player, String id, FormatText parameter){
        Actions actions=getActions(id);
        if (actions==null){
            return false;
        }
        actions.putObject("self",player);
        actions.execute(parameter);
        return true;
    }
}
