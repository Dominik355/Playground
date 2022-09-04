package basics.reflection.objectsForDemonstration;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;

public class BattleShip implements Ship {

    private String id = "123Ship_Original";

    private String getPrivate() {
        return "How did you get here ???";
    }

    private String getOtherPrivate(int thisInt, String thatString) {
        return "How did you get here ? " + thisInt + ", " + thatString;
    }

    public BattleShip(int number, String randString) {
        System.out.println("You created BattleShip with: " + number + ", " + randString);
    }

    public BattleShip(BattleShip battleShip) {
        System.out.println("You provided BattleShip target for this BattleShip");
    }

    public BattleShip() {
        System.out.println("Default constructor");
    }

    @Override
    public String toString() {
        return "BattleShip{" +
                "id='" + id + '\'' +
                '}';
    }

    public void setId(String id) {
        this.id = id;
        System.out.println("ID was set to: " + id);
    }

    private static class ShipCrew {
        private List<String> members;

        {
            members = new ArrayList<>();
            members.add("Emil");
            members.add("Robert");
            members.add("Michael");
        }

        public List<String> getMembers() {
            return members;
        }
    }
}
