import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Metode {

    public ArrayList<MonitoredData> citire(ArrayList<MonitoredData> m){
        String fileName = "Activities.txt";
        //read file into stream, try-with-resources
        List<String> list = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            list = stream.collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }
        //list.forEach(System.out::println);
        int i=0;
        if(!list.isEmpty())
            for(String s:list){
                i++;
                MonitoredData mD=new MonitoredData("","","");
                this.impartire(s,mD);
                m.add(mD);
            }
        return m;
    }

    public void impartire(String s,MonitoredData mD){
        String []linie= s.split("[\\t]+");
        mD.setStartTime(linie[0].trim());
        mD.setEndTime(linie[1].trim());
        mD.setActivity(linie[2].trim());
    }

    public void scriere(int i, ArrayList<MonitoredData> mD) throws IOException {
            FileWriter myWriter = new FileWriter("Task"+i+".txt");
            if(i==1){
            for(MonitoredData m:mD)
                myWriter.write(m.getStartTime() + " " + m.getEndTime() +" " + m.getActivity()+"\n");
            }
            if(i==2)
                myWriter.write(count(mD)+"\n");
            if(i==3){
                HashMap<String,Integer> l= task3(mD);
                for (HashMap.Entry<String, Integer> cont : l.entrySet())
                    myWriter.write(cont.getKey()+" "+cont.getValue()+"\n");
            }
            if (i == 4) {
                HashMap< Integer,HashMap< String,Integer>> mapa=task4(mD);
                for (Map.Entry<Integer, HashMap<String, Integer>> cont : mapa.entrySet())
                    myWriter.write("Ziua nr: "+cont.getKey() + " " + cont.getValue()+"\n");
            }
            if(i==5){
                HashMap<String,Integer> mm= task5(mD);
                for (HashMap.Entry<String, Integer> cont : mm.entrySet())
                    myWriter.write(cont.getKey()+" "+cont.getValue()+" sec"+"\n");
            }
            if(i==6){
                List<String> string = task6(mD);
                for(String s:string)
                    myWriter.write(s+"\n");
            }
            myWriter.close();
    }

    public int count( ArrayList<MonitoredData> mD){
        //mD=citire(mD);
        //String s1;
        ArrayList<String> lista=new ArrayList<String>();
        //String []list=new String[mD.size()];
        for(MonitoredData m:mD){
            String s=m.getStartTime();
            String []t=s.split(" ");
            s=t[0].trim();
            lista.add(s);
        }
        long c=lista.stream().distinct().count();
        return (int)c;
    }

    public HashMap< String,Integer> task3( ArrayList<MonitoredData> mD){
        HashMap <  String,Integer> map=new HashMap<  String,Integer>();
        ArrayList<String> lista=new ArrayList<String>();
        for(MonitoredData m:mD) {
            String t = m.getActivity();
            lista.add(t);
        }
        for(MonitoredData m:mD){
            String t=m.getActivity();
            //List<String> list=lista.stream().filter(s->s.startsWith(t)).collect(Collectors.toList());
            long count=lista.stream().filter(s->s.equals(t)).count();
            map.put(t,(int)count);
        }
        return map;
    }

    public HashMap< Integer,HashMap< String,Integer>> task4(ArrayList<MonitoredData> mD){
        HashMap< Integer,HashMap< String,Integer>> mapaMapei= new HashMap< Integer,HashMap< String,Integer>>();
        ArrayList<String> lista1=new ArrayList<String>();
        for(MonitoredData m:mD) {
            String st = m.getStartTime();
            String []str=st.split(" ");
            st=str[0].trim();
            if(!lista1.contains(st))
            lista1.add(st);
        }
        for(String string : lista1) {
            HashMap <  String,Integer> map=new HashMap<  String,Integer>();
            for (MonitoredData m : mD) {
                String str=m.getStartTime();
                String []st=str.split(" ");
                str=st[0].trim();
                if(str.equals(string)) {
                    String t = m.getActivity();
                    String finalStr = str;
                    long count=mD.stream().filter(mon->(mon.getStartTime().startsWith(finalStr) ||mon.getEndTime().startsWith(finalStr)) && mon.getActivity().equals(t)).count();
                    map.put(t, (int) count);
                }
            }
            mapaMapei.put(mapaMapei.size()+1,map);
        }
        return mapaMapei;
    }

    public  HashMap<String, Integer> task5(ArrayList<MonitoredData> mD){
        HashMap<String, Integer> map= new HashMap<String, Integer>();
        for(MonitoredData m:mD){
            String t=m.getActivity();
            List<MonitoredData> list=mD.stream().filter(mon->mon.getActivity().equals(t)).collect(Collectors.toList());
            long s=0;
            if(map.get(t)==null){
            for (MonitoredData mm:list){
                    String str= mm.getStartTime();
                    String str1=mm.getEndTime();
                Date dataEnd,dataStart;
                try{
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    dataEnd  = dateFormat.parse(str1);
                    dataStart  = dateFormat.parse(str);
                    s+=dataEnd.getTime()-dataStart.getTime();
            }
                catch(ParseException e){
                e.printStackTrace();
            }
            }
            s=s/1000;
            map.put(t,(int)s);}
        }
        return map;
    }

    public List<String> task6(ArrayList<MonitoredData> m){
        List<String> lista= new ArrayList<String>();
        HashMap<String,Integer> map1=task3(m);
        for(MonitoredData mD:m){
            String string=mD.getActivity();
            List<MonitoredData> listaDate=m.stream().filter(mon->mon.getActivity().equals(string)).collect(Collectors.toList());
            int activitate=map1.get(string);
            int count=0;
                if(!lista.contains(string)){
                    for(MonitoredData mm:listaDate){
                        String str= mm.getStartTime();
                        String str1=mm.getEndTime();
                        Date dataEnd,dataStart;
                        try{
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            long s=dateFormat.parse(str1).getTime()-dateFormat.parse(str).getTime();
                            if(s/1000<300){
                                count++;
                            }
                        }
                        catch(ParseException e){
                            e.printStackTrace();
                        }
                }
                    if(activitate*0.9<count){
                        lista.add(string);
                    }
            }
        }
        return lista;
    }


}
