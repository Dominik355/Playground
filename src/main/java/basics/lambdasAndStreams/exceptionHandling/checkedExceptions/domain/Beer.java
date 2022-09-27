package basics.lambdasAndStreams.exceptionHandling.checkedExceptions.domain;

public class Beer {

    public String name;
    public double alcohol;

    public Beer(String name, double alcohol) {
        this.name = name;
        this.alcohol = alcohol;
    }

    public String pour() throws PouringException {
        return "Pouring " + name + ". Damn ,it has " + alcohol + "%";
    }

    public static Beer of(String name, double alcohol) {
        return new Beer(name, alcohol);
    }

    @Override
    public String toString() {
        return """
            Beer{
                name='%s', 
                alcohol=%s
            }
            """.formatted(name, alcohol);
    }
}
