package com.itheima.bos.web.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Region;
import com.itheima.bos.domain.Subarea;
import com.itheima.bos.service.ISubareaService;
import com.itheima.bos.utils.FileUtils;
import com.itheima.bos.web.action.base.BaseAction;

@Controller
@Scope("prototype")
public class SubareaAction extends BaseAction<Subarea> {
	@Autowired
	private ISubareaService subareaService;

	/**
	 * 分区添加功能
	 */
	public String add() {
		subareaService.save(model);
		return LIST;
	}

	/**
	 * 分页查询方法
	 * 
	 */
	public String pageQuery() {
		DetachedCriteria detachedCriteria = pageBean.getDetachedCriteria();
		// 动态添加过滤条件
		String addresskey = model.getAddresskey();
		if (StringUtils.isNotBlank(addresskey)) {
			detachedCriteria.add(Restrictions.like("addresskey", "%" + addresskey + "%"));
		}
		Region region = model.getRegion();
		if (region != null) {
			// 添加过滤条件，根据省份模糊查询-----多表关联查询，使用别名方式实现
			// 参数一：分区对象中关联的区域对象属性名称
			// 参数二：别名，可以任意
			detachedCriteria.createAlias("region", "r");
			String province = region.getProvince();
			if (StringUtils.isNotBlank(province)) {
				detachedCriteria.add(Restrictions.like("r.province", "%" + province + "%"));
			}
			String city = region.getCity();
			if (StringUtils.isNotBlank(city)) {
				detachedCriteria.add(Restrictions.like("r.city", "%" + city + "%"));
			}
			String district = region.getDistrict();
			if (StringUtils.isNotBlank(district)) {
				detachedCriteria.add(Restrictions.like("r.district", "%" + district + "%"));
			}

		}
		subareaService.pageQuery(pageBean);
		java2Json(pageBean, new String[] { "currentPage", "subareas", "detachedCriteria", "pageSize", "decidedzone" });
		return NONE;
	}

	/**
	 * 检查分域编号是否存在
	 * 
	 * @throws IOException
	 */
	public String checkIdExists() throws IOException {
		String check = subareaService.checkIdExists(model.getId());
		printToWEB(check);
		return NONE;
	}

	/**
	 * 分区修改
	 */
	public String edit() {
		Subarea subarea = subareaService.findByid(model.getId());
		subarea.setAddresskey(model.getAddresskey());
		subarea.setEndnum(model.getEndnum());
		subarea.setPosition(model.getPosition());
		subarea.setRegion(model.getRegion());
		subarea.setSingle(model.getSingle());
		subarea.setStartnum(model.getStartnum());
		subareaService.update(subarea);
		return LIST;
	}

	/**
	 * 分区删除功能
	 */
	public String delete() {
		subareaService.delete(model.getId());
		return LIST;
	}

	/**
	 * 分区导出功能
	 * 
	 * @throws IOException
	 */
	public String exportXls() throws IOException {
		// 第一步：查询所有的分区数据
		List<Subarea> list = subareaService.findAll();
		// 第二步：使用POI将数据写到Excel文件中
		// 在内存中创建一个Excel文件
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 创建一个标签页
		HSSFSheet sheet = workbook.createSheet("分区数据");
		// 创建标题行
		HSSFRow headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("分区编号");
		headRow.createCell(1).setCellValue("开始编号");
		headRow.createCell(2).setCellValue("结束编号");
		headRow.createCell(3).setCellValue("位置信息");
		headRow.createCell(4).setCellValue("省市区");
		for (Subarea subarea : list) {
			HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
			dataRow.createCell(0).setCellValue(subarea.getId());
			dataRow.createCell(1).setCellValue(subarea.getStartnum());
			dataRow.createCell(2).setCellValue(subarea.getEndnum());
			dataRow.createCell(3).setCellValue(subarea.getPosition());
			dataRow.createCell(4).setCellValue(subarea.getRegion().getName());
		}
		// 第三步：使用输出流进行文件下载（一个流、两个头）
		String filename = "分区数据.xls";
		String contentType = ServletActionContext.getServletContext().getMimeType(filename);
		ServletOutputStream out = ServletActionContext.getResponse().getOutputStream();
		ServletActionContext.getResponse().setContentType(contentType);
		// 获取客户端浏览器类型
		String agent = ServletActionContext.getRequest().getHeader("User-Agent");
		filename = FileUtils.encodeDownloadFilename(filename, agent);
		ServletActionContext.getResponse().setHeader("content-disposition", "attachment;filename=" + filename);
		workbook.write(out);
		return NONE;
	}

	/**
	 * 查询所有未关联到定区的分区，返回json
	 */
	public String listajax() {
		List<Subarea> list = subareaService.findListNotAssociation();
		java2Json(list, new String[] { "region", "decidedzone" });
		return NONE;
	}

	// 属性驱动接受页面的定区Id
	private String decidedzoneId;

	public void setDecidedzoneId(String decidedzoneId) {
		this.decidedzoneId = decidedzoneId;
	}

	/**
	 * 根据定区Id查询该定区下的所有分区
	 */
	public String findListByDecidedzoneId() {
		List<Subarea> list = subareaService.findListByDecidedzoneId(decidedzoneId);
		java2Json(list, new String[] { "decidedzone", "subareas" });
		return NONE;
	}
}
