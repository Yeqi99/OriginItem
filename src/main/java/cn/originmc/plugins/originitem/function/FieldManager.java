package cn.originmc.plugins.originitem.function;

import cn.originmc.plugins.origincore.util.data.yaml.YamlManager;
import cn.originmc.plugins.originitem.data.FieldData;
import cn.originmc.plugins.originitem.data.object.field.Field;

public class FieldManager {
    public static Field getField(String id){
        for (Field field : FieldData.getFieldList()) {
            if (field.getId().equalsIgnoreCase(id)){
                return field;
            }
        }
        return null;
    }
    public static boolean hasField(String id){
        for (Field field : FieldData.getFieldList()) {
            if (field.getId().equalsIgnoreCase(id)){
                return true;
            }
        }
        return false;
    }
    public static boolean register(Field field){
        if (hasField(field.getId())){
            return false;
        }
        FieldData.getFieldList().add(field);
        YamlManager ym=FieldData.getYamlManager();
        ym.create(field.getId());
        ym.set(field.getId(),"name",field.getName());
        ym.set(field.getId(),"info",field.getInfo());
        ym.set(field.getId(),"value.nbt.space",field.getNbt().getSpaceName());
        ym.set(field.getId(),"value.nbt.data-type",field.getNbt().getDataType().toString());
        ym.set(field.getId(),"value.nbt.sign",field.getNbt().getSign());
        ym.set(field.getId(),"value.nbt.key",field.getNbt().getKey());
        ym.save(field.getId());
        return true;
    }

}
