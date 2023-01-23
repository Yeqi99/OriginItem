package cn.originmc.plugins.originitem.data;

import cn.originmc.plugins.origincore.util.data.yaml.YamlElement;
import cn.originmc.plugins.origincore.util.data.yaml.YamlManager;
import cn.originmc.plugins.originitem.OriginItem;
import cn.originmc.plugins.originitem.data.object.external.External;
import cn.originmc.plugins.originitem.data.object.info.Info;
import cn.originmc.plugins.originitem.data.object.inherent.Inherent;
import cn.originmc.plugins.originitem.data.object.item.OItem;
import cn.originmc.plugins.originitem.function.ExternalManager;
import cn.originmc.plugins.originitem.function.InherentManager;

import java.util.ArrayList;
import java.util.List;

public class ItemData {
    private static YamlManager yamlManager;
    private static final String DATATYPE = "item";
    private static List<OItem> oItems=new ArrayList<>();
    public static void load(){
        oItems.clear();
        yamlManager=new YamlManager(OriginItem.getInstance(),OriginItem.getPath(DATATYPE),OriginItem.getDirName(DATATYPE),true);
        for (YamlElement ye : yamlManager.getYamlElements()) {
            OItem oItem=new OItem();
            oItem.setId(ye.getId());
            External external=ExternalManager.getExternal((String) yamlManager.get(ye.getId(),"external"));
            Inherent inherent= InherentManager.getInherent((String) yamlManager.get(ye.getId(),"inherent"));
            if (external==null){
                OriginItem.getSender().sendToLogger("&c"+ye.getId()+":错误的物品外在模板");
                continue;
            }
            if (inherent==null){
                OriginItem.getSender().sendToLogger("&c"+ye.getId()+":错误的物品内在模板");
                continue;
            }
            oItem.setExternal(external);
            oItem.setInherent(inherent);
            oItem.setType((String) yamlManager.get(ye.getId(),"type"));
            oItems.add(oItem);
        }
    }

    public static YamlManager getYamlManager() {
        return yamlManager;
    }

    public static void setYamlManager(YamlManager yamlManager) {
        ItemData.yamlManager = yamlManager;
    }

    public static List<OItem> getoItems() {
        return oItems;
    }

    public static void setoItems(List<OItem> oItems) {
        ItemData.oItems = oItems;
    }
}
