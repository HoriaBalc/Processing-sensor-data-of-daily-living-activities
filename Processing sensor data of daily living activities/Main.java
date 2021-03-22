import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String args[]) {
        MonitoredData m = new MonitoredData("", "", "");
        ArrayList<MonitoredData> mD = new ArrayList<MonitoredData>();
        Metode met = new Metode();
        mD = met.citire(mD);
        try {
            met.scriere(1, mD);
            met.scriere(2, mD);
            met.scriere(3, mD);
            met.scriere(4, mD);
            met.scriere(5, mD);
            met.scriere(6, mD);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
