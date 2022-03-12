package content.management.model;

public enum NodeType {
    SPORT("SPORT"),
    COMPETITION("COMPETITION");

    private final String value;

    NodeType(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}