package task;

import java.time.LocalDate;
import java.util.Collection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TaskService {
    private Map<Integer, Task> taskMap = new HashMap<>();
    private Collection<Task> removedTasks = new ArrayList<>() {
    };

    public void add(Task task) {
        taskMap.put(task.getId(), task);
    }

    public void edit(int id, Task task) {
        taskMap.replace(id, task);

    }

    public Task getTask(int id) {
        return taskMap.get(id);
    }

    public void removed(int id) {
        removedTasks.add(taskMap.get(id));
        taskMap.remove(id);
    }

    public Collection<Task> getAllTaskFromTaskList() {
        List<Task> resultList = new ArrayList<>();
        for (Map.Entry<Integer, Task> integerTaskEntry : taskMap.entrySet()) {
            var task = integerTaskEntry.getValue();
            resultList.add(task);
        }
        return resultList;
    }

    public Collection<Task> getRemoveTaskFromTaskList() {
        return this.removedTasks;
    }

    public Collection<Task> getAllByDate(LocalDate inputDate) {
        List<Task> resultList = new ArrayList<>();
        for (Map.Entry<Integer, Task> integerTaskEntry : taskMap.entrySet()) {
            var task = integerTaskEntry.getValue();
            if (task.isAvailable(inputDate)) {
                resultList.add(task);
            }
        }
        return resultList;
    }

    public Map<LocalDate, List<Task>> getAllTaskByGroup() {
        Map<LocalDate, List<Task>> taskByDate = new HashMap<>();
        for (Task value : taskMap.values()) {
            addGroupedTask(taskByDate, value);
        }
        return taskByDate;
    }

    private void addGroupedTask(Map<LocalDate, List<Task>> taskByDate, Task task) {
        if (!taskByDate.containsKey(task.getDateTime().toLocalDate())) {
            taskByDate.put(task.getDateTime().toLocalDate(), List.of(task));
        } else {
            var groupedTask = taskByDate.get(task.getDateTime().toLocalDate());
            groupedTask.add(task);
            taskByDate.put(task.getDateTime().toLocalDate(), List.of(task));
        }
    }
}

