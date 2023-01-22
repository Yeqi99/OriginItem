package cn.originmc.plugins.originitem.data;

import cn.originmc.plugins.origincore.util.data.yaml.YamlElement;
import cn.originmc.plugins.origincore.util.data.yaml.YamlManager;
import cn.originmc.plugins.originitem.OriginItem;
import cn.originmc.plugins.originitem.data.object.info.Info;

import java.util.ArrayList;
import java.util.List;

public class InfoData {
    private static YamlManager yamlManager;
    private static final String DATATYPE = "info";
    private static List<Info> infoList=new ArrayList<>();
    public static void load(){
        infoList.clear();
        yamlManager=new YamlManager(OriginItem.getInstance(),OriginItem.getPath(DATATYPE),OriginItem.getDirName(DATATYPE));
        for (YamlElement ye : yamlManager.getYamlElements()) {
            Info info=new Info();
            info.setId(ye.getId());
            List<String> keyList=yamlManager.getKeyList(ye.getId(),"contains",false);
            if (keyList==null){
                continue;
            }
            for (String s : keyList) {
                info.getInfoList().add((List<String>) yamlManager.get(ye.getId(),"contains."+s));
            }
            infoList.add(info);
        }
    }

    public static YamlManager getYamlManager() {
        return yamlManager;
    }

    public static void setYamlManager(YamlManager yamlManager) {
        InfoData.yamlManager = yamlManager;
    }

    public static List<Info> getInfoList() {
        return infoList;
    }

    public static void setInfoList(List<Info> infoList) {
        InfoData.infoList = infoList;
    }
}
