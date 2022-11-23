package Enum;

public enum TypeEnergie {
    // indetifer chaque pays avec son indicatif telephonique
    ELECTRIQUE("ELECTRIQUE"), // 00
    THERMIQUE("THERMIQUE"), // 01
    CHIMIQUE("CHIMIQUE"), // 02
    MECANIQUE("MECANIQUE"), // 03
    LUMINEUSE("LUMINEUSE"); // 04

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