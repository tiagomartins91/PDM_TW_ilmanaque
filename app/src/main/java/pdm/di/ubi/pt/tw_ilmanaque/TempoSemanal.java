package pdm.di.ubi.pt.tw_ilmanaque;

/**
 * Created by joaosaraiva on 18-12-2017.
 */

public class TempoSemanal {

    int humity;
    double temp;
    String dt, city_name, weather;

    public TempoSemanal(){

    }

    public TempoSemanal(int humity, double temp, String dt, String city_name, String weather) {
        this.humity = humity;
        this.temp = temp;
        this.dt = dt;
        this.city_name = city_name;
        this.weather = weather;
    }


    public int getHumity() {
        return humity;
    }

    public void setHumity(int humity) {
        this.humity = humity;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    @Override
    public String toString() {
        return "TempoSemanal{" +
                "dt=" + dt +
                ", humity=" + humity +
                ", temp=" + temp +
                ", city_name='" + city_name + '\'' +
                ", weather='" + weather + '\'' +
                '}';
    }
}
