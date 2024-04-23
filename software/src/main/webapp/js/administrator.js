/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
var duplication=0;
var usersCounter=0;
var docsCounter=0;
var myKeys;
var obj
var arrayOfUsernames=[];
var arrayOfDoctornames=[];

function init(){
    duplication=0;
    document.getElementById("allUsers").style.display="none";
    document.getElementById("allDocs").style.display="none";
}


function userAccess(){
    var x = document.getElementById("allUsers");
    if (x.style.display !== 'block') {
      x.style.display = 'block';
    } else {
      x.style.display = 'none';
    }
    
    
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        var mes=xhr.responseText;
        var div=document.getElementById("allUsers");
        if (xhr.readyState === 4 && xhr.status === 200) { 
            if(duplication===0){
                //duplication=1;
                obj = JSON.parse(mes);
                myKeys = Object.keys(obj)
               


                div.innerHTML+='Users: <br><br>';
                for (var i = 0; i < myKeys.length; i++){
                    var iter = myKeys[i];
                    if(myKeys[i].includes("username")){
                        arrayOfUsernames[usersCounter]=obj[iter];
                        usersCounter++;
                        div.innerHTML+='<br><br>';
                    }
                    div.innerHTML+=obj[iter]+'<br>';
                 

                    if(i+1===myKeys.length){
                        div.innerText+='-----------------------------------------------------';
                    }
                }
                div.innerHTML+='<br>What user do you want to delete?<br>'
                div.innerHTML+="<input type='number' id='userNumber'><br>"
                div.innerHTML+="<button id='clicker' onclick='deleteUser();return false;'>DELETE</button><br><br><br>"
            
                
                // call docs
                doctorAccess();
                //console.log(usersCounter);
                
               
            }
        } else if (xhr.status !== 200) {
            //console.log(mes);
        }
    };
    var data="&val=1";
    xhr.open('POST', 'administrator?' + data);
    xhr.send();
}

function deleteUser(){
    var num=document.getElementById("userNumber").value;
    if(usersCounter===0){
        alert("No users to delete!")
    }else{
        if(num<1 || num>usersCounter){
            alert("Give a number between 1 and "+usersCounter)
        }else{
            console.log(arrayOfUsernames[num-1]);
            var xhr = new XMLHttpRequest();
            xhr.onload = function () {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    alert("#User has been deleted!");
                    var x = document.getElementById("allUsers");
                    x.style.display = 'none';
                    $("#admin").load("login.html")
                } else if (xhr.status !== 200) {
                    alert("Error!");
                }
            };
            var data="&username="+arrayOfUsernames[num-1];
            xhr.open('POST', 'DeleteUserAdmin?' + data);
            xhr.send();
        }
    }
}

function doctorAccess(){
    //duplication=0;
    console.log("insidious");
    var x = document.getElementById("allDocs");
    if (x.style.display !== 'block') {
      x.style.display = 'block';
    }else{
        x.style.display = 'none';
    }    
    
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        x.innerHTML=""
        var mes=xhr.responseText;
        if (xhr.readyState === 4 && xhr.status === 200) { 
            console.log("xefiga")
            if(duplication===0){
                duplication=1;
                obj = JSON.parse(mes);
                myKeys = Object.keys(obj)
               

                    
                x.innerHTML+='Docs: <br><br>';
                for (var i = 0; i < myKeys.length; i++){
                    var iter = myKeys[i];
                    if(myKeys[i].includes("username")){
                        arrayOfDoctornames[docsCounter]=obj[iter];
                        docsCounter++;
                        x.innerHTML+='<br><br>';
                    }
                    x.innerHTML+=obj[iter]+'<br>';
                 

                    if(i+1===myKeys.length){
                        x.innerText+='-----------------------------------------------------';
                    }
                }
                x.innerHTML+='<br>What doctor do you want to delete?<br>'
                x.innerHTML+="<input type='number' id='doctorNumber'><br>"
                x.innerHTML+="<button id='clicker1' onclick='deleteDoctor();return false;'>DELETE</button><br>"
                x.innerHTML+="---------------------------------------------------"
                x.innerHTML+='<br>What doctor do you want to certify?<br>'
                x.innerHTML+="<input type='number' id='doctorCertify'><br>"
                x.innerHTML+="<button id='clicker2' onclick='certifyDoctor();return false;'>CERTIFY</button><br><br><br>"
            
                
                
                //console.log(usersCounter);
                
               
            }
        } else if (xhr.status !== 200) {
            //alert
        }
    };
    xhr.open('GET', 'administrator?');
    xhr.send();
}

function deleteDoctor(){
    var num=document.getElementById("doctorNumber").value;
    if(docsCounter===0){
        alert("No users to delete!")
    }else{
        if(num<1 || num>docsCounter){
            alert("Give a number between 1 and "+docsCounter)
        }else{
            console.log(arrayOfDoctornames[num-1]);
            var xhr = new XMLHttpRequest();
            xhr.onload = function () {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    alert("#Doctor has been deleted!");
                    var x = document.getElementById("allDocs");
                    x.style.display = 'none';
                    $("#admin").load("login.html")
                } else if (xhr.status !== 200) {
                    alert("Error!");
                }
            };
            var data="&username="+arrayOfDoctornames[num-1];
            xhr.open('POST', 'DeleteDoctorAdmin?' + data);
            xhr.send();
        }
    }
}

function certifyDoctor(){
    var num=document.getElementById("doctorCertify").value;
    if(docsCounter===0){
        alert("No users to delete!")
    }else{
        if(num<1 || num>docsCounter){
            alert("Give a number between 1 and "+docsCounter)
        }else{
            console.log(arrayOfDoctornames[num-1]);
            var xhr = new XMLHttpRequest();
            xhr.onload = function () {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    alert("#Doctor has been certified!");
                    $("#admin").load("login.html")
                } else if (xhr.status !== 200) {
                    //console.log("already cert")
                   // alert("Doctor Already Certified!");
                }
            };
            var data="&username="+arrayOfDoctornames[num-1];
            alert(data)
            xhr.open('POST', 'CertifyDoctorAdmin?' + data);
            xhr.send();
        }
    }
}
