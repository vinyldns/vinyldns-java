package io.vinyldns.java.model.membership;

public class MemberId {
    private final String id;
    public MemberId(String id) {
        this.id = id;
    }

    public String getId() { return id; }

    @Override
    public String toString() {
        return "MemberId{"
            + "id='"
            + id
            + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MemberId memberId = (MemberId) o;

        return id.equals(memberId.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
