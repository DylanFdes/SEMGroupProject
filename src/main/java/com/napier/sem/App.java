package com.napier.sem;

import java.sql.*;

public class App
{
    /**
     * Connection to MySQL database.
     */
    private Connection con = null;

    /**
     * Connect to the MySQL database.
     */
    public void connect()
    {
        try
        {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 10;
        for (int i = 0; i < retries; ++i)
        {
            System.out.println("Connecting to database...");
            try
            {
                // Wait a bit for db to start
                Thread.sleep(30000);
                // Connect to database
                con = DriverManager.getConnection("jdbc:mysql://db:3306/world?useSSL=false", "root", "example");
                System.out.println("Successfully connected");
                break;
            }
            catch (SQLException sqle)
            {
                System.out.println("Failed to connect to database attempt " + Integer.toString(i));
                System.out.println(sqle.getMessage());
            }
            catch (InterruptedException ie)
            {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }
    }


    /**
     * Disconnect from the MySQL database.
     */
    public void disconnect()
    {
        if (con != null)
        {
            try
            {
                // Close connection
                con.close();
            }
            catch (Exception e)
            {
                System.out.println("Error closing connection to database");
            }
        }
    }

    public Country getCountries()
    {
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String sql = "SELECT code, name, population "
                    + "FROM country "
                    + "ORDER BY population ASC";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(sql);
            // Return new country if valid.
            // Check one is returned
            if (rset.next())
            {
                Country ctry = new Country();
                ctry.code = rset.getString("code");
                ctry.name = rset.getString("name");
                ctry.population = rset.getInt("population");
                return ctry;
            }
            else
                return null;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country data");
            return null;
        }

    }


    public void displayEmployee(Country ctry)
    {
        if (ctry != null)
        {
            System.out.println(
                    ctry.code + " "
                    + ctry.name + " "
                    + ctry.population + "\n");
        }
    }

    public void displayRecords()
    {
        try
        {
            System.out.println("All the countries in the world organised by largest population to smallest");
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String sql = "SELECT code, name, population "
                    + "FROM country "
                    + "ORDER BY population DESC";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(sql);
            // Return new country if valid.
            // Check one is returned
            while (rset.next())
            {
                System.out.println(rset.getString("name"));
            }

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country data");

        }
    }
    public static void main(String[] args)
    {
        // Create new Application
        App a = new App();


        // Connect to database
        a.connect();
        // Get Country
        //Country ctry = a.getCountries();
        // Display results
        //a.displayEmployee(ctry);
        a.displayRecords();
        // Disconnect from database
        a.disconnect();
    }

}