package connect_postgres;

public class main {
    public static void main(String[] args) {
        /*DbAdapter dbAdapter = new DbAdapter();
        dbAdapter.connect();
        dbAdapter.disconnect();
        */

        // Connect

        Shohoz_DbImpl shohoz_DbImpl = new Shohoz_DbImpl();
        shohoz_DbImpl.connect();
        int pass = shohoz_DbImpl.login_db("*****","KKKK");

        System.out.println("pass  : "+ pass);

        //String trip = shohoz_DbImpl.sent_request("mirpur-10","dhanmondi","motor",23) ;
        //System.out.println("trip id : "+ trip) ;
        //shohoz_DbImpl.location_map(49);
        //shohoz_DbImpl.printdata();
        //shohoz_DbImpl.Load_Graph_AND_Request("mirpur-10","polashi","motor",23);
        // Disconnect
        shohoz_DbImpl.disconnect();
    }
}
