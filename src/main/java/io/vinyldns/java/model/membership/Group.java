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
}
