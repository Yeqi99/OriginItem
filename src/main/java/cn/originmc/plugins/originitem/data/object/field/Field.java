package cn.originmc.plugins.originitem.data.object.field;

public class Field {
    private String id;
    private String name;
    private NBT nbt;
    private String format;


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

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

}
