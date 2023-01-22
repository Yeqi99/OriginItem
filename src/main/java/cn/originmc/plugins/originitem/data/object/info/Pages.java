package cn.originmc.plugins.originitem.data.object.info;

import cn.originmc.plugins.originitem.function.InfoManager;

import java.util.ArrayList;
import java.util.List;

public class Pages {
    private List<Info> infoList=new ArrayList<>();
    public Pages(List<String> infoIdList){
        for (String s : infoIdList) {
            Info info= InfoManager.getInfo(s);
            if (info!=null){
                addInfo(info);
            }
        }
    }
    public int getSize(){
        int amount=0;
        for (Info info : infoList) {
            amount+=info.getInfoList().size();
        }
        return amount;
    }
    public void addInfo(Info info){
        getInfoList().add(info);
    }
    public List<String> getPage(int index,List<String> defaultList){
        if (index>=getSize()||index<0){
            return defaultList;
        }
        int amount=0;
        for (Info info : infoList) {
            for (List<String> strings : info.getInfoList()) {
                if (amount==index){
                    return strings;
                }
                amount++;
            }
        }
        return defaultList;
    }
    public List<Info> getInfoList() {
        return infoList;
    }

    public void setInfoList(List<Info> infoList) {
        this.infoList = infoList;
    }
}
