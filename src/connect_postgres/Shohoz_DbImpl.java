package connect_postgres;

import sample.*;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static sample.CurrentTrip.count_trip;

public class Shohoz_DbImpl extends DbAdapter {
    public static List<tripTable> tripTableList = new ArrayList<>() ;
    public static List<current_trip_insert> currentTripInsertList = new ArrayList<>() ;
    public static List<Rideinfo> rideList = new ArrayList<>();
    public static List<DriverInfo> nearByDriverList = new ArrayList<>() ;
    public static List<String> path_list = new ArrayList<>() ;
    public static List<Integer> path_id_list = new ArrayList<>() ;

    public  static List<passengerPromo> passengerPromoList = new ArrayList<>() ;
    public  static List<DriverPromo> motorDriverList = new ArrayList<>() ;
    public  static List<tripPromo> tripPromoList = new ArrayList<>() ;

    public static double distance[] ;

    HashMap<String,Integer> location_name_id ;
    static int count = 0 ;
    public int passenger_id ;
    public String trip_id ;
    public double source_dest_distance = 0 ;
    public static String RidePath_from_s_to_d ;


    public void printdata(){
        System.out.println("data : table passenger :");
        try
        {
            stmt = conn.createStatement();
            String query = "SELECT *  FROM public.\"Passenger\"";
            //Statement st = conn.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next())
            {
                int passenger_id = rs.getInt("passenger_id");
                String name = rs.getString("name");
                //System.out.println(passenger_id+ " LLLLLL  "+ name);
                String phone_number = rs.getString("phone_number");
                String email = rs.getString("email");
                String home_address = rs.getString("home_address");
                String password = rs.getString("password");
                int total_ride = rs.getInt("total_ride");
                String current_location = rs.getString("current_location");

                // print the results

                System.out.format("%s, %s, %s, %s, %s, %s , %s , %s \n", passenger_id, name, phone_number, email,
                        home_address,password, total_ride , current_location);

            }
            stmt.close();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
    }

    public void Load_Graph_AND_Request(String source , String destination , String type){

        //1.request sent

        trip_id = sent_request(source,destination,type,passenger_id) ;

        System.out.println("map load  :");
        int vertex =  0 ;
        try {

            //2. get vertex number

            stmt = conn.createStatement();
            String query = "SELECT count(*) as rowcount FROM public.\"location\"";
            rs = stmt.executeQuery(query);
            rs.next();
            vertex = rs.getInt("rowcount");
            System.out.println("vertex : " + vertex);

            //3. load location into hash map

            HashMap<Integer,String> location = location_map(vertex) ;
            //System.out.println(Arrays.asList(location));
            stmt.close();

            //4. Read map and make graph + dijkstra

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

            int start_vertex = location_name_id.get(source) - 101 ;
            int end_vertex = location_name_id.get(destination) - 101 ;
            graph.dijkstra(start_vertex);

            String path = graph.printPath(end_vertex) ;
            path = path.substring(0, path.length() - 1) ;
            //path = new StringBuilder(path).reverse().toString() ;
            System.out.println("\n\n");
            System.out.println(path);
            String[] patharr = path.split(",") ;
            String path_location = "" ;
            for (int i = patharr.length-1; i >= 0; i--) {

                path_location += location.get(Integer.parseInt(patharr[i])+100+1)+"," ;
                path_list.add(location.get(Integer.parseInt(patharr[i])+100+1)) ;
                path_id_list.add(Integer.parseInt(patharr[i])) ;

            }

            path_location = path_location.substring(0, path_location.length() - 1) ;
            System.out.println("location path : "+path_location);

            //ride path fixed
            RidePath_from_s_to_d = path_location ;
            RidePath(trip_id,path_location) ;

            //////////----------------------------------------------------------count
            System.out.println(patharr[patharr.length-1-count]) ;
            System.out.println("count : "+count);
            //----------------------------------------------------------------------------


            // 4km er majhe driver

            distance = graph.shortestDistances ;
            String four_km  = "" ;
            for (int i = 0; i < distance.length ; i++) {
                if (distance[i] <= 6) {
                    four_km += "current_location = "+ "'"+ location.get(i+101) + "'" + " OR " ;
                }
            }
            four_km = four_km.substring(0,four_km.length()-3) ;
            ArrayList<Integer> driver_id_list = print_driver(four_km) ;


            //driver accept
            System.out.println("array list size : "+ driver_id_list.size());
            if(driver_id_list.size() > 0)
            {
                System.out.println(trip_id);
                Driver_accept(driver_id_list.get(0),trip_id) ;
            }

            //distance of dest from source
            source_dest_distance = distance[end_vertex] ;

            stmt.close();

        }
        catch (Exception e)
        {
            System.err.println("Got an exception! ");
            System.err.println(e);
        }


    }

    public String sent_request(String source , String destination , String type , int passenger_id) {
        String trip_id = "" ;
        try
        {
            stmt = conn.createStatement();
            String query = "Select request_sent( '" + source + "','"+destination +"','"+ type
                    + "','"+ passenger_id + "' ) as trip_id " ;
            rs = stmt.executeQuery(query);
            while (rs.next())
            {
                trip_id = rs.getString("trip_id");
            }
            stmt.close();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception! ");
            System.err.println(e);
        }
        return trip_id ;

    }

    public void Update_current_trip() {
        if(count_trip < path_id_list.size())
        {
            try
            {
                stmt = conn.createStatement();
                int dist = (int) distance[path_id_list.get(count_trip)] ;
                String loc = path_list.get(count_trip) ;
                String query = "Select currentlocationupdet( '" + trip_id + "',"+dist +",'"+ loc
                        + "' )  " ;
                rs = stmt.executeQuery(query);
                System.out.println("Update ............");
                stmt.close();
            }
            catch (Exception e) {
                System.err.println("Got an exception! ");
                System.err.println(e);
            }
        }

    }
    public double paymentAmount() {
        double amount = 0.0 ;
        int dist = (int) source_dest_distance;
        System.out.println("source dest distance : "+ source_dest_distance );
        try
        {
            stmt = conn.createStatement();
            String query = "Select payment_insert( " + dist + ", '"+ trip_id +"' ) as amount " ;
            rs = stmt.executeQuery(query);
            while (rs.next())
            {
                amount = rs.getInt("amount");
                System.out.println("amount : "+ amount);
            }
            stmt.close();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception! ");
            System.err.println(e);
        }
        return amount ;

    }

    public void RidePath(String trip_id, String ride_path) {
        try
        {
            stmt = conn.createStatement();
            String query = "Select Ridepath( '" + trip_id + "','"+ride_path + "' )  " ;
            rs = stmt.executeQuery(query);
            stmt.close();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception! ");
            System.err.println(e);
        }
    }

    public int login_db(String phone, String password) {
        passenger_id = 0 ;

        try
        {
            stmt = conn.createStatement();
            String query = "Select Log_in( '" + phone + "','"+password + "' ) as passid  " ;
            rs = stmt.executeQuery(query);
            System.out.println( phone + "  phn + pass " + password );
            while (rs.next())
            {
            passenger_id = rs.getInt("passid") ;
            }
            System.out.println(passenger_id);
            stmt.close();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception! ");
            System.err.println(e);
        }
        return passenger_id ;
    }

    public String CurrentLocation() {
        String current_loc = "" ;
        try
        {
            stmt = conn.createStatement();
            String query = "select current_location\n" +
                    "from public.\"Passenger\"\n" +
                    "where passenger_id = "+ passenger_id ;
            rs = stmt.executeQuery(query);
            while (rs.next())
            {
                current_loc = rs.getString("current_location") ;
            }
            System.out.println(current_loc);
            stmt.close();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception! ");
            System.err.println(e);
        }
        return current_loc ;
    }

    public int User_signUp(String name , String phone , String email , String home , String password) {
        try
        {
            stmt = conn.createStatement();
            String query = "select passenger_signup('"+ name + "','"+phone +"',' "+ email +"','"+ home+"','"+password+"','"+ home+"') as passenger" ;
            rs = stmt.executeQuery(query);
            while (rs.next())
            {
                passenger_id = rs.getInt("passenger") ;
            }
            System.out.println(passenger_id);
            stmt.close();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception! ");
            System.err.println(e);
        }
        return passenger_id ;
    }

    public void Driver_accept(int driver_id, String trip_id) {
        try {
            stmt = conn.createStatement();
            String query = "Select driveraccept( " + driver_id + ",'" + trip_id + "' )  ";
            rs = stmt.executeQuery(query);
            stmt.close();
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e);
        }
    }

