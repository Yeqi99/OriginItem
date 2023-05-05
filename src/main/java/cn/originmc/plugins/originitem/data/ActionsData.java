package cn.originmc.plugins.originitem.data;

import cn.originmc.plugins.origincore.util.action.object.Actions;
import cn.originmc.plugins.origincore.util.data.yaml.YamlElement;
import cn.originmc.plugins.origincore.util.data.yaml.YamlManager;
import cn.originmc.plugins.originitem.OriginItem;

import java.util.ArrayList;
import java.util.List;

public class ActionsData {
    private static YamlManager yamlManager;
    private static final String DATATYPE = "actions";
    private static List<Actions> actionsList=new ArrayList<>();
    public static void load(){
        actionsList.clear();
        yamlManager=new YamlManager(OriginItem.getInstance(),OriginItem.getPath(DATATYPE),OriginItem.getDirName(DATATYPE),true);
        for (YamlElement ye : yamlManager.getYamlElements()) {
           Actions actions=new Actions((List<String>) yamlManager.get(ye.getId(),"actions"));
           actions.putObject("id",ye.getId());
           actionsList.add(actions);
        }
    }

    public static YamlManager getYamlManager() {
        return yamlManager;
    }

    public static void setYamlManager(YamlManager yamlManager) {
        ActionsData.yamlManager = yamlManager;
    }

    public static List<Actions> getActionsList() {
        return actionsList;
    }

    public static void setActionsList(List<Actions> actionsList) {
        ActionsData.actionsList = actionsList;
    }
}
