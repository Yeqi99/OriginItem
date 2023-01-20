package cn.originmc.plugins.originitem.data;

import cn.originmc.plugins.origincore.util.data.yaml.YamlElement;
import cn.originmc.plugins.origincore.util.data.yaml.YamlManager;
import cn.originmc.plugins.originitem.OriginItem;
import cn.originmc.plugins.originitem.data.object.info.Info;
import cn.originmc.plugins.originitem.data.object.inherent.Attributes;
import cn.originmc.plugins.originitem.data.object.inherent.Inherent;
import cn.originmc.plugins.originitem.data.object.inherent.Tier;
import cn.originmc.plugins.originitem.data.object.inherent.TierLevelSetting;

import java.util.ArrayList;
import java.util.List;

public class InherentData {
    private static YamlManager yamlManager;
    private static final String DATATYPE = "inherent";
    private static List<Inherent> inherentList=new ArrayList<>();
    public static void load(){
        inherentList.clear();
        yamlManager=new YamlManager(OriginItem.getInstance(),OriginItem.getPath(DATATYPE),OriginItem.getDirName(DATATYPE));
        for (YamlElement ye : yamlManager.getYamlElements()) {
            Inherent inherent=new Inherent();
            inherent.setId(ye.getId());
            inherent.setTierAmount((Integer) yamlManager.get(ye.getId(), "tier-amount"));
            inherent.setMaxLevel((Integer) yamlManager.get(ye.getId(), "level.max"));
            inherent.setMinLevel((Integer) yamlManager.get(ye.getId(), "level.min"));
            inherent.setAttributes(new Attributes((List<String>) yamlManager.get(ye.getId(),"field-set")));
            for (int i=0;i<inherent.getTierAmount();i++){
                Tier tier=new Tier();
                tier.setIndex(i);
                tier.setName((String) yamlManager.get(ye.getId(),i+".name"));
                tier.setColor((String) yamlManager.get(ye.getId(),i+".color"));
                tier.setWeight((Integer) yamlManager.get(ye.getId(),i+".weight"));
                tier.setPrefixList((List<String>) yamlManager.get(ye.getId(),i+".prefix"));
                tier.setAttributes(new Attributes((List<String>) yamlManager.get(ye.getId(),i+".field-set")));
                TierLevelSetting tierLevelSetting=new TierLevelSetting();
                for (String s : yamlManager.getKeyList(ye.getId(), i + ".level.special",false)) {
                    tierLevelSetting.getSpecialLvlPerAddAttributesMap().put(Integer.valueOf(s)
                ,new Attributes((List<String>) yamlManager.get(ye.getId(),i+".level.special."+s)));
                }
                tierLevelSetting.setLvlPerAddAttributes(new Attributes((List<String>) yamlManager.get(ye.getId(), i+".field-set")));
                tier.setTierLevelSetting(tierLevelSetting);
                inherent.getTiers().add(tier);
            }
            inherentList.add(inherent);
        }
    }
    public static YamlManager getYamlManager() {
        return yamlManager;
    }

    public static void setYamlManager(YamlManager yamlManager) {
        InherentData.yamlManager = yamlManager;
    }

    public static List<Inherent> getInherentList() {
        return inherentList;
    }

    public static void setInherentList(List<Inherent> inherentList) {
        InherentData.inherentList = inherentList;
    }
}
