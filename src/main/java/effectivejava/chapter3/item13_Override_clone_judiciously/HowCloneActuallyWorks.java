package effectivejava.chapter3.item13_Override_clone_judiciously;

import java.util.Arrays;
import java.util.Objects;

/**
 * BASIC RULE : Use default Object clone() method only when your class has primitives and immutable.
 *
 * ... Or if you are aware of non primitive and immutable objects, you have to do deep cloning by yourself.. properly
 */
public class HowCloneActuallyWorks {

    public static void main(String[] args) {
        String[] members = new String[]{"emil", "ivan", "kubo"};
        ExampleClass ec = new ExampleClass(4, "name", members, 8);
        ExampleClass ec2 = ec.clone();

        System.out.println("originial: " + ec + ", new: " + ec2);
        System.out.println("==: " + (ec == ec2));
        System.out.println("equals: " + ec.equals(ec2));
        System.out.println("originial: " + ec.getNumber() + ", new: " + ec2.getNumber());
        System.out.println("originial: " + ec.getName() + ", new: " + ec2.getName());
        System.out.println("== of String: " + (ec.getName() == ec2.getName()));
        System.out.println("originial: " + ec.getMembers() + ", new: " + ec2.getMembers());
        System.out.println("originial: " + ec.getAnotherNum() + ", new: " + ec2.getAnotherNum());
        System.out.println("== of Integer: " + (ec.getAnotherNum() == ec2.getAnotherNum()));
        System.out.println("wait, but Integer and String object reference is the same !! .. yea, but those are immutable, so if you change value, new object is created anyway");
        System.out.println("oriignal: " + Arrays.toString(ec.getMembers()) + ", new: " + Arrays.toString(ec2.getMembers()));

        ec.getMembers()[1] = "new name";
        System.out.println("oriignal: " + Arrays.toString(ec.getMembers()) + ", new: " + Arrays.toString(ec2.getMembers()));
        System.out.println("""
                Here you got problem, for any non immutable objects, you have to do Deep cloning.
                Cloning by itself is done by reflection (i hope). So in order to do Deep cloning
                (which basically means - clone non imutable objects of instance also), you have to od it by yourself.
                How to deal with deep cloning ? - take a look at Stack class
                """);
    }

}

class ExampleClass implements Cloneable {

    private int number;
    private String name;
    private String[] members;
    private Integer anotherNum;

    public ExampleClass(int number, String name, String[] members, Integer anotherNum) {
        this.number = number;
        this.name = name;
        this.members = members;
        this.anotherNum = anotherNum;
    }

    @Override
    protected ExampleClass clone() {
        try {
            return (ExampleClass) super.clone();
        } catch (CloneNotSupportedException ex) {
            throw new RuntimeException("CloneNotSupportedException thrown while cloning ExampleClass");
        }
    }



    public String toStringg() {
        return "ExampleClass{" + "number=" + number + ", name='" + name + '\'' + ", members=" + Arrays.toString(
                members) + '}';
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getMembers() {
        return members;
    }

    public void setMembers(String[] members) {
        this.members = members;
    }

    public Integer getAnotherNum() {
        return anotherNum;
    }

    public void setAnotherNum(Integer anotherNum) {
        this.anotherNum = anotherNum;
    }
}
