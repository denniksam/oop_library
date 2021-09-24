package step.java;

import step.java.excercises.Db;
import step.java.excercises.Threads;
import step.java.fs.CreateFiles;
import step.java.library.*;

import java.util.Date;

public class Main {

    public static void main(String[] args) {
/*
        Library lib = new Library() ;
        lib.addDirectoryAsync(
                "./src/step/java/fs/",
                lib::print
        ) ;
        System.out.println("--------");
        lib.print() ;
        System.out.println("--------");
*/
        /*lib.add( new Journal( "Quantum Mechanics", "2021, 1" ) ) ;
        lib.add( new Book( "Martin Eden", "Jack London" ) ) ;
        lib.add( new Hologram( "Pectoral" ) ) ;
        lib.add( new Newspaper( "Times", new Date() ) ) ;
        */
        // CreateFiles creator = new CreateFiles() ;
        // creator.showDir() ;
        // creator.createBook() ;
        /* System.out.println( creator.getFileContent(
                "./src/step/java/fs/" +
                "Jack London_Martin Eden.json"
        ));*/
        // new Threads().demo3() ;
        new Db().demo() ;
    }
}
/*
    ООП - объектно-ориентированная парадигма программирования
    Суть: объекты + связи
    Предметная область --> объекты + связи
                   моделирование
    Модель - представление об исходной системе, ограниченное
             определенными критериями
    В терминах ООП моделирование = абстрагирование

    Инкапсуляция - "все мое ношу с собой"
    "+" самостоятельность объектов, возможность копирования/переноса
    "-" дублирование общих элементов в каждом объекте
      --> IoC/DI (Inversion of Control / Dependency Injection)

    Полиморфизм - (от поли- много, морф- форма) / обобщение
     - параметрический П - перегрузка методов
     - статический П - разные реализации одного интерфейса
     одна из главных задач - сложить в один массив разные объекты

    Наследование / расширение - механизм реализации полиморфизма
 */