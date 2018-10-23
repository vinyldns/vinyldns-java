package io.vinyldns.java.model.membership;

import java.util.Set;
import org.joda.time.DateTime;

public class Group {
    private String name, email, description, id;
    private DateTime created;
    private GroupStatus status;
    private Set<String> memberIds, adminUserIds;

    public Group(String name, String email, String description, String id, DateTime created, GroupStatus status,
                 Set<String> memberIds, Set<String> adminUserIds) {
        this.name = name;
        this.email = email;
        this.description = description;
        this.id = id;
        this.status = status;
        this.memberIds = memberIds;
        this.adminUserIds = adminUserIds;
    }

    public Group(String name, String email, String id, DateTime created, GroupStatus status,
                 Set<String> memberIds, Set<String> adminUserIds) {
        this.name = name;
        this.email = email;
        this.id = id;
        this.status = status;
        this.memberIds = memberIds;
        this.adminUserIds = adminUserIds;
    }

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
          + ", status="
          + status
          + ", memberIds="
          + memberIds
          + ", adminUserIds="
          + adminUserIds
          + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Group group = (Group) o;

        if (!name.equals(group.name)) return false;
        if (!email.equals(group.email)) return false;
        if (description != null ? !description.equals(group.description) : group.description != null) return false;
        if (!id.equals(group.id)) return false;
        if (status != group.status) return false;
        if (!memberIds.equals(group.memberIds)) return false;
        return adminUserIds.equals(group.adminUserIds);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + id.hashCode();
        result = 31 * result + status.hashCode();
        result = 31 * result + memberIds.hashCode();
        result = 31 * result + adminUserIds.hashCode();
        return result;
    }
}
