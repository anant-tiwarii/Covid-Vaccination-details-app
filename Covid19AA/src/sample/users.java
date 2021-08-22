package sample;

public class users {
    int age;
    String name, FVac, SVac, PrevI;

    public void setAge(int age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFVac(String FVac) {
        this.FVac = FVac;
    }

    public void setSVac(String SVac) {
        this.SVac = SVac;
    }

    public void setPrevI(String prevI) {
        PrevI = prevI;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public String getFVac() {
        return FVac;
    }

    public String getSVac() {
        return SVac;
    }

    public String getPrevI() {
        return PrevI;
    }

    public users(int age, String name, String FVac, String SVac, String prevI) {
        this.age = age;
        this.name = name;
        this.FVac = FVac;
        this.SVac = SVac;
        PrevI = prevI;
    }
}
