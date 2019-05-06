package mx.itesm.equipo5.Objects;

public enum difficulty {
    EASY,
    MEDIUM,
    HARD;
    private static difficulty[] vals = values();
    public difficulty next()
    {
        return vals[(this.ordinal()+1) % vals.length];
    }

}
