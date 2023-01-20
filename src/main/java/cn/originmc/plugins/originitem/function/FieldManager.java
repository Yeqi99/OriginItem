package cn.originmc.plugins.originitem.function;

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
}
