package com.zwk.movie_recommend.common;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 返回参数
 * 
 * @author Peter
 * @email LR
 * @date 2019-02-19 16:22:07
 */
public class ResultVo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * code  0-true  1-false
	 */
	private int code;
	
	/**
	 * 是否操作成功
	 */
	private boolean success;
	
	/**
	 * 单一结果
	 */
	private Object result;
	/**
	 * 反馈信息1
	 */
	private String message;

	private String resultName;
	
	/**
	 * token
	 */
	private String token;
	
	
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	/**
	 * List结果集
	 */
	@SuppressWarnings("rawtypes")
	private List resultList;
	/**
	 * Map结果集
	 */
	@SuppressWarnings("rawtypes")
	private Map resultMap;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public boolean getSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getResultName() {
		return resultName;
	}
	public void setResultName(String resultName) {
		this.resultName = resultName;
	}
	public List getResultList() {
		return resultList;
	}
	public void setResultList(List resultList) {
		this.resultList = resultList;
	}
	public Map getResultMap() {
		return resultMap;
	}
	public void setResultMap(Map resultMap) {
		this.resultMap = resultMap;
	}
	
	
}
