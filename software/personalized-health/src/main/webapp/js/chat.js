
function ConnectWithUser(){
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            $('#message').remove();
            var data = JSON.parse(xhr.responseText);
            var messages = document.createElement('div');
            messages.id = 'message';
            
            
            document.getElementById("foundmsg").innerHTML = "Connected with User: " + user;
            
            messages.innerHTML = "";
            for (const i in data) {
                if(data[i].sender === "doctor"){
                    messages.innerHTML += "Doctor: " + data[i].message + "<br>"; 
                }else{
                    messages.innerHTML += "User: " + data[i].message + "<br>";
                }
            }
            document.getElementById("chatbox").appendChild(messages);

        }else{
            document.getElementById("foundmsg").innerHTML = "Couldn't find User";
            document.getElementById("chatbox").innerHTML = "";
        }
    };
    let user = document.getElementById("connect_with").value;
    let doctor = document.getElementById("id").value;
    let data = "?doctor_id=" + doctor + "&user_id=" + user;
    xhr.open('GET', 'Chatting' + data);
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send();
}



function ConnectWithDoctor(){
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            $('#message').remove();
            var data = JSON.parse(xhr.responseText);
            var messages = document.createElement('div');
            messages.id = 'message';
            
            
            document.getElementById("foundmsg").innerHTML = "Connected with Doctor: " + doctor;
            
            messages.innerHTML = "";
            for (const i in data) {
                if(data[i].sender === "doctor"){
                    messages.innerHTML += "Doctor: " + data[i].message + "<br>"; 
                }else{
                    messages.innerHTML += "User: " + data[i].message + "<br>";
                }
            }
            document.getElementById("chatbox").appendChild(messages);

        }else{
            document.getElementById("foundmsg").innerHTML = "Couldn't find User";
            document.getElementById("chatbox").innerHTML = "";
        }
    };
    let user = document.getElementById("id").value;
    let doctor = document.getElementById("connect_with").value;
    let data = "?doctor_id=" + doctor + "&user_id=" + user;
    xhr.open('GET', 'ChattingUser' + data);
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send();
}

function sendMessage(){
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            alert("Message Sent! Please Connect again");
        }else{
            alert("Error with message!");
        }
    };
    let user = document.getElementById("connect_with").value;
    let doctor = document.getElementById("id").value;
    let msg = document.getElementById("doctor_msg").value;
    let data = "?doctor_id=" + doctor + "&user_id=" + user + "&message=" + msg;
    alert(data);
    xhr.open('POST', 'Chatting' + data);
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send();
}


function sendMessageUser(){
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            alert("Message Sent! Please Connect again");
        }else{
            alert("Error with message!");
        }
    };
    let user = document.getElementById("id").value;
    let doctor = document.getElementById("connect_with").value;
    let msg = document.getElementById("user_msg").value;
    let data = "?doctor_id=" + doctor + "&user_id=" + user + "&message=" + msg;
    alert(data);
    xhr.open('POST', 'ChattingUser' + data);
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send();
}
