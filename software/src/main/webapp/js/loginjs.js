//let loginPwdStatus = false;



function signing() {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            document.getElementById("error").innerHTML="Wrong Credentials";
        } else if (xhr.status !== 200) {
            console.log("maresei h")
            var mes=xhr.responseText;

            var obj = JSON.parse(mes);
            var myKeys = Object.keys(obj)

           
            if(obj[myKeys[1]]==="admin999"){
                $("#insertion").load("administrator.html");
                //window.location.href="administrator.html";
            }else{
                $("#insertion").load("userHTML.html",function(){
                   //document.getElementById("results").style.display="block";
                document.getElementById("id").value=obj[myKeys[0]];
                document.getElementById("id").disabled=true;

                document.getElementById("naming").value=obj[myKeys[1]];
                //document.getElementById("naming").disabled=true;

                document.getElementById("e-mail").value=obj[myKeys[2]];
                document.getElementById("passwording").value=obj[myKeys[3]];
                document.getElementById("first-name").value=obj[myKeys[4]];
                document.getElementById("last-name").value=obj[myKeys[5]];
                document.getElementById("birth-date").value=obj[myKeys[6]];


                console.log(obj[myKeys[7]]);
                if(obj[myKeys[7]]==="male"){
                    document.getElementById("male").checked=true;
                }else if(obj[myKeys[7]]==="female"){
                    document.getElementById("female").checked=true;
                }else{
                    document.getElementById("other").checked=true;
                }

                document.getElementById("amka").value=obj[myKeys[8]];
                //document.getElementById("amka").disabled=true;

                document.getElementById("country").value=obj[myKeys[9]];
                document.getElementById("city").value=obj[myKeys[10]];
                document.getElementById("address").value=obj[myKeys[11]];
                document.getElementById("lat").value=obj[myKeys[12]];
                document.getElementById("lon").value=obj[myKeys[13]];
                document.getElementById("telephone").value=obj[myKeys[14]];
                document.getElementById("height").value=obj[myKeys[15]];
                document.getElementById("weight").value=obj[myKeys[16]];


                if(obj[myKeys[17]]==="1"){
                    document.getElementById("voluntary-blood-donor").checked=true;
                }else{
                    document.getElementById("voluntary-blood-donor2").checked=true;
                }

                document.getElementById("blood-type").value=obj[myKeys[18]]; 
                });
            }

        }
    };
    //var data = $('#logto').serialize();
    var username=document.getElementById("username").value;
    var password=document.getElementById("password").value;
    var data="&username="+username+"&password="+password;
    var newdata=data+"&val=1";
    console.log(newdata);
    xhr.open('POST', 'Servlet?' + newdata);
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xhr.send();
}



function changePwdView(){
    /*let getLoginInput= document.getElementById("passwording");
    if(loginPwdStatus===false){
        getLoginInput.setAttribute("type","text");
        loginPwdStatus=true;
    }else if (loginPwdStatus===true){
        getLoginInput.setAttribute("type","password");
        loginPwdStatus=false;
    }*/
}

function credentials(){
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {

    };
    var data = $('#logto').serialize();
    console.log(data);
    var newdata=data+"&val=1";
    xhr.open('GET', 'Servlet?' + newdata);
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xhr.send();
}


function email_check(){

    /*var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            //document.getElementById("texting").style.display = "block";
            document.getElementById("emailing").innerHTML = "email already exists!";
            document.getElementById("emailing").style.color = "#ac0202";
            document.getElementById("emailing").style.display = "block";
            $('#ajaxContent').html("Successful Registration. Now please log in!<br> Your Data");
            $("#ajaxContent").html(createTableFromJSON(JSON.parse(xhr.responseText)));
            //  $("#ajaxContent").html("Successful Login");
        } else if (xhr.status !== 200) {
            document.getElementById("emailing").innerHTML = "email is ok!";
            document.getElementById("emailing").style.color = "green";
            document.getElementById("emailing").style.display = "block";
            $("#ajaxContent").html("User not exists");
        }
    };*/


    var data = $('#loginForm').serialize();
    var newdata=data+"&val=2";
    xhr.open('GET', 'Servlet?' + newdata);
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xhr.send();
}


function isLoggedIn(){
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            console.log("Yeia sas");
        } else if (xhr.status !== 200) {
            $("#insertion").load("userHTML.html");
            console.log("unlogged");
        }
    };
    xhr.open('GET', 'Login?');
    xhr.send();
}


