$(function() {
	var editBatchId = -1;
//	$("#commenceDate").datepicker();
	$("#commenceDate").datepicker("option", "dateFormat", "yy-mm-dd");
	$("#tabs").tabs();

	var batchName = $("#batchName").val(), intake = $("#intake").val(), fee = $(
			"#fee").val(), commenceDate = $("#commenceDate").val(), scheduleDiscription = $(
			"#scheduleDiscription").val(), subject = $("#subject").val(), allFields = $(
			[]).add(batchName).add(intake).add(fee).add(commenceDate).add(
			scheduleDiscription).add(subject), tips = $(".validateTips");

	function checkLength(o, n, min, max) {
		if (o.val().length > max || o.val().length < min) {
			o.addClass("ui-state-error");
			updateTips("Length of " + n + " must be between " + min + " and "
					+ max + ".");
			return false;
		} else {
			return true;
		}
	}

	function checkRegexp(o, regexp, n) {
		if (!(regexp.test(o.val()))) {
			Object
			o.addClass("ui-state-error");
			updateTips(n);
			return false;
		} else {
			return true;
		}
	}

	function createBatch() {

		var data = new Object();
		data.subject = $("#subject").val();
		data.batch = new Object();
		data.batch.id = editBatchId;
		data.batch.batchName = $('#batchName').val();
		data.batch.intake = $('#intake').val();
		data.batch.fee = $('#fee').val();
		data.batch.commenceDate = $('#commenceDate').val();
		data.batch.scheduleDiscription = $('#scheduleDiscription').val();

		$
				.ajax({
					type : "POST",
					url : "/JOpenClass/createbatch",
					contentType : "application/json; charset=utf-8",
					data : JSON.stringify(data),
					success : function(response) {
						$('#info').html(response.message);
						$('#batchName').val('');
						$('#intake').val('');
						$('#fee').val('');
						$('#commenceDate').val('');
						$('#scheduleDiscription').val('');

						if (editBatchId < 0) {
							$("#batches tbody")
									.append(
											"<tr id='batch"
													+ response.batch.id
													+ "'>"
													+ "<td><a class='batchlink' href='enterbatch.html?batchId="
													+ response.batch.id
													+ "'>"
													+ response.batch.batchName
													+ "</a></td>"
													+ "<td>"
													+ response.batch.subject.subjectName
													+ "</td>"
													+ "<td>"
													+ response.batch.subject.grade
													+ "</td>"
													+ "<td>"
													+ response.batch.intake
													+ "</td>"
													+ "<td>"
													+ response.batch.fee
													+ "</td>"
													+ "<td><a class='edit_batch' href='#"
													+ response.batch.id
													+ "'>edit</a></td>"
													+ "<td><input type='checkbox' name='batch_del_list' value='"
													+ response.batch.id
													+ "'></td>" + "</tr>");
						} else {

							$("tr#batch" + response.batch.id)
									.replaceWith(
											"<tr id='batch"
													+ response.batch.id
													+ "'>"
													+ "<td><a class='batchlink' href='enterbatch.html?batchId="
													+ response.batch.id
													+ "'>"
													+ response.batch.batchName
													+ "</a></td>"
													+ "<td>"
													+ response.batch.subject.subjectName
													+ "</td>"
													+ "<td>"
													+ response.batch.subject.grade
													+ "</td>"
													+ "<td>"
													+ response.batch.intake
													+ "</td>"
													+ "<td>"
													+ response.batch.fee
													+ "</td>"
													+ "<td><a class='edit_batch' href='#"
													+ response.batch.id
													+ "'>edit</a></td>"
													+ "<td><input type='checkbox' name='batch_del_list' value='"
													+ response.batch.id
													+ "'></td>" + "</tr>");
							editBatchId = -1;
						}
					},
					error : function(e) {
						alert('Error: ' + e);
					}
				});
	}

	$("#dialog-form-batch").dialog({
		autoOpen : false,
		height : 570,
		width : 300,
		modal : true,
		buttons : {
			"Submit" : function() {

				var bValid = true;
				allFields.removeClass("ui-state-error");

				// bValid = bValid
				// && checkLength(batchName,
				// "Class Room Name", 1, 30);
				// bValid = bValid && checkLength(fee, "Fee", 1, 30);
				// bValid = bValid
				// && checkLength(Intake, "Intake", 1,
				// 30);
				if (bValid) {

					createBatch();
					$(this).dialog("close");
				}
			},
			Cancel : function() {
				$(this).dialog("close");
				editBatchId = -1;
			}
		},
		close : function() {
			allFields.val("").removeClass("ui-state-error");
		}
	});

	$("#create-batch").button().live('click', function() {
		editBatchId = -1;
		$("#dialog-form-batch").dialog("open");
	});
	$('a.edit_batch').live(
			'click',
			function() {

				editBatchId = $(this).attr('href').substring(1);
				var response = $.ajax({
					type : "POST",
					url : "/JOpenClass/getbatchbyid",
					data : "id=" + editBatchId,
					success : function(response) {
						$('#info').html(response["message"]);
						$('input#batchName').val(response.batch.batchName);
						$('input#intake').val(response.batch.intake);
						$('input#fee').val(response.batch.fee);
						$('input#commenceDate')
								.val(response.batch.commenceDate);
						$('input#scheduleDiscription').val(
								response.batch.scheduleDiscription);
						$('select#subject').val(response.batch.subject.id);
						editBatchId = response.batch.id;

						$("#dialog-form-batch").dialog("open");
					},
					error : function(e) {
						alert('Error: ' + e);
					}
				});
			});

	$("#delete-batch").button().live(
			'click',
			function() {

				var values = $('input[name="batch_del_list"]:checkbox:checked')
						.map(function() {
							return this.value;
						}).get();
				if (0 < values.length) {
					$("#dialog-confirm-batchdelete").dialog("open");
				} else {
					$("#dialog-message-noitem").dialog("open");
				}

			});
	$("#dialog-message-noitem").dialog({
		autoOpen : false,
		modal : true,
		buttons : {
			Ok : function() {
				$(this).dialog("close");
			}
		}
	});

	$("#dialog-confirm-batchdelete").dialog({

		autoOpen : false,
		resizable : false,
		height : 180,
		width : 350,
		modal : true,
		buttons : {
			"Delete" : function() {
				$(this).dialog("close");
				deleteBatches();
			},
			Cancel : function() {
				$(this).dialog("close");
			}
		}
	});

	function deleteBatches() {
		var delIds = $('input[name="batch_del_list"]:checkbox:checked').map(
				function() {
					return this.value;
				}).get();
		var response = $.ajax({
			type : "POST",
			url : "/JOpenClass/deletebatches",
			contentType : "application/json; charset=utf-8",
			data : JSON.stringify(delIds),
			success : function(response) {
				$('#info').html(response["message"]);
				delIds = response.delList;
				for ( var i = 0; i < delIds.length; i++) {
					$("tr#batch" + delIds[i]).remove();
				}
			},
			error : function(e) {
				alert('Error: ' + e);
			}
		});
	}

	$('#select-all').click(function(event) {
		if (this.checked) {
			$(':checkbox').each(function() {
				this.checked = true;
			});
		} else {
			$(':checkbox').each(function() {
				this.checked = false;
			});
		}
	});

	$('#changePassBtn')
			.click(
					function(event) {

						var newPass = $('input#newPassword').val();
						var newPassConfirm = $('input#newPassConfirm').val();

						if (newPass != newPassConfirm) {
							document.getElementById("passMisMatchErr").innerHTML = "password mismatch";

						} else {
							$('#chagePassModal').modal('hide');

							var dataObj = new Object();
							dataObj.password = $('input#password').val();
							dataObj.newPassword = newPass;
							dataObj.newPassConfirm = newPassConfirm;

							var response = $
									.ajax({
										type : "POST",
										url : "/JOpenClass/changepass",
										contentType : "application/json; charset=utf-8",
										data : JSON.stringify(dataObj),
										success : function(response) {
											$('#info')
													.html(response["message"]);
										},
										error : function(e) {
											alert('Error: ' + e);
										}
									});

						}
					});

	$("#editProfileBtn").button().live('click', function() {
		var response = $.ajax({
			type : "POST",
			url : "/JOpenClass/loggedlecturerinfo",
			success : function(response) {
				//console.log(response);
				$('input#firstName').val(response.lecturer.firstName);
				$('input#lastName').val(response.lecturer.lastName);
				$('input#address').val(response.lecturer.address);
				$('input#contactNumber').val(response.lecturer.contactNumber);
				$('input#lecturerInfo').val(response.lecturer.lecturerInfo);
				$('input#email').val(response.lecturer.email);
			},
			error : function(e) {
				alert('Error: ' + e);
			}
		});
	});

	$("#saveProfileBtn").button().live('click', function() {

		var data = new Object();
		data.lecturer = new Object();
		data.lecturer.firstName = $('input#firstName').val();
		data.lecturer.lastName = $('input#lastName').val();
		data.lecturer.address = $('input#address').val();
		data.lecturer.contactNumber = $('input#contactNumber').val();
		data.lecturer.lecturerInfo = $('input#lecturerInfo').val();
		data.lecturer.email = $('input#email').val();

		$('#editProfileModal').modal('hide');
		var response = $.ajax({

			type : "POST",
			url : "/JOpenClass/saveloggedlecinfo",
			contentType : "application/json; charset=utf-8",
			data : JSON.stringify(data),
			success : function(response) {
				document.getElementById("info").innerHTML = "successfully updated";//response.message;
				document.getElementById("tbFirstName").innerHTML = $('input#firstName').val();
				document.getElementById("tbLastName").innerHTML = $('input#lastName').val();
				document.getElementById("tbAddress").innerHTML = $('input#address').val();
				document.getElementById("tbContactNumber").innerHTML = $('input#contactNumber').val();
				document.getElementById("tbLecInfo").innerHTML = $('input#lecturerInfo').val();
				
				if(response.emailChanged=="yes"){
					$('#loginMsgModal').modal('show');
				}
			},
			error : function(e) {
				alert('Error: ' + e);
			}
		});
	});
	
	
	//load subject grid
	var jq = jQuery.noConflict();
	jq(function() {
		jq("#grid").jqGrid({
		   	url:'/JOpenClass/displayallsubjects',
			datatype: 'json',
			mtype: 'GET',
		   	colNames:['Id', 'Grade', 'Subejct Name', 'Subject Details'],
		   	colModel:[
		   		{name:'id',index:'id', width:100,editable:false,editoptions:{readonly:true,size:20},hidden:true},
		   		{name:'grade',index:'grade', width:130,editable:true, editrules:{required:true}, editoptions:{size:20}},
		   		{name:'subjectName',index:'subjectName', width:130,editable:true, editrules:{required:true}, editoptions:{size:20}},
		   		{name:'subjectDetails',index:'subjectDetails', width:130,editable:true, editrules:{required:true}, editoptions:{size:20}}
		   	],
		   	postData: { 
			},
			rowNum:20,
		   	rowList:[20,40,60],
		   	height: 200,
		   	width: 700,
			rownumbers: true,
		   	pager: '#pager',
		   	sortname: 'id',
		    viewrecords: true,
		    sortorder: "asc",
		    caption:"Subjects",
		    emptyrecords: "Empty records",
		    loadonce: false,
		    loadComplete: function() {
			},
		    jsonReader : {
		        root: "rows",
		        page: "page",
		        total: "total",
		        records: "records",
		        repeatitems: false,
		        cell: "cell",
		        id: "id"
		    }
		});
		jq("#grid").jqGrid('navGrid','#pager',
				{edit:false,add:false,del:false,search:true},
				{ },
		        { },
		        { }, 
				{ 
			    	sopt:['eq', 'ne', 'lt', 'gt', 'cn', 'bw', 'ew'],
			        closeOnEscape: true, 
			        	multipleSearch: true, 
			         	closeAfterSearch: true }
		);


		
		jq("#grid").navButtonAdd('#pager',
				{ 	caption:"Add", 
					buttonicon:"ui-icon-plus", 
					onClickButton: addRow,
					position: "last", 
					title:"", 
					cursor: "pointer"
					
				} 
		);
		
		jq("#grid").navButtonAdd('#pager',
				{   caption:"Edit",
					buttonicon:"ui-icon-pencil", 
					onClickButton: editRow,
					position: "last", 
					title:"", 
					cursor: "pointer"
					
				} 
		);
		
		jq("#grid").navButtonAdd('#pager',
			{   caption:"Delete",
				buttonicon:"ui-icon-trash", 
				onClickButton: deleteRow,
				position: "last", 
				title:"", 
				cursor: "pointer"
				
			} 
		);

		jq("#btnFilter").click(function(){
			jq("#grid").jqGrid('searchGrid',
					{multipleSearch: false, 
						sopt:['eq']}
			);
		});

		// Toolbar Search
		jq("#grid").jqGrid('filterToolbar',{stringResult: true,searchOnEnter : true, defaultSearch:"cn"});

	});
	
});

