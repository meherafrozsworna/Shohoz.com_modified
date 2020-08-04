package connect_postgres;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;

public class Database extends DbAdapter {


    public void Load_Graph_AND_Request(String source , String destination , String type , int passenger_id){

        //1.request sent

        String trip_id = sent_request(source,destination,type,passenger_id) ;

        System.out.println("map load  :");
        int vertex =  0 ;
        try {
            stmt = conn.createStatement();
            String query = "SELECT count(*) as rowcount FROM public.\"location\"";
            rs = stmt.executeQuery(query);
            rs.next();
            vertex = rs.getInt("rowcount");
            System.out.println("vertex : " + vertex);
            HashMap<Integer,String> location = location_map(vertex) ;
            //System.out.println(Arrays.asList(location));
            stmt.close();
            stmt = conn.createStatement();
            String query2 = "SELECT * FROM public.\"Map\"";
            rs = stmt.executeQuery(query2) ;
            Graph graph = new Graph(vertex) ;
            while (rs.next())
            {
                String from = rs.getString("from");
                String to = rs.getString("to");
                double distance = rs.getDouble("distance");
                //System.out.println(from + "     " + to + "    " + distance);
                int u = Integer.parseInt(from) - 100-1;
                int v = Integer.parseInt(to) - 100-1;
                System.out.println(u +"  XD  "+ v + "  " +  distance);
                graph.addEdge(u,v,distance);
            }

            graph.dijkstra(20);
            String path = graph.printPath(3) ;
            path = path.substring(0, path.length() - 1) ;
            System.out.println(path);

            String[] patharr = path.split(",") ;
            String path_location = "" ;
            for (int i = patharr.length-1; i >= 0; i--) {
                path_location += location.get(Integer.parseInt(patharr[i])+100+1)+"," ;
            }

            path_location = path_location.substring(0, path_location.length() - 1) ;
            System.out.println(path_location);
            double distance[] = graph.shortestDistances ;
            String four_km  = "" ;
            for (int i = 0; i < distance.length ; i++) {
                if (distance[i] <= 5)
                {
                    four_km += "current_location = "+ "'"+ location.get(i+101) + "'" + " OR " ;
                }
            }
            four_km = four_km.substring(0,four_km.length()-3) ;
            print_driver(four_km) ;
            stmt.close();

        }
        catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e);
        }


    }

    public String sent_request(String source , String destination , String type , int passenger_id)
    {
        String trip_id = "" ;
        try {
            stmt = conn.createStatement();
            String query = "Select request_sent( '" + source + "','"+destination +"','"+ type
                    + "','"+ passenger_id + "' ) as trip_id " ;
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                trip_id = rs.getString("trip_id");
            }
            stmt.close();
        }
        catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e);
        }
        return trip_id ;
    }


    public HashMap location_map(int vertex){
        System.out.println("data : table passenger :");
        HashMap<Integer, String> location = new HashMap<>();
        try {
            stmt = conn.createStatement();
            String query = "SELECT * FROM public.\"location\"";
            rs = stmt.executeQuery(query);
            int i = 0 ;
            while (rs.next()) {
                int location_id = rs.getInt("location_id");
                String name = rs.getString("name");
                location.put(location_id,name) ;
                i++ ;
            }
            stmt.close();
        }
        catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return  location ;
    }


    public void print_driver(String str){

        try {
            stmt = conn.createStatement();
            //System.out.println(str);
            String query = "select public.\"Driver\".name , public.\"Driver\".driver_id\n" +
                    "from public.\"Driver\",public.\"Vehicle\"\n" +
                    "where\t " + " ( " + str +" ) " +
                    "\tAND (public.\"Driver\".driver_id=public.\"Vehicle\".driver_id)\n" +
                    "\tAND (public.\"Vehicle\".type='car') " ;

            rs = stmt.executeQuery(query);
            while (rs.next()) {
                int driver_id = rs.getInt("driver_id");
                String name = rs.getString("name") ;
                System.out.println("Driver  : "+driver_id + "   "+ name);
            }
            stmt.close();
        }
        catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }

    }

}