function logout(){
    console.log("malaperda")
    /*var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            console.log("Logouted");
            
        } else if (xhr.status !== 200) {
            console.log("NOPE");
        }
    };
    xhr.open('POST', 'Logout?');
    xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
    xhr.send();*/
}

function seedoc(){
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        var mes=xhr.responseText;
        var div=document.getElementById("certified_docs");
        if (xhr.readyState === 4 && xhr.status === 200) {
            document.getElementById("certified_docs").style.border = "1px solid black";
            document.getElementById("certified_docs").style.backgroundColor = "#c0acfc";

            var obj = JSON.parse(mes);
            var myKeys = Object.keys(obj)

            div.innerText+='Certified Doctors: \n\n';
            for (var i = 0; i < myKeys.length; i++){
                var iter = myKeys[i];
                div.innerText+=obj[iter]+'\n';
                //console.log(myKeys[i]);
                //console.log(obj[iter]);
                if(i+1===myKeys.length){
                    div.innerText+='\n-----------------------------------------------------\n';
                }
                if(myKeys[i+1].includes("firstname")){
                    div.innerText+='\n\n';
                }
            }
            console.log(mes);
        } else if (xhr.status !== 200) {
            console.log(mes);
        }
    };
    var data = "&val=4";
    xhr.open('GET', 'Servlet?' + data);
    xhr.send();
}



function getBmi(){
    const data = null;
    var birthdate = document.getElementById("birth-date").value;
    var age=2021-birthdate.substring(0,4);

    var weight=document.getElementById("weight").value;
    var height=document.getElementById("height").value;

    const xhr = new XMLHttpRequest();
    xhr.withCredentials = true;

    xhr.addEventListener("readystatechange", function () {
        if (this.readyState === this.DONE) {
            console.log(this.responseText);


            var obj = JSON.parse(this.responseText);

            document.getElementById("certified_docs").style.border = "1px solid black";
            document.getElementById("certified_docs").style.backgroundColor = "#c0acfc";
            document.getElementById("certified_docs").innerText="\nStatus:\nBmi: "+obj.data.bmi+"\nHealth: "+obj.data.health+"\n\n";
        }
    });

    xhr.open("GET", "https://fitness-calculator.p.rapidapi.com/bmi?age="+age+"&weight="+weight+"&height="+height);
    xhr.setRequestHeader("x-rapidapi-host", "fitness-calculator.p.rapidapi.com");
    xhr.setRequestHeader("x-rapidapi-key", "2856b08a61mshad89a598734d4f3p15303bjsncd1a60ddb412");

    xhr.send(data);
}


function getIdealWeight(){
    const data = null;
    var height=document.getElementById("height").value;
    var gender;
    if(document.getElementById("male").checked){
        gender="male";
    }else if(document.getElementById("female").checked){
        gender="female";
    }else{
        alert("Gender must be male or female!");
        return false;
    }

    const xhr = new XMLHttpRequest();
    xhr.withCredentials = true;

    xhr.addEventListener("readystatechange", function () {
        if (this.readyState === this.DONE) {
            console.log(this.responseText);

            var obj = JSON.parse(this.responseText);

            document.getElementById("certified_docs").style.border = "1px solid black";
            document.getElementById("certified_docs").style.backgroundColor = "#c0acfc";
            document.getElementById("certified_docs").innerText="\nIdeal Weight:\nHamwi: "+obj.data.Hamwi+"\nDevine: "+obj.data.Devine+"\nMiller: "+obj.data.Miller+"\nRobinson: "+obj.data.Robinson+"\n\n";
        }
    });

    xhr.open("GET", "https://fitness-calculator.p.rapidapi.com/idealweight?gender="+gender+"&height="+height);
    xhr.setRequestHeader("x-rapidapi-host", "fitness-calculator.p.rapidapi.com");
    xhr.setRequestHeader("x-rapidapi-key", "2856b08a61mshad89a598734d4f3p15303bjsncd1a60ddb412");

    xhr.send(data);
}

function goToBloodTest(){
    var amka=document.getElementById("amka").value;
    var lat=document.getElementById("lat").value;
    var lon=document.getElementById("lon").value;
    console.log("mpika "+amka);
    $("#userPage").load("resting.html",function(){
        document.getElementById("amka").value=amka;
        //document.getElementById("amka").disabled=true;
        
        document.getElementById("amka2").value=amka;
        //document.getElementById("amka2").disabled=true;
        
        document.getElementById("amka3").value=amka;
        //document.getElementById("amka3").disabled=true;
        
        document.getElementById("LAT").value=lat;
        document.getElementById("LON").value=lon;
    
    });
}