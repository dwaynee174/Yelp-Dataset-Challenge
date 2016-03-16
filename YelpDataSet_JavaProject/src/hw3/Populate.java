package HW3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import static java.lang.Class.forName;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import static java.lang.Class.forName;

public class Populate {
static String folder= "C:\\Users\\Dhwani\\Desktop\\YelpDataset\\YelpDataset-CptS451\\";
    public static void main(String[] args) {
        
        Connection con = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");

        } catch (Exception E) {
            System.err.println("Unable to load driver.");
            E.printStackTrace();
        }
        
            // user_yelp(con);
                user_friends(con);
                create_elite(con);
                create_votes(con);
                create_compliments(con);
                create_checkin(con);
                create_business(con);
                create_business_attribute(con);
                create_business_category(con);
                create_hours(con);
                create_reviews(con);
/*
        for (int i = 0; i < 4; i++) {
            if (args[i].equalsIgnoreCase("yelp_user.json")) {
                user_yelp(con,args[i]);
                user_friends(con,args[i]);
                create_elite(con,args[i]);
                create_votes(con,args[i]);
                create_compliments(con,args[i]);
            } else if (args[i].equalsIgnoreCase("yelp_checkin.json")) {
                create_checkin(con,args[i]);
            } else if (args[i].equalsIgnoreCase("yelp_business.json")) {
                create_business(con,args[i]);
                create_business_attribute(con,args[i]);
                create_business_category(con,args[i]);
                create_hours(con,args[i]);
            } else if (args[i].equalsIgnoreCase("yelp_review.json")) {
                create_reviews(con,args[i]);

            }
        }*/
    }


    public static void user_yelp(Connection con) {
        PreparedStatement stmt;
        BufferedReader br = null;

        try {
            String sCurrentLine;
            br = new BufferedReader(new FileReader(folder+"yelp_user.json"));

            while ((sCurrentLine = br.readLine()) != null) {
                org.json.simple.JSONObject jsonobject = (org.json.simple.JSONObject) new JSONParser().parse(sCurrentLine);

                String yelping_since = (String) jsonobject.get("yelping_since");
                long review_count = (long) jsonobject.get("review_count");
                String name = (String) jsonobject.get("name");
                String user_id = (String) jsonobject.get("user_id");
                long fan = (long) jsonobject.get("fans");
                double average_stars = (double) jsonobject.get("average_stars");

                Integer pp = (int) (review_count);
                Integer qq = (int) (fan);
                try {

                    stmt = con.prepareStatement("Insert into user_yelp values (?,?,?,?,?,?)");
                    stmt.setString(1, yelping_since);
                    stmt.setInt(2, pp);
                    stmt.setString(3, name);
                    stmt.setString(4, user_id);
                    stmt.setInt(5, qq);
                    stmt.setDouble(6, average_stars);

                    stmt.executeUpdate();
                    stmt.close();
                } catch (SQLException E) {
                    System.out.println("SQLException: " + E.getMessage());
                    System.out.println("SQLState:" + E.getSQLState());
                    System.out.println("VendorError:" + E.getErrorCode());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void user_friends(Connection con) {

        PreparedStatement stmt;
        BufferedReader br = null;

        try {
            String sCurrentLine;
            br = new BufferedReader(new FileReader(folder+"yelp_user.json"));

            while ((sCurrentLine = br.readLine()) != null) {
                org.json.simple.JSONObject jsonobject = (org.json.simple.JSONObject) new JSONParser().parse(sCurrentLine);

                String user_id = (String) jsonobject.get("user_id");

                JSONArray array = (JSONArray) jsonobject.get("friends");
                for (int i = 0; i < array.size(); i++) {

                    try {

                        stmt = con.prepareStatement("Insert into friends values (?,?)");
                        stmt.setString(1, user_id);
                        stmt.setString(2, (String) array.get(i));

                        stmt.executeUpdate();
                        stmt.close();
                    } catch (SQLException E) {
                        System.out.println("SQLException: " + E.getMessage());
                        System.out.println("SQLState:" + E.getSQLState());
                        System.out.println("VendorError:" + E.getErrorCode());
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void create_elite(Connection con) {
        PreparedStatement stmt;
        BufferedReader br = null;

        try {
            String sCurrentLine;
            br = new BufferedReader(new FileReader(folder+"yelp_user.json"));

            while ((sCurrentLine = br.readLine()) != null) {
                org.json.simple.JSONObject jsonobject = (org.json.simple.JSONObject) new JSONParser().parse(sCurrentLine);

                String user_id = (String) jsonobject.get("user_id");

                org.json.simple.JSONArray array = (org.json.simple.JSONArray) jsonobject.get("elite");
                for (int i = 0; i < array.size(); i++) {

                    long year = (long) array.get(i);

                    try {

                        stmt = con.prepareStatement("Insert into elite values (?,?)");
                        stmt.setString(1, user_id);
                        stmt.setInt(2, (int) year);
                        stmt.executeUpdate();
                        stmt.close();
                    } catch (SQLException E) {
                        System.out.println("SQLException: " + E.getMessage());
                        System.out.println("SQLState:" + E.getSQLState());
                        System.out.println("VendorError:" + E.getErrorCode());
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void create_votes(Connection con) {
        PreparedStatement stmt;
        BufferedReader br = null;

        try {
            String sCurrentLine;
            br = new BufferedReader(new FileReader(folder+"yelp_user.json"));

            while ((sCurrentLine = br.readLine()) != null) {
                org.json.simple.JSONObject jsonobject = (org.json.simple.JSONObject) new JSONParser().parse(sCurrentLine);

                String user_id = (String) jsonobject.get("user_id");
                org.json.simple.JSONObject nestedObject = (org.json.simple.JSONObject) jsonobject.get("votes");
                long funnyLong = (long) nestedObject.get("funny");
                long usefulLong = (long) nestedObject.get("useful");
                long coolLong = (long) nestedObject.get("cool");

                Integer funny = (int) (funnyLong);
                Integer useful = (int) (usefulLong);
                Integer cool = (int) (coolLong);

                try {
                    stmt = con.prepareStatement("Insert into votes values (?,?,?,?)");
                    stmt.setString(1, user_id);
                    stmt.setInt(2, funny);
                    stmt.setInt(3, useful);
                    stmt.setInt(4, cool);

                    stmt.executeUpdate();
                    stmt.close();
                } catch (SQLException E) {
                    System.out.println("SQLException: " + E.getMessage());
                    System.out.println("SQLState:" + E.getSQLState());
                    System.out.println("VendorError:" + E.getErrorCode());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void create_checkin(Connection con) {
        PreparedStatement stmt;
        BufferedReader br = null;

        try {
            String sCurrentLine;
            br = new BufferedReader(new FileReader(folder+"yelp_checkin.json"));

            while ((sCurrentLine = br.readLine()) != null) {
                org.json.simple.JSONObject jsonobject = (org.json.simple.JSONObject) new JSONParser().parse(sCurrentLine);
                String business_id = (String) jsonobject.get("business_id");
                String type = (String) jsonobject.get("type");
                org.json.simple.JSONObject nestedObject = (JSONObject) jsonobject.get("checkin_info");
                Collection keyColl = nestedObject.keySet();
                Object[] keyType = keyColl.toArray();
                for (int i = 0; i < keyType.length; i++) {

                    String combine = (String) keyType[i];
                    String[] parts = combine.split("-");
                    Integer time = Integer.parseInt(parts[0]);
                    Integer day = Integer.parseInt(parts[1]);

                    try {
                        stmt = con.prepareStatement("Insert into checkin values (?,?,?,?,?)");
                        stmt.setString(1, business_id);
                        stmt.setString(2, type);
                        stmt.setInt(3, time);
                        stmt.setInt(4, day);
                        stmt.setInt(5, (int) ((long) nestedObject.get(keyType[i])));
                        stmt.executeUpdate();
                        stmt.close();
                    } catch (SQLException E) {
                        System.out.println("SQLException: " + E.getMessage());
                        System.out.println("SQLState:" + E.getSQLState());
                        System.out.println("VendorError:" + E.getErrorCode());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void create_compliments(Connection con) {
        PreparedStatement stmt;
        BufferedReader br = null;

        try {
            String sCurrentLine;
            br = new BufferedReader(new FileReader(folder+"yelp_user.json"));

            while ((sCurrentLine = br.readLine()) != null) {
                org.json.simple.JSONObject jsonobject = (org.json.simple.JSONObject) new JSONParser().parse(sCurrentLine);

                String user_id = (String) jsonobject.get("user_id");

                org.json.simple.JSONObject nestedObject = (org.json.simple.JSONObject) jsonobject.get("compliments");
                Collection keyColl = nestedObject.keySet();
                Object[] keyType = keyColl.toArray();
                for (int i = 0; i < keyType.length; i++) {
                    //System.out.println(user_id + " " + keyType[i] +" " + nestedObject.get(keyType[i]));
                    try {
                        stmt = con.prepareStatement("Insert into compliment values (?,?,?)");
                        stmt.setString(1, user_id);
                        stmt.setString(2, (String) keyType[i]);
                        stmt.setInt(3, (int) ((long) nestedObject.get(keyType[i])));
                        stmt.executeUpdate();
                        stmt.close();
                    } catch (SQLException E) {
                        System.out.println("SQLException: " + E.getMessage());
                        System.out.println("SQLState:" + E.getSQLState());
                        System.out.println("VendorError:" + E.getErrorCode());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void create_business(Connection con) {
        PreparedStatement stmt;
        BufferedReader br = null;

        try {
            String sCurrentLine;
            br = new BufferedReader(new FileReader(folder+"yelp_business.json"));

            while ((sCurrentLine = br.readLine()) != null) {
                org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) new JSONParser().parse(sCurrentLine);

                String bid = (String) jsonObject.get("business_id");
                String address = (String) jsonObject.get("full_address");
                String cat = (String) jsonObject.get("categories").toString();
                String city = (String) jsonObject.get("city");
                int count = (int) ((long) jsonObject.get("review_count"));
                String name = (String) jsonObject.get("name");
                String longitude = (String) jsonObject.get("longitude").toString();
                String latitude = (String) jsonObject.get("latitude").toString();
                String state = (String) jsonObject.get("state");
                double stars = ((double) jsonObject.get("stars"));
                stmt = con.prepareStatement("insert into business values(?, ?, ?, ?, ?, ?, ?, ?, ?)");
                stmt.setString(1, bid);
                stmt.setString(2, address);
                stmt.setString(3, city);
                stmt.setInt(4, count);
                stmt.setString(5, name);
                stmt.setString(6, longitude);
                stmt.setString(7, latitude);
                stmt.setString(8, state);
                stmt.setDouble(9, stars);
                stmt.executeUpdate();
                stmt.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void create_business_attribute(Connection con) {
        PreparedStatement stmt;
        BufferedReader br = null;

        try {
            String sCurrentLine;
            br = new BufferedReader(new FileReader(folder+"yelp_business.json"));

            while ((sCurrentLine = br.readLine()) != null) {
                org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) new JSONParser().parse(sCurrentLine);

                org.json.simple.JSONObject nestedObject = (JSONObject) jsonObject.get("attributes");
                Collection keyColl = nestedObject.keySet();
                Object[] keyType = keyColl.toArray();
                for (int i = 0; i < keyType.length; i++) {
                    String attrkey = (String) keyType[i];
                    String attrvalue = (String) nestedObject.get(keyType[i]).toString();

                    String bid = (String) jsonObject.get("business_id");

                    if (nestedObject.get(keyType[i]) instanceof JSONObject) {
                        String temp = (String) keyType[i];
                        org.json.simple.JSONObject nestedObjectOne = (JSONObject) nestedObject.get(temp);

                        Collection keyCollOne = nestedObjectOne.keySet();
                        Object[] keyTypeOne = keyCollOne.toArray();
                        for (int j = 0; j < keyTypeOne.length; j++) {
                            attrkey = temp + " " + keyTypeOne[j];
                            attrvalue = (String) nestedObjectOne.get(keyTypeOne[j]).toString();

                            stmt = con.prepareStatement("insert into business_attribute values(?, ?, ?)");
                            stmt.setString(1, bid);
                            stmt.setString(2, attrkey);
                            stmt.setString(3, attrvalue);
                            stmt.executeUpdate();
                            stmt.close();
                        }
                    } else {
                        stmt = con.prepareStatement("insert into business_attribute values(?, ?, ?)");
                        stmt.setString(1, bid);
                        stmt.setString(2, attrkey);
                        stmt.setString(3, attrvalue);
                        stmt.executeUpdate();
                        stmt.close();
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void create_business_category(Connection con) {
        PreparedStatement stmt;
        BufferedReader br = null;

        try {
            String sCurrentLine;
            br = new BufferedReader(new FileReader(folder+"yelp_business.json"));

            while ((sCurrentLine = br.readLine()) != null) {
                org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) new JSONParser().parse(sCurrentLine);
                String bid = (String) jsonObject.get("business_id");
                JSONArray array = (JSONArray) jsonObject.get("categories");
                if (array.size() == 1) {
                    String one = (String) array.get(0);
                    // System.out.println(bid + "  " + one + " null");
                    try {

                        stmt = con.prepareStatement("Insert into business_category values (?,?,?)");
                        stmt.setString(1, bid);
                        stmt.setString(2, one);
                        stmt.setString(3, "null");

                        stmt.executeUpdate();
                        stmt.close();
                    } catch (SQLException E) {
                        System.out.println("SQLException: " + E.getMessage());
                        System.out.println("SQLState:" + E.getSQLState());
                        System.out.println("VendorError:" + E.getErrorCode());
                    }

                } else {
                    String last = (String) array.get(array.size() - 1);
                    for (int i = 0; i < (array.size() - 1); i++) {
                        String two = (String) array.get(i);
                        System.out.println(bid + "  " + last + "  " + two);
                        try {

                            stmt = con.prepareStatement("Insert into business_category values (?,?,?)");
                            stmt.setString(1, bid);
                            stmt.setString(2, last);
                            stmt.setString(3, two);

                            stmt.executeUpdate();
                            stmt.close();
                        } catch (SQLException E) {
                            System.out.println("SQLException: " + E.getMessage());
                            System.out.println("SQLState:" + E.getSQLState());
                            System.out.println("VendorError:" + E.getErrorCode());
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public static void create_hours(Connection con) {
        PreparedStatement stmt;
        BufferedReader br = null;

        try {
            String sCurrentLine;
            br = new BufferedReader(new FileReader(folder+"yelp_business.json"));

            while ((sCurrentLine = br.readLine()) != null) {
                org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) new JSONParser().parse(sCurrentLine);
                String bid = (String) jsonObject.get("business_id");
                String open = "null";
                String close = "null";
                org.json.simple.JSONObject nestedObject = (JSONObject) jsonObject.get("hours");
                Collection keyColl = nestedObject.keySet();
                Object[] keyType = keyColl.toArray();
                for (int i = 0; i < keyType.length; i++) {
                    String attrkey = (String) keyType[i];
                    // String attrvalue = (String) nestedObject.get(keyType[i]).toString();

                    if (nestedObject.get(keyType[i]) instanceof JSONObject) {
                        String day = (String) keyType[i];
                        org.json.simple.JSONObject nestedObjectOne = (JSONObject) nestedObject.get(day);

                        Collection keyCollOne = nestedObjectOne.keySet();
                        Object[] keyTypeOne = keyCollOne.toArray();
                        for (int j = 0; j < keyTypeOne.length; j++) {
                            attrkey = (String) keyTypeOne[j];
                            if (attrkey.equals("open")) {
                                open = (String) nestedObjectOne.get(keyTypeOne[j]).toString();
                            } else {
                                close = (String) nestedObjectOne.get(keyTypeOne[j]).toString();
                            }
                        }
                        stmt = con.prepareStatement("insert into operatingHours values(?, ?, ? ,?)");
                        stmt.setString(1, bid);
                        stmt.setString(2, day);
                        stmt.setString(3, open);
                        stmt.setString(4, close);
                        stmt.executeUpdate();
                        stmt.close();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public static void create_reviews(Connection con) {
        PreparedStatement stmt;
        BufferedReader br = null;

        try {
            String sCurrentLine;
            br = new BufferedReader(new FileReader(folder+"yelp_review.json"));

            while ((sCurrentLine = br.readLine()) != null) {
                org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) new JSONParser().parse(sCurrentLine);
                String user_id = (String) jsonObject.get("user_id");
                String review_id = (String) jsonObject.get("review_id");

                String business_id = (String) jsonObject.get("business_id");
                int stars = (int) ((long) jsonObject.get("stars"));
                String date = (String) jsonObject.get("date");
                String text = ((String) jsonObject.get("text").toString());
                //   System.out.println(text);

                try {

                    stmt = con.prepareStatement("Insert into Reviews values (?,?,?,?,?,?)");
                    stmt.setString(1, user_id);
                    stmt.setString(2, review_id);
                    stmt.setInt(3, stars);
                    stmt.setString(4, date);
                    //System.out.println(user_id);
                    stmt.setString(5, text);
                    stmt.setString(6, business_id);

                    stmt.executeUpdate();
                    stmt.close();
                } 
                catch (SQLException E) {
                    //System.out.println(review_id);
                    System.out.println("SQLException: " + E.getMessage());
                    System.out.println("SQLState:" + E.getSQLState());
                    System.out.println("VendorError:" + E.getErrorCode());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
