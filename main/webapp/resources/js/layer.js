/**
* 레이어 팝업
*
*/
const layer = {
	/**
	* 팝업 열기 
	*
	* @param callback -> 레이어 팝업 컨텐츠가 로딩 된 후 실행 
	*/
	popup(url, width, height, callback) {
		width = width || 350;
		height = height || 350;
		
		/** 백그라운드 처리  */
		if (!document.querySelector("#layer_dim")) { //layer_dim이 없으면 생성 
			const div = document.createElement("div");
			div.id="layer_dim";
			div.style = "position:fixed;width:100%;height:100%;top:0;left:0;background:rgba(0,0,0,0.6);z-index:100";
			document.body.appendChild(div);	
			
			/** 백그라운드 클릭시 닫기 처리 */
			div.addEventListener("click", function() {
				layer.close();
			}, false);
		}
		
		/** 레이어 팝업 영역 */
		if (!document.querySelector("#layer_popup")) {
			const div = document.createElement("div");
			div.id = "layer_popup";
			const xpos = Math.round((window.innerWidth - width) / 2);
			const ypos = Math.round((window.innerHeight - height) / 2);
			
			div.style=`position:fixed;z-index:101;width:${width}px;height:${height}px;background:#ffffff;border-radius:20px; padding: 20px;left:${xpos}px; top:${ypos}px`;
			/** 닫기 버튼 추가 S  */
			const closeBtn = document.createElement("i"); 
			closeBtn.classList.add("xi-close");
			closeBtn.style="position: absolute; top: 10px; right: 10px; z-index:102; cursor: pointer; font-size: 22px;";
			closeBtn.addEventListener("click", function() {
				layer.close();	
			}, false);
			
			div.appendChild(closeBtn);
			/** 닫기 버늩 추가 E */
			
			const innerDiv = document.createElement("div");
			innerDiv.id = "inner_html";

			div.appendChild(innerDiv);	
			document.body.appendChild(div);
		}
		
		if (url.indexOf("?") == -1) {
			url += "?";
		} else {
			url += "&";
		}
		url += "outline=none";
		axios({
			method : "GET",
			url : url,
		})
		.then((res) => {
			const el = document.querySelector("#layer_popup #inner_html");
			if (el) {
				el.innerHTML = res.data;
			}
			if (typeof callback == 'function') {
				callback();
			}
		})
		.catch((err) => {
			console.error(err);
		});
	},
	/**
		팝업 닫기 
	 */
	close() {
		const layerDim = document.querySelector("#layer_dim");
		const layerPopup = document.querySelector("#layer_popup");
		
		if (layerDim) {
			document.body.removeChild(layerDim);
		}
		
		if (layerPopup) {
			document.body.removeChild(layerPopup);
		}
	}	
};


