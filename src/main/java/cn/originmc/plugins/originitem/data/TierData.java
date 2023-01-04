package cn.originmc.plugins.originitem.data;

import cn.originmc.plugins.origincore.util.data.yaml.YamlManager;
import cn.originmc.plugins.originitem.OriginItem;


public class TierData {
    private static YamlManager yamlManager;
    private static final String DATATYPE = "tier";
    public static void get(){
        yamlManager=new YamlManager(OriginItem.getInstance(),OriginItem.getPath(DATATYPE),OriginItem.getDirName(DATATYPE));
    }
    public static YamlManager getYamlManager() {
        return yamlManager;
    }

    public static void setYamlManager(YamlManager yamlManager) {
        TierData.yamlManager = yamlManager;
    }

}
