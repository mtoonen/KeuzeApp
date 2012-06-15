package meine.util;
public enum Status {

    GEACTIVEERD("Geactiveerd"),
    BEZIG("Bezig"),
    AFGEROND("Afgerond");

    private String description;

    Status(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static Status fromDescription(String d) {
        for (Status c: Status.values()) {
            if (c.description.equals(d)) {
                return c;
            }
        }
        throw new IllegalArgumentException(d);
    }

    @Override
    public String toString(){
        return description;
    }
}
