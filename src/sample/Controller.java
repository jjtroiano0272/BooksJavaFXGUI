package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class Controller {

	@FXML
	void connect(ActionEvent event) {
			final String DATABASE_URL = "jdbc:derby:lib\\books"; // Not gonna change, thus fina;
			final String SELECT_QUERY =
					"SELECT authorID, firstName, lastName FROM authors"; // This is directly SQL.
			// Make sure table & field names match up
			
			/*
			 * THIS TRY BLOCK IS SUPER FUCKING IMPORTANT!!! UNDERSTAND IT!
			 * */
			// use try-with-resources to connect to and query the database
			try (
					java.sql.Connection connection = java.sql.DriverManager.getConnection(
							DATABASE_URL, "deitel", "deitel");  // user/pass are params in
					// Getconnection class covered in...video 2??? You'd have to have created a username and password
					// some of this stuff is within the Apache Derby tutorial
					java.sql.Statement statement = connection.createStatement();
					java.sql.ResultSet resultSet = statement.executeQuery(SELECT_QUERY)) // ResultSet SUPER imporant
			// (it's THE DATA), this works getting passed the params on l.20, authorID, first and Last name
			//    SQL query.
			{
				// get ResultSet's meta data
				java.sql.ResultSetMetaData metaData = resultSet.getMetaData();
				int numberOfColumns = metaData.getColumnCount(); // Figuring out how many columns are in
				// the query (l.21)
				
				System.out.printf("Authors Table of Books Database:%n%n");
				
				// display the names of the columns in the ResultSet
				for (int i = 1; i <= numberOfColumns; i++)
					System.out.printf("%-8s\t", metaData.getColumnName(i)); // Formats as left align, 8
				// spaces preceding. Authorname, firstname, lastname
				System.out.println();
				
				// display query results
				while (resultSet.next()) // We don't know how many results we'll have.
				{
					for (int i = 1; i <= numberOfColumns; i++)
						System.out.printf("%-8s\t", resultSet.getObject(i));
					System.out.println();
				}
			} // AutoCloseable objects' close methods are called now
			catch (java.sql.SQLException sqlException)
			{
				sqlException.printStackTrace();
			}
			// TOTALLY OPTIONAL. Catches any exception.
			catch (Exception ex){
				System.out.println("Fuckin' whoops!");
			}
	}
	
}
