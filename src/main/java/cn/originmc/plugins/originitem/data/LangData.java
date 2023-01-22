package cn.originmc.plugins.originitem.data;

import cn.originmc.plugins.origincore.util.data.yaml.YamlManager;
import cn.originmc.plugins.originitem.OriginItem;
public class LangData {
    private static YamlManager yamlManager;
    private static final String DATATYPE = "lang";
    public static void load(){
        yamlManager=new YamlManager(OriginItem.getInstance(),OriginItem.getPath(DATATYPE),OriginItem.getDirName(DATATYPE));
    }
    public static Object get(String langName,String key,Object defaultValue){
        return yamlManager.get(langName,"contains."+key,defaultValue);
    }
    public static void set(String langName,String key,Object value){
        if (!yamlManager.hasElement(langName)){
            yamlManager.create(langName);
        }
        yamlManager.set(langName,"contains."+key,value);
    }
    public static String getLangDisplay(String langName){
        return (String) yamlManager.get(langName,"display");
    }

    public static YamlManager getYamlManager() {
        return yamlManager;
    }

    public static void setYamlManager(YamlManager yamlManager) {
        LangData.yamlManager = yamlManager;
    }

}
