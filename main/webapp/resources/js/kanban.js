/**
* 칸반보드 전용 
*
*/
window.addEventListener("DOMContentLoaded", function() {
	const addWork = document.querySelector(".add_work");
	addWork.addEventListener("click", function() {
		layer.popup("../kanban/add", 500, 600, callbackAddPopup);
	}, false);
}, false);

function callbackAddPopup()
{
	const addFile = document.querySelector(".add_file"); 
	if (addFile) {
		addFile.addEventListener("click", function() {
			addFileForm(); 
		}, false);
	}
	
	const delFile = document.querySelector(".del_file");
	if (delFile) {
		delFile.addEventListener("click", function() {
			delFileForm();
		}, false);
	}
}

/** 파일 첨부 추가 */
function addFileForm() {
	const fileUpload = document.getElementById("file_upload");
	if (fileUpload) {
		const file = fileUpload.firstElementChild;
		const addRows = file.cloneNode();
		console.log(addRows);
	}
}

/** 파일 첨부 삭제 */
function delFileForm() {
	
}
