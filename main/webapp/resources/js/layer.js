/**
* 레이어 팝업
*
*/
const layer = {
	/**
	* 팝업 열기 
	*
	*/
	popup(url, width, height) {
		width = width || 350;
		height = height || 350;
		
		/** 백그라운드 처리  */
		if (!document.querySelector("#layer_dim")) { //layer_dim이 없으면 생성 
			const div = document.createElement("div");
			div.id="layer_dim";
			div.style = "position:fixed;width:100%;height:100%;top:0;left:0;background:rgba(0,0,0,0.6);z-index:100";
			document.body.appendChild(div);	
		}
		
	},
	/**
		팝업 닫기 
	 */
	close() {
		
	}	
};


