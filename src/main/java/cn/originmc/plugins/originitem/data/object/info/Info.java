package cn.originmc.plugins.originitem.data.object.info;

import java.util.ArrayList;
import java.util.List;

public class Info {
    private String id;
    private List<List<String>> infoList=new ArrayList<>();


    public List<List<String>> getInfoList() {
        return infoList;
    }

    public void setInfoList(List<List<String>> infoList) {
        this.infoList = infoList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
