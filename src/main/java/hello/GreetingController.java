package hello;

import java.util.concurrent.atomic.AtomicLong;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import instanceClasses.CurrentReading;
import instanceClasses.CurrentTemperature;
import instanceClasses.Greeting;
import instanceClasses.HashStorage;
import instanceClasses.NoAccess;
import instanceClasses.Response;
import instanceClasses.SecurityConsts;
import jwt_generation.GenerateJWT;

@RestController
public class GreetingController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();
	private CurrentReading reading = new CurrentReading(0, 0, "zoneId", 0, "zoneName", "buildingId", "twCustomHeader1");
	private HashStorage storage = new HashStorage("zoneId", "buildingId", "setTemperature", 0.0);

	@RequestMapping(value = "/greeting", method = RequestMethod.GET)
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}

	@RequestMapping(value = "/protected_greeting", method = RequestMethod.POST)
	public Object NoAccess(@RequestParam(value = "name", defaultValue = "World") String name,
			@RequestHeader(value = SecurityConsts.HEADER_STRING, defaultValue = "none") String auth) {

		if (ValidateJWT.isValid(auth)) {//

			return new Greeting(counter.incrementAndGet(), String.format(template, name));

		} else {

			return new NoAccess();

		}
	}

	@RequestMapping(value = "/create_token", method = RequestMethod.POST)
	public GenerateJWT jwt(@RequestParam(value = "subject") String subject,
			@RequestParam(value = "duration") long duration, 
			@RequestParam(value = "name") String name,
			@RequestParam(value = "audience") String audience,
			@RequestParam(value = "issuer", defaultValue = SecurityConsts.ISS) String issuer

	) {

		return new GenerateJWT(name, issuer, subject, audience, duration * 3600000);
	}

	/*
	 * @RequestMapping(value = "/storeCurrentReadings", method = RequestMethod.POST)
	 * public String reading (
	 * 
	 * @RequestParam(value="setTemperatureMin", defaultValue="0") float
	 * setTemperatureMin,
	 * 
	 * @RequestParam(value="setTemperatureMax", defaultValue="0") float
	 * setTemperatureMax,
	 * 
	 * @RequestParam(value="zoneId", defaultValue="0") String zoneId,
	 * 
	 * @RequestParam(value="setTemperature", defaultValue="0") float setTemperature,
	 * 
	 * @RequestParam(value="zoneName", defaultValue="0") String zoneName,
	 * 
	 * @RequestParam(value="buildingId", defaultValue="0") String buildingId,
	 * 
	 * @RequestHeader(value="X-API-KEY") String twCustomHeader1
	 * 
	 * ) {
	 * 
	 * reading.setSetTemperature(setTemperature);
	 * 
	 * 
	 * return reading.getResponse(); }
	 * 
	 */

	@PostMapping("/storeCurrentReadings")

	public Response storeReadings(@RequestBody String stringToParse) {

		double setTemperature;

		try {
			JSONArray jsonarray = new JSONArray(stringToParse);
			JSONObject jsonObj = jsonarray.getJSONObject(0);

			setTemperature = (double) jsonObj.getDouble("setTemperature");
			reading.setSetTemperature(setTemperature);
			return new Response("Ok!");
		} catch (JSONException e) {
			e.printStackTrace();
			return new Response("Failure!");
		}

	}

	@RequestMapping(value = "/readCurrentSettings", method = RequestMethod.GET)
	public CurrentTemperature getTemp(@RequestBody String stringToParse) {

		String temp = "" + reading.getSetTemperature();

		return new CurrentTemperature(temp);
	}

}