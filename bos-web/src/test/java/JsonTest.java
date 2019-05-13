import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.PropertyPreFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.google.gson.Gson;
import com.itheima.bos.domain.Staff;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * 测试json的序列化及反序列化
 * @author Administrator
 *
 */
public class JsonTest {
	private Staff staff;
	/**
	 * 测试json-lib序列化反序列化
	 */
	@Test
	public void json_lib(){
		Staff staff = staffReady();
		String string = JSONObject.fromObject(staff).toString();
		System.out.println("staffJSON = "+string);
		printStaffResore(string);
	}
	private Staff staffReady() {
		
		Staff staff = new Staff();
		staff.setHaspda("1");
		staff.setId("1");
		staff.setName("张三");
		return staff;
	}
	/**
	 * 测试json-lib选择性序列化
	 */
	@Test
	public void json_lib2(){
		Staff staff = staffReady();
		JsonConfig jc = new JsonConfig();
		jc.setExcludes(new String[]{"decidedzones","deltag","id"});
		String string = JSONObject.fromObject(staff, jc).toString();
		System.out.println("staffJSON = "+string);
		printStaffResore(string);
	}
	private void printStaffResore(String string) {
		Staff staffRestore = (Staff) JSONObject.toBean(JSONObject.fromObject(string), Staff.class);
		System.out.println(staffRestore);
	}
	/**
	 * fastjson序列化及反序列化
	 */
	@Test
	public void fastjson(){
		//准备数据
		Staff staff = staffReady();
		//转化成JSON字符串
		String jsonString = JSON.toJSONString(staff);
		System.out.println(jsonString);
		//字符串转化成实体对象
		Staff parseObject = JSON.parseObject(jsonString,Staff.class); 
		System.out.println(parseObject);
	}
	
	/**
	 * fastjson选择性序列化及反序列化
	 */
	@Test
	public void fastjson1(){
		//准备数据
		Staff staff = staffReady();
		//这个过滤器的构造函数是设置选择需要保存的字段，可以翻查源码，就是循环getIncludes().add("xxx")
//		SimplePropertyPreFilter filter = new SimplePropertyPreFilter(Staff.class, "id","decidedzones");
		SimplePropertyPreFilter filter = new SimplePropertyPreFilter();
		//设置需要过滤的属性
//		filter.getExcludes().add("id");
		//设置需要的属性
		filter.getIncludes().add("id");
		//转化成字符串
		String jsonString = JSON.toJSONString(staff, filter);
		System.out.println(jsonString);
		//字符转转换成实体对象
		Staff parseObject = JSON.parseObject(jsonString,Staff.class); 
		System.out.println(parseObject);
	}
	/**
	 * 使用Gson
	 * 
	 */
	@Test
	public void GsonTest(){
		//创建Gson对象
		Gson gson = new Gson();
		//准备数据
		Staff staff = staffReady();
		//对象转换成字符串
		String jsonString = gson.toJson(staff);
		//打印字符串
		System.out.println(jsonString);
		//字符串转换成对象
		Staff fromJson = gson.fromJson(jsonString, Staff.class);
		System.out.println(fromJson);
		
	}

}

