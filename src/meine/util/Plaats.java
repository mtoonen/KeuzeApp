package meine.util;


public enum Plaats{

    LINKS("Links"),
    RECHTS("Rechts"),
    RECHTSONDER("Rechtsonder"),
    RECHTSBOVEN("Rechtsboven"),
    LINKSONDER("Linksonder"),
    LINKSBOVEN("Linksboven");
    private String description;

    Plaats(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return description;
    }
}
