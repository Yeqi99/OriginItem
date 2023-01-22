package cn.originmc.plugins.originitem.function;

import cn.originmc.plugins.originitem.data.InfoData;
import cn.originmc.plugins.originitem.data.object.info.Info;

public class InfoManager {
    public static Info getInfo(String id){
        for (Info info : InfoData.getInfoList()) {
            if (info.getId().equalsIgnoreCase(id)){
                return info;
            }
        }
        return null;
    }
}
