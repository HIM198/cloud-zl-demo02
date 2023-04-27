package com.zl.vo;

import lombok.Data;

import java.util.HashMap;

@Data
public class Result {
	private boolean success;
	private Integer code;
	private String message;
	private HashMap<String,Object> data = new HashMap<>();

	//当前的result不对外提供公开的构造方法，而是提供静态方法，在静态方法中创建result对象
	private Result(){}

	/**
	 * 成功调用的方法
	 * @return
	 */
	public static Result ok(){
		Result r = new Result();//调用自己私有的构造方法
		r.setSuccess(true);
		r.setCode(ResultCode.OK);
		r.setMessage("成功");
		return r;
	}

	/**
	 * 失败调用的方法
	 * @return
	 */
	public static Result error(){
		Result r = new Result();//调用自己私有的构造方法
		r.setSuccess(false);
		r.setCode(ResultCode.ERROR);
		r.setMessage("失败");
		return r;
	}

	/**
	 * 使用者调用ok方法或者error方法之后，如果需要修改message信息，那么可以继续调用这个方法，修改message信息
	 * @param message
	 * @return
	 */
	public Result message(String message){
		this.setMessage(message);
		return this;
	}

	public Result code(Integer code){
		this.setCode(code);
		return this;
	}

	public Result data(HashMap<String,Object> map){
		this.setData(map);
		return this;
	}


	public Result data(String key,Object value){
		this.data.put(key,value);
		return this;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public HashMap<String, Object> getData() {
		return data;
	}

	public void setData(HashMap<String, Object> data) {
		this.data = data;
	}
}
