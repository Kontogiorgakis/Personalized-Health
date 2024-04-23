function AddRandevouz(){
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            alert("The randevouz was successfully added. " + xhr.responseText);
        }else{
            alert(xhr.responseText);
        }
    };
    let myForm = document.getElementById("new_randevou");
    let formData = new FormData(myForm);
    const data = {};
    formData.forEach((value, key) => (data[key] = value));
    xhr.open('POST', 'RandevouzServlet');
    xhr.setRequestHeader("Content-type", "application/json");
    xhr.send(JSON.stringify(data));
}

function GetRandevouz(){
    var xhr = new XMLHttpRequest();
    xhr.onload = function(){
        if(xhr.readyState === 4 && xhr.status === 200){
            var data = JSON.parse(xhr.responseText);
            document.getElementById("get_randevouz").innerHTML = "<table class='tab' id='randtab'><tr><th>Randevou id</th><th>User id</th><th>Date</th><th>Price</th><th>Status</th></tr></table>";
            for (const i in data) {
                $('#randtab').append("<tr>"
                + "<td>" + data[i].randevouz_id + "</td>"
                + "<td>" + data[i].user_id + "</td>"
                + "<td>" + data[i].date_time + "</td>"
                + "<td>" + data[i].price + "</td>"
                + "<td>" + data[i].status + "</td>"
                + "</tr>"                
                );
            }
        }else{
            alert(xhr.responseText);
            document.getElementById("get_randevouz").innerHTML = "";
        }
    };
    let date = document.getElementById("randevou_date").value;
    let id = document.getElementById("id").value;
    let data = "randevou_date=" + date + "&doctor_id=" + id;
    console.log(data);
    xhr.open("GET","RandevouzServlet?" + data);
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send();
}

function CancelRandevouz(){
    var xhr = new XMLHttpRequest();
    xhr.onload = function(){
        if(xhr.readyState === 4 && xhr.status === 200){
            alert("Randevou is Cancelled");
        }
    };
    let data = "randevouz_id=" + document.getElementById("randevouz_id").value;
    console.log(data);
    xhr.open("GET", "CancelRandevouz?" + data);
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send();
}

function DoneRandevouz(){
    var xhr = new XMLHttpRequest();
    xhr.onload = function(){
        if(xhr.readyState === 4 && xhr.status === 200){
            alert(xhr.responseText);
            document.getElementById("getBloodtest").style.display = "block";
            var data = JSON.parse(xhr.responseText);
            document.getElementById("getBloodtest").innerHTML = "Bloodtest Results <br> <table class='tab'><tr><th>Bloodtest ID</th><th>Amka</th><th>Date</th><th>Medical Center</th>"
                + "<th>Vitamin D3</th><th>Vitamin D3 Level</th><th>Vitamin B12</th><th>Vitamin B12 Level</th><th>Cholesterol</th><th>CholesterolLevel</th><th>Blood Sugar</th>"
                + "<th>Blood Sugar Level</th><th>Iron</th><th>Iron Level</th></tr>"
                + "<tr>"
                + "    <td>" + data.bloodtest_id + "</td>"
                + "    <td>" + data.amka + "</td>"
                + "    <td>" + data.test_date + "</td>"
                + "    <td>" + data.medical_center + "</td>"
                + "    <td>" + data.vitamin_d3 + "</td>"
                + "    <td>" + data.vitamin_d3_level + "</td>"
                + "    <td>" + data.vitamin_b12 + "</td>"
                + "    <td>" + data.vitamin_b12_level + "</td>"
                + "    <td>" + data.cholesterol + "</td>"
                + "    <td>" + data.cholesterol_level + "</td>"
                + "    <td>" + data.blood_sugar + "</td>"
                + "    <td>" + data.blood_sugar_level + "</td>"
                + "</tr>";             
            let doc_id = document.getElementById("id").value;
            document.getElementById("doctor_id").value = doc_id; //Set Doctor's Id automatically
            document.getElementById("bloodtest_id").value = data.bloodtest_id; //Set Bloodtest Id automatically
            document.getElementById("createTreatment").style.display = "block";
        }
    };
    let data = "randevouz_id=" + document.getElementById("randevouz_id_done").value;
    console.log(data);
    xhr.open("GET", "DoneRandevouz?" + data);
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send();
}

function UpdateRandevouz(){
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            alert(xhr.responseText);
        }
    };
    let myForm = document.getElementById("update_randevou");
    let formData = new FormData(myForm);
    const data = {};
    formData.forEach((value, key) => (data[key] = value));
    xhr.open('POST', 'UpdateRandevouz');
    xhr.setRequestHeader("Content-type", "application/json");
    xhr.send(JSON.stringify(data));
}

function createPDF() {
    var sTable = document.getElementById('get_randevouz').innerHTML;
    var win = window.open('', '', 'height=600,width=600');
    win.document.write('<html><head>');
    win.document.write('<title>Randevou Data</title>');
    win.document.write('</head>');
    win.document.write('<body>');
    win.document.write(sTable);
    win.document.write('</body></html>');
    win.document.close();
    win.print();
}
