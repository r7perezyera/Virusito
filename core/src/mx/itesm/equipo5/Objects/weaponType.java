package mx.itesm.equipo5.Objects;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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

    private static final List<weaponType> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static weaponType randomWeapon()  {
        weaponType value = NONE;
        while (value == NONE){
        value = VALUES.get(RANDOM.nextInt(SIZE));
        }
        return value;
    }

}