function addRow() {

	// Get the currently selected row
	var jq = jQuery.noConflict();
    jq("#grid").jqGrid('editGridRow','new',
    		{ 	url: "/JOpenClass/addsubject", 
					editData: {
			    },
			    recreateForm: true,
			    beforeShowForm: function(form) {
			    },
				closeAfterAdd: true,
				reloadAfterSubmit:false,
				afterSubmit : function(response, postdata) 
				{ 
			        var result = eval('(' + response.responseText + ')');
					var errors = "";
					
			        if (result.success == false) {
						for (var i = 0; i < result.message.length; i++) {
							errors +=  result.message[i] + "<br/>";
						}
			        }  else {
			        	jq("#dialog").text('Entry has been added successfully');
						jq("#dialog").dialog( 
								{	title: 'Success',
									modal: true,
									buttons: {"Ok": function()  {
										jq(this).dialog("close");} 
									}
								});
	                }
			    	// only used for adding new records
			    	var new_id = null;
			    	
					return [result.success, errors, new_id];
				}
    		});

}

function editRow() {
	// Get the currently selected row
	var jq = jQuery.noConflict();
	var row = jq("#grid").jqGrid('getGridParam','selrow');
	
	if( row != null ) 
		jq("#grid").jqGrid('editGridRow',row,
			{	url: "/JOpenClass/editsubject", 
				editData: {
		        },
		        recreateForm: true,
		        beforeShowForm: function(form) {
		        },
				closeAfterEdit: true,
				reloadAfterSubmit:false,
				afterSubmit : function(response, postdata) 
				{ 
		            var result = eval('(' + response.responseText + ')');
					var errors = "";
					
		            if (result.success == false) {
						for (var i = 0; i < result.message.length; i++) {
							errors +=  result.message[i] + "<br/>";
						}
		            }  else {
		            	jq("#dialog").text('Entry has been edited successfully');
						jq("#dialog").dialog( 
								{	title: 'Success',
									modal: true,
									buttons: {"Ok": function()  {
										jq(this).dialog("close");} 
									}
								});
	                }
		        	
					return [result.success, errors, null];
				}
			});
	else jq( "#dialogSelectRow" ).dialog();
}

