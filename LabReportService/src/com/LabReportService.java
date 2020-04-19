package com;

import model.LabReport;
//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
//For JSON
import com.google.gson.*;
//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;
@Path("/LabReports")
public class LabReportService
{
LabReport LabreportObj = new LabReport();
@GET
@Path("/")
@Produces(MediaType.TEXT_HTML)
public String readItems()
{
return LabreportObj.readLabReports();
}
@POST
@Path("/")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.TEXT_PLAIN)
public String insertItem(@FormParam("lReportNum") String lReportNum, 
		@FormParam("lReportName") String lReportName,
		
		@FormParam("lReportDet") String lReportDet) 
{
	String output = LabreportObj.insertLabReport(lReportNum, lReportName, lReportDet);
	return output;
}

@PUT
@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.TEXT_PLAIN)
public String updateLabReport(String labreportData) { // Convert the input string to a JSON object
	JsonObject LabreportObject = new JsonParser().parse(labreportData).getAsJsonObject();

	// Read the values from the JSON object
	String lReportID= LabreportObject.get("lReportID").getAsString();
	String lReportNum = LabreportObject.get("lReportNum").getAsString();
	String lReportName = LabreportObject.get("lReportName").getAsString();
	
	String lReportDet = LabreportObject.get("lReportDet").getAsString();

	String output = LabreportObj.updateLabReport(lReportID, lReportNum, lReportName, lReportDet);

	return output;
}

@DELETE
@Path("/")
@Consumes(MediaType.APPLICATION_XML)
@Produces(MediaType.TEXT_PLAIN)
public String deleteLabReport(String labreportData)

{
	// Convert the input string to an XML document
	Document doc = Jsoup.parse(labreportData, "", Parser.xmlParser()); 
	//Read the value from the element <itemID> 
	String itemID = doc.select("lReportID").text();

	String output = LabreportObj.deleteLabReport(itemID);

	return output;
}




}