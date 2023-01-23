package cn.originmc.plugins.originitem.data;

import cn.originmc.plugins.origincore.util.data.yaml.YamlElement;
import cn.originmc.plugins.origincore.util.data.yaml.YamlManager;
import cn.originmc.plugins.origincore.util.item.DataType;
import cn.originmc.plugins.originitem.OriginItem;
import cn.originmc.plugins.originitem.data.object.field.Field;
import cn.originmc.plugins.originitem.data.object.field.NBT;

import java.util.ArrayList;
import java.util.List;

public class FieldData {
    private static YamlManager yamlManager;
    private static final String DATATYPE = "field";
    private static List<Field> fieldList=new ArrayList<>();

    public static void load(){
        fieldList.clear();
        yamlManager=new YamlManager(OriginItem.getInstance(),OriginItem.getPath(DATATYPE),OriginItem.getDirName(DATATYPE),true);
        for (YamlElement ye : yamlManager.getYamlElements()) {
            Field field=new Field();
            field.setId(ye.getId());
            field.setName((String) yamlManager.get(ye.getId(),"name"));
            field.setInfo((String) yamlManager.get(ye.getId(),"info"));
            NBT nbt=new NBT();
            nbt.setKey((String)yamlManager.get(ye.getId(),"value.nbt.key"));
            nbt.setDataType(DataType.valueOf((String)yamlManager.get(ye.getId(),"value.nbt.data-type")));
            nbt.setSpaceName((String)yamlManager.get(ye.getId(),"value.nbt.space"));
            nbt.setSign((int)yamlManager.get(ye.getId(),"value.nbt.sign",0));
            field.setNbt(nbt);
            fieldList.add(field);
        }
    }
    public static YamlManager getYamlManager() {
        return yamlManager;
    }

    public static void setYamlManager(YamlManager yamlManager) {
        FieldData.yamlManager = yamlManager;
    }


    public static List<Field> getFieldList() {
        return fieldList;
    }

    public static void setFieldList(List<Field> fieldList) {
        FieldData.fieldList = fieldList;
    }
}
