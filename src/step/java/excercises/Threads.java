package step.java.excercises;

public class Threads {

    public void demo() {
        Thread thread1 = new Thread() {
            @Override
            public void run() {
                System.out.println("Hello from thread 1" ) ;
            }
        };
        thread1.start() ;

        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        System.out.println( "Hello from thread 2" ) ;
                    }
        }).start() ;

        Runnable r3 = () -> {
            System.out.println( "Hello from thread 3" ) ;
        } ;
        new Thread( r3 ).start() ;

        new Thread( ()->System.out.println("Thread 4") ).start() ;

        new NumberedThread( 5 ).start() ;
        new NumberedThread( 6 ).start() ;
        new NumberedThread( 7 ).start() ;
    }

    private double res ;  // non-reference type, not valid for sync
    private final Object semaphore = new Object() ;

    public void demo2() {
        Runnable plus10percent = () -> {
            /* double oldValue = res ;  // read value
            double newValue = oldValue + 0.1 * oldValue ;
            res = newValue ;  // write value
             */
            double newValue = res + 0.1 * res ;
            res = newValue ;
            // res *= 1.1;
            System.out.println( newValue ) ;
        } ;
        res = 100 ;
        for( int i = 0; i < 12; ++i ) {
            new Thread( plus10percent ).start() ;
        }
    }

    public void demo3() {
        Runnable plus10percent = () -> {
            double oldValue ;
            double newValue ;
            synchronized( semaphore ) {
                oldValue = res;  // read value
                newValue = oldValue + 0.1 * oldValue;
                res = newValue;  // write value
            }
            System.out.println(newValue);
        } ;

        res = 100 ;
        for( int i = 0; i < 12; ++i ) {
            new Thread( plus10percent ).start() ;
        }
    }

    class NumberedThread extends Thread {
        int num ;
        public NumberedThread( int num ) {
            this.num = num ;
        }
        @Override
        public void run() {
            System.out.println( "Hello from thread " + num );
        }
    }
}

/*
    Thread (поток) - часть процесса
        поток создается на функции (методе). В Java
        методы не являются самостоятельными, в поток
        передается объект с одним методом.
    Основным классом для управления потоками
        является Thread. Основным методом, который
        выполняется, является run()
    Runnable - интерфейс для объектов, запускаемых в
        отдельных потоках

    II Синхронизация
    Главная проблема потоков - работа с одной памятью
        (с одной общей переменной). Суть - разделенные
        во времени операции чтения и записи.
        С учетом того, что операция присваивания по сути
        разделена во времени (на вычисление-чтение и запись),
        любая операция присваивания (с общей переменной)
        при многопоточности является опасной.
    Одно из направлений решения проблемы - атомарные типы
        AtomicBoolean, AtomicInteger, ..., которые на внутреннем
        уровне создают транзакции чтения-записи.
    Другой подход - синхронизация: создание блокировки
        переменной на использование (~как блокировка
        открытого файла) + создание и обслуживания
        очереди ожидающих потоков.
        Аналогично .NET, все объекты (ссылочные типы) в
        Java имеют признак блокировки потоком.
        ! Если все тело метода находится в синхро-блоке,
        то реально многопоточность не имеет эффекта
    ! Синхронизация по не-финальному объекту
        Довольно часто синхроблок делают по самому объекту
        с которым ведется работа
        synchronized( label ) { label.text = "..." }
        НО! такой подход может привести к проблеме
        synchronized( str ) { str += "..." }
        ! str += сформирует новую строку (новый объект)
        старый объект остается "закрытым" (и не будет открыт
            в конце блока)
        а новый не защищен от других потоков
 */