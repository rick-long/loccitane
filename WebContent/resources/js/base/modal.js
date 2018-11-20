
function openModal(openUrl){
	$.ajax({
		 url:openUrl,
		 type: "post",
		 dataType: "text",
		 success: function(response) {
			
			$(".modal-body").html(response);
		 },
		 error:function(){}
	});
}


//$('#myModal').on('show.bs.modal', function (event) {
//	  var button = $(event.relatedTarget) // Button that triggered the modal
//	  var url = button.data('whatever') // Extract info from data-* attributes
//	  // If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
//	  // Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
//	  var modal = $(this)
//	  $.ajax({
//		 url:url,
//		 type: "post",
//		 dataType: "text",
//		 success: function(response) {
////			 $("#modal-body-content").html(response);
//			 modal.find('.modal-body').html(response)
//		 },
//		 error:function(){}
//	  });
//	  
//	 
//	})