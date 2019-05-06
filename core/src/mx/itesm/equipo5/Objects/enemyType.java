package mx.itesm.equipo5.Objects;

public enum enemyType {
    FLOATER,
    FLOATBOSS,
    CRAWLER,
    CRAWLBOSS,
    TEETH,
    TEEHTBOSS;
    private static enemyType[] vals = values();
    public enemyType next()
    {
        return vals[(this.ordinal()+1) % vals.length];
    }


}
