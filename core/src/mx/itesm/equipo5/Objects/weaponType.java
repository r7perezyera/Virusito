package mx.itesm.equipo5.Objects;

public enum weaponType {
    NONE,
    PISTOL,
    SHOTGUN,
    BAZOOKA;
    private static weaponType[] vals = values();
    public weaponType next()
    {
        return vals[(this.ordinal()+1) % vals.length];
    }

}
