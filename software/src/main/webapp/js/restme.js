/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


function newBloodtest(){
    let myForm = document.getElementById('myForm');
    let formData = new FormData(myForm);
    const data = {};
    formData.forEach((value, key) => (data[key] = value));
    
    $.each(data, function(key, value){
        if (value === "" || value === null){
            delete data[key];
        }
    });
    
    var jsonData=JSON.stringify(data);
    console.log(jsonData);
    
    
    
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            document.getElementById('output').innerHTML="#Success: BloodTest successfully added!"
        } else if (xhr.status !== 200) {
            document.getElementById('output')
            .innerHTML = 'Request failed. Returned status of ' + xhr.status + "<br>"+
            JSON.stringify(xhr.responseText);
        }
    };
    xhr.open('POST', 'http://localhost:8080/Computers_REST_API/computers/eshop/newBloodTest');
    xhr.setRequestHeader("Content-type", "application/json");
    xhr.send(jsonData); 
}

function getAmkaResults(){
    const xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            document.getElementById('output2').innerHTML="#Success:"+xhr.responseText;
            //const obj = JSON.parse(xhr.responseText);
            /*var obj = JSON.parse(xhr.responseText);
            var res = [];
              
            for(var i in obj)
                res.push(obj[i]);
              
            document.getElementById('output2').innerHTML= "Array of values - ["
                            + res + "]";*/
        } else if (xhr.status !== 200) {
            document.getElementById("output2")
            .innerHTML = 'Request failed. Returned status of ' + xhr.status + "<br>"+xhr.responseText;
        }
    };
    var amka=document.getElementById("amka2").value;
    console.log(amka);
    //alert(document.getElementById("from_date").value);
    //alert(document.getElementById("to_date").value);
    
    
    if(document.getElementById("from_date").value==="" || document.getElementById("to_date").value===""){
        //alert("inside");
        xhr.open("GET", "http://localhost:8080/Computers_REST_API/computers/eshop/bloodTestMeasure/"+amka);
        //xhr.open("GET", "http://localhost:8080/Computers_REST_API/computers/eshop/bloodTestMeasure/"+amka+"?fromDate="+from+"?toDate="+to);
    }else{    
        xhr.open("GET", "http://localhost:8080/Computers_REST_API/computers/eshop/bloodTestMeasure/"+amka+"?fromDate="+document.getElementById("from_date").value+"&toDate="+document.getElementById("to_date").value);
        //xhr.open("GET", "http://localhost:8080/Computers_REST_API/computers/eshop/bloodTestMeasure/"+amka);
    }
    xhr.setRequestHeader("Accept", "application/json");
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send(); 
}


function getMeasure(){
    const xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            document.getElementById('output3').innerHTML="#Success:"+xhr.responseText;
            //const obj = JSON.parse(xhr.responseText);
            /*var obj = JSON.parse(xhr.responseText);
            var res = [];
              
            for(var i in obj)
                res.push(obj[i]);
              
            document.getElementById('output2').innerHTML= "Array of values - ["
                            + res + "]";*/
        } else if (xhr.status !== 200) {
            document.getElementById("output3")
            .innerHTML = 'Request failed. Returned status of ' + xhr.status + "<br>"+xhr.responseText;
        }
    };
    var amka=document.getElementById("amka3").value;
    var measure=document.getElementById("measure").value;
    console.log(amka);
    
    xhr.open("GET", "http://localhost:8080/Computers_REST_API/computers/eshop/bloodTestMeasure/"+amka+"/"+measure);
    
    xhr.setRequestHeader("Accept", "application/json");
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send(); 
}

function updateBloodtest(){
    console.log("asdf")
    var amka=document.getElementById("amka").value;
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            var mess=xhr.responseText;
            var lowAmka=mess.substring(mess.indexOf('}') + 1)
            console.log(lowAmka+"----"+amka)
            if(lowAmka!==amka){
                document.getElementById('output4').innerHTML='Request failed <br>This bloodTest doesnt belongs to you!'
            }else{
                document.getElementById('output4').innerHTML='Request succeed. Returned status of ' + xhr.status + "<br>"+JSON.stringify(xhr.responseText);
            }
        } else if (xhr.status !== 200) {
            document.getElementById('output4').innerHTML = 'Request failed. Returned status of ' + xhr.status + "<br>"+JSON.stringify(xhr.responseText);
        }
    };
    var bloodtest_id=document.getElementById("bloodtest_id").value;
    var measure=document.getElementById("measure2").value;
    var value=document.getElementById("value").value;
    xhr.open('PUT', 'http://localhost:8080/Computers_REST_API/computers/eshop/bloodTest/'+bloodtest_id+"/"+measure+"/"+value);
    xhr.setRequestHeader("Content-type", "application/json");
    xhr.send(); 
}

