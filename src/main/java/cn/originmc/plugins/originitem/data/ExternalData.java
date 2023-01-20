package cn.originmc.plugins.originitem.data;

import cn.originmc.plugins.origincore.util.data.yaml.YamlElement;
import cn.originmc.plugins.origincore.util.data.yaml.YamlManager;
import cn.originmc.plugins.origincore.util.item.DataType;
import cn.originmc.plugins.originitem.OriginItem;
import cn.originmc.plugins.originitem.data.object.external.External;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class ExternalData {
    private static YamlManager yamlManager;
    private static final String DATATYPE = "external";
    private static List<External> externalList=new ArrayList<>();
    public static void load(){
        externalList.clear();
        yamlManager=new YamlManager(OriginItem.getInstance(),OriginItem.getPath(DATATYPE),OriginItem.getDirName(DATATYPE));
        for (YamlElement ye : yamlManager.getYamlElements()) {
            External external=new External();
            external.setId(ye.getId());
            external.setDisplay((String) yamlManager.get(ye.getId(),"display"));
            external.setMaterial(Material.valueOf((String) yamlManager.get(ye.getId(),"material")));
            external.setCustomModelData((int)yamlManager.get(ye.getId(),"custom-model-data"));
            external.setLore((List<String>) yamlManager.get(ye.getId(), "info.normal"));
            external.setInfoIdList((List<String>) yamlManager.get(ye.getId(), "info.info-add"));
            externalList.add(external);
        }
    }

    public static YamlManager getYamlManager() {
        return yamlManager;
    }

    public static void setYamlManager(YamlManager yamlManager) {
        ExternalData.yamlManager = yamlManager;
    }

    public static List<External> getExternalList() {
        return externalList;
    }

    public static void setExternalList(List<External> externalList) {
        ExternalData.externalList = externalList;
    }
}
