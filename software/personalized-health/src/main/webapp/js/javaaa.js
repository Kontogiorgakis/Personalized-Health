let loginPwdStatus = false;
var dateString;

/*PASSWORD JS*/
	function onBlurFunc(){
		correct_passwords();
		safe_password();
	}

	function correct_passwords(){
		//erase the content of div
		document.getElementById("correction").innerHTML = '';
		
		var password=document.getElementById("password").value;
		var passwordRepeat=document.getElementById("password-repeat").value;	


		var counter1=0;
		for(var i=0; i<password.length;i++){
			counter1++;
		}

		document.getElementById("password-repeat").setCustomValidity("");

		if(password==="" || passwordRepeat===""){
			document.getElementById("correction").style.backgroundColor="red";	
			document.getElementById("correction").innerHTML += 'passwords are wrong!'.fontsize(4).fontcolor("black");
			document.getElementById("password-repeat").setCustomValidity("Invalid field.");
			return false;
		}else if(counter1<8){
			document.getElementById("correction").style.backgroundColor="red";	
			document.getElementById("correction").innerHTML += 'passwords are wrong!'.fontsize(4).fontcolor("black");
			document.getElementById("password-repeat").setCustomValidity("Invalid field.");
			return false;
		}else{	
			if(password===passwordRepeat) {
				document.getElementById("correction").style.backgroundColor='rgb(' + 168 + ',' + 225 + ',' + 210 + ')';
				document.getElementById("correction").innerHTML += 'passwords are correct!'.fontsize(4).fontcolor("black");
				document.getElementById("password-repeat").setCustomValidity("");
				return true;
			}else{
				document.getElementById("correction").style.backgroundColor="red";	
				document.getElementById("correction").innerHTML += 'passwords are wrong!'.fontsize(4).fontcolor("black");
				document.getElementById("password-repeat").setCustomValidity("Invalid field.");
				
				return false;
			}
		}

	}


	function safe_password(){
		document.getElementById("safety").innerHTML = '';

		var password=document.getElementById("password").value;
		
		numberCounter=0;
		allCounter=0;
		upToOneCounter=0;

		var counts = {};
		var ch,count,truth=0;

		for(var i=0; i<password.length;i++){
			//for weak 1
			if(password.charAt(i)>=0 && password.charAt(i)<=9){
				numberCounter++;
			}
			
			//for weak 2
			ch = password.charAt(i);
			// Get the count for it, if we have one; we'll get `undefined` if we
			// don't know this character yet
			count = counts[ch];
			// If we have one, store that count plus one; if not, store one
			// We can rely on `count` being falsey if we haven't seen it before,
			// because we never store falsey numbers in the `counts` object.
			counts[ch] = count ? count + 1 : 1;

			allCounter++;
		}

		
		for (ch in counts) {
			if(counts[ch]>=(allCounter/2)){
				truth=1;
			}
			//count how many cahracters are shown more than 1 time
			if(counts[ch]>1){
				upToOneCounter++;
			}
		}


		if(allCounter<8){
			document.getElementById("safety").style.backgroundColor='rgb(' + 255 + ',' + 255 + ',' + 224 + ')';
			document.getElementById("safety").innerHTML += 'not right size'.fontsize(5).fontcolor("black");
		}else{
			//WEAKNESS
			if(numberCounter>= (allCounter/2) || truth===1){
				document.getElementById("safety").style.backgroundColor='rgb(' + 32 + ',' + 178 + ',' + 170 + ')';
				document.getElementById("safety").innerHTML += 'weak'.fontsize(5).fontcolor("black");
			}
			//STRONGNESS
			else if((allCounter-upToOneCounter)>=(allCounter*0.8)){
				document.getElementById("safety").style.backgroundColor='rgb(' + 255 + ',' + 69 + ',' + 0 + ')';
				document.getElementById("safety").innerHTML += 'strong'.fontsize(5).fontcolor("black");
			}
			//MEDIUMNESS
			else{
				document.getElementById("safety").style.backgroundColor='rgb(' + 210 + ',' + 105 + ',' + 30 + ')';
				document.getElementById("safety").innerHTML += 'medium'.fontsize(5).fontcolor("black");
			}
		}
	}


	function changePwdView(){
		let getLoginInput= document.getElementById("password");
		if(loginPwdStatus===false){
			getLoginInput.setAttribute("type","text");
			loginPwdStatus=true;
		}else if (loginPwdStatus===true){
			getLoginInput.setAttribute("type","password");
			loginPwdStatus=false;
		}
	}

/*DOCTOR JS*/
	function getValue(){
		//alert("asdf")
		let selectedValue;
		const rbs =document.querySelectorAll('input[name="type_of_user"]');
        for (const rb of rbs) {
            if (rb.checked) {
                selectedValue = rb.value;
                break;
            }
        }
		if(selectedValue==="doctor"){
				doctor();
		}else{
			user();
		}
	}


	function doctor(){
		var x = document.getElementById("doctype");
		var y = document.getElementById("texting");

		x.style.display = "block";
		y.style.display = "block";
		
		document.getElementById('addressLabel').innerHTML = 'Clinic Αddress';
	}

	function user(){
		var x = document.getElementById("doctype");
		var y = document.getElementById("texting");

		x.style.display = "none";
		y.style.display = "none";
		
		document.getElementById('addressLabel').innerHTML = 'Αddress';
	}

/*AMKA JS*/
	function amkaGo(){
		dateString = document.getElementById("birth-date").value;
		dateString=dateString.split("-").reverse().join("");
		var amka=document.getElementById("amka").value;
		var date=dateString.substring(0,4)+dateString.substring(6,8)
		
		document.getElementById("amka").setCustomValidity("");

		if(amka.length>5){
			if(amka.substring(0,6) != date){
				document.getElementById("amka").setCustomValidity("Invalid field.");
			}
		}
	}

/*CHECKBOX JS*/
	function checkbox(){
		var x = document.getElementById("checking");

		if (x.style.display === "none") {
			x.style.display = "block";
		} else {
			x.style.display = "none";
		}
		
	}


	function initiates(){
		document.getElementById("usernaming").style.display = "none";
		document.getElementById("emailing").style.display = "none";
		document.getElementById("amkaing").style.display = "none";
	}



function signing() {
	//alert("mpika")
	var xhr = new XMLHttpRequest();
	xhr.onload = function () {
		if (xhr.readyState === 4 && xhr.status === 200) {
			document.getElementById("error").innerHTML="Wrong Credentials"
		} else if (xhr.status !== 200) {
			document.getElementById("results").style.display="block";
			document.getElementById("error").innerHTML="USER EXISTS!";
			var mes=xhr.responseText;
			mes.replace('{','');
			mes.replace(',','\n');

			console.log(xhr.responseText);
		}
	};
	var data = $('#logto').serialize();
	console.log(data);
	//var newdata ="&val=2";
	xhr.open('POST', 'Servlet?' + data);
	xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
	xhr.send();
}
