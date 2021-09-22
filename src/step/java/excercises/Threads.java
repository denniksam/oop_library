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

    class NumberedThread extends Thread {
        int num ;
        public NumberedThread( int num ) {
            this.num = num ;
        }
        @Override
        public void run() {
            System.out.println( "Hello from thread " + num ) ;
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
 */