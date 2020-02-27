# JpaClassGenerater
spring boot jpa实体类生成工具，能够依据数据库表生成对应的entity(实体类)、dao、service、controller等java类，能够配置生成文件的作者等信息，自动编写内容。

## 生成的文件示例
### entity类

	package test;
	
	import lombok.Data;
	import javax.persistence.*;
	import org.hibernate.annotations.GenericGenerator;
	import java.io.Serializable;
	
	/**
	 *
	 * @Author shalt gao
	 * @Date 2020-02-26 16:35:12
	 */
	
	@Data
	@Entity
	@Table(name = "app_text_color")
	public class AppTextColor implements Serializable {
	
		private static final long serialVersionUID = 1L;
	
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;
	
		/** 类别 */
		@Column(name = "classify")
		private String classify;
	
		/** 中文 */
		@Column(name = "chinese")
		private String chinese;
	
		/** 颜色 */
		@Column(name = "color")
		private String color;
	
		/** 字典名 */
		@Column(name = "dict_name")
		private String dictName;
	
		/** 是否测试 */
		@Column(name = "is_test")
		private String isTest;
	}

### controller类
	
	package test;
	
	import org.springframework.beans.factory.annotation.Autowired;
	import test.ResultValue;
	import test.ResultBuilder;
	import io.swagger.annotations.Api;
	import io.swagger.annotations.ApiOperation;
	import org.springframework.web.bind.annotation.*;
	import test.AppTextColorService;
	import test.AppTextColor;
	
	/**
	 *
	 * @Author shalt gao
	 * @Date 2020-02-26 16:35:12
	 */
	
	@RestController
	@Api(tags="AppTextColor Api")
	@RequestMapping(value = "")
	public class AppTextColorController {
	
		private final AppTextColorService appTextColorDao;
	
		@Autowired
		public AppTextColorController(final AppTextColorService appTextColorDao) {
			this.appTextColorDao = appTextColorDao;
		}
		@ApiOperation(value = "创建数据", httpMethod = "POST", response = AppTextColor.class)
		@RequestMapping(value = "/create", method = RequestMethod.POST)
		public ResultValue create (@RequestBody AppTextColor appTextColor) {
			return null;
		}
		@ApiOperation(value = "分页获取数据", httpMethod = "GET", response = AppTextColor.class)
		@RequestMapping(value = "/page", method = RequestMethod.GET)
		public ResultValue page (@RequestParam("pageSize") Integer pageSize, @RequestParam("pageNum") Integer pageNum) {
			return null;
		}
		@ApiOperation(value = "获取所有数据", httpMethod = "GET", response = AppTextColor.class)
		@RequestMapping(value = "/all", method = RequestMethod.GET)
		public ResultValue all (@RequestBody AppTextColor appTextColor) {
			return null;
		}
		@ApiOperation(value = "获取单个数据", httpMethod = "GET", response = AppTextColor.class)
		@RequestMapping(value = "/one", method = RequestMethod.GET)
		public ResultValue one (@RequestParam("id") Long id) {
			return null;
		}
		@ApiOperation(value = "删除单个数据", httpMethod = "DELETE", response = Boolean.class)
		@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
		public ResultValue delete (@RequestParam("id") Long id) {
			return null;
		}
	}
## 说明
生成的entity类使用lombok简化代码，使用jpa相关注解。项目需引入相关maven依赖包,相关依赖包可见我的[模板项目](https://github.com/newbby123/BaseStructure)

主键依据数据库表的主键信息生成，若数据库中主键类型为字符型，则使用uuid，若数字类型则自增，使用者可以自行更改

**controller类预生成了部分方法**，如果不配置result-value或result-builder参数，则不预生成方法

## 使用

##### 1、下载或克隆本项目

```
git pull git@github.com:newbby123/JpaClassGenerater.git
```

##### 2、配置
找到resources目录下的applicatio.yml文件，在其中配置数据库，生成文件路径及是否生成dao、service等。

##### 3、生成
直接运行主类的main方法

主类所在目录src>main>java,名称为Main
