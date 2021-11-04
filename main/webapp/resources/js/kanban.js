/**
* 칸반보드 전용 
*
*/
window.addEventListener("DOMContentLoaded", function() {
	const addWork = document.querySelector(".add_work");
	addWork.addEventListener("click", function() {
		layer.popup("../member/login", 500, 600);
	}, false);
}, false);
