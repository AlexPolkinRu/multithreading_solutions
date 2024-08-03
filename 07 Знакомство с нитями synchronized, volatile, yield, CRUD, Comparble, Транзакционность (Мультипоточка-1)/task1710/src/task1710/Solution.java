package task1710;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/* 
CRUD
CRUD - Create, Read, Update, Delete.

Программа запускается с одним из следующих наборов параметров:
-c name sex bd
-r id
-u id name sex bd
-d id

Значения параметров:
name - имя, String
sex - пол, "м" или "ж", одна буква
bd - дата рождения в следующем формате 15/04/1990
-c - добавляет человека с заданными параметрами в конец allPeople, выводит id (index) на экран
-r - выводит на экран информацию о человеке с id: name sex (м/ж) bd (формат 15-Apr-1990)
-u - обновляет данные человека с данным id
-d - производит логическое удаление человека с id, заменяет все его данные на null

id соответствует индексу в списке.
Все люди должны храниться в allPeople.
Используй Locale.ENGLISH в качестве второго параметра для SimpleDateFormat.

Пример параметров:
-c Миронов м 15/04/1990

Пример вывода для параметра -r:
Миронов м 15-Apr-1990

Если программа запущена с параметрами, то они попадают в массив args (аргумент метода main - String[] args).
Например, при запуске программы c параметрами:
-c name sex bd
получим в методе main
args[0] = "-c"
args[1] = "name"
args[2] = "sex"
args[3] = "bd"
Для запуска кода с параметрами в IDE IntellijIDEA нужно их прописать в поле Program arguments
 в меню Run -> Edit Configurations.


Requirements:
1. Класс Solution должен содержать public static поле allPeople типа List<Person>.
2. Класс Solution должен содержать статический блок, в котором добавляются два человека в список allPeople.
3. При запуске программы с параметром -с программа должна добавлять человека с заданными параметрами
 в конец списка allPeople, и выводить id (index) на экран.
4. При запуске программы с параметром -r программа должна выводить на экран данные о человеке
 с заданным id по формату указанному в задании.
5. При запуске программы с параметром -u программа должна обновлять данные человека с заданным id в списке allPeople.
6. При запуске программы с параметром -d программа должна логически удалять человека с заданным id в списке allPeople.
*/

public class Solution {
    private static final List<Person> allPeople = new ArrayList<>();
    private static final Logger logger = Logger.getGlobal();
    private static final SimpleDateFormat simpleDateFormatInput = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
    private static final SimpleDateFormat simpleDateFormatOutput = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
    private static final String errorMsg = "Ошибка формата входных параметров";


    static {
        allPeople.add(Person.createMale("Иванов Иван", new Date()));  //сегодня родился    id=0
        allPeople.add(Person.createMale("Петров Петр", new Date()));  //сегодня родился    id=1
    }

    public static void main(String[] args) {
        //напишите тут ваш код
        if (args == null || args.length < 2 || args.length > 5) {
            logger.log(Level.WARNING, errorMsg);
        } else {
            switch (args[0]) {
                case "-c":
                    if (!createEntry(args))
                        logger.log(Level.WARNING, errorMsg);
                    break;
                case "-r":
                    if (!readEntry(args))
                        logger.log(Level.WARNING, errorMsg);
                    break;
                case "-u":
                    if (!updateEntry(args))
                        logger.log(Level.WARNING, errorMsg);
                    break;
                case "-d":
                    if (!deleteEntry(args))
                        logger.log(Level.WARNING, errorMsg);
                    break;
                default:
                    logger.log(Level.WARNING, errorMsg);
            }
        }

    }

    // -c name sex bd
    private static boolean createEntry(String[] args) {
        if (args.length != 4) {
            return false;
        } else {
            Date birthday;
            try {
                birthday = simpleDateFormatInput.parse(args[3]);
            } catch (ParseException e) {
                return false;
            }

            if (!(args[2].equals("м") || args[2].equals("ж"))) {
                return false;
            }

            Person person;
            if (args[2].equals("м")) {
                person = Person.createMale(args[1], birthday);
            } else {
                person = Person.createFemale(args[1], birthday);
            }

            allPeople.add(person);
            System.out.println(allPeople.size() - 1);
        }
        return true;
    }

    //-r id
    private static boolean readEntry(String[] args) {
        if (args.length != 2) {
            return false;
        } else {
            int id;
            try {
                id = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                return false;
            }

            Person person;
            try {
                person = allPeople.get(id);
            } catch (IndexOutOfBoundsException e) {
                return false;
            }

            System.out.println(person.getName() + " " +
                    (person.getSex().equals(Sex.MALE) ? "м" : "ж") + " " +
                    simpleDateFormatOutput.format(person.getBirthDate())
            );
        }
        return true;
    }

    // -u id name sex bd
    private static boolean updateEntry(String[] args) {
        if (args.length != 5) {
            return false;
        } else {
            int id;
            try {
                id = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                return false;
            }

            Date birthday;
            try {
                birthday = simpleDateFormatInput.parse(args[4]);
            } catch (ParseException e) {
                return false;
            }

            if (!(args[3].equals("м") || args[3].equals("ж"))) {
                return false;
            }

            Person currentPerson = allPeople.get(id);
            currentPerson.setName(args[2]);
            currentPerson.setSex(args[3].equals("м") ? Sex.MALE : Sex.FEMALE);
            currentPerson.setBirthDate(birthday);
        }
        return true;
    }

    // -d id
    private static boolean deleteEntry(String[] args) {
        if (args.length != 2) {
            return false;
        } else {
            int id;
            try {
                id = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                return false;
            }

            Person currentPerson;
            try {
                currentPerson = allPeople.get(id);
            } catch (IndexOutOfBoundsException e) {
                return false;
            }
            currentPerson.setName(null);
            currentPerson.setSex(null);
            currentPerson.setBirthDate(null);
        }
        return true;
    }
}
