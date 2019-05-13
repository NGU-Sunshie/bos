package com.itheima.bos.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Region;
import com.itheima.bos.domain.Staff;
import com.itheima.bos.service.IRegionService;
import com.itheima.bos.utils.PageBean;
import com.itheima.bos.utils.PinYin4jUtils;
import com.itheima.bos.web.action.base.BaseAction;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * @author Administrator
 *
 */
@Controller
@Scope("prototype")
public class RegionAction extends BaseAction<Region> {
	// 属性驱动接受页面上传的文件
	private File regionFile;
	// 注入service
	@Autowired
	private IRegionService regionService;

	public void setRegionFile(File regionFile) {
		this.regionFile = regionFile;
	}

	/**
	 * 实现区域导入功能
	 * 
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public String importXls() throws Exception {
		// 使用POI解析Excel文件
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(new FileInputStream(regionFile));
		// 根据名称获得指定Sheet对象
		HSSFSheet sheet = hssfWorkbook.getSheet("Sheet1");
		ArrayList<Region> regionList = new ArrayList<Region>();
		// 遍历Sheet,获得行对象
		for (Row row : sheet) {
			int rowNum = row.getRowNum();
			if (rowNum == 0) {
				continue;
			}
			// 获取每列中的数据
			String id = row.getCell(0).getStringCellValue();
			String province = row.getCell(1).getStringCellValue();
			String city = row.getCell(2).getStringCellValue();
			String district = row.getCell(3).getStringCellValue();
			String postcode = row.getCell(4).getStringCellValue();
			// 把区域数据封装到区域对象中
			Region region = new Region(id, province, city, district, postcode, null, null, null);
			// 获取剩下属性
			province = province.substring(0, province.length() - 1);
			city = city.substring(0, city.length() - 1);
			district = district.substring(0, district.length() - 1);
			String info = province + city + district;
			String[] headByString = PinYin4jUtils.getHeadByString(info);
			String shortcode = StringUtils.join(headByString);
			// 城市编码---->>shijiazhuang
			String citycode = PinYin4jUtils.hanziToPinyin(city, "");
			region.setShortcode(shortcode);
			region.setCitycode(citycode);
			regionList.add(region);
		}
		// 批量保存区域对象
		regionService.saveBatch(regionList);
		return NONE;
	}

	/**
	 * 分页查询方法
	 * 
	 */
	public String pageQuery(){
		regionService.pageQuery(pageBean);
		java2Json(pageBean, new String[] { "currentPage", "detachedCriteria", "pageSize" ,"subareas"});
		return NONE;
	}
	/**
	 * 区域添加功能
	 */
	public String add(){
		regionService.save(model);
		return LIST;
	}
	/**
	 * 区域删除功能
	 */
	public String delete(){
		regionService.delete(model.getId());
		return LIST;
	}
	/**
	 * 区域修改功能
	 */
	public String edit(){
		regionService.update(model);
		return LIST;
	}
	/**
	 * 检查区域编号是否存在
	 * @throws IOException 
	 */
	public String checkIdExists() throws IOException{
		String check = regionService.checkIdExists(model.getId());
		printToWEB(check);
		return NONE;
	}
	//属性驱动接受页面提交的过滤参数
	private String q;
	public void setQ(String q) {
		this.q = q;
	}

	/**
	 * 查询所有区域，写回json数据
	 */
	public String listajax(){
		
		List<Region> regionList = null;
		if(StringUtils.isNotBlank(q)){
			regionList = regionService.listajaxByQ(q);
		}else{
			regionList = regionService.listajax();
		}
		java2Json(regionList, new String[]{"subareas","province","city",
				"district","postcode","shortcode","citycode"});
		return NONE;
	}

}
