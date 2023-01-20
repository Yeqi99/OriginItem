package cn.originmc.plugins.originitem.function;

import cn.originmc.plugins.originitem.data.ExternalData;
import cn.originmc.plugins.originitem.data.object.external.External;

public class ExternalManager {
    public static External getExternal(String id){
        for (External external : ExternalData.getExternalList()) {
            if (external.getId().equalsIgnoreCase(id)){
                return external;
            }
        }
        return null;
    }
}
