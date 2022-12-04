package task;


import java.lang.annotation.Annotation;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public  class Task implements Repeatable {
    private int id;
    private String title;
    private String description;
    private Type type;
    private LocalDateTime dateTime;

    private static int idGenerator = 0;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Task(String title, String description, Type type, LocalDateTime dateTime) {
        this.id = idGenerator++;
        this.title = title;
        this.description = description;
        this.type = type;
        this.dateTime = dateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && title.equals(task.title) && Objects.equals(description, task.description) && type == task.type
                && Objects.equals(dateTime, task.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, type, dateTime);
    }
    @Override
    public boolean isAvailable(LocalDate inputDate) {
        return inputDate.equals(getDateTime().toLocalDate());
    }

    @Override
    public String toString() {
        return "Задача {" +
                "id" + id +
                ",title='" + title + '\'' +
                ",description='" + description + '\'' +
                ",type =" + type +
                ", dataTime=" + dateTime +
                '}';
    }
}