
function showPhotoTab(id){
	var d = document;
	for(var i=1;i<=4;i++){
		if(i==id){
			d.getElementById("content"+i).style.display="block";
			d.getElementById("title"+i).className="dorp";
		}
		else{
			d.getElementById("content"+i).style.display="none";
			d.getElementById("title"+i).className="nodorp";
		}
	}
}

function showPhotoTabIdx(id){
	var d = document;
		if(id==1){
			d.getElementById("content1").style.display="block";
			d.getElementById("content2").style.display="none";
			d.getElementById("_sp1").style.color="#005bac";
			d.getElementById("_sp2").style.color="#0080ff";
			d.getElementById("title1").className="dorp";
		}
		else{
			d.getElementById("content1").style.display="none";
			d.getElementById("content2").style.display="block";
			d.getElementById("_sp2").style.color="#005bac";
			d.getElementById("_sp1").style.color="#0080ff";
			d.getElementById("title2").className="nodorp";
		}
}

function yshowPhotoTab(id){
	var d = document;
	for(var i=1;i<=6;i++){
		if(i==id){
			d.getElementById("business_content"+i).style.display="block";
			d.getElementById("ytitle"+i).className="dorp";
		}
		else{
			d.getElementById("business_content"+i).style.display="none";
			d.getElementById("ytitle"+i).className="nodorp";
		}
	}
}

function yyshowPhotoTab(id){
	var d = document;
	for(var i=1;i<=6;i++){
		if(i==id){
			d.getElementById("ybusiness_content"+i).style.display="block";
			d.getElementById("yytitle"+i).className="dorp";
		}
		else{
			d.getElementById("ybusiness_content"+i).style.display="none";
			d.getElementById("yytitle"+i).className="nodorp";
		}
	}
}

function yyyshowPhotoTab(id){
	var d = document;
	for(var i=1;i<=6;i++){
		if(i==id){
			d.getElementById("yybusiness_content"+i).style.display="block";
			d.getElementById("yyytitle"+i).className="dorp";
		}
		else{
			d.getElementById("yybusiness_content"+i).style.display="none";
			d.getElementById("yyytitle"+i).className="nodorp";
		}
	}
}

function ayyshowPhotoTab(id){
	var d = document;
	for(var i=1;i<=6;i++){
		if(i==id){
			d.getElementById("aybusiness_content"+i).style.display="block";
			d.getElementById("ayytitle"+i).className="dorp";
		}
		else{
			d.getElementById("aybusiness_content"+i).style.display="none";
			d.getElementById("ayytitle"+i).className="nodorp";
		}
	}
}