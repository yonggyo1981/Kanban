window.addEventListener("DOMContentLoaded", function() {
	const addFile = document.querySelector(".add_file");
	if (addFile) {
		addFile.addEventListener("click", function(e) {
			const target = e.target.parentElement.nextElementSibling;
			console.log(target);
		}, false);
	}
}, false);