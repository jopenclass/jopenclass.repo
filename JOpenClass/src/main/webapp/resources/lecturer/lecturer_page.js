$(function() {
	var editBatchId = -1;
	$("#commenceDate").datepicker();
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
		height : 450,
		width : 400,
		modal : true,
		buttons : {
			"Submit" : function() {

				var bValid = true;
				allFields.removeClass("ui-state-error");

				// 							bValid = bValid
				// 									&& checkLength(batchName,
				// 											"Class Room Name", 1, 30);
				// 							bValid = bValid && checkLength(fee, "Fee", 1, 30);
				// 							bValid = bValid
				// 									&& checkLength(Intake, "Intake", 1,
				// 											30);
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

});