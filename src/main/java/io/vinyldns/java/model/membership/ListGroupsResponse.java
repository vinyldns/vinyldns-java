package io.vinyldns.java.model.membership;

import java.util.Collection;

public class ListGroupsResponse {
    private Collection<Group> groups;
    private String groupNameFilter, startFrom, nextId;
    private Integer maxItems;

    public ListGroupsResponse(Collection<Group> groups, String groupNameFilter, String startFrom, String nextId,
                              Integer maxItems) {
        this.groups = groups;
        this.groupNameFilter = groupNameFilter;
        this.startFrom = startFrom;
        this.nextId = nextId;
        this.maxItems = maxItems;
    }

    public Collection<Group> getGroups() { return groups; }

    public void setGroups(Collection<Group> groups) { this.groups = groups; }

    public String getGroupNameFilter() { return groupNameFilter; }

    public void setGroupNameFilter(String groupNameFilter) { this.groupNameFilter = groupNameFilter; }

    public String getStartFrom() { return startFrom; }

    public void setStartFrom(String startFrom) { this.startFrom = startFrom; }

    public Integer getMaxItems() { return maxItems; }

    public void setMaxItems(Integer maxItems) { this.maxItems = maxItems; }

    @Override
    public String toString() {
        return "ListGroupsResponse{"
                + "groups='"
                + groups
                + '\''
                + ", groupNameFilter='"
                + groupNameFilter
                + '\''
                + ", startFrom='"
                + startFrom
                + '\''
                + ", nextId='"
                + nextId
                + '\''
                + ", maxItems="
                + maxItems
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListGroupsResponse that = (ListGroupsResponse) o;

        if (!groups.equals(that.groups)) return false;
        if (groupNameFilter != null ? !groupNameFilter.equals(that.groupNameFilter) : that.groupNameFilter != null)
        if (startFrom != null ? !startFrom.equals(that.startFrom) : that.startFrom != null)
            return false;
        if (nextId != null ? !nextId.equals(that.nextId) : that.nextId != null) return false;
        return maxItems != null ? maxItems.equals(that.maxItems) : that.maxItems == null;
    }

    @Override
    public int hashCode() {
        int result = groups.hashCode();
        result = 31 * result + (groupNameFilter != null ? groupNameFilter.hashCode() : 0);
        result = 31 * result + (startFrom != null ? startFrom.hashCode() : 0);
        result = 31 * result + (nextId != null ? nextId.hashCode() : 0);
        result = 31 * result + (maxItems != null ? maxItems.hashCode() : 0);
        return result;
    }
}
