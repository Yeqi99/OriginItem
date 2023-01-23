package cn.originmc.plugins.originitem.data.object.field;

import java.util.ArrayList;
import java.util.List;

public class Field {
    private String id;
    private String name;
    private NBT nbt;
    private String info;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public NBT getNbt() {
        return nbt;
    }

    public void setNbt(NBT nbt) {
        this.nbt = nbt;
    }


    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
