function appendParam(key, value){
       let url = new URL(window.location);
      	let queryString = window.location.href;
      	if(queryString.indexOf(key+'='+value) > 0) {
        	   let params = removeParam(url.search, key, value);
            	window.location.href = url.origin + params;
        } else {
        	    url.searchParams.append(key, value);
                window.location.href = url;
        }
  }
function removeParam(sourceURL, key, value) {
	var params = sourceURL.replace("?", "").split("&");
	let removeParam = key + "=" + value;
	params = params.filter((param) => param !== removeParam);
	return "/?" + params.join("&");
}

function sortOrder(key,value){
     let url = new URL(window.location);
      url.searchParams.set(key, value);
        window.location.href = url;
}

function filterByDate(){
    startDate=document.getElementById("startDate").value;
    endDate=document.getElementById("endDate").value;
     value=startDate+","+endDate;
     let url = new URL(window.location);
     url.searchParams.append("date", value);
     window.location.href = url;
}