    public HashMap location_map(int vertex){
        System.out.println("data : table passenger :");
        //String[][] location = new String[vertex][2];
        HashMap<Integer, String> location = new HashMap<>();
        location_name_id = new HashMap<>() ;
        try
        {
            stmt = conn.createStatement();
            String query = "SELECT * FROM public.\"location\"";
            //Statement st = conn.createStatement();
            rs = stmt.executeQuery(query);
            int i = 0 ;
            while (rs.next())
            {
                int location_id = rs.getInt("location_id");
                String name = rs.getString("name");
                location.put(location_id,name) ;
                location_name_id.put(name,location_id) ;
                i++ ;
            }
            stmt.close();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return  location ;
    }

    public void ShowRideList(){
        //System.out.println("data : table passenger :");
        try
        {
            stmt = conn.createStatement();
            String query = "\n" +
                    "SELECT Public.\"Ride_record\".source, Public.\"Ride_record\".destination,\n" +
                    " Public.\"Ride_record\".total_distance,Public.\"Ride_record\".start_time,Public.\"Ride_record\".end_time\n" +
                    "FROM Public.\"Ride_record\",Public.\"Passenger\",Public.\"Request\"\n" +
                    "WHERE (Public.\"Ride_record\".trip_id=Public.\"Request\".trip_id) AND (Public.\"Request\".passenger_id=Public.\"Passenger\".passenger_id) AND\n" +
                    "   (Public.\"Passenger\".passenger_id="+ passenger_id +")";
            //Statement st = conn.createStatement();

            rs = stmt.executeQuery(query);
            while (rs.next())
            {
                String source = rs.getString("source");
                String destination = rs.getString("destination");
                double distance = rs.getDouble("total_distance");
                String Start_time = rs.getString("start_time");
                String end_time = rs.getString("end_time");

                System.out.println("start time "+ Start_time + "   end  : "+ end_time );
                rideList.add(new Rideinfo(source,destination,distance,Start_time,end_time)) ;
                // print the results
            }
            stmt.close();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
    }



    public ArrayList<Integer> print_driver(String str){
        ArrayList<Integer> driver_id_list = new ArrayList<>() ;
        try
        {
            stmt = conn.createStatement();
            //System.out.println(str);
            String query = "select public.\"Driver\".name ,public.\"Driver\".current_location , public.\"Driver\".driver_id\n" +
                    "from public.\"Driver\",public.\"Vehicle\"\n" +
                    "where\t " + " ( " + str +" ) " +
                    "\tAND (public.\"Driver\".driver_id=public.\"Vehicle\".driver_id)\n" +
                    "\tAND (public.\"Vehicle\".type='car') " ;

            //Statement st = conn.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next())
            {
                int driver_id = rs.getInt("driver_id");
                String name = rs.getString("name") ;
                String loc = rs.getString("current_location") ;
                nearByDriverList.add(new DriverInfo(name,loc)) ;
                System.out.println("Driver  : "+driver_id + "   "+ name);
                driver_id_list.add(driver_id) ;

            }

            System.out.println("driver list : "+ nearByDriverList.size());
            for (int i = 0; i < nearByDriverList.size(); i++) {
                System.out.println(nearByDriverList.get(i));
            }


            stmt.close();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return  driver_id_list ;

    }

    public int Count_carTrips() {
        int trips = 0 ;
        try {
            stmt = conn.createStatement();
            String query = "\n" +
                    "SELECT COUNT(*) as cartrips\n" +
                    "FROM Public.\"Trip\"\n" +
                    "where Public.\"Trip\".trip_id LIKE'c%'" ;
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                trips = rs.getInt("cartrips") ;
            }
            System.out.println(trips);
            stmt.close();
        }
        catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e);
        }
        return trips ;
    }