function deleteRow() {
	var jq = jQuery.noConflict();
	// Get the currently selected row
    var row = jq("#grid").jqGrid('getGridParam','selrow');

    // A pop-up dialog will appear to confirm the selected action
	if( row != null ) 
		jq("#grid").jqGrid( 'delGridRow', row,
          	{ url: '/JOpenClass/deletesubject, 
						recreateForm: true,
			            beforeShowForm: function(form) {
			              //change title
			              jq(".delmsg").replaceWith('<span style="white-space: pre;">' +
			            		  'Delete selected record?' + '</span>');
	            		  
						  //hide arrows
			              jq('#pData').hide();  
			              jq('#nData').hide();  
			            },
          				reloadAfterSubmit:false,
          				closeAfterDelete: true,
          				afterSubmit : function(response, postdata) 
						{ 
			                var result = eval('(' + response.responseText + ')');
							var errors = "";
							
			                if (result.success == false) {
								for (var i = 0; i < result.message.length; i++) {
									errors +=  result.message[i] + "<br/>";
								}
			                }  else {
			                	jq("#dialog").text('Entry has been deleted successfully');
								jq("#dialog").dialog( 
										{	title: 'Success',
											modal: true,
											buttons: {"Ok": function()  {
												jq(this).dialog("close");} 
											}
										});
			                }
		                	// only used for adding new records
		                	var new_id = null;
		                	
							return [result.success, errors, new_id];
						}
          	});
	 else jq( "#dialogSelectRow" ).dialog();
	
}
	
	