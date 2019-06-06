
function uploadFile(obj) {
    var html = '';
    if(obj.files[0] != undefined){
        var filename = obj.files[0].name;
        html = '<p id="selectedFile">Selected file:<br/>' + filename + '</p>';

        // alert("Selected file: " + filename);
    } else {
        // alert("no file");
        html = '<img id="adder" src="add.png">';
    }
    $("#fileLable").html(html);
};

function submitFile() {
	var file = document.getElementById('file').files[0];
	var guestName = $("#name").val();
	if(file == undefined){
		alert("Please select a picture.");
	} else if(guestName === ""){
		alert("Please enter the guest name.");
	}else {
		var filename = file.name;
		var formData = new FormData();
		formData.append('img', file);
		formData.append('name', guestName);
		$.ajax({
            url: './AddNewGuest',
            type: 'POST',
            data: formData,
            // async: true,
            cache: false,
            contentType: false,
            processData: false,
            success: function(res) {
                alert(res);
				window.location.reload();
            }
        });
	}
}

function updateSavedGuest() {

}

function updateVisitingInformation() {

}

function deleteGuest(obj) {

}

function deleteInfo(obj) {

}

function clearInfo() {

}