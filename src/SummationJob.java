public class SummationJob implements Runnable {

    private static int X = 0;
    private static int numberDone = 0; //lets us know when all the threads are done so we can finish
    private static final int NUMBER_THREADS = 10;

    private static Integer XLock = X;

    @Override
    public void run() {
        synchronized (XLock) {
            for (int i = 0; i < 1000; i++) {
                X = X + 1; //THIS IS OUR RACE CONDITION
            }
            numberDone += 1; // THIS IS ANOTHER RACE CONDITION
        }
    }

    public static void main(String[] args) {
        try{
            Thread[] threads = new Thread[NUMBER_THREADS];
            for(int k = 0; k < NUMBER_THREADS; k++){
                threads[k] = new Thread(new SummationJob());
            }
            for(int k = 0; k < NUMBER_THREADS; k++){
                threads[k].start();
            }

            while(numberDone < NUMBER_THREADS){
                Thread.sleep(100);
            }

            System.out.println("X = " + X);
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("OOPS " + e);
        }
    }
}
