package com.example.emojiinclusion.emojy;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Value;

import java.net.InetAddress;
import java.util.stream.Collectors;

@Controller
public class EmojiUiController {

    private final EmojiApplicationService applicationService;

    @Value("${spring.datasource.url}")
    private String dataSourceUrl;

    public EmojiUiController(final EmojiApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping
    public String fetchUI(Model model) {
        model.addAttribute("emojis", applicationService.fetchEmojis().stream().limit(20).map(Emoji::getStringValue)
                .collect(Collectors.joining ("")));

        var dataSourceUrlSubstring = dataSourceUrl.replace("jdbc:", "");
        logIt("***INFO*** EmojiUiController.fetchUI() dataSourceUrlSubstring = " + dataSourceUrlSubstring);
        String networkInfo = getHostInfo();
        logIt("***INFO*** EmojiUiController.fetchUI() networkInfo = " + networkInfo);
        //String databaseInfo = dataSourceUrlSubstring.substring(0, dataSourceUrlSubstring.indexOf(":")).toUpperCase();
        String msg = "Welcome! Time is: " + getTS() + "<br><br>";
        msg = msg + "The app is running on this host: " + networkInfo + "<br><br>";
        msg = msg + "The app is connected to this DB: " + dataSourceUrlSubstring;
        model.addAttribute("database", msg);
        return "index";
    }

    private static String getHostInfo()
    {
		// Obtain hostname & IP address 
		String theHostname = "no idea";
		String theIPaddress = "no idea";
		try 
		{
			theHostname = InetAddress.getLocalHost().getHostName();
			theIPaddress = InetAddress.getLocalHost().getHostAddress(); 
		}
		catch (Exception ex)
		{
			String theErrMsg = "Problem! An exception was raised when getting info about the network interfaces : " + ex.getMessage();
			theHostname = theErrMsg;
			logIt(theErrMsg);
		}

		//return
		String toReturn = theHostname + " (" + theIPaddress  +")";
        return toReturn;
    }

    private static String getTS()
	{
		String toReturn = "no idea";
		java.util.Date d = new java.util.Date();
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS");
		toReturn = formatter.format(d);
		return toReturn;
	}

    private static void logIt(String msg)
	{
		System.out.println("[EmojiUiController][v1]["+getTS()+"]"+msg);
	}  
}
