package forohfor.scryfall.api;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Utility class, mostly for dealing with casting JSON objects when they
 * may be null
 * @author ForOhForError
 */

public class JSONUtil {
	public static String getStringData(JSONObject data, String key)
	{
		Object obj = data.get(key);
		if(obj==null){
			return null;
		}
		return obj.toString();
	}
	
	public static Long getLongData(JSONObject data, String key)
	{
		Object obj = data.get(key);
		if(obj==null){
			return null;
		}
		if(obj instanceof Long){
			return (Long)obj;
		}
		return null;
	}
	
	public static Integer getIntData(JSONObject data, String key)
	{
		Object obj = data.get(key);
		if(obj==null){
			return null;
		}
		if(obj instanceof Long){
			return ((Long)obj).intValue();
		}
		return null;
	}
	
	public static HashMap<String,String> getStringMap(JSONObject data, String key)
	{
		Object obj = data.get(key);
		if(obj==null){
			return null;
		}
		
		HashMap<String,String> map = new HashMap<String,String>();
		
		JSONObject o = (JSONObject)obj;
		for(Object k:o.keySet())
		{
			map.put(((String)k).toLowerCase(), (String)o.get(k));
		}
		return map;
	}
	
	public static Boolean getBoolData(JSONObject data, String key)
	{
		Object obj = data.get(key);
		if(obj==null){
			return null;
		}
		if(obj instanceof Boolean){
			return (Boolean)obj;
		}
		return null;
	}
	
	public static String[] getStringArrayData(JSONObject data, String key)
	{
		Object obj = data.get(key);
		if(obj==null){
			return null;
		}
		
		JSONArray jarr = (JSONArray)obj;
		
		String[] arr = new String[jarr.size()];
		int i = 0;
		for(Object o:jarr)
		{
			arr[i] = (String)o;
			i++;
		}
		return arr;
	}
	
	public static Date getIsoDateData(JSONObject data, String key)
	{
		String date = getStringData(data,key);
		if(date==null)
		{
			return null;
		}
		String[] isoSplit = date.split("-");
		Calendar c = Calendar.getInstance();
		c.set(
				Integer.parseInt(isoSplit[0]), 
				Integer.parseInt(isoSplit[1]), 
				Integer.parseInt(isoSplit[2])
		);
		return c.getTime();
	}
}