function deleteBloodTest(){
    var amka=document.getElementById("amka").value;
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            var mess=xhr.responseText;
            var lowAmka=mess.substring(mess.indexOf('}') + 1)
            console.log(lowAmka+"----"+amka)
            if(lowAmka!==amka){
                document.getElementById('output5').innerHTML='Request failed <br>This bloodTest doesnt belongs to you!'
            }else{
                document.getElementById('output5').innerHTML='Request succeed. Returned status of ' + xhr.status + "<br>"+JSON.stringify(xhr.responseText);
            }
        } else if (xhr.status !== 200) {
            document.getElementById('output5').innerHTML = 'Request failed. Returned status of ' + xhr.status + "<br>"+JSON.stringify(xhr.responseText);
        }
    };
    var id=document.getElementById("bloodtest_id2").value;
    xhr.open('DELETE', 'http://localhost:8080/Computers_REST_API/computers/eshop/bloodTestDeletion/'+id+"/"+amka);
    xhr.setRequestHeader("Content-type", "application/json");
    xhr.send();
}

function getTreatment(){
    console.log("bcc rackatack")
    var amka=document.getElementById("amka").value;
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        console.log(xhr.responseText);
        if (xhr.readyState === 4 && xhr.status === 200) {
            document.getElementById('outputTreatment').innerHTML = (xhr.responseText)+"<br>";
        } else if (xhr.status !== 200) {
            document.getElementById('outputTreatment').innerHTML = (xhr.responseText)+"<br>";
        }
    };
    //var id=document.getElementById("bloodtest_id2").value;
    var data="&amka="+amka;
    xhr.open('POST', 'GetTreatment?'+data);
    xhr.setRequestHeader("Content-type", "application/json");
    xhr.send();
}

/*function back(){
    $("#rester").load("login.html",function(){
        document.getElementById("username").value="admin999"
        document.getElementById("password").value="admin12*"
        document.getElementById("signBut").click();
        
    });
}*/

function getDoctorDistance(){
    console.log("yo inside");
    var lat=document.getElementById("LAT").value;
    var lon=document.getElementById("LON").value;
    
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        //console.log(xhr.responseText);
        var mes=xhr.responseText 
        var parsedJSON = JSON.parse(mes);
        var lats=[];
        var lons=[];
        var doctors=[];
        for (var i=0;i<parsedJSON.length;i++) {
            lats[i]=parsedJSON[i].lat
            lons[i]=parsedJSON[i].lon
            doctors[i]=parsedJSON[i].lastname;
            //console.log(parsedJSON[i].lat); 
        }
        
        var distanceOf=[]
        //calculate distance
        for (var i=0;i<parsedJSON.length;i++) {
            distanceOf[i]=distance(lat,lats[i],lon,lons[i]);
            //console.log(distanceOf[i]);
        }
        
        distanceOf.sort();
        doctors.sort(function(a, b){  
            return distanceOf.indexOf(a) - distanceOf.indexOf(b);
         });
         
        //var lospou=document.getElementById("outputDistance").innerHTML;
        for (var i=0;i<parsedJSON.length;i++) { 
            document.getElementById("outputDistance").innerHTML=document.getElementById("outputDistance").innerHTML+distanceOf[i]+" km   ----->   "+doctors[i]+"<br>";
        }
         
         
         
        //console.log(obj.country); // Outputs: United States
        
        if (xhr.readyState === 4 && xhr.status === 200) {
            //document.getElementById('outputTreatment').innerHTML = (xhr.responseText)+"<br>";
        } else if (xhr.status !== 200) {
            //document.getElementById('outputTreatment').innerHTML = (xhr.responseText)+"<br>";
        }
    };
    //var id=document.getElementById("bloodtest_id2").value;
    //var data="&amka="+amka;
    xhr.open('GET', 'GetAllDoctors?');
    xhr.setRequestHeader("Content-type", "application/json");
    xhr.send();
    
    
}

function distance(lat1,lat2, lon1, lon2){
   
        // The math module contains a function
        // named toRadians which converts from
        // degrees to radians.
        lon1 =  lon1 * Math.PI / 180;
        lon2 = lon2 * Math.PI / 180;
        lat1 = lat1 * Math.PI / 180;
        lat2 = lat2 * Math.PI / 180;
   
        // Haversine formula
        let dlon = lon2 - lon1;
        let dlat = lat2 - lat1;
        let a = Math.pow(Math.sin(dlat / 2), 2)
                 + Math.cos(lat1) * Math.cos(lat2)
                 * Math.pow(Math.sin(dlon / 2),2);
               
        let c = 2 * Math.asin(Math.sqrt(a));
   
        // Radius of earth in kilometers. Use 3956
        // for miles
        let r = 6371;
   
        // calculate the result
        return(c * r);
    }