package cn.originmc.plugins.originitem.function;

import cn.originmc.plugins.originitem.data.InherentData;
import cn.originmc.plugins.originitem.data.object.inherent.Inherent;

public class InherentManager {
    public static Inherent getInherent(String id){
        for (Inherent inherent : InherentData.getInherentList()) {
            if (inherent.getId().equalsIgnoreCase(id)){
                return inherent;
            }
        }
        return null;
    }
}
