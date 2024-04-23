function createTableFromJSON(data) {
    var html = "<table><tr><th>Category</th><th>Value</th></tr>";
    for (const x in data) {
        var category = x;
        var value = data[x];
        html += "<tr><td>" + category + "</td><td>" + value + "</td></tr>";
    }
    html += "</table>";
    return html;

}


function username_check() {
    var us=document.getElementById("username").value;

    if(us.length<8){
        document.getElementById("usernaming").innerHTML = "username is invalid";
        document.getElementById("usernaming").style.color = "red";
        document.getElementById("usernaming").style.display = "block";
    }else {
        var xhr = new XMLHttpRequest();
        xhr.onload = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                //document.getElementById("texting").style.display = "block";
                document.getElementById("usernaming").innerHTML = "username already exists!";
                document.getElementById("usernaming").style.color = "#ac0202";
                document.getElementById("usernaming").style.display = "block";
                $('#ajaxContent').html("Successful Registration. Now please log in!<br> Your Data");
                $("#ajaxContent").html(createTableFromJSON(JSON.parse(xhr.responseText)));
                //  $("#ajaxContent").html("Successful Login");
            } else if (xhr.status !== 200) {
                document.getElementById("usernaming").innerHTML = "username is ok!";
                document.getElementById("usernaming").style.color = "green";
                document.getElementById("usernaming").style.display = "block";
                $("#ajaxContent").html("User not exists");
            }
        };

        var data = $('#loginForm').serialize();
        var newdata=data+"&val=1";
        xhr.open('GET', 'GetUser?' + newdata);
        xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
        xhr.send();
    }
}

function email_check(){

    var xhr = new XMLHttpRequest();
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
    };


    var data = $('#loginForm').serialize();
    var newdata=data+"&val=2";
    xhr.open('GET', 'GetUser?' + newdata);
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xhr.send();
}


function amka_check(){

    var am=document.getElementById("amka").value;
    if(am.length<11){
        document.getElementById("amkaing").innerHTML = "amka is invalid";
        document.getElementById("amkaing").style.color = "red";
        document.getElementById("amkaing").style.display = "block";
    }else {
        var xhr = new XMLHttpRequest();
        xhr.onload = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                //document.getElementById("texting").style.display = "block";
                document.getElementById("amkaing").innerHTML = "amka already exists!";
                document.getElementById("amkaing").style.color = "#ac0202";
                document.getElementById("amkaing").style.display = "block";
                $('#ajaxContent').html("Successful Registration. Now please log in!<br> Your Data");
                $("#ajaxContent").html(createTableFromJSON(JSON.parse(xhr.responseText)));
                //  $("#ajaxContent").html("Successful Login");
            } else if (xhr.status !== 200) {
                document.getElementById("amkaing").innerHTML = "amka is ok!";
                document.getElementById("amkaing").style.color = "green";
                document.getElementById("amkaing").style.display = "block";
                $("#ajaxContent").html("User not exists");
            }
        };



        var data = $('#loginForm').serialize();
        var newdata=data+"&val=3";
        xhr.open('GET', 'GetUser?' + newdata);
        xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
        xhr.send();
    }
}

function register(){
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        alert(document.querySelector('input[name="type_of_user"]:checked').value)
        if(document.querySelector('input[name="type_of_user"]:checked').value==="user"){
            document.getElementById("successful_reg").innerHTML="Your registration was successful!"
        }else{
            document.getElementById("successful_reg").innerHTML="Your registration was successful but administrator must certify you!"
        }
    };
    var data = $('#loginForm').serialize();
    alert(data);
    xhr.open('POST', 'GetUser?' + data);
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xhr.send();
}

function valid_inputs(){

    var good1=0;
    var good2=0;
    var check=0;

    var lat=document.getElementById("lat").value;
    var lon=document.getElementById("lon").value;
    var username=document.getElementById("username").checkValidity();
    var email=document.getElementById("e-mail").checkValidity();
    var password=document.getElementById("password").checkValidity();
    var con_password=document.getElementById("password-repeat").checkValidity();
    var firstname=document.getElementById("first-name").checkValidity();
    var lastname=document.getElementById("last-name").checkValidity();
    var amka=document.getElementById("amka").checkValidity();
    var city=document.getElementById("city").checkValidity();
    var address=document.getElementById("address").checkValidity();
    var telephone=document.getElementById("telephone").checkValidity();
    var bloodtype=document.getElementById("blood-type").checkValidity();


    //check radios
    if(document.getElementById('male').checked) {
        //Male radio button is checked
    }else if(document.getElementById('female').checked) {
        //Female radio button is checked
    }else if(document.getElementById('other').checked) {
        //Other radio button is checked
    }else{
        good1=1;
    }


    if(document.getElementById('voluntary-blood-donor').checked) {
        //Male radio button is checked
    }else if(document.getElementById('voluntary-blood-donor2').checked) {
        //Female radio button is checked
    }else{
        good2=1;
    }

    if(document.getElementById("scales").checked === false){
        check=1;
    }

    if(username===true && email===true && password===true && con_password===true &&
        firstname===true && lastname===true && amka===true && city===true && address===true &&
        telephone===true && bloodtype===true && good1===0 && good2===0 && check===0 && lat!=null && lon!=null){

        register();
    }else{
        alert("Please fill all the required inputs!");
    }

    /*console.log(username);
    console.log(email);
    console.log(password);
    console.log(con_password);
    console.log(firstname);
    console.log(lastname);
    console.log(amka);
    console.log(city);
    console.log(address);
    console.log(telephone);
    console.log(bloodtype);*/
}
