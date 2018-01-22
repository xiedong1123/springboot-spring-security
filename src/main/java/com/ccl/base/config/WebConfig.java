package com.ccl.base.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.ccl.base.utils.converter.CryptFastJsonHttpMessageConverter;
import com.ccl.base.utils.security.xss.XSSFilter;

/**
 * 
 * @ClassName：WebConfig
 * @Description：mvc 配置
 * @Author：xiedong
 * @Date：2017年12月19日上午9:39:48
 * @version：1.0.0
 */
@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {

	/**
	 * 使用fastjson替换默认的Jackson
	 */
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		//1.设置fastjson相关
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
		
		
		//2.处理中文乱码问题
		List<MediaType> fastMediaTypes = new ArrayList<>();
		fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
		fastMediaTypes.add(MediaType.TEXT_HTML);
		fastMediaTypes.add(MediaType.APPLICATION_XML);
		
		//3.设置一个converter
		FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
		fastConverter.setSupportedMediaTypes(fastMediaTypes);
		
		fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
		fastConverter.setFastJsonConfig(fastJsonConfig);
		
		converters.add(fastConverter);
		converters.add(cryptFastJsonHttpMessageConverter());
	}
	
	/**
	 * 
	 * @MethodName：cryptFastJsonHttpMessageConverter
	 * @return
	 * @ReturnType：CryptFastJsonHttpMessageConverter
	 * @Description：自定义处理请求参数 与 返回参数
	 * @Creator：xiedong
	 * @CreateTime：2017年12月18日下午5:21:16
	 * @Modifier：
	 * @ModifyTime：
	 */
	@Bean
	public CryptFastJsonHttpMessageConverter cryptFastJsonHttpMessageConverter() {
		//3.设置一个converter
		CryptFastJsonHttpMessageConverter cryptFastJsonHttpMessageConverter = new CryptFastJsonHttpMessageConverter();
		/*fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
		cryptFastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);*/
		
		return cryptFastJsonHttpMessageConverter;
	}
	
	
	
	
	
	/**
	 * 
	 * @MethodName：CommonsMultipartResolver
	 * @return
	 * @ReturnType：CommonsMultipartResolver
	 * @Description：配置文件上传
	 * @Creator：xiedong
	 * @CreateTime：2017年9月7日上午3:04:44
	 * @Modifier：
	 * @ModifyTime：
	 */
	@Bean
	public CommonsMultipartResolver CommonsMultipartResolver() {
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
		commonsMultipartResolver.setDefaultEncoding("utf-8");
		commonsMultipartResolver.setMaxInMemorySize(40960);
		commonsMultipartResolver.setMaxUploadSize(104857600);
		return commonsMultipartResolver;
	}
	/**
	 * 配置登录页视图跳转
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("/system/login");
		super.addViewControllers(registry);
	}
	
	
	/**
	 * 
	 * @MethodName：viewResolver
	 * @return
	 * @ReturnType：InternalResourceViewResolver
	 * @Description：视图添加前后缀
	 * @Creator：xiedong
	 * @CreateTime：2017年12月19日上午9:40:59
	 * @Modifier：
	 * @ModifyTime：
	 */
	@Bean  
    public InternalResourceViewResolver viewResolver(){  
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();  
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");  
        viewResolver.setViewClass(JstlView.class);  
        return  viewResolver;  
    }  
	
	
	/**
	 * 设置静态资源
	 */
    @Override  
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //addResourceLocations指的是文件放置的目录，addResourceHandler指的是对外暴露的访问路径  
        registry.addResourceHandler("/static/**", "/popup/**").addResourceLocations("/static/", "/popup/");
        super.addResourceHandlers(registry);
    }  
    /**
     * 
     * @MethodName：xssFilter
     * @return
     * @ReturnType：XSSFilter
     * @Description：防止xss攻击
     * @Creator：xiedong
     * @CreateTime：2017年12月18日下午5:20:24
     * @Modifier：
     * @ModifyTime：
     */
    @Bean
    public XSSFilter xssFilter(){
    	return new XSSFilter();
    }
}
