package ClassEnergie.Enum;

public enum TypeEnergie {
    // indetifer chaque pays avec son identifiant
    ELECTRICITE("ELERCTRICITE"),
    GAZ_NATUREL("GAZ_NATUREL"), // 01
    ESSENCE("ESSENCE"), // 02
    DIESEL("DIESEL"), // 03
    SUPERCARBURANTS_SP95("SUPERCARBURANTS_SP95"), // 04
    SUPERCARBURANTS_SP98("SUPERCARBURANTS_SP98"), // 05
    GPL("GPL"), // 06
    SUPERETHANOL("SUPERETHANOL"), // 07
    GAZOLE("GAZOLE"); // 08

    private String nom;

    TypeEnergie(String nom) {
        this.nom = nom;
    }

    public String toString() {
        return nom;
    }

    public static TypeEnergie getValue(String s) {
        s = s.toUpperCase();
        for (TypeEnergie te : TypeEnergie.values()) {
            if (te.nom.equals(s))
                return te;
        }
        return null;
    }
}