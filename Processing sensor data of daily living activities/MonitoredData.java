import org.w3c.dom.ls.LSOutput;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MonitoredData {
    private String startTime;
    private String endTime;
    private String activity;

    public MonitoredData(String sT,String eT,String s){
        this.startTime=sT;
        this.endTime=eT;
        this.activity=s;
    }


    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getActivity() {
        return activity;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }
}