    public int Count_motorTrips() {
        int trips = 0 ;
        try {
            stmt = conn.createStatement();
            String query = "SELECT COUNT(*) as motortrips\n" +
                    "FROM Public.\"Trip\"\n" +
                    "where Public.\"Trip\".trip_id LIKE'm%'" ;
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                trips = rs.getInt("motortrips") ;
            }
            System.out.println(trips);
            stmt.close();
        }
        catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e);
        }
        return trips ;
    }

    public void passenger_using_promo() {
        try {
            stmt = conn.createStatement();
            String query = "\n" +
                    "SELECT  Public.\"Passenger\".name,Public.\"Passenger\".home_address\n" +
                    "FROM Public.\"Promo\",Public.\"Passenger\"\n" +
                    "where  Public.\"Promo\".passenger_id=Public.\"Passenger\".passenger_id ;" ;
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                String name = rs.getString("name") ;
                String home_address = rs.getString("home_address") ;
                passengerPromoList.add(new passengerPromo(name,home_address)) ;
            }
            stmt.close();
        }
        catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e);
        }
    }

    public void driver_using_promo() {
        try {
            stmt = conn.createStatement();
            String query = "\n" +
                    "SELECT Public.\"Driver\".name,Public.\"Driver\".driver_id\n" +
                    " FROM Public.\"Driver\",Public.\"Vehicle\"\n" +
                    " WHERE Public.\"Vehicle\".type='motor' AND Public.\"Vehicle\".driver_id=Public.\"Driver\".driver_id\n" +
                    " GROUP BY Public.\"Driver\".driver_id;" ;
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                String name = rs.getString("name") ;
                int driver_id = rs.getInt("driver_id") ;
                motorDriverList.add(new DriverPromo(name,driver_id)) ;
            }
            stmt.close();
        }
        catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e);
        }
    }

    public void trip_using_promo() {
        try {
            stmt = conn.createStatement();
            String query = "SELECT DISTINCT Public.\"Trip\".trip_id\n" +
                    "FROM Public.\"Promo\",Public.\"Payment\",Public.\"Trip\"\n" +
                    "WHERE (Public.\"Trip\".payment_id=Public.\"Payment\".payment_id) AND \n" +
                    "      (Public.\"Payment\".promo_code IS NOT NULL) ;" ;
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                String trippp_id = rs.getString("trip_id") ;
                tripPromoList.add(new tripPromo(trippp_id)) ;
            }
            stmt.close();
        }
        catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e);
        }
    }

    public void Current_insert_trip_print() {
        try {
            stmt = conn.createStatement();
            String query = "select * from public.\"Current_trip\"\n" +
                            "where trip_id = '"+ trip_id  + "'";
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                String trippp_id = rs.getString("trip_id") ;
                Timestamp start_time =  rs.getTimestamp("start_time") ;
                String elapsed_time = rs.getString("elapsed_time") ;
                int dist = rs.getInt("elapsed_distance") ;
                String location = rs.getString("current_location") ;
                System.out.println("");

                currentTripInsertList.add(new current_trip_insert(trippp_id,
                        start_time,elapsed_time,dist,location)) ; ;
            }
            stmt.close();
        }
        catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e);
        }
    }

    public void Trip_trig_print() {
        try {
            stmt = conn.createStatement();
            String query = "select * from public.\"Trip\"\n" +
                    "where trip_id = '"+ trip_id  + "'";
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                Boolean isAc = rs.getBoolean("is_accepted");
                String tripp_id = rs.getString("trip_id") ;
                String payment_id = rs.getString("payment_id");
                int driver_id = rs.getInt("driver_id") ;
                System.out.println("MMMM"+isAc);

                tripTableList.add(new tripTable(isAc,tripp_id,payment_id,driver_id)) ;
            }
            stmt.close();
        }
        catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e);
        }
    }

//#ADFF2F-- G
    // #ffff00 -- y
    //ffffff--sada

}
