
//global vars
var lat;
var lon;
var mapop = document.getElementById("Map");
var map = new OpenLayers.Map("Map");

/*askisi 2*/
function openStreetMaps(){
    
    var country = document.getElementById("country").value;

    var city = document.getElementById("city").value;
    
    var address=document.getElementById("address").value;
    
    //total address
    var connected=address+" "+city+" "+country;
    

    
    const data = null;

    const xhr = new XMLHttpRequest();
    xhr.withCredentials = true;

    xhr.addEventListener("readystatechange", function () {
        //do something
        if (this.readyState === this.DONE) {
            console.log(this.responseText);
            
            var x = document.getElementById("address_not_found");

            //if its null print error
            if(this.responseText=="{}"){
                x.style.display = "table-cell";
                mapop.style.display="none";
            }else{
                // show map
                x.style.display = "none";
                if(this.responseText.includes("Crete")){
                    
                    mapop.style.display="block";

                    //JSON to javascript
                    var response=this.responseText;
                    response = JSON.parse(response)[0]

                    //get lat and lons
                    lat=response.lat;
                    lon=response.lon;
                    document.getElementById("lat").setAttribute('value',lat);
                    document.getElementById("lon").setAttribute('value',lon);

                    
                    display_map();
                }
                //print error 
                else{
                    x.style.display="table-cell";
                    mapop.style.display="none";
                    x.innerHTML="service is only available in Crete at the moment";
                }
            }
        }
    });

    xhr.open("GET", "https://forward-reverse-geocoding.p.rapidapi.com/v1/search?q="+connected+"&accept-language=en&polygon_threshold=0.0");
    xhr.setRequestHeader("x-rapidapi-host", "forward-reverse-geocoding.p.rapidapi.com");
    xhr.setRequestHeader("x-rapidapi-key", "2856b08a61mshad89a598734d4f3p15303bjsncd1a60ddb412");

    xhr.send(data);
}

function display_map(){
    //MAPPING
    var mapnik = new OpenLayers.Layer.OSM();
    map.addLayer(mapnik);


    //Markers
    var markers = new OpenLayers.Layer.Markers( "Markers" );
    map.addLayer(markers);
    
    //Protos Marker
    var position=setPosition(lat,lon);
    var mar=new OpenLayers.Marker(position);
    markers.addMarker(mar);
    mar.events.register('mousedown', mar, function(evt) { 
    handler(position,'niaouuuu')});

    const zoom= 16;
    map.setCenter(position, zoom);

}


//position
function setPosition(lat, lon){
    var fromProjection = new OpenLayers.Projection("EPSG:4326");   // Transform from WGS 1984
    var toProjection = new OpenLayers.Projection("EPSG:900913"); // to Spherical Mercator Projection
    var position       = new OpenLayers.LonLat(lon, lat).transform( fromProjection, 
    toProjection);
    return position;
}

//hendler
function handler(position, message){
    var popup = new OpenLayers.Popup.FramedCloud("Popup", 
    position, null,
    message, null,
    true // <-- true if we want a close (X) button, false otherwise
    );
    map.addPopup(popup);
}

//autofill locations
function autofill(){
    var x = document.getElementById("address_not_found");
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(showPosition);
        x.style.display="none";
      } else {
        x.style.display="table-cell";
        x.innerHTML="Geolocation is not supported by this browser.";
      }
}


  
function showPosition(position) {
        lat=position.coords.latitude;
        lon=position.coords.longitude;

        document.getElementById("lat").setAttribute('value',lat);
        document.getElementById("lon").setAttribute('value',lon);


        const data = null;

        const xhr = new XMLHttpRequest();
        xhr.withCredentials = true;

        xhr.addEventListener("readystatechange", function () {
            if (this.readyState === this.DONE) {
                
                console.log(this.responseText);
                var city=this.responseText;
                //JSON to javascript
                city = JSON.parse(city);
                console.log(city);
                console.log(city.address);
                console.log(city.address.road);
                
                document.getElementById("country").value=city.address.country;
                document.getElementById("city").value=city.address.city;
                document.getElementById("address").value=city.address.road+" "+city.address.house_number;
                
            }
        });

        xhr.open("GET", "https://forward-reverse-geocoding.p.rapidapi.com/v1/reverse?lat="+lat+"&lon="+lon+"&accept-language=en&polygon_threshold=0.0");
        xhr.setRequestHeader("x-rapidapi-host", "forward-reverse-geocoding.p.rapidapi.com");
        xhr.setRequestHeader("x-rapidapi-key", "2856b08a61mshad89a598734d4f3p15303bjsncd1a60ddb412");

        xhr.send(data);
        mapop.style.display="block";
        display_map();
}

function map_show_off(){
    if (map && map.remove) {
        map.off();
        map.remove();
      }
}

function dissapear_not(){
    document.getElementById("address_not_found").setAttribute("style", "display: none;");
}
function disapear_map(){
    document.getElementById("Map").setAttribute("style", "display: none;");
}