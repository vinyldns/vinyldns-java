package io.vinyldns.java.model.membership;

import java.util.Collections;
import java.util.Set;
import org.joda.time.DateTime;

public class Group {
    private final String name, email;
    private String id, description; // optional
    private DateTime created; // optional
    private GroupStatus status;
    private final Set<UserInfo> members, admins;

    public Group(String name, String email, Set<UserInfo> members, Set<UserInfo> admins) {
        this.name = name;
        this.email = email;
        this.members = members;
        this.admins = admins;
    }

    public Group(String name, String email) {
        this(name, email, Collections.emptySet(), Collections.emptySet());
    }

    public Group(String name, String email, Set<UserInfo> members, Set<UserInfo> admins, String id,
                 String description, DateTime created, GroupStatus status) {
        this(name, email, members, admins);
        this.id = id;
        this.description = description;
        this.created = created;
        this.status = status;
    }

    public String getName() { return name; }

    public String getEmail() { return email; }

    public void setId(String id) { this.id = id; }

    public String getId() { return id; }

    public void setDescription(String description) { this.description = description; }

    public String getDescription() { return description; }

    public void setCreated(DateTime created) { this.created = created; }

    public DateTime getCreated() { return created; }

    public void setStatus(GroupStatus status) { this.status = status; }

    public GroupStatus getStatus() { return status; }

    public Set<UserInfo> getMembers() { return members; }

    public Set<UserInfo> getAdmins() { return admins; }

    @Override
    public String toString() {
      return "Group{"
          + "name='"
          + name
          + '\''
          + ", email='"
          + email
          + '\''
          + ", description='"
          + description
          + '\''
          + ", id='"
          + id
          + '\''
          + ", created='"
          + created
          + '\''
          + ", status="
          + status
          + ", members="
          + members
          + ", admins="
          + admins
          + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Group group = (Group) o;

        if (!name.equals(group.name)) return false;
        if (!email.equals(group.email)) return false;
        if (!id.equals(group.id)) return false;
        if (!created.equals(group.created)) return false;
        if (description != null ? !description.equals(group.description) : group.description != null) return false;
        if (status != group.status) return false;
        if (!members.equals(group.members)) return false;
        return admins.equals(group.admins);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + id.hashCode();
        result = 31 * result + created.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + status.hashCode();
        result = 31 * result + members.hashCode();
        result = 31 * result + admins.hashCode();
        return result;
    }
}
