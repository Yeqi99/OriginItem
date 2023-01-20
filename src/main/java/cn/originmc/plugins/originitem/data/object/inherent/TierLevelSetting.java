package cn.originmc.plugins.originitem.data.object.inherent;

import java.util.HashMap;
import java.util.Map;

public class TierLevelSetting {
    private Attributes lvlPerAddAttributes;
    private Map<Integer,Attributes> specialLvlPerAddAttributesMap=new HashMap<>();

    public Attributes getLvlPerAddAttributes() {
        return lvlPerAddAttributes;
    }

    public void setLvlPerAddAttributes(Attributes lvlPerAddAttributes) {
        this.lvlPerAddAttributes = lvlPerAddAttributes;
    }

    public Map<Integer, Attributes> getSpecialLvlPerAddAttributesMap() {
        return specialLvlPerAddAttributesMap;
    }

    public void setSpecialLvlPerAddAttributesMap(Map<Integer, Attributes> specialLvlPerAddAttributesMap) {
        this.specialLvlPerAddAttributesMap = specialLvlPerAddAttributesMap;
    }
}
