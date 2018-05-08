enum EnumFS {
    EMPTY(0),
    BOMB(9);

    private int value;

    private EnumFS(int value) {
        this.value = value;
    }

    static EnumFS fromValue(int value) {
        for (EnumFS my : EnumFS.values()) {
            if (my.value == value) {
                return my;
            }
        }
        return null;
    }

    int value() {
        return value;
    }
}