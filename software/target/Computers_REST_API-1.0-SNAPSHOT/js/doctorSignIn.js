function doctorSigning() {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            console.log("maresei h");
            var obj = JSON.parse(xhr.responseText);
            var myKeys = Object.keys(obj);
            console.log(xhr.responseText);
            $("#insertion").load("doctorHTML.html",function(){
            document.getElementById("id").value= obj.doctor_id;
            document.getElementById("id").disabled=true;
            
            /* ID of doctor for new randevou */
            document.getElementById("id_doctor").value= obj.doctor_id;

            document.getElementById("naming").value= obj.username;
            //document.getElementById("naming").disabled=true;

            document.getElementById("e-mail").value=obj.email;
            document.getElementById("passwording").value=obj.password;
            if(obj.specialty === "pathologist"){
                document.getElementById("selectiva").value = "pathologist";
            }else{
                document.getElementById("selectiva").value = "general doctor";
            }
            document.getElementById("info").value = obj.doctor_info;
            document.getElementById("first-name").value=obj.firstname;
            document.getElementById("last-name").value=obj.lastname;
            document.getElementById("birth-date").value=obj.birthdate;

            if(obj.gender==="Male"){
                document.getElementById("male").checked=true;
            }else if(obj.gender==="Female"){
                document.getElementById("female").checked=true;
            }else{
                document.getElementById("other").checked=true;
            }

            document.getElementById("amka").value=obj.amka;

            document.getElementById("country").value=obj.country;
            document.getElementById("city").value=obj.city;
            document.getElementById("address").value=obj.address;
            document.getElementById("lat").value=obj.lat;
            document.getElementById("lon").value=obj.lon;
            document.getElementById("telephone").value=obj.telephone;
            document.getElementById("height").value=obj.height;
            document.getElementById("weight").value=obj.weight;


            if(obj.blooddonor === 1){
                document.getElementById("voluntary-blood-donor").checked=true;
            }else{
                document.getElementById("voluntary-blood-donor2").checked=true;
            }

            document.getElementById("blood-type").value=obj.bloodtype; 
            });
        } else if (xhr.status !== 200) {
            document.getElementById("error").innerHTML = xhr.responseText;
        }
    };
    var username=document.getElementById("username").value;
    var password=document.getElementById("password").value;
    var data="username="+username+"&password="+password;
    console.log(data);
    xhr.open('POST', 'DoctorLogin?' + data);
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xhr.send();
}

function updateCredentials(){
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {

    };
    let myForm = document.getElementById("logto");
    let formData = new FormData(myForm);
    const data = {};
    formData.forEach((value, key) => (data[key] = value));
    xhr.open('POST', 'UpdateDoctorData');
    xhr.setRequestHeader("Content-type", "application/json");
    xhr.send(JSON.stringify(data));
}
