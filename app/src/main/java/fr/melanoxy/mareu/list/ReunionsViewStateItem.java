package fr.melanoxy.mareu.list;

import androidx.annotation.NonNull;

import java.util.Objects;

public class ReunionsViewStateItem {

    private final long id;
    @NonNull
    private final String fieldTop;
    @NonNull
    private final String fieldBottom;

    public ReunionsViewStateItem(long id, @NonNull String fieldTop, @NonNull String fieldBottom) {
        this.id = id;
        this.fieldTop = fieldTop;
        this.fieldBottom = fieldBottom;
    }

    public long getId() {
        return id;
    }

    @NonNull
    public String getFieldTop() {
        return fieldTop;
    }

    @NonNull
    public String getFieldBottom() {
        return fieldBottom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReunionsViewStateItem that = (ReunionsViewStateItem) o;
        return id == that.id && fieldTop.equals(that.fieldTop) && fieldBottom.equals(that.fieldBottom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fieldTop, fieldBottom);
    }

    @NonNull
    @Override
    public String toString() {
        return "NeighboursViewStateItem{" +
                "id=" + id +
                ", name='" + fieldTop + '\'' +
                ", avatarUrl='" + fieldBottom + '\'' +
                '}';
    }
}
