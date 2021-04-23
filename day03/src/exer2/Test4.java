package exer2;

//模拟     一个人生产50个玩具，每200毫秒生产一个，
//        当生产到第20个时加入每秒吃1个馒头，共吃完3个后在接着生产的多线程。


class People{
    private int toy=0;
    private int bun =0;

    public People() {

    }

    public synchronized void pro() {
        while (toy < 100) {
            notify();

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            toy++;
            System.out.println(Thread.currentThread().getName() + toy);
            if (toy == 20) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }



    public synchronized void eat() {
        while (toy == 20) {

            notify();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            bun++;
            System.out.println(Thread.currentThread().getName() + bun);

            if (bun >= 3) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}

class Producer extends Thread{
    private People people;

    public Producer( People people){
        this.people =people;
    }
    private int toy=0;
    @Override
    public void run() {

        System.out.println("开始生产玩具");
        people.pro();

    }
}
class Eater extends Thread{
    private People people;

    public Eater ( People people){
        this.people =people;
    }

    @Override
    public void run() {

        System.out.println("开始生产馒头");

        people.eat();

    }
}

public class Test4 {
    public static void main(String[] args) {
        People people =new People();
        Producer p1 =new Producer(people);
        Eater eater =new Eater(people);
        p1.setName("玩具:");
        eater.setName("馒头：");
        p1.start();
        eater.start();

    }
}
