import task.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;


public class Main {
    private static Scanner scanner;

    public static void main(String[] args) throws IOException {
        TaskService taskService = new TaskService();
        try (Scanner scanner = new Scanner(System.in)) {
            label:
            while (true) {
                printMenu();
                System.out.println("Выберите пункт меню:");
                if (scanner.hasNextInt()) {
                    int menu = scanner.nextInt();
                    switch (menu) {
                        case 1:
                            addTask(taskService, scanner);
                            break;
                        case 2:
                            removeTask(taskService, scanner);
                            break;
                        case 3:
                            getTaskByDay(taskService, scanner);
                            break;
                        case 4:
                            getAllTask(taskService);
                            break;
                        case 5:
                            editTask(taskService, scanner);
                            break;
                        case 6:
                            getRemoveTask(taskService);
                            break;
                        case 0:
                            break label;
                    }
                } else {
                        scanner.next();
                        System.out.println("Выберите пункт меню из списка!");
                    }
            }
        }
    }

    private static void getAllTask (TaskService taskService) throws IOException {
        var allTaskByDay = taskService.getAllTaskFromTaskList();
        System.out.println("Список задач :");
        for (Task task : allTaskByDay) {
            System.out.println("ID задачи: " + task.getId());
            System.out.println("Заголовок задачи: " + task.getTitle());
            System.out.println("Описание задачи: " + task.getDescription());
            String type = task.getType() == Type.PERSONAL ? "Личный" : "Рабочий";
            System.out.println("Тип задачи: " + type);
            System.out.println("Дата задачи: " + task.getDateTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
            System.out.println("Время задачи: " + task.getDateTime().format(DateTimeFormatter.ofPattern("HH:mm")));
            System.out.println("\n");
        }
        System.in.read();
    }

    private static void getRemoveTask (TaskService taskService) throws IOException {
        var allTaskByDay = taskService.getRemoveTaskFromTaskList();
        System.out.println("Список удаленных задач :");
        for (Task task : allTaskByDay) {
            System.out.println("ID задачи: " + task.getId());
            System.out.println("Заголовок задачи: " + task.getTitle());
            System.out.println("Описание задачи: " + task.getDescription());
            String type = task.getType() == Type.PERSONAL ? "Личный" : "Рабочий";
            System.out.println("Тип задачи: " + type);
            System.out.println("Дата задачи: " + task.getDateTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
            System.out.println("Время задачи: " + task.getDateTime().format(DateTimeFormatter.ofPattern("HH:mm")));
            System.out.println("\n");
        }
        System.in.read();
    }

        private static LocalDate getDateFromUser (Scanner scanner){
            LocalDate result = null;
            boolean forceUserToAnswer = true;
            while (forceUserToAnswer) {
                try {
                    System.out.println("Введите дату задачи в формате dd.mm.yyyy:");
                    String date = scanner.nextLine();
                    result = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
                    forceUserToAnswer = false;
                } catch (Exception e) {
                    System.out.println("Введите еще раз, пожалуйста!");
                }
            }
            return result;
        }

    private static void editTask (TaskService taskService, Scanner scanner){
        System.out.println("Введите id задачи которую необходимо отредактировать");
        int id = scanner.nextInt();
        System.out.println("Введите заголовок задачи");
        String name = scanner.next();
        scanner.nextLine();
        System.out.println("Введите описание задачи:");
        String description = scanner.nextLine();
        taskService.edit( id, new Task(name, description, taskService.getTask(id).getType(), taskService.getTask(id).getDateTime()));
    }

        private static void addTask (TaskService taskService, Scanner scanner){
            System.out.println("Введите заголовок задачи");
            String name = scanner.next();
            scanner.nextLine();
            System.out.println("Введите описание задачи:");
            String description = scanner.nextLine();
            LocalDate taskDate = getDateFromUser(scanner);
            System.out.println("Введите время задачи в формате НН:mm");
            String time = scanner.nextLine();
            LocalTime taskTime = LocalTime.parse(time);
            LocalDateTime resultDate = LocalDateTime.of(taskDate, taskTime);
            System.out.println("Введите тип задачи: Личный(1) или Рабочий(2)");
            int type = scanner.nextInt();
            Type taskType = type == 1 ? Type.PERSONAL : Type.WORK;

            System.out.println("Введите повторяемость задачи:");
            System.out.println("0 - не повторяется");
            System.out.println(" 1 - Дневная");
            System.out.println(" 2 - Недельная");
            System.out.println(" 3 - Месячная");
            System.out.println(" 4 - Годовая");
            int typeTask = scanner.nextInt();
            switch (typeTask) {
                case 0:
                    taskService.add(new Task(name, description, taskType, resultDate));
                    break;

                case 1:
                    taskService.add(new DailyTask(name, description, taskType, resultDate));
                    break;

                case 2:
                    taskService.add(new WeeklyTask(name, description, taskType, resultDate));
                    break;
                case 3:
                    taskService.add(new MonthlyTask(name, description, taskType, resultDate));
                    break;
                case 4:
                    taskService.add(new YearlyTask(name, description, taskType, resultDate));
                    break;
                default:
                    throw new RuntimeException("Нет такого типа задач!");
            }
        }

                    private static void removeTask (TaskService taskService, Scanner scanner){
                    System.out.println("Введите id задачи которую необходимо удалить");
                    int id = scanner.nextInt();
                    taskService.removed(id);
                    System.out.println("Задача удалена");
                }

                private static void getTaskByDay (TaskService taskService, Scanner scanner) throws IOException {
                    System.out.println("Введите дату задачи в формате dd.mm.yyyy:");
                    scanner.nextLine();
                    String date = scanner.nextLine();
                    LocalDate taskDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
                    var allTaskByDay = taskService.getAllByDate(taskDate);
                    System.out.println("Список задач этого дня");
                    for (Task task : allTaskByDay) {
                        System.out.println("ID задачи: " + task.getId());
                        System.out.println("Заголовок задачи: " + task.getTitle());
                        System.out.println("Описание задачи: " + task.getDescription());
                        String type = task.getType() == Type.PERSONAL ? "Личный" : "Рабочий";
                        System.out.println("Тип задачи: " + type);
                        System.out.println("Время задачи: " + task.getDateTime().format(DateTimeFormatter.ofPattern("HH:mm")));
                        System.out.println("\n");
                    }
                    System.in.read();
                }

                public static void printMenu () {
                    System.out.println(

                            "1. Добавить задачу\n" +
                                    "2. Удалить задачу\n" +
                                    "3.Получить задачи на указанный день\n" +
                                    "4.Получить все задачи\n" +
                                    "5.Редактровать заголовок и описание задачи\n" +
                                    "6. Получить список всех удаленных задач\n" +
                                    "0. Выход"
                    );
                }
            }



