// JavaScript Document
  var img=document.getElementById("pic");
  var imgIndex=0;
  var arr=new Array;
  var timeInterval=2000;
  arr[0]="images/focusPic/tb01.jpg";
  arr[1]="images/focusPic/tb02.jpg";
  arr[2]="images/focusPic/tb03.jpg";
  arr[3]="images/focusPic/tb04.jpg";
  arr[4]="images/focusPic/tb05.jpg";
  /*隔2秒自动换图*/
  var picChange=setInterval(picc,timeInterval);
  function picc(){
	  if(imgIndex==4){
		  imgIndex=0;
	  }else{
		  imgIndex++;
	  }
	  img.src=arr[imgIndex];
	  /*img.setAttribute("src",arr[imgIndex])*/
	  numChange();	  
  }
   /*单击数字按钮自动切换对应图*/	
  function setPicNum(no){
	  imgIndex=no;
	  img.src=arr[imgIndex];
	  numChange();
  }
  /*单击pre按钮，切换到前一张图*/
  function prePic(){
	  if(imgIndex==0){
		  imgIndex=4;
	  }else{
		  imgIndex--;
	  }
	  img.src=arr[imgIndex];
	  numChange();
  }
  /*单击next按钮，切换到后一张图*/
  function nextPic(){
	  if(imgIndex==4){
		  imgIndex=0;
	  }else{
		  imgIndex++;
	  }
	  img.src=arr[imgIndex];
	  numChange()
  }
   /*对应的数字按钮样式发生相应变化*/	  
   /*非当前图片对应的其他数字按钮样式恢复未选中状态*/
  function numChange(){
	  for(var i=0; i<=4; i++){
		if(i!=imgIndex){
			var numObj=document.getElementById("t"+i);
			numObj.style.backgroundColor="#B7B7B7";
		}else{
			var numObj=document.getElementById("t"+i);
	  		numObj.style.backgroundColor="#F40";
		}
	  }
  } 