/**
 * 自定义JQ 方法
 */
var default_i18n_path=ctp+"styles/sking_default/i18n/i18n_";
(function($) {
	$.extend({
		i18nText: function(key, language, path){
			var tPath = default_i18n_path;
			if(path){
				tPath = path;
			}
			if(language){
			}else{
				language = user_language;
			}
			$.getJSON(tPath+language+'.json',function(data){
				var i18nLang = {};
                if (data != null) {
                    i18nLang = data;
                }
                return i18nLang[key];
			});
		},
		/**
		 * 数字千分位格式化
		 * 
		 * @public
		 * @param mixed mVal 数值
		 * @param int iAccuracy 小数位精度(默认为2)
		 * @return string
		 */
		formatThNumber : function(mVal, iAccuracy) {
			var fTmp = 0.00;// 临时变量
			var iFra = 0;// 小数部分
			var iInt = 0;// 整数部分
			var aBuf = new Array(); // 输出缓存
			var bPositive = true; // 保存正负值标记(true:正数)
			/**
			 * 输出定长字符串，不够补0
			 * <li>闭包函数</li>
			 * 
			 * @param int
			 *            iVal 值
			 * @param int
			 *            iLen 输出的长度
			 */
			function funZero(iVal, iLen) {
				var sTmp = iVal.toString();
				var sBuf = new Array();
				for (var i = 0, iLoop = iLen - sTmp.length; i < iLoop; i++)
					sBuf.push('0');
				sBuf.push(sTmp);
				return sBuf.join('');
			}
			;

			if (typeof (iAccuracy) === 'undefined'){
				bPositive = (mVal >= 0);// 取出正负号
				fTmp = (isNaN(fTmp = parseFloat(mVal))) ? 0 : Math.abs(fTmp);// 强制转换为绝对值数浮点
				// 所有内容用正数规则处理
				iInt = parseInt(fTmp); // 分离整数部分
				if(fTmp.toString().indexOf(".")>0){
					iAccuracy = (fTmp.toString().split(".")[1].length); 
					iFra = parseInt((fTmp - iInt) * Math.pow(10, iAccuracy) + 0.5); // 分离小数部分(四舍五入)
					do {
						aBuf.unshift(funZero(iInt % 1000, 3));
					} while ((iInt = parseInt(iInt / 1000)));
					aBuf[0] = parseInt(aBuf[0]).toString();// 最高段区去掉前导0
					return ((bPositive) ? '' : '-') + aBuf.join(',') + '.' + ((0 === iFra) ? '00' : funZero(iFra, iAccuracy));
				}else{
					do {
						aBuf.unshift(funZero(iInt % 1000, 3));
					} while ((iInt = parseInt(iInt / 1000)));
					aBuf[0] = parseInt(aBuf[0]).toString();// 最高段区去掉前导0
					return ((bPositive) ? '' : '-') + aBuf.join(',');
				}
				
			}else{
				bPositive = (mVal >= 0);// 取出正负号
				fTmp = (isNaN(fTmp = parseFloat(mVal))) ? 0 : Math.abs(fTmp);// 强制转换为绝对值数浮点
				// 所有内容用正数规则处理
				iInt = parseInt(fTmp); // 分离整数部分
				iFra = parseInt((fTmp - iInt) * Math.pow(10, iAccuracy) + 0.5); // 分离小数部分(四舍五入)

				do {
					aBuf.unshift(funZero(iInt % 1000, 3));
				} while ((iInt = parseInt(iInt / 1000)));
				aBuf[0] = parseInt(aBuf[0]).toString();// 最高段区去掉前导0
				return ((bPositive) ? '' : '-') + aBuf.join(',') + '.' + ((0 === iFra) ? '00' : funZero(iFra, iAccuracy));
			}
			
		},
		/**
		 * 将千分位格式的数字字符串转换为浮点数
		 * 
		 * @public
		 * @param string
		 *            sVal 数值字符串
		 * @return float
		 */
		unformatThNumber : function(sVal) {
			var fTmp = parseFloat(sVal.replace(/,/g, ''));
			return (isNaN(fTmp) ? 0 : fTmp);
		},
		
		/**
		 * 表单重新渲染
		 */
		formRendering : function(fid,selectClass) {
			return formRendering(fid,selectClass);
		},
		
		/**
		 * 导出Excel
		 */
		exportExcel : function (fid, url){
			$.confirm_swal_ui("导出时间可能要几分钟，您确认要导出吗?",
					function(isConfirm) {
						if (isConfirm) {
							if(url && url!=null){
								exportExcel(fid,url);
							}else {
								exportExcel(fid,$("#"+fid).attr("action"));
							}
							
						}else {
						}
				});
		},
		showApprovalStepResult: function(requestId,title,area){
		    var url=ctp+"/core/approval/result/view?exclude=1&requestId="+requestId;
		    if(title){
		    }else{
		    	title = "进度详情";
		    }
		    if(area){
		    }else{
		    	area = ['80%','80%'];
		    }
		    openLayerIFrame(title,url, area);
		},
		/**
		 * 获取列表选中返回字符串，
		 */
		getDataTableChecked: function(pkname){
			var zhi = "";
		    var flag  = false;
		    $("input[name='"+pkname+"']:checkbox").each(function(){
		        if($(this).parents("table.DTFC_Cloned").length>0){
		            flag = true;
		            return ;
		        }
		    });
		    if(flag){
		        $("input[name='"+pkname+"']:checkbox:checked").each(function(){
		            if($(this).parents("table.DTFC_Cloned").length>0){
		                if(zhi.indexOf($(this).val())<0) {
		                    zhi += $(this).val() + ",";
		                }
		            }
		        });
		    }else{
		        $("input[name='"+pkname+"']:checkbox:checked").each(function(){
		            if(zhi.indexOf($(this).val())<0) {
		                zhi += $(this).val() + ",";
		            }
		        });
		    }
			return zhi;
		},
		/**
		 * 获取列表选中返回数组 row.data
		 */
		getDataTableCheckedRowDatas: function(dtid,pkname){
			var zhi = '';
			var datas = [];
		    var flag  = false;
		    var dttable = $("#"+dtid).DataTable();
		    $("input[name='"+pkname+"']:checkbox").each(function(){
		        if($(this).parents("table.DTFC_Cloned").length>0){
		            flag = true;
		            return ;
		        }
		    });
		    if(flag){
		        $("input[name='"+pkname+"']:checkbox:checked").each(function(){
		            if($(this).parents("table.DTFC_Cloned").length>0){
		                if(zhi.indexOf($(this).val())<0) {
		                    zhi += $(this).val() + ",";
		                    datas.push(dttable.row($(this)).data());
		                }
		            }
		        });
		    }else{
		        $("input[name='"+pkname+"']:checkbox:checked").each(function(){
		            if(zhi.indexOf($(this).val())<0) {
		                zhi += $(this).val() + ",";
		                datas.push(dttable.row($(this)).data());
		            }
		        });
		    }
			return datas;
		},
		confirm_swal_ui: function(text,callback){
			var title = "温馨提示";
			var type = "warning";
			if(text ==null){
				text = "您确认执行此操作吗？";
			}
			swal({
				title : title,
				text : text,
				type : type,
				showCancelButton : true,
				cancelButtonText : t_confirm_cancel,
				confirmButtonColor : "#DD6B55",
				confirmButtonText : t_confirm_ok,
				closeOnConfirm : true
			}, function(isConfirm) {
				if (callback) {
					callback(isConfirm);
				}
			});
		},
		/**
		 * 判断列表是否选中
		 */
		dataTableAtleaseOneChecked: function(pkname){
			var checkedIds = $.getDataTableChecked(pkname);
			if (checkedIds == "") {
				return false;
			} else {
				return true;
			}
		},
		approvalBatchSubmit: function(approvalUrl,data,index){
			$.confirm_swal_ui( '您确定要审批吗？',
					function(isConfirm) {
						if (isConfirm) {
							$.ajaxOperation(approvalUrl,data,function (res){
								if(res.success){
									layer.close(index);
								}
							});
						} else {
						}
			});
		},
		requestBatchSubmit: function(approvalUrl,data,index){
			$.confirm_swal_ui( '您确定要提交申请吗？',
				function(isConfirm) {
					if (isConfirm) {
						$.ajaxOperation(approvalUrl,data,function (res){
							if(res.success){
								layer.close(index);
							}
						});
					} else {
					}
				});
		},
		approvalBatch: function(pkname,approvalUrl,dgid,title,area){
			var checkedIds = $.getDataTableChecked(pkname);
		    if (checkedIds == "") {
		        msgShow("请最少选中一条");
		        return false;
		    } else {
		    	if(title && title!=null){
		    	}else{
		    		title = "批量审批：";
		    	}
		    	if(area && area!=null){
				}else{
					area = ['500px','300px'];
				}
		    	var approval = function(status){
		    	};
		    	var content = '<div id="approval_div" style="padding: 10px;">';
		    	content += '<textarea rows="4" class="form-control" style="width: 100%;height: 180px" placeholder="请输入审批意见" name="comments" maxlength="500" id="approval_comments" ></textarea>'
	    		content += '</div>';
		    	content += '<div class="box-footer" style="text-align: center;">';
		    	content += ' <button type="button" id="but_approved" class="btn btn-primary"><i class="fa fa-check"></i>&nbsp;同意</button>&nbsp;&nbsp;';
		    	content += ' <button type="button" id="but_rejected" class="btn btn-danger"><i class="fa fa-times"></i>&nbsp;拒绝</button>';
		    	content += '</div>';
		    	var result = "";
		    	var index = layer.open({
					  type: 1,
					  title:title,
					  area: area, //宽高
					  anim: 2,
					  shadeClose: true, //开启遮罩关闭
					  content: content,
					  maxmin: false,
					  end:function(){
						  $('#' + dgid).DataTable().ajax.reload(null,true);
						  if(WebSite_Flag==2){
							  flushTodolistIframe();
						  }
					  },
					  success: function(layero, index){
						 $("#but_approved").click(function (){
							 var comments =  $("#approval_comments").val();
							 var data = {requestId:checkedIds,result:"APPROVED",comments:comments};
							 $.approvalBatchSubmit(approvalUrl,data,index);
						 });
						 $("#but_rejected").click(function (){
							 var comments =  $("#approval_comments").val();
							 if(comments==''){
								 msgShow("请输入审批意见！");
							 }else{
								 var data = {requestId:checkedIds,result:"REJECTED",comments:comments};
								 $.approvalBatchSubmit(approvalUrl,data,index);
							 }
						 });

					  }
				});
		    }
		},
        requestBatch: function(pkname,approvalUrl,dgid,title,area){
            var checkedIds = $.getDataTableChecked(pkname);
            if (checkedIds == "") {
                msgShow("请最少选中一条");
                return false;
            } else {
                if(title && title!=null){
                }else{
                    title = "申请理由"
                }
                if(area && area!=null){
                }else{
                    area = ['500px','300px']
                }
                var content = '<div id="approval_div" style="padding: 10px;">';
                content += '<textarea rows="4" class="form-control" style="width: 100%;height: 180px" placeholder="请输入'+title+'" name="comments" maxlength="500" id="approval_comments" ></textarea>'
                content += '</div>';
                content += '<div class="box-footer" style="text-align: center;">';
                content += ' <button type="button" id="but_request" class="btn btn-primary"><i class="fa fa-check"></i>&nbsp;提交</button>&nbsp;&nbsp;';
                content += '</div>';
                var result = "";
                var index = layer.open({
                    type: 1,
                    title:title,
                    area: area, //宽高
                    anim: 2,
                    shadeClose: true, //开启遮罩关闭
                    content: content,
                    maxmin: false,
                    end:function(){
                        $('#' + dgid).DataTable().ajax.reload(null,false);
                    },
                    success: function(layero, index){
                        $("#but_request").click(function (){
                            var comments =  $("#approval_comments").val();
                            var data = {requestId:checkedIds,result:"APPROVED",comments:comments};
                            $.requestBatchSubmit(approvalUrl,data,index);
                        });
                    }
                });
            }
        },
		/**
		 * 获取DT数据 根据key和value 
		 * 只返回匹配到的第一条。
		 */
		getDatatablesData: function(dtid,kValue,keyId){
			var table = $('#'+dtid).DataTable();
			var data={};
			if(keyId){
			}else{
				var op = $("#"+dtid).attr("data-config");
				var conf = null;
				if (op) {
					conf = eval("(" + op + ")");
				} else {
					conf = new Object();
				}
				keyId = conf.keyId;
			}
			if(keyId){
				var datas = table.data();
				for(var i=0;i<datas.length;i++){
					if(datas[i][keyId]==kValue){
						data = datas[i];
						return data;
					}
				}
			}
			return null;
		},
		openSettingDatatablesColumns: function (dtid) {
			bloadMask();
			var url = ctp+"/tools/module/datatable/config/setting?excludes=1&"+"tid="+$("#" + dtid).attr("tid");
			$.get(url, function(result){
				bUnloadMask();
				var title = '设置显示列（字段）';
				var area = ['500px','600px'];
				var ps = "tid="+$("#" + dtid).attr("tid");
				layer.open({
					  type: 1,
					  title:title+"",
					  //skin: 'layui-layer-molv', //样式类名
					  area: area, //宽高
					  anim: 2,
					  shadeClose: false, //开启遮罩关闭
					  content: result,
					  maxmin: false
				});
			});
		},
		ajaxDatatableOperationSelect: function (fromid, pkname, url, msg, sformid){
			ajaxOperationSelect(fromid, pkname, url, msg, sformid);
		},
		ajaxOperation: function(url, data, callback) {
			if(url.startWith("/")){
				if(url.startWith(ctp)){
				}else{
					url = ctp+url;
				}
			}
			bloadMask();
			$.ajax({
				url : url,
				data : data,
				type : 'POST',
				success : function(res) {
					bUnloadMask();
					var msg = res.message;
					if (res.success) {
						if(msg!=null && msg != ''){
							showMsgBRN(t_alert_title,msg, 5);
						}else{
							showMsgBRN(t_alert_title,'操作成功', 5);
						}
					} else {
						if(msg!=null && msg != ''){
							showMsgBRN(t_alert_title,res.message, 1);
						}else{
							showMsgBRN(t_alert_title,'操作失败', 1);
						}
					}
					if (callback) {
						callback(res);
					}
				}
			});
		},
		ajaxOperationNt: function(url, data, callback) {
			ajaxtest(url, data, callback);
		},
		ajaxOperationHasConfirm: function(title,url,data,callback){
		    confirmOption(title, function(isConfirm) {
		        if(isConfirm){
		        	ajaxtest(url,data,function(msg){
		        		try {
		                    if(msg.success){
		                        showMsgBRN(t_alert_title,msg.message, 5);
							}else{
		                        showMsgBRN(t_alert_title,msg.message, 1);
							}
		                    callback(msg);
		                } catch (err) {
		                    showMsgBRN(t_alert_title,t_form_submit_noright, 1);
		                }
		        	});
				}
		    });
		},
		ajaxOperationHasConfirmNt: function(title,url,data,callback){
		    confirmOption(title, function(isConfirm) {
		        if(isConfirm){
		        	ajaxtest(url,data,callback);
				}
		    });
		},
		approvalStepBatchSubmit: function(approvalUrl,data,index){
			//此异步方法主要做两件事
			//2、根据requests获取下拉框的值
			ajaxtest(ctp+approvalUrl,data,function (res){
				if(res.success){
                    var val=res.data;
                    var optionHtml="";
                    if(val.length>0){
                        // optionHtml+="<option></option>";
                        for (var i = 0; i < val.length; i++) {
                            if('1'==val[i].orderNum && i==0){
                            	//默认退回第一步
                                optionHtml+="<option selected value='"+val[i].stepId+"'>"+val[i].stepName+"</option>";
                            }else{
                                optionHtml+="<option value='"+val[i].stepId+"'>"+val[i].stepName+"</option>";
                            }
                        }
                        console.log(optionHtml);
                        $("#approval_dept").html(optionHtml);
                    }else{
                        $("#approval_dept").html('');
                    }
				}else{
					layer.close(index);
				}
			});
        },
        /**
         *查看审批步骤信息
         *参数：{requestId:'',approvalId:'',referenceObj:''};
         */
        approvalStepView: function (parmas){
        	//requestId,approvalId,referenceObj
        	var parmasStr = "";
        	if(parmas){
        		if(parmas.approvalId && parmas.approvalId!=''){
        			parmasStr += '&approvalId='+parmas.approvalId;
        		} else {
        			if(parmas.requestId && parmas.requestId!=''){
        				parmasStr += '&requestId='+parmas.requestId;
        				if(parmas.referenceObj && parmas.referenceObj!=''){
        					parmasStr += '&referenceObj='+parmas.referenceObj;
                		}
        			}else{
        				return false;
        			}
        		}
        	}
        	var url = ctp + "/core/approval/result/view?exclude=1"+ parmasStr;
        	openLayerIFrame("查看审批进度", url, [ '80%', '80%' ]);
        },
		//批量审批中的审批退回，仅能单个操作
		approvalStepBatch: function(pkname,approvalUrl,dgid,title,area){
            var checkedIds = $.getDataTableChecked(pkname);
            if (checkedIds == "") {
                msgShow("请最少选中一条");
                return false;
            } else {
                if(title && title!=null){
                }else{
                    title = "批量审批："
                }
                if(area && area!=null){
                }else{
                    area = ['800px','400px']
                }
                var approval = function(status){
                };
                var content = '<div id="approval_div" style="padding: 10px;">';
                content += '<textarea rows="4" class="form-control" style="width: 100%;height: 180px" placeholder="请输入审批意见" name="comments" maxlength="500" id="approval_comments" ></textarea>';
                content += '</div>';
                content += '<div class="box-footer" style="text-align: center;">';
                content += '<input type="hidden" id="stepId_input" name="stepId_input" />';
                content += ' <button type="button" id="but_approved" class="btn btn-primary"><i class="fa fa-check"></i>&nbsp;同意</button>&nbsp;&nbsp;';
                content += ' <button type="button" id="but_rejected" class="btn btn-danger"><i class="fa fa-times"></i>&nbsp;拒绝</button>';
                content += '</div>';

                var deptDiv = '<form action="" id="stepFormBatch">'
				deptDiv += '<div id="dept_div" style="padding: 10px;">';
                deptDiv += '<select class="select2 form-control" name="approval_dept"  id="approval_dept" validate="{required:true}">';
                deptDiv += '</select></div>';
                deptDiv += '<div class="box-footer" style="text-align: center;">';
                deptDiv += ' <button type="button" id="but_confirm" class="btn btn-primary"><i class="fa fa-check"></i>&nbsp;确定</button>';
                deptDiv += '</div>'
                deptDiv += '</form>';

                var result = "";
                var index = layer.open({
                    type: 1,
					zIndex:99999,
                    title:title,
                    area: area, //宽高
                    anim: 2,
                    shadeClose: true, //开启遮罩关闭
                    content: content,
                    maxmin: false,
                    end:function(){
                        $('#' + dgid).DataTable().ajax.reload(null,false);
                    },
                    success: function(layero, index){
                        $("#but_approved").click(function (){
                            var comments =  $("#approval_comments").val();
                            var data = {requestId:checkedIds,result:"APPROVED",comments:comments};
                            $.approvalBatchSubmit(approvalUrl,data,index);
                        });
                        $("#but_rejected").click(function (){
                            var comments =  $("#approval_comments").val();
                            if(comments==''){
                                msgShow("请输入审批意见！");
                            }else{
								//点击拒绝按钮要做的事情 20191024
								//判断是否选择多个，如果选择多个，不弹出，如果选择一个，弹出
                                var approvalIds = checkedIds.split(",");
                                if(approvalIds.length > 2){
                                    var data = {requestId:checkedIds,result:"REJECTED",comments:comments};
                                    $.approvalBatchSubmit(approvalUrl,data,index);
								}else if(approvalIds.length == 2){
                                    var index_dept=layer.open({
                                        type: 1
										,zIndex:100000
                                        ,area: ['500px','200px'] //宽高
                                        ,title:'选择退回节点'
                                        ,id: 'layerDemo' //防止重复弹出
                                        ,content: deptDiv
                                        ,btnAlign: 'c' //按钮居中
                                        ,shade: 0 //不显示遮罩
                                        ,success: function(layero, index){
                                            // formatFormSelect2('stepForm','select3');
                                            $('#approval_dept').select2({
												dropdownParent: $("#dept_div"),//指定select2的父元素，避免index问题
												minimumResultsForSearch: -1   //设置不能删除
                                            });
                                            formatSelect2();
                                        }
                                    });
                                    var stepUrl="/approval/getApprovalStep";
                                    var data = {requestId:checkedIds};
                                    $.approvalStepBatchSubmit(stepUrl,data,index_dept);
                                    //确定时关闭弹出窗
                                    $("#but_confirm").click(function (){
                                        var approvalStep = null;
                                        if($("#approval_dept") != undefined){
                                            approvalStep = $("#approval_dept").val();
                                        }
                                        var data = {requestId:checkedIds,result:"REJECTED",comments:comments,approvalStep:approvalStep};
                                        $.approvalBatchSubmit(approvalUrl,data,index);
										layer.close(index_dept);
                                    });
								}
                            }
                        });
                    }
                });
            }
        },
		//单个审批带退回
		//第一个参数是requestId,第二个参数是url，第三个参数是审批意见
        approvalStepSingle: function(requestId,approvalUrl,approvalCmments,formId){
            var requestId=$("#"+requestId).val();
            var comments = $("#"+approvalCmments).val();
            if(comments==''){
                msgShow("请输入审批意见！");
                return;
            }
            var deptDiv = '<form action="" id="stepForm">'
            deptDiv += '<div id="dept_div" style="padding: 10px;">';
            deptDiv += '<select class="select2 form-control" name="approval_dept"  id="approval_dept" validate="{required:true}">';
            deptDiv += '</select></div>';
            deptDiv += '<div class="box-footer" style="text-align: center;">';
            deptDiv += '   <button type="button" id="but_confirm" class="btn btn-primary"><i class="fa fa-check"></i>&nbsp;确定</button>';
            deptDiv += '</div>'
            deptDiv += '</form>';
            var index_dept=layer.open({
                type: 1
                ,area: ['500px','200px'] //宽高
                ,title:'选择退回节点'
                ,id: 'layerDemo' //防止重复弹出
                ,content: deptDiv
                ,btnAlign: 'c' //按钮居中
                ,shade: 0 //不显示遮罩
                ,success: function(layero, index){
                    $('#approval_dept').select2({
                        dropdownParent: $("#dept_div"),//指定select2的父元素，避免index问题
                        minimumResultsForSearch: -1   //设置不能删除
                    });
					formatSelect2();
                }
            });
            var stepUrl="/approval/getApprovalStep";
            var data = {requestId:requestId};
            $.approvalStepBatchSubmit(stepUrl,data,index_dept);

            //确定时关闭弹出窗
            $("#but_confirm").click(function (){
                var approvalStep = null;
                if($("#approval_dept") != undefined){
                    approvalStep = $("#approval_dept").val();
                }
                var data = {requestId:requestId,result:"REJECTED",comments:comments,approvalStep:approvalStep};
                layer.close(index_dept);
                $.confirm_swal_ui( '您确定要否决此申请吗？',
                    function(isConfirm) {
                        if (isConfirm) {
                            $.ajaxOperation(approvalUrl,data,function (res){
                                if(res.success){
                                    closeLayer();
                                }
                            });
                        }
                    });
            });
		},
		openSettingDatatablesReportColumns: function (dtid) {
            bloadMask();
            var url = ctp+"/tools/module/datatable/config/settingReport?excludes=1&"+"tid="+$("#" + dtid).attr("tid");
            $.get(url, function(result){
                bUnloadMask();
                var title = '设置显示列（字段）';
                var area = ['500px','600px'];
                var ps = "tid="+$("#" + dtid).attr("tid");
                layer.open({
                    type: 1,
                    title:title+"",
                    //skin: 'layui-layer-molv', //样式类名
                    area: area, //宽高
                    anim: 2,
                    shadeClose: false, //开启遮罩关闭
                    content: result,
                    maxmin: false
                });
            });
        }
	});
	/**
	 * 普通表单数据 to json
	 */
	$.fn.serializeJson = function() {
		var obj = {};
        var formArray = $(this).serializeArray();
        $.each(formArray, function () {
            if (obj[this.name] !== undefined) {
                if (!obj[this.name].push) {
                    obj[this.name] = [obj[this.name]];
                }
                obj[this.name].push(this.value || '');
            } else {
                obj[this.name] = this.value || '';
            }
        });
        return obj;
	}  
})(jQuery);